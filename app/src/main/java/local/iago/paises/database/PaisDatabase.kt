package local.iago.paises.database

import androidx.room.Database
import androidx.room.RoomDatabase
import local.iago.paises.model.Pais

@Database(entities = [Pais::class], version = 1)
// @TypeConverters(PaisConverters::class)
abstract class PaisDatabase : RoomDatabase() {
    abstract fun paisDao() : PaisDao // esta función retorna la interfaz PaisDao antes creada
} // tendríamos así la configuración más básica para crear una base de datos con Room.
// lo que sigue es registrar dicha base de datos y que se inicialice dentro de la aplicación,
// para eso vamos a crear una clase más, que llamaremos PaisApplication