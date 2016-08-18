package models.daos

import models.entities.{Songs}
import models.persistence.SlickTables
import models.persistence.SlickTables.SongsTable

import scala.concurrent.Future

/**
  * Created by fmosso on 18-08-16.
  */
object SongsDAO extends BaseDAO[SongsTable,Songs] {
  import dbConfig.driver.api._
  override protected val tableQ: dbConfig.driver.api.TableQuery[SongsTable] =  SlickTables.songsTableQ
  val genreTableQ = SlickTables.genreTableQ



  def getSongsWithGenre() : Future[Seq[(String, String, String, String, String, Long)]] = {
    val query  = for{
      songs <- tableQ
      genres <- genreTableQ if songs.genre === genres.id
    } yield (songs.title, songs.artist ,songs.album, songs.route, genres.name, songs.released)
    db.run(query.result)
  }

}
