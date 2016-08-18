package controllers

import javax.inject.{Inject, Singleton}

import models.daos.AbstractBaseDAO
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
class PlayListController @Inject()(playlistDAO : AbstractBaseDAO[PlaylistTable,Playlist],
                                   playlistHasSongDAO : AbstractBaseDAO[PlaylistHasSongTable, PlaylistHasSong],
                                   genreDAO:AbstractBaseDAO[GenreTable, Genre]) (implicit ec: ExecutionContext) extends Controller {

  def playlist() = Action.async{ implicit request =>
    genreDAO.getAllRows.flatMap(genres => {
      playlistDAO.getAllRows.map(pl =>  Ok(views.html.playlist(genres, pl)))
    })
  }

  def selectPlaylist(id: Long) = Action{ implicit request =>
    // TODO
    Ok(views.html.index())
  }

  def createPlaylist() = Action.async{ implicit request =>
    val name: String = request.body.asFormUrlEncoded.get("name")(0)
    val genre: Long = request.body.asFormUrlEncoded.get("genre")(0).toLong
    playlistDAO.getAllRows().map(s => {
      playlistDAO.insert(Playlist(0, name, genre)).map{
        p => println("inserte ",p)
      }
      Ok(views.html.index())
    })
  }

}
