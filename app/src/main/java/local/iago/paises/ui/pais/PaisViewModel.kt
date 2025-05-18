package local.iago.paises.ui.pais

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import local.iago.paises.model.Pais
import local.iago.paises.repositories.PaisRepository

// sobre el private val me avisa de que "constructor parameter is never used as a property".
class PaisViewModel(private val repo: PaisRepository, idPais: Int) : ViewModel() {
    /*
    Defino una propiedad llamada 'pais' de tipo StateFlow<Pais?>
    y la inicializo con el StateFlow<Pais?> retornado por el metodo stateIn, el cual invoco desde
    el retorno (Flow<Pais?>) de la función getPaisByIdPais de mi interfaz PaisRepository.
    .stateIn convierte un Flow frío a un StateFlow caliente que es iniciado en el "scope" de corutina dado.
    */
    val pais: StateFlow<Pais?> = repo.getPaisByIdPais(idPais)
        .stateIn(scope = viewModelScope, started = SharingStarted.Lazily, initialValue = null)
    // para eliminar el error, hay que poner interrogante por lo menos en StateFlow<Pais?> en esta clase.
    // profe también pondría (v. examen 2nda eval) interrogante en Flow<Pais?> en PaisRepository, cosa que yo tmb hago.
    // el interrogante indica que puede ser null.

} // fin de la clase PaisViewModel, que es notablemente más escueta que PaisesViewModel.