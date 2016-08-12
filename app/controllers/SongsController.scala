package controllers


import javax.inject.{Inject, Singleton}

import models.daos.AbstractBaseDAO
import models.persistence.SlickTables.{SongsTable}
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
class SongsController @Inject()(songsDAO : AbstractBaseDAO[SongsTable,Songs]) (implicit ec: ExecutionContext) extends Controller {

  def reproduceSong() = Action.async{ implicit request =>
    songsDAO.getAllRows().map (s => print (s) )
    songsDAO.findById(1).map(s => Ok(views.html.reproducesong(s.get.route)))
  }

  def showSong() = Action.async{ implicit request =>
    songsDAO.findById(1).map(s => Ok(views.html.reproducesong(s.get.route)))
  }


}