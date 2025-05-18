package local.iago.paises.repositories

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.flow.Flow
import local.iago.paises.database.PaisDatabase
import local.iago.paises.model.Pais

private const val DB_NAME = "paisesedit.db"

class PaisDbRepository private constructor(context: Context): PaisRepository {
    // propiedad database:
    private val database: PaisDatabase = Room.databaseBuilder(
        context.applicationContext,
        PaisDatabase::class.java,
        DB_NAME
    ).createFromAsset(DB_NAME).build()

    // Exponemos Flow para observar cambios en tiempo real y sobreescribimos funciones de interfaz:
    override fun getAll(): Flow<List<Pais>> {
        return database.paisDao().getAll() // profe a esta función le llamó getAll() a secas.
    }

    override suspend fun insert(pais: Pais) {
        database.paisDao().addPais(pais) // a esta función el profe le llamó insert
    }

    override fun getPaisByIdPais(idPais: Int): Flow<Pais?> {
        return database.paisDao().getPaisByIdPais(idPais)
    }

    // companion object:
    companion object {
        @Volatile
        private var INSTANCE: PaisDbRepository? = null

        fun getInstance(context: Context): PaisDbRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: PaisDbRepository(context.applicationContext).also { INSTANCE = it }
            } // it es de tipo PaisDbRepository
        } // el getInstance se invoca en PaisApplication.kt directamente desde PaisDbRepository.
    }
}