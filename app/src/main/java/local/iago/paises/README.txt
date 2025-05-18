// INTRODUCCIÓN

El proyecto lo he elaborado yo en su mayoría "a mano" (i.e. tecleando yo sin copiar-pegar),
pero tomando como referencia mi solución (hecha en Navidades) al examen de la segunda evaluación.
Dicha solución ya parte de una plantilla facilitada por el profesor.

Donde pasé más tiempo con diferencia fue en tratar de averiguar por qué mi RecyclerView mostraba
una pantalla en blanco al ejecutar el proyecto.
La solución consistió en:
1. ponerle a la tabla de la bd el mismo nombre que puse en tableName de @Entity en data class Pais.
2. poner en la bd como not null todos los campos que en Pais.kt y Heraldica.kt tengo como not null.
Afortunadamente, no me hizo falta cambiar el orden de los campos.
El resultado de esos cambios fue el archivo de base de datos paisesedit.db,
que en este proyecto está en el directorio assets.
Este directorio fue creado por mí, ya que no viene creado por defecto.

// LO QUE LE FALTA AL PROYECTO:
Incorporarle los aspectos relativos a las UDs de la 3era evaluación.
Lograr que el estado de carga desaparezca una vez se ha cargado la lista de países i.e. el RecyclerView.

// EXPLICACIÓN DEL PROYECTO

RESUMEN:

El proyecto consta esencialmente de un fragmento con un RecyclerView que muestra una lista de cerca de 30 países
(PaisesFragment.kt)
        +
un fragmento que muestra un solo país pero al detalle (PaisFragment.kt).
Se navega del primer fragmento (el del RV) al segundo (el de detalle) clicando en un país de la lista.
Si por ejemplo clicamos en el país Brasil, navegaremos al fragmento de detalle de Brasil.

Utilicé XML en vez de Compose.

El proyecto a mí me funciona correctamente al ejecutarlo. Simplemente que a veces lanza el mensaje
de que la aplica no responde o que tarda en responder, pero en verdad siempre me termina respondiendo.
Realmente es solo cosa de que tarda algo en cargar el RecyclerView, tanto al arrancar la aplica
como si vuelvo para atrás (clicando en el triangulito) desde el fragmento de la vista de detalle.

ETAPAS DE DESARROLLO DEL PROYECTO i.e. explicación del proyecto PASO A PASO:

0. Creo un nuevo proyecto llamado PaisesRoomRV escogiendo la plantilla "Empty Views Activity".

1. paisesedit.db

Lo primero es tener unos datos con los que trabajar. Para ello, me descargué de internet un par
de archivos .sqlite con datos de países. Como ninguno me convencía, elaboré "a mano" una combinación de ambos.
El resultado de esa combinación hecha "a mano" es paisesedit.db. Para su creación empleé DbBrowser (SQLite).
Una vez creado paisesedit.db, lo copie en el explorador de archivos de mi Windows11
y lo pegué en en el directorio assets de este proyecto.

2. Modelo de datos:

Mi modelo de datos consta de dos data classes relacionadas, las cuales son Pais y Heraldica. Ambas están
dentro del paquete 'model'. Aprovecho para decir que salvo local.iago.paises,
que existe desde la creación del proyecto, el resto de paquetes los creé yo a posteriori.
¿Cuál es la relación entre Pais.kt y Heraldica.kt? Que la propiedad 'heraldica' de Pais.kt es de tipo
la data class Heraldica. Esta última clase contiene dos propiedades de tipo ByteArray, una para
la imagen de la bandera del país y la otra para la imagen del emblema del mismo.
Si NO pongo la anotación @Embedded junto a la propiedad 'heraldica' de Pais.kt, la ejecución me lanza el error:
"Cannot figure out how to save this field into database. You can consider adding a type converter for it."
Gracias a la proyecto-solución del profesor al examen de la primera evaluación,
descubrí que NO necesitaba usar una clase PaisTypeConverters para mi caso concreto. Me bastó con
poner la anotación @Embedded tal y como he dicho antes.

También podemos observar que en el paquete 'model' tengo un "file" llamado Funciones.kt, el cual
cuenta con una función análoga a la empleada en la mencionada solución al examen de la 1era eval.
Utilizo la función para setearle la imagen a los ImageView de la bandera y el emblema
tanto en PaisFragment.kt (el fragmento que muestra solo un país pero al detalle)
como en class PaisViewHolder en PaisesAdapter.kt (para los item del RecyclerView).

