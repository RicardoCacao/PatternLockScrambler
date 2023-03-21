package com.example.prototipotese

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.andrognito.patternlockview.PatternLockView
import com.andrognito.patternlockview.PatternLockView.Dot
import com.andrognito.patternlockview.listener.PatternLockViewListener
import com.andrognito.patternlockview.utils.PatternLockUtils
import com.example.prototipotese.databinding.ActivityMainBinding


//private const val TAG = "Activity Main"
const val StoredPatternInSettings = "034125"
class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mPatternLockView: PatternLockView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mPatternLockView = binding.patternLockView
        mPatternLockView.addPatternLockListener(mPatternLockViewListener)
        val rotateButton = binding.rotateButton
        rotateButton.setOnClickListener(this)
    }

    private val mPatternLockViewListener: PatternLockViewListener =
        object : PatternLockViewListener {
            override fun onStarted() {
                Log.d(javaClass.name, "Pattern drawing started")
            }

            override fun onProgress(progressPattern: List<Dot>) {
                Log.d(
                    javaClass.name, "Pattern progress: " +
                            PatternLockUtils.patternToString(mPatternLockView, progressPattern)
                )
            }

            override fun onComplete(pattern: List<Dot>) {
                Log.d(
                    javaClass.name, "Pattern complete: " +
                            PatternLockUtils.patternToString(mPatternLockView, pattern)
                )
                val lista = PatternLockUtils.patternToString(mPatternLockView,pattern).split("").filterNot { it.isBlank() }.map { it.toInt() }
                val size = binding.patternLockView.dotCount
                MatrixOperations.listToMatrix(lista,size,size)

            }

            override fun onCleared() {
                Log.d(javaClass.name, "Pattern has been cleared")
            }
        }

    override fun onClick(v: View?) {

        if (v != null) {
            when (v.id) {
                binding.rotateButton.id -> {
                    TransformationAlgorithm.rotate90Degrees(StoredPatternInSettings)
    //              finish()
                }
    /*            binding.humidityCard.id -> {
                    val goToGraphActivity = Intent(applicationContext, GraphActivity::class.java)
                    goToGraphActivity.putExtra("Parameter", "humidity")
                    goToGraphActivity.putExtra("Station", "$stationId")
                    startActivity(goToGraphActivity)
    //              finish()
                }
                binding.soilTemperatureCard.id -> {
                    val goToGraphActivity = Intent(applicationContext, GraphActivity::class.java)
                    goToGraphActivity.putExtra("Parameter", "soiltemperature")
                    goToGraphActivity.putExtra("Station", "$stationId")
                    startActivity(goToGraphActivity)
    //              finish()
                }
                binding.soilHumidityCard.id -> {
                    val goToGraphActivity = Intent(applicationContext, GraphActivity::class.java)
                    goToGraphActivity.putExtra("Parameter", "soilhumidity")
                    goToGraphActivity.putExtra("Station", "$stationId")
                    startActivity(goToGraphActivity)
    //              finish()
                }

                binding.pressureCard.id -> {
                    val goToGraphActivity = Intent(applicationContext, GraphActivity::class.java)
                    goToGraphActivity.putExtra("Parameter", "barometricpressure")
                    goToGraphActivity.putExtra("Station", "$stationId")
                    startActivity(goToGraphActivity)
    //              finish()
                }
                binding.rain24HrsCard.id -> {
                    val goToGraphActivity = Intent(applicationContext, GraphActivity::class.java)
                    goToGraphActivity.putExtra("Parameter", "precipitation")
                    goToGraphActivity.putExtra("Station", "$stationId")
                    startActivity(goToGraphActivity)
    //              finish()
                }
                binding.windSpeedCard.id -> {
                    val goToGraphActivity = Intent(applicationContext, GraphActivity::class.java)
                    goToGraphActivity.putExtra("Parameter", "windspeed")
                    goToGraphActivity.putExtra("Station", "$stationId")
                    startActivity(goToGraphActivity)
    //              finish()
                }
                binding.locationNameTextView.id -> {
                    val goToGraphActivity =
                        Intent(applicationContext, MapsMarkerActivity::class.java)
                    goToGraphActivity.putExtra("Parameter", "windspeed")
                    goToGraphActivity.putExtra("Station", "$stationId")
                    startActivity(goToGraphActivity)
                }*/
            }
        }
    }
}
