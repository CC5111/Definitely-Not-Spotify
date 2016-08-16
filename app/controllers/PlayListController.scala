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
    genreDAO.getAllRows.map(s => Ok(views.html.playlist(s.toList)))
  }

  def selectPlaylist(id: Long) = Action{ implicit request =>
    // TODO
    Ok(views.html.index())
  }

  def createPlaylist() = Action{ implicit request =>
    val name = request.body.asFormUrlEncoded.get("name")(0)
    val genre = request.body.asFormUrlEncoded.get("genre")(0)
    println(name)
    println(genre)
    Ok(views.html.index())
  }

}
