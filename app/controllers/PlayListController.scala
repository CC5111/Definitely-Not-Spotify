package controllers

import javax.inject.{Inject, Singleton}

import models.daos.{GenreDAO, PlaylistDAO, PlaylistHasSongDAO, AbstractBaseDAO}
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

  def selectPlaylist(id: Long) = Action{ implicit request =>
    // TODO
    Ok(views.html.dummy())
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

  def aPlaylist() = Action.async{ implicit request =>
    PlaylistHasSongDAO.getSongsofPlaylist(2).map{
      songs =>{
        val songList = for (song <- songs) yield SongsWithGenre(song._1,song._2,song._3,song._4,song._5,song._6)
        Ok(views.html.songsPlaylist2(songList))
      }
    }
  }

  def addSongtoPlaylist(idSong : Long, idPlayList : Long) = {
    PlaylistHasSongDAO.insert(PlaylistHasSong(0,idSong,idPlayList))
  }

}
