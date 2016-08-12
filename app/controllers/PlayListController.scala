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
    genreDAO.findById(1).map(s => Ok(views.html.playlist(s.get.name)))
  }

  def selectPlaylist(id: Long) = Action{ implicit request =>
    // TODO
    Ok(views.html.index())
  }

  def createPlaylist() = Action{ implicit request =>
    // TODO
    Ok(views.html.index())
  }

}
