package local.iago.paises.ui.paises

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import local.iago.paises.PaisViewModelFactory
import local.iago.paises.application.PaisApplication
import local.iago.paises.databinding.FragmentPaisesBinding

class PaisesFragment: Fragment() {
    // Dos atributos para el binding, uno nullable y el otro no:
    private var _binding: FragmentPaisesBinding? = null
    private val binding
        get() = checkNotNull(_binding) {"No se puede acceder al binding porque es nulo"}

    /* Declaro la propiedad de tipo PaisesViewModel :
    Se obtiene el ViewModel, inicializándolo con un “delegado de propiedad” llamado viewModels (una función)
    Nótese que hago uso de las clases PaisApplication y PaisViewModelFactory, creadas por mí.
    */
    private val paisesViewModel: PaisesViewModel by viewModels {
        val repo = (requireActivity().application as PaisApplication).repository
        PaisViewModelFactory(repo) // 2ndo param idPlataforma = args.idPlataforma en examen 2nda eval solucion
    }

    // Sobreescritura de los 4 metodos:
    /*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    } //  Se configura la instancia del fragmento.
    */

    /*
    Se crea y configura la vista del fragmento
    Se infla y vincula el layout para la vista del fragmento
    y se devuelve la View inflada a la actividad que lo aloja.
    */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Las dos líneas siguientes las pongo yo (no vienen por defecto):
        _binding = FragmentPaisesBinding.inflate(inflater,container,false)
        return binding.root // getRoot() Retorna el "outermost" View en el archivo de layout asociado.
    }

    /*
    Se llama cuando la vista del fragmento se ha creado, inmediata después de onCreateView.
    */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState) // hasta aquí viene por defecto.
        // el resto del onViewCreated lo pongo yo (no viene por defecto):
        binding.rvPaises.layoutManager = LinearLayoutManager(context) // setLayoutManager(RecyclerView.LayoutManager)
        // Establece el RecyclerView.LayoutManager que este RecyclerView empleará.

        // binding.rvPaises.adapter = PaisesListAdapter()
        /*
        setAdapter(RecyclerView.Adapter) Setea un nuevo adapter para proveer de vistas hijo "on demand".
        Conectamos PaisesAdapter al RecyclerView i.e. vinculamos el PaisesAdapter:
        Si a continuación pongo PaisesAdapter(), me avisa de que "unnecessary parenthesis in function call with lambda".
        */
        binding.rvPaises.adapter = PaisesAdapter { idPais -> // sintaxis de lambda final para el constructor de PaisesAdapter
            Log.d("PaisesFragment", "País seleccionado: $idPais") // idPais es un Int. Log.d es para "debugging".
            findNavController().navigate(PaisesFragmentDirections.fromJuegosToJuego(idPais))
        } /* encontramos la instancia (de NavController) a través de la función de extensión findNavController,
        la cual busca en la jerarquía de vistas y fragmentos el NavController y lo devuelve.
        Una vez que se obtenga el NavController, se invoca en él a la función navigate(NavDirections): Unit,
        pasándole como parámetro la acción de navegación 'fromJuegosToJuego' que definí en el elemento
        fragment con id 'paisesFragment' en nav_graph.xml. A 'fromJuegosToJuego' le paso el id del país seleccionado.
        */

        // Observar el estado de carga:
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // estadoDeCarga es de tipo StateFlow<Boolean>. Gracias al collect, estaCargando es Boolean.
                paisesViewModel.estadoDeCarga.collect { estaCargando ->
                    /*
                    si estaCargando es true, entonces que sea visible el progress bar,
                    si estaCargando es false, entonces que desaparezca el progress bar.
                    */
                    binding.progBar.visibility = if (estaCargando) View.VISIBLE else View.GONE
                } // fin del collect
            } // fin del repeatOnLifecycle
        } // fin del launch

        // Observar los datos ("fetching data"):
        /*
        viewLifecycleOwner.lifecycleScope.launch es una función que lanza una corutina dentro del
        ámbito/scope de un LifecycleOwner específico, normalmente asociado con una vista de Fragment.
        Es comúnmente usada en desarrollo Android para realizar tareas asíncronas relacionadas con el ciclo de vida de la UI,
        tal como "fetching data" (obtener datos) o actualizar las componentes de la UI.
        La corutina es automáticamente cancelada cuando el ciclo de vida del LifecycleOwner asociado es destruído,
        evitando así "leaks" de memoria.
        */
        viewLifecycleOwner.lifecycleScope.launch { // lanza una corutina dentro del "scope" de un LifecycleOwner específico.
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) { // mejora la eficiencia de la corutina, ahorra recursos.
                /*
                La línea de código del repeatOnLifecycle mejora la eficiencia de la corrutina,
                pues solo se ejecutará cuando el Fragment esté en un estado STARTED o superior.
                Cuando el Fragment pase a un estado inferior (como STOPPED),
                la corrutina se cancelará automáticamente, lo que ahorrará recursos.
                */
                paisesViewModel.paises.collect { listaPaises -> // el collect "convierte" el StateFlow<List<Pais>> en List<Pais>
                    // (binding.rvPaises.adapter as PaisesListAdapter).submitList(listaPaises)
                    (binding.rvPaises.adapter as PaisesAdapter).actualizarLista(listaPaises)
                    Log.d("ListaPaises", "Recogidos $listaPaises")
                } // fin de la lambda del collect.
            } // fin del repeatOnLifecycle
        }
    }


    // Desvinculo el binding en el metodo onDestroyView para mejorar la eficiencia de la memoria:
    override fun onDestroyView() {
        super.onDestroyView() // hasta aquí lo que viene por defecto del onDestroyView().
        _binding = null // le vuelvo a asignar null a la propiedad _binding.
    }
}