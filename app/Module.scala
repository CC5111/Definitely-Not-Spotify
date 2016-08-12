import com.google.inject.name.Names
import com.google.inject.{Provides, AbstractModule}
import java.time.Clock

import models.daos.{AbstractBaseDAO, BaseDAO}
import models.entities._
import models.persistence.SlickTables
import models.persistence.SlickTables._


/**
 * This class is a Guice module that tells Guice how to bind several
 * different types. This Guice module is created when the Play
 * application starts.

 * Play will automatically use any class called `Module` that is in
 * the root package. You can create modules in other locations by
 * adding `play.modules.enabled` settings to the `application.conf`
 * configuration file.
 */
class Module extends AbstractModule {

  override def configure() = {
    // Use the system clock as the default implementation of Clock
    bind(classOf[Clock]).toInstance(Clock.systemDefaultZone)
  }

  @Provides
  def provideSuppliersDAO : AbstractBaseDAO[SuppliersTable,Supplier] = new BaseDAO[SuppliersTable,Supplier]{
    override protected val tableQ: dbConfig.driver.api.TableQuery[SuppliersTable] = SlickTables.suppliersTableQ
  }

  @Provides
  def provideSongsDAO : AbstractBaseDAO[SongsTable,Songs] = new BaseDAO[SongsTable,Songs]{
    override protected val tableQ: dbConfig.driver.api.TableQuery[SongsTable] = SlickTables.songsTableQ
  }

  @Provides
  def providePlaylistDAO : AbstractBaseDAO[PlaylistTable,Playlist] = new BaseDAO[PlaylistTable,Playlist]{
    override protected val tableQ: dbConfig.driver.api.TableQuery[PlaylistTable] = SlickTables.playlistTableQ
  }

  @Provides
  def providePlaylistHasSongDAO : AbstractBaseDAO[PlaylistHasSongTable,PlaylistHasSong] = new BaseDAO[PlaylistHasSongTable,PlaylistHasSong]{
    override protected val tableQ: dbConfig.driver.api.TableQuery[PlaylistHasSongTable] = SlickTables.playlistHasSongTableQ
  }
  @Provides
  def provideGenreDAO : AbstractBaseDAO[GenreTable,Genre] = new BaseDAO[GenreTable,Genre]{
    override protected val tableQ: dbConfig.driver.api.TableQuery[GenreTable] = SlickTables.genreTableQ
  }


}



