package controllers


import javax.inject.{Inject, Singleton}

import models.daos.AbstractBaseDAO
import models.daos.SongsDAO
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

import scala.concurrent.duration._

@Singleton
class SongsController @Inject()() (implicit ec: ExecutionContext) extends Controller {



  def reproduceSong(id: Long) = Action.async{ implicit request =>
    SongsDAO.findById(id).map(s => Ok(views.html.reproducesong(s.get.route)))
  }

  def getSongName(id: Long) = Action.async{ implicit request =>
    SongsDAO.findById(id).map(s => Ok(views.html.ajaxresponse(s.get.title + " - " + s.get.artist)))
  }

  def showSongs() = Action.async{implicit request =>
    SongsDAO.getSongsWithGenre().map( songs => {
      val songList = for (song <- songs) yield SongsWithGenre(song._1,song._2,song._3,song._4,song._5,song._6, song._7)
      Ok(views.html.index(songList))})
  }


}