package models.persistence


import models.entities.{PlaylistHasSong, Playlist, Songs, Supplier}
import play.api.db.slick.HasDatabaseConfig
import play.api.Play
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfig}
import slick.driver.JdbcProfile

import scala.reflect.internal.util.NoPosition

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
    def genre = column[Long]("GENRE")
    def released = column[Long]("RELEASED")
    def * = (id, title, artist,album,route,genre,released) <> (Songs.tupled, Songs.unapply)
  }

  class PlaylistTable(tag: Tag) extends BaseTable[Playlist](tag, "PLAYLIST") {
    def name = column[String]("NAME")
    def genre = column[Int]("GENRE")
    def * = (id, name, genre) <> (Playlist.tupled, Playlist.unapply)
  }

  class PlaylistHasSongTable(tag: Tag) extends BaseTable[PlaylistHasSong](tag, "PLAYLISTHASSONG") {
    def playlist = column[Long]("PLAYLIST")
    def song = column[Long]("SONG")
    def * = (id, playlist, song) <> (PlaylistHasSong.tupled, PlaylistHasSong.unapply)
  }

  val suppliersTableQ : TableQuery[SuppliersTable] = TableQuery[SuppliersTable]
  val songsTableQ : TableQuery[SongsTable] = TableQuery[SongsTable]
  val playlistTableQ : TableQuery[PlaylistTable] = TableQuery[PlaylistTable]
  val playlistHasSongTableQ : TableQuery[PlaylistHasSongTable] = TableQuery[PlaylistHasSongTable]

}
