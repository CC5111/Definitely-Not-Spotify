package models.entities

case class Songs(id: Long, title: String, artist: String, album: String, route: String, genre: Long, released: Long) extends BaseEntity

case class SongsWithGenre(id: Long, title: String, artist: String, album: String, route: String, genre: String, released: Long)
