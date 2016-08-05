package controllers

/**
  * Created by fmosso on 28-06-16.
  */

import java.io.InputStream
import java.nio.file.Paths
import javafx.scene.media.Media
import javax.inject._
import models.daos.{AbstractBaseDAO, BaseDAO}
import models.entities.Supplier
import models.persistence.SlickTables.{SongsTable, SuppliersTable}
import play.api.libs.json.{Json, Writes}
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
class NotSpotifyController @Inject()(songsDAO : AbstractBaseDAO[SongsTable,Songs])(implicit ec: ExecutionContext) extends Controller {

  def root() = Action{ implicit request =>
    Ok(views.html.index())
  }

  def showSongs() =
   Action.async{ implicit request =>
   songsDAO.findById(1).map(s => Ok(views.html.reproducesong(s.get.route)))
  }

}
