package com.example.prototipotese

import android.util.Log
import com.andrognito.patternlockview.PatternLockView
import com.andrognito.patternlockview.PatternLockView.Dot

object TransformationAlgorithm {

    fun rotate90Degrees(patternString: String) {
        var i: Int = 0
        Log.d(javaClass.name, "String do padrão $patternString")
        //"034125"

        for (i in patternString.indices) {

            val indices: Int = patternString[i].digitToInt()
            val test = PatternLockView.Dot.of(indices)

            Log.d(javaClass.name, "ID: $indices & Test: $test")

        }

    }

    fun rotate270Degrees() {
        TODO("ALL OF IT")
    }

    fun mirrorHorizontally() {
        TODO("ALL OF IT")
    }

    fun mirrorVertically() {
        TODO("ALL OF IT")
    }
}