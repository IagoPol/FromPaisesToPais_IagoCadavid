package local.iago.paises.repositories

import kotlinx.coroutines.flow.Flow
import local.iago.paises.model.Pais

// interfaz creada por mí, cuyos miembros son sobreescritos en PaisDbRepository, clase creada por mí.
interface PaisRepository {
    fun getAll(): Flow<List<Pais>> // se invoca en la clase PaisesViewModel en su función getPaises()

    suspend fun insert(pais: Pais) // no me consta haberla usado finalmente en mi proyecto, pero
    // junto con el getAll() tengo entendido que es lo principal/lo que se suele definir en la interfaz.

    fun getPaisByIdPais(idPais: Int): Flow<Pais?> // esta función se invoca en PaisViewModel
}