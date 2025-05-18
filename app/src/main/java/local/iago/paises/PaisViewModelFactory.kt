package local.iago.paises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import local.iago.paises.repositories.PaisRepository
import local.iago.paises.ui.pais.PaisViewModel
import local.iago.paises.ui.paises.PaisesViewModel

/*
2ndo y 3er param de JuegoViewModelFactory en examen 3era eval:
private val idPlataforma: Int = 0,
private val idPais: Int = 0
*/
class PaisViewModelFactory(private val r: PaisRepository, private val idPais: Int = 0): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        /* Solo RecyclerView:
        if (modelClass.isAssignableFrom(PaisesViewModel::class.java)) {
            return PaisesViewModel(r) as T
        }
        throw IllegalArgumentException("Clase ViewModel desconocida")
        */

        // RecyclerView más fragmento de un país al detalle:
        return when {
            modelClass.isAssignableFrom(PaisViewModel::class.java) -> {
                PaisViewModel(repo = r, idPais = idPais) as T
            }
            modelClass.isAssignableFrom(PaisesViewModel::class.java) -> {
                PaisesViewModel(r) as T
            }
            else -> throw IllegalArgumentException("Clase ViewModel desconocida")
        } // fin del when
    } // Hemos añadido la opción de crear un PaisesViewModel.
}