3. Diseño de vistas con XML:

Para el RecyclerView:
En res/layout creo un Layout Resource File llamado item_pais.xml donde defino el diseño de un item
cualquiera del listado del RecyclerView.
Creo otro Layout Resource File llamado fragment_paises.xml, y dentro defino un elemento
androidx.recyclerview.widget.RecyclerView dentro de un FrameLayout (i.e. elmto. raíz de este xml file).

Para el fragmento de detalle de un país:
Creo un Layout Resource File en res/layout denominado fragment_pais.xml y en el defino el diseño
del fragmento PaisFragment.kt, que quiero que muestre todos los datos de un país (el que se seleccione
en el listado del RecyclerView).

Para la main activity:
Creo el Layout Resource File activity_main.xml.
Como quiero gestionar la navegación con un nav graph,
en activity_main.xml pongo estas tres líneas:
android:name="androidx.navigation.fragment.NavHostFragment"
app:defaultNavHost="true"
app:navGraph="@navigation/nav_graph"
La tercera línea exige crear el archivo nav_graph.xml, cosa que hago del siguiente modo:
Hago clic derecho en el directorio res y selecciono New → Android Resource file.
Nombro el archivo 'nav_graph' y escojo 'Navigation' en "Resource type".
En la vista de diseño de nav_graph.xml clicaré en "New Destination" para añadir los fragmentos,
pero primero debo crear las clases i.e. archivos .kt de dichos fragmentos.
Una vez creadas esas dos clases y añadidas como nuevo destino al grafo de navegación, en nav_graph.xml
pondré tools:layout="@layout/fragment_paises" en el elemento 'fragment' de la lista de paises y
tools:layout="@layout/fragment_pais" en el elemento 'fragment' de la vista de detalle de un país.
Así, se verá la previsualización/diseño de ambos fragmentos en la vista de diseño de nav_graph.xml.
Si quitase el tools:layout, entonces simplemente aparecería "Preview Unavailable".

4. Clases .kt de los fragmentos:
Primero creo un package llamado ui (user interface) y dentro creo dos paquetes más a los que llamo
'pais' y 'paises'.
Para el RecyclerView creo PaisesFragment.kt en el paquete 'paises'.
Para el fragmento de detalle de un país creo la clase PaisFragment.

4.1. PaisesFragment.kt
PaisesFragment.kt extiende de Fragment -invocando su constructor con ()- y su body consta de:

1. propiedad binding y su copia no nulable (se chequea que el original no sea null con checkNotNull).
_binding será de tipo FragmentPaisesBinding, el cual tiene que reconocérmelo AndroidStudio a medida que yo voy tecleando.
Para ello, es necesario activar el uso de binding en build.gradle.kts (Module:app), cosa que se puede hacer
desde Project Structure->Modules->Enable View Binding: true
o bien poniendo nosotros a mano el código: buildFeatures { viewBinding = true }

2. propiedad de tipo PaisesViewModel (por ende tuve que crear esta clase): obtiene el ViewModel,
inicializándolo con un “delegado de propiedad” llamado viewModels. Se trata de una función que
toma una expresión lambda que crea una instancia de PaisViewModelFactory (clase que también hubo que crear)
y la usa para crear una instancia de PaisesViewModel, recogiendo como parámetro el repositorio de la aplicación.
Para la inicialización de la variable 'repo' empleo la clase PaisApplication (que por ende hubo que crear).
Las tres clases que hubo que crear (PaisesViewModel, PaisApplication y PaisViewModelFactory) las
explico en comentarios al código en sus respectivos archivos kt (PaisesViewModel.kt, etc.).

3. Sobreescritura de los cuatro métodos de Fragment:

onCreate (como aquí no quiero modificar nada, puedo saltármelo)

onCreateView:
le asigno como valor a _binding el retorno del método inflate invocado desde FragmentPaisesBinding
y retorno binding.root.

