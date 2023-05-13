package com.example.patternlockscrambler

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.andrognito.patternlockview.PatternLockView
import com.andrognito.patternlockview.listener.PatternLockViewListener
import com.example.patternlockscrambler.databinding.ActivitySettingsBinding
import java.security.MessageDigest
import kotlin.text.Charsets.UTF_8

private var TAG = "Settings Activity"
class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var mPatternLockView: PatternLockView
    private lateinit var hash: ByteArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mPatternLockView = binding.patternLockView
        mPatternLockView.addPatternLockListener(mPatternLockViewListener)

        val setPatternButton = binding.setPattern
        setPatternButton.setOnClickListener {
            if (this::hash.isInitialized) {
                contentResolver.delete(HashContract.CONTENT_URI, null, null)
                val values = ContentValues().apply { put(HashContract.Columns.HASH_VALUE, hash.toHex()) }
                val uri = contentResolver.insert(HashContract.CONTENT_URI, values)
                Toast.makeText(this, "Padrão guardado", Toast.LENGTH_SHORT).show()
                Log.d(TAG, uri.toString())

            }
            else{
                Toast.makeText(this, "Por favor desenhe um padrão antes de pressionar o botão", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private val mPatternLockViewListener: PatternLockViewListener =
        object : PatternLockViewListener {
            override fun onStarted() {
                Log.d(javaClass.name, "Progress started")
            }

            override fun onProgress(progressPattern: List<PatternLockView.Dot>) {
                Log.d(
                    javaClass.name, "Pattern progress: " +
                            patternToString(mPatternLockView, progressPattern)
                )
            }

            override fun onComplete(pattern: List<PatternLockView.Dot>) {
                Log.d(
                    javaClass.name, "Pattern complete: " +
                            patternToString(mPatternLockView, pattern)
                )
                val lista =
                    patternToString(mPatternLockView, pattern).split(",").filterNot { it.isBlank() }
                        .map { it.toInt() }
                val size = binding.patternLockView.dotCount
                val matrix = MatrixOperations.listToMatrix(lista, size, size)
                Log.d(javaClass.name, matrix.contentDeepToString())
                Log.d(javaClass.name, matrix.hashCode().toString())
                val flattenedMatrix = matrix.flatMap { it.asList() }
                hash = sha256(flattenedMatrix.toString())
                Log.d(javaClass.name, "Hash of the pattern is ${hash.toHex()}")
            }

            override fun onCleared() {
                Log.d(javaClass.name, "Pattern has been cleared")
            }
        }

    fun patternToString(
        patternLockView: PatternLockView,
        pattern: List<PatternLockView.Dot>
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

    //These two functions are from here: https://stackoverflow.com/questions/64171624/how-to-generate-an-md5-hash-in-kotlin
    fun ByteArray.toHex() = joinToString(separator = "") { byte -> "%02x".format(byte) }
    fun sha256(str: String): ByteArray = MessageDigest.getInstance("SHA-256").digest(str.toByteArray(UTF_8))
}