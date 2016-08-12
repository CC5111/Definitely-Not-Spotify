package models.entities

case class PlaylistHasSong(id: Long, playlist: Long, song: Long) extends BaseEntity