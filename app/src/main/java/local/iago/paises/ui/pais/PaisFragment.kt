package local.iago.paises.ui.pais

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
// import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import local.iago.paises.PaisViewModelFactory
// import local.iago.paises.R
import local.iago.paises.application.PaisApplication
import local.iago.paises.databinding.FragmentPaisBinding
import local.iago.paises.model.setImageFromBytes // esta import no la necesita PaisesFragment.kt

class PaisFragment: Fragment() {
    // Dos atributos para el binding, uno nullable y el otro no:
    private var _binding: FragmentPaisBinding? = null
    private val binding
        get() = checkNotNull(_binding) {"No puede acceder al binding porque es nulo."}

    private val args: PaisFragmentArgs by navArgs() // Usamos la propiedad delegada navArgs.
    /*
    Al usar la propiedad delegada navArgs,
    Se puede acceder a los argumentos de navegación para un destino en particular de manera segura en cuanto a tipos.
    Para esto hacemos uso del plugin Safe Args (incluido en el proyecto en los build.gradle.kts -el del proyecto y el Module:app-
    que además de generar código para realizar navegaciones seguras en cuanto a tipos,
    también permite acceder de manera segura a los argumentos de navegación una vez que el usuario está en su destino.
    */

    /* Declaro la propiedad de tipo PaisViewModel :
    Se obtiene el ViewModel, inicializándolo con un “delegado de propiedad” llamado viewModels (una función)
    Nótese que hago uso de las clases PaisApplication y PaisViewModelFactory, creadas por mí.
    */
    private val paisViewModel: PaisViewModel by viewModels {
        val repo = (requireActivity().application as PaisApplication).repository
        PaisViewModelFactory(r = repo, idPais = args.idCountry) // idCountry es de tipo integer.
    } // idCountry es como le llamé al argumento que creé en fragmento de vista de detalle en nav_graph.xml.

    // sobreescritura de los 4 métodos de Fragment:
    // override fun onCreate no lo pongo porque no le quiero modificar nada entonces sobra ponerlo.
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { // onCreateView always returns non-null type (just a warning).
        _binding = FragmentPaisBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("PaisFragment", "idPais recibido: ${args.idCountry}")

        viewLifecycleOwner.lifecycleScope.launch {
            paisViewModel.pais.collect { nacion ->
                nacion?.let {
                    // binding.ivBandera.setImageResource(R.drawable.unsc)
                    binding.ivBandera.setImageFromBytes(it.heraldica.flag)
                    // binding.ivEmblema.setImageResource(R.drawable.equiponoble)
                    binding.ivEmblema.setImageFromBytes(it.heraldica.emblema)
                    binding.tvNombrePais.text = it.nombre
                    binding.tvPoblacion.text = it.poblacion.toString()
                    binding.tvCapital.text = it.capital
                    binding.tvDivisa.text = it.divisa
                    binding.tvTlfno.text = it.telefono
                } // fin del let
            } // fin del collect
        } // fin dle launch
    } // fin del onViewCreated

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}