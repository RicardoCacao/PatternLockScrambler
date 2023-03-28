package com.example.prototipotese

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.andrognito.patternlockview.PatternLockView
import com.andrognito.patternlockview.PatternLockView.Dot
import com.andrognito.patternlockview.listener.PatternLockViewListener
import com.example.prototipotese.databinding.ActivityMainBinding
import java.security.MessageDigest
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


private const val TAG = "Activity Main"
const val StoredPatternInSettings = "034125"

class MainActivity : AppCompatActivity() {

    private lateinit var hora: String
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

        val goToSettingPageButton = binding.setPatternActivityButton
        goToSettingPageButton.setOnClickListener {
            startActivity(
                Intent(
                    applicationContext,
                    SettingsActivity::class.java
                )
            )
        }

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
                val lista =
                    patternToString(mPatternLockView, pattern).split(",").filterNot { it.isBlank() }
                        .map { it.toInt() }
                val size = binding.patternLockView.dotCount

                val matrix = MatrixOperations.listToMatrix(lista, size, size)

                var resultingMatrix: Array<Array<Int>> = when {
                    Clock.isHourEven() -> TransformationAlgorithm.rotate90Degrees(matrix)
                    else -> TransformationAlgorithm.rotate270Degrees(matrix)
                }
                resultingMatrix = when {
                    Clock.isMinuteEven() -> TransformationAlgorithm.mirrorHorizontally(
                        resultingMatrix
                    )
                    else -> TransformationAlgorithm.mirrorVertically(resultingMatrix)
                }

                for (f in resultingMatrix.indices) {
                    var columns: String = ""
                    for (g in 0 until resultingMatrix[f].size) {
                        columns = if (g == 0) {
                            columns.plus("${resultingMatrix[f][g]}")
                        } else {
                            columns.plus(",${resultingMatrix[f][g]}")
                        }
                    }
                    Log.d(javaClass.name, "88888888888888888 $columns 88888888888888888")
                }
                Log.d(TAG, resultingMatrix.contentDeepToString())

                checkAuthenticity(resultingMatrix)

            }

            override fun onCleared() {
                Log.d(javaClass.name, "Pattern has been cleared")
            }
        }


    fun patternToString(
        patternLockView: PatternLockView,
        pattern: List<Dot>
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

    fun checkAuthenticity(matrix: Array<Array<Int>>): Boolean {
        val hash = sha256(matrix.toString()).toHex()
        val projection = arrayOf(HashContract.Columns.HASH_VALUE)
        val cursor = contentResolver.query(HashContract.CONTENT_URI, projection, null, null, null)
        var dbHash = ""
        //val dbhash2 = cursor?.getString(1)
        matrix.hashCode()
        Log.d(TAG, matrix.hashCode().toString())
        val hashColumnIndex = cursor?.getColumnIndex(HashContract.Columns.HASH_VALUE)
        if (hashColumnIndex != null && hashColumnIndex > 0){
            if (cursor.moveToFirst()){
                if (!(cursor.getString(hashColumnIndex).isNullOrEmpty())){
                    dbHash = getString(hashColumnIndex)
                }
            }
        }

        Log.d(javaClass.name,"A hash de agora é $hash")
 //       Log.d(javaClass.name,"A hash da db é $dbhash2")
        Log.d(javaClass.name,"A hash depois das ligaçoes todas é $dbHash")
        Log.d(javaClass.name,"O indice da coluna é $hashColumnIndex")

        cursor?.close()

        return hash == dbHash

        TODO()

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


    //These two functions are from here: https://stackoverflow.com/questions/64171624/how-to-generate-an-md5-hash-in-kotlin
    fun ByteArray.toHex() = joinToString(separator = "") { byte -> "%02x".format(byte) }
    fun sha256(str: String): ByteArray = MessageDigest.getInstance("SHA-256").digest(
        str.toByteArray(
            Charsets.UTF_8
        )
    )
}
