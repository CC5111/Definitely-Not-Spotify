package models.daos

import models.entities.{Genre, Songs}
import models.persistence.SlickTables
import models.persistence.SlickTables.{GenreTable, SongsTable}

import scala.concurrent.Future

/**
  * Created by fmosso on 18-08-16.
  */
object GenreDAO extends BaseDAO[GenreTable,Genre] {
  import dbConfig.driver.api._
  override protected val tableQ: dbConfig.driver.api.TableQuery[GenreTable] =  SlickTables.genreTableQ
  val genreTableQ = SlickTables.genreTableQ


}
