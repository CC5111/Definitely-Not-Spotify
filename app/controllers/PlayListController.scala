package controllers

import javax.inject.{Inject, Singleton}

import models.daos.{SongsDAO, GenreDAO, PlaylistDAO, PlaylistHasSongDAO, AbstractBaseDAO}
import models.persistence.SlickTables.{GenreTable, PlaylistHasSongTable, PlaylistTable, SongsTable}

import play.api.mvc._
import slick.jdbc.meta.MTable
import slick.lifted.TableQuery
import scala.concurrent._
import scala.util.{Success, Failure}
import models.entities._

import play.api.data._
import play.api.data.Forms._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._


import play.api.i18n.Messages.Implicits._
import play.api.Play.current
import scala.concurrent._
import scala.util.{Failure, Success}
import models.entities.{Playlist, _}

import scala.concurrent.duration._

@Singleton
class PlayListController @Inject()() (implicit ec: ExecutionContext) extends Controller {

  def playlist() = Action.async{ implicit request =>
    GenreDAO.getAllRows.flatMap(genres => {
      PlaylistDAO.getAllRows.map(pl =>  Ok(views.html.playlist(genres, pl)))
    })
  }

  def getPlaylistToAdd(song: Long) = Action.async {implicit request =>
    PlaylistDAO.getAllRows().map(playlists => {
      val listString = for{
        pl <- playlists
      } yield ("<li><a onclick='addSongToList("+song+","+pl.id+");'>"+ pl.name + "</a></li>\n")
      Ok(views.html.ajaxresponse(listString.fold("")((x: String, y: String) => x + y )))
    })
  }

  def createPlaylist() = Action.async{ implicit request =>
    val name: String = request.body.asFormUrlEncoded.get("name")(0)
    SongsDAO.getSongsWithGenre().map( songs => {
      val songList = for (song <- songs) yield SongsWithGenre(song._1,song._2,song._3,song._4,song._5,song._6, song._7)
      PlaylistDAO.insert(Playlist(0, name, 4)).map{
        p => Unit
      }
      Ok(views.html.index(songList, "All songs"))
    })
  }


  def showPlaylist(id :Long) = Action.async{ implicit request =>
    PlaylistHasSongDAO.getSongsofPlaylist(id).flatMap{
      songs =>{
        val songList = for (song <- songs) yield SongsWithGenreAndPL(song._1,song._2,song._3,song._4,song._5,song._6,song._7, song._8, song._9)
        PlaylistDAO.findById(id).map(
          pl =>
            Ok(views.html.songsPlaylist2(songList, pl.get.name))
        )
      }
    }
  }

  def addSongtoPlaylist(idSong : Long, idPlayList : Long) = Action.async{ implicit request =>
    PlaylistHasSongDAO.insert(PlaylistHasSong(0,idPlayList,idSong)).map{
      r => Ok(views.html.dummy())
    }
  }

  def getListInMenu() = Action.async{ implicit request =>
    PlaylistDAO.getAllRows().map(playlists => {
      val listString = for{
        pl <- playlists
      } yield ("<a href='/playlist/"+pl.id+"'>"+pl.name+"</a>\n")
      Ok(views.html.ajaxresponse(listString.fold("<div><form method=\"POST\" id=\"new\" action=\"./createplaylist\"><input type=\"text\" style=\"background:#000000;color : #ffffff; width: 90%;\" name=\"name\" placeholder=\"New List\"></div>")((x: String, y: String) => x + y )))
    })
  }

  def removeFromPlaylist(id: Long) = Action.async{ implicit request =>
    println("deleting "+id)
    PlaylistHasSongDAO.deleteById(id).map{
      r => Ok(views.html.dummy())
    }
  }
}
