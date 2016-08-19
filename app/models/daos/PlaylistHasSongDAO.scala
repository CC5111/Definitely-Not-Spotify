package models.daos

import models.daos.PlaylistDAO._
import models.entities.{PlaylistHasSong}
import models.persistence.SlickTables
import models.persistence.SlickTables.{PlaylistHasSongTable}

import scala.concurrent.Future

/**
  * Created by fmosso on 18-08-16.
  */
object PlaylistHasSongDAO extends BaseDAO[PlaylistHasSongTable,PlaylistHasSong] {
  import dbConfig.driver.api._
  override protected val tableQ: dbConfig.driver.api.TableQuery[PlaylistHasSongTable] =  SlickTables.playlistHasSongTableQ


  val SongTableQ = SlickTables.songsTableQ
  val GenreTableQ = SlickTables.genreTableQ


  def getSongsofPlaylist(IdPlaylist : Long) = {
    val query  = for{
      songsOfPlaylist  <- tableQ  if  songsOfPlaylist.playlist === IdPlaylist
      songs <- SongTableQ if  songs.id === songsOfPlaylist.song
      genres <- GenreTableQ if songs.genre === genres.id
    } yield (songs.id, songs.title, songs.artist ,songs.album, songs.route, genres.name, songs.released,songsOfPlaylist.id )
    db.run(query.result)
  }
}
