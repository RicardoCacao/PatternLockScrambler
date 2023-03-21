package com.example.prototipotese

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.andrognito.patternlockview.PatternLockView
import com.andrognito.patternlockview.PatternLockView.Dot
import com.andrognito.patternlockview.listener.PatternLockViewListener
import com.example.prototipotese.databinding.ActivityMainBinding
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


private const val TAG = "Activity Main"
const val StoredPatternInSettings = "034125"
class MainActivity : AppCompatActivity() {

    public lateinit var hora: String
    private lateinit var binding: ActivityMainBinding
    private lateinit var mPatternLockView: PatternLockView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mPatternLockView = binding.patternLockView
        mPatternLockView.addPatternLockListener(mPatternLockViewListener)

        DateTimeFormatter.ISO_INSTANT.format(Instant.now())
        binding.clockTextView.text = DateTimeFormatter
            .ofPattern("HH:mm")
            .withZone(ZoneOffset.UTC)
            .format(Instant.now())


//        val rotateButton = binding.rotateButton
//        rotateButton.setOnClickListener(this)


    }

    private val mPatternLockViewListener: PatternLockViewListener =
        object : PatternLockViewListener {
            override fun onStarted() {
                hora = DateTimeFormatter
                    .ofPattern("HH:mm")
                    .withZone(ZoneOffset.UTC)
                    .format(Instant.now())
                binding.clockTextView.text = hora
                Log.d(javaClass.name, "Pattern drawing started")

            }

            override fun onProgress(progressPattern: List<Dot>) {
                Log.d(
                    javaClass.name, "Pattern progress: " +
                            patternToString(mPatternLockView, progressPattern)
                )
            }

            override fun onComplete(pattern: List<Dot>) {
                Log.d(
                    javaClass.name, "Pattern complete: " +
                            patternToString(mPatternLockView, pattern)
                )
                val lista = patternToString(mPatternLockView,pattern).split(",").filterNot { it.isBlank() }.map { it.toInt() }
                val size = binding.patternLockView.dotCount

                val matrix = MatrixOperations.listToMatrix(lista,size,size)


            }

            override fun onCleared() {
                Log.d(javaClass.name, "Pattern has been cleared")
            }
        }


    fun patternToString(
        patternLockView: PatternLockView,
        pattern: List<Dot>?
    ): String {
        if (pattern == null) {
            return ""
        }
        val patternSize = pattern.size
        val stringBuilder = StringBuilder()
        for (i in 0 until patternSize) {
            val dot = pattern[i]
            stringBuilder.append(dot.row * patternLockView.dotCount + dot.column).append(",")
        }
        return stringBuilder.toString()
    }




/*    override fun onClick(v: View?) {

        if (v != null) {
            when (v.id) {
                binding.rotateButton.id -> {
                    TransformationAlgorithm.rotate90Degrees(StoredPatternInSettings)
    //              finish()
                }
            }
        }
    }*/
}
