package com.example.prototipotese

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.andrognito.patternlockview.PatternLockView
import com.andrognito.patternlockview.PatternLockView.Dot
import com.andrognito.patternlockview.listener.PatternLockViewListener
import com.andrognito.patternlockview.utils.PatternLockUtils
import com.example.prototipotese.databinding.ActivityMainBinding


//private const val TAG = "Activity Main"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mPatternLockView : PatternLockView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mPatternLockView = binding.patternLockView
        mPatternLockView.addPatternLockListener(mPatternLockViewListener)
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
            }

            override fun onCleared() {
                Log.d(javaClass.name, "Pattern has been cleared")
            }
        }
}
