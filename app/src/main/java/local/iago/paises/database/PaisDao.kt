package local.iago.paises.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import local.iago.paises.model.Pais

// con profe no es solamente una interfaz, sino que son dos y por medio del camino más cosas (JuegoDbRepository y GameDatabase).
@Dao
interface PaisDao { // https://www.youtube.com/watch?v=uX6BKcBYcfU este tmb mete los @Query y @Insert aquí ya directamente en la interfaz al igual que el del curso de ANT
    // Aquí vamos a definir todas las consultas:
    @Query("SELECT * FROM PaisEntity")
    fun getAll() : Flow<List<Pais>> // MutableList<Pais>
    // Usamos Flow para observar cambios

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPais(pais: Pais)

    @Query("SELECT * FROM PaisEntity where idPais = :idPais")
    fun getPaisByIdPais(idPais: Int): Flow<Pais?> // el idPais de esta línea permite poner :idPais en línea anterior.
    // esta función se invoca en PaisDbRepository

    // podríamos añadir actualizaciones (@Update) y eliminaciones (@Delete) (de registros) también.
    // Después de ponerle el code a esta interfaz, lo siguiente es definir nuestra base de datos,
    // para lo cual creo la clase PaisDatabase
}