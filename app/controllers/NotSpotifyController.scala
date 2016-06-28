package controllers

/**
  * Created by fmosso on 28-06-16.
  */
import javax.inject._
import models.daos.{AbstractBaseDAO, BaseDAO}
import models.entities.Supplier
import models.persistence.SlickTables.SuppliersTable
import play.api.libs.json.{Json, Writes}
import play.api.mvc._
import scala.concurrent.{Future, ExecutionContext}

import models.entities._

import play.api.data._
import play.api.data.Forms._
import play.api.mvc._
import scala.concurrent.{Future, ExecutionContext}
import play.api.data._
import play.api.data.Forms._


import play.api.i18n.Messages.Implicits._
import play.api.Play.current

@Singleton
class NotSpotifyController @Inject()()(implicit ec: ExecutionContext) extends Controller {

  def root() = Action{ implicit request =>
    Ok(views.html.index())
  }
}
