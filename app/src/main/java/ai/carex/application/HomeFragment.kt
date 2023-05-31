package ai.carex.applicationimport ai.carex.vitals.CarexCameraExceptionimport ai.carex.vitals.CarexCameraViewimport ai.carex.vitals.entity.AllDetectionResultimport ai.carex.vitals.entity.Demographicimport ai.carex.vitals.entity.DetectionListenerimport ai.carex.vitals.entity.DetectionStateimport ai.carex.vitals.entity.DetectionTypeimport ai.carex.vitals.entity.VisualUpdateimport android.os.Bundleimport android.util.Logimport android.view.LayoutInflaterimport android.view.Viewimport android.view.ViewGroupimport androidx.core.view.isVisibleimport androidx.fragment.app.Fragmentimport ai.carex.application.databinding.FragmentHomeBindingclass HomeFragment : Fragment() {    private var _binding: FragmentHomeBinding? = null    private lateinit var carexCameraView: CarexCameraView    // This property is only valid between onCreateView and    // onDestroyView.    private val binding get() = _binding!!    override fun onCreateView(        inflater: LayoutInflater,        container: ViewGroup?,        savedInstanceState: Bundle?    ): View {        _binding = FragmentHomeBinding.inflate(inflater, container, false)        carexCameraView = binding.carexCameraView        carexCameraView.initialization(            lifeCycleOwner = this,            detectionType = DetectionType.SELFIE_ALL,            demographic = Demographic(sex = "male", birthYear = 1989) // if provided, or null if not        )        // Set Detection Listener:        carexCameraView.setOnDetectionListener(object : DetectionListener {            override fun onDetectionStateChanged(state: DetectionState){                Log.d("State", "State $state")                binding.stateTextView.text = state.name                binding.resultTextView.isVisible = state == DetectionState.COMPLETED            }            override fun onError(error: CarexCameraException?){                // Error occured in the detection process                Log.d("State", "State ERROR ${error?.message}")            }            override fun onVisualUpdate(visualUpdate: VisualUpdate) {            }            override fun onAllResult(result: AllDetectionResult){                Log.d("State", "State RESULT HR ${result.result.heartRate?.value as Double} BP ${result.result.systolicBloodPressure?.value as Double}")                val stringBuilder = StringBuilder()                result.result.heartRate?.let {                    stringBuilder.append("HR ${it.value as Double}  ${it.unit}  ${it.valueType}\n")                }                result.result.respiratoryRate?.let {                    stringBuilder.append("RR ${it.value as Double}  ${it.unit}  ${it.valueType}\n")                }                result.result.systolicBloodPressure?.let {                    stringBuilder.append("BP SYS ${it.value as Double}  ${it.unit}  ${it.valueType}\n")                }                result.result.diastolicBloodPressure?.let {                    stringBuilder.append("BP DIA ${it.value as Double}  ${it.unit}  ${it.valueType}\n")                }                binding.resultTextView.text = stringBuilder.toString()            }            override fun onConfigInitialized() {                // Indicates that config info are received from the server                binding.carexCameraView.start()            }        })        return binding.root    }    override fun onDestroyView() {        super.onDestroyView()        binding.carexCameraView.onDestroyView()        _binding = null    }    override fun onStart(){        super.onStart()        binding.carexCameraView.onStart()    }    override fun onResume(){        super.onResume()        binding.carexCameraView.onResume()    }    override fun onPause(){        super.onPause()        binding.carexCameraView.onPause()    }}