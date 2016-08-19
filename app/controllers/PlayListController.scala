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
    val genre: Long = request.body.asFormUrlEncoded.get("genre")(0).toLong
    PlaylistDAO.getAllRows().map(s => {
      PlaylistDAO.insert(Playlist(0, name, genre)).map{
        p => println("inserte ",p)
      }
      Ok(views.html.dummy())
    })
  }


  def showPlaylist(id :Long) = Action.async{ implicit request =>
    PlaylistHasSongDAO.getSongsofPlaylist(id).map{
      songs =>{
        val songList = for (song <- songs) yield SongsWithGenre(song._1,song._2,song._3,song._4,song._5,song._6,song._7)
        Ok(views.html.songsPlaylist2(songList))
      }
    }
  }



  def addSongtoPlaylist(idSong : Long, idPlayList : Long) = Action.async{ implicit request =>
    PlaylistHasSongDAO.insert(PlaylistHasSong(0,idPlayList,idSong)).map{
      r => Ok(views.html.dummy())
    }
  }

}