onViewCreated:
configuro un LinearLayoutManager (LLM) como el LayoutManager para el RecyclerView.
LLM situará los elementos en la lista verticalmente, uno tras otro, como un LinearLayout.
Conecto/vinculo el PaisesAdapter al RecyclerView, usando la sintaxis de lambda final para el constructor de PaisesAdapter.
Dentro de las llaves de la lambda, encuentro la instancia (de NavController)
a través de la función de extensión findNavController.
Una vez obtenido el NavController, invoco en él a la función navigate, a la que le paso como param
el id ('fromJuegosToJuego') de la acción de navegación 'fromJuegosToJuego' definida en nav_graph.xml.
A 'fromJuegosToJuego' le paso como param el id del pais seleccionado (mediante click en la lista) por el usuario.
A continuación, lanzo una corutina (dentro del "scope" de un LifecycleOwner específico) para observar los datos ("data fetching"),
mejorando la eficiencia de la corutina mediante la línea de código del repeatOnLifecycle, que permitirá ahorrar recursos.
Dentro del repeatOnLifecycle invoco al método collect desde la propiedad 'paises' de la instancia de PaisesViewModel,
pasando una expresión lambda como param del collect. Así, "extraigo" List<Pais> del StateFlow<List<Pais>> de 'paises'.
LLamo a la función actualizarLista que definí en PaisesAdapter pasándole como param la lista de países.

onDestroyView:
Desvinculo el binding en el metodo onDestroyView para mejorar la eficiencia de la memoria.
Para ello, le vuelvo a asignar null a la propiedad _binding (_binding = null).

4.2. PaisFragment.kt
En cuanto a PaisFragment.kt, su código es análogo al de PaisesFragment.kt, pero con dos diferencias:
1. Añadimos propiedad 'args' y la usamos al invocar el constructor de la clase PaisViewModelFactory.
2. En el collect del onViewCreated, en vez de trabajar con List<Pais> e invocar la función actualizarLista de PaiseAdapter,
lo que hacemos es trabajar a nivel de Pais y ponerle el texto/imagen a cada TextView/ImageView del framgento de vista de detalle.
Para ello, uno a uno, cojo el valor de la propiedad que sea de la instancia de Pais y asigno dicho valor al TextView/ImageView.
Para la asignación de la imagen, empleo la función setImageFromBytes, la cual definí en un kt file
que llamé Funciones y que creé en el paquete 'model'. Esta función la creé a imagen y semejanza de
la empleada por el profesor en la solución al examen de la primera evaluación.

5. MainActivity.kt
Antes de la sobreescritura del método onCreate, declaro la propiedad del binding
indicando con 'lateinit' que se inicializará después (dentro del onCreate).
Dentro del onCreate y justo antes del enableEdgeToEdge(),
inicializo el binding invocando el método inflate desde ActivityMainBinding con layoutInflater como param.
Justo después del enableEdgeToEdge(), cambio el param del método setContentView, poniéndole binding.root.
Data Binding es una utilidad ("feature") que nos permite vincular/asociar ("bind") elementos de UI (vistas)
en nuestros archivos XML de layout (diseño) directamente con/a fuentes de datos dentro de la lógica de la app.

6. Implementación de lo relativo a Room y al repositorio
Me he basado principalmente en la plantilla del examen de la 2nda evaluación y secundariamente en
el vídeo 14 del curso Room y Retrofit del youtuber ANT:
https://www.youtube.com/watch?v=mMmWPyGsF1Y&list=PL_z8ReaP-3kSZDH645gZtONyyyXfM0gQp&index=14
Me gustaría destacar el "flujo de trabajo" en lo que respecta a la invocación de la función getAll() de la interfaz:
PaisesViewModel invoca desde la propiedad de mi interfaz PaisRepository a la función getAll().
Esta función se sobreescribe en PaisDbRepository.
En dicha sobreescritura, se invoca a la función abstracta paisDao() desde una instancia de mi clase PaisDatabase.
Dicha función abstracta tiene por tipo de retorno a la interfaz PaisDao, desde la cual se llama a su función getAll().

7. El estado de carga
Se implementa y gestiona en 3 lugares:
    1. En fragment_paises.xml en el elemento ProgressBar
    2. En PaisesViewModel.kt en viewModelScope.launch en función getPaises(), previa declaración e
    inicialización de la propiedad _estadoDeCarga y su copia estadoDeCarga no privada ni Mutable.
    3. En PaisesFragment.kt en el viewLifecycleOwner.lifecycleScope.launch para observar el estado de carga.
Pero no sé cómo lograr realmente que desaparezca el círculo de loading una vez ya se cargó la lista de países del RV.