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

  def searchSong(keyWord : String) = Action.async{implicit request =>
    SongsDAO.getSongsWithGenre().map( songs => {
      val songList = for (song <- songs) yield SongsWithGenre(song._1,song._2,song._3,song._4,song._5,song._6, song._7)
      val songFinded: Seq[SongsWithGenre] = for {
        song <- songList if isSimilar(song.title, keyWord) || isSimilar(song.album , keyWord) || isSimilar(song.artist , keyWord) || isSimilar(song.genre, keyWord) || song.released == getLong(keyWord)
      } yield song
      Ok(views.html.index(songFinded))})
  }



  private def isSimilar(s1 :String, s2:String): Boolean= {
    def max(s1 :String, s2:String) : String = if (s1.length > s2.length) s1 else s2
    def min(s1 :String, s2:String) : String = if (s1.length > s2.length) s2 else s1
    val longer :String = max(s1,s2)
    val shorter :String = min(s1,s2)
    if (longer.length == 0) true
    else (longer.length - Levenshtein.distance(longer, shorter)) / longer.length.toDouble >= 0.75
  }


  // Example implementation of the Levenshtein Edit Distance
  // See http://rosettacode.org/wiki/Levenshtein_distance#Java
  private object Levenshtein {
    import scala.math._
    def minimum(i1: Int, i2: Int, i3: Int)=min(min(i1, i2), i3)
    def distance(s1:String, s2:String)={
      val dist=Array.tabulate(s2.length+1, s1.length+1){(j,i)=>if(j==0) i else if (i==0) j else 0}

      for(j<-1 to s2.length; i<-1 to s1.length)
        dist(j)(i)=if(s2(j-1)==s1(i-1)) dist(j-1)(i-1)
        else minimum(dist(j-1)(i)+1, dist(j)(i-1)+1, dist(j-1)(i-1)+1)

      dist(s2.length)(s1.length)
    }
  }

  private def getLong(s: String): Long = {
    def toLong(s: String): Option[Long] = {
      try {
        Some(s.toLong)
      } catch {
        case e: Exception => None
      }
    }
    toLong(s) match {
      case Some(v) => v
      case None => -1
    }
  }

}