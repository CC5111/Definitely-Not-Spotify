package models.persistence


import models.entities.{Songs, Supplier}
import play.api.Play
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfig}
import slick.driver.JdbcProfile

/**
  * The companion object.
  */
object SlickTables extends HasDatabaseConfig[JdbcProfile] {

  protected lazy val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
  import dbConfig.driver.api._

  abstract class BaseTable[T](tag: Tag, name: String) extends Table[T](tag, name) {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
  }

  case class SimpleSupplier(name: String, desc: String)

  class SuppliersTable(tag: Tag) extends BaseTable[Supplier](tag, "suppliers") {
    def name = column[String]("name")
    def desc = column[String]("desc")
    def * = (id, name, desc) <> (Supplier.tupled, Supplier.unapply)
  }

  class SongsTable(tag: Tag) extends BaseTable[Songs](tag, "SONGS") {
    def title = column[String]("TITLE")
    def artist = column[String]("ARTIST")
    def album = column[String]("ALBUM")
    def route = column[String]("ROUTE")
    def * = (id, title, artist,album,route) <> (Songs.tupled, Songs.unapply)
  }


  val suppliersTableQ : TableQuery[SuppliersTable] = TableQuery[SuppliersTable]
  val songsTableQ : TableQuery[SongsTable] = TableQuery[SongsTable]

}
