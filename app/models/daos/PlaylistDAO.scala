package models.daos

import models.daos.SongsDAO._
import models.entities.{Playlist, Songs}
import models.persistence.SlickTables
import models.persistence.SlickTables.{PlaylistTable, SongsTable}

import scala.concurrent.Future

/**
  * Created by fmosso on 18-08-16.
  */
object PlaylistDAO extends BaseDAO[PlaylistTable,Playlist] {

  import dbConfig.driver.api._

  override protected val tableQ: dbConfig.driver.api.TableQuery[PlaylistTable] = SlickTables.playlistTableQ
  val playListhasSongTableQ = SlickTables.genreTableQ


}