package com.example.prototipotese

import android.opengl.Matrix
import android.util.Log
import com.andrognito.patternlockview.PatternLockView
import com.example.prototipotese.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.internal.MainDispatcherFactory
import java.lang.Float

object MatrixOperations {

    fun listToMatrix(list: List<Int>, rows: Int, cols: Int): Array<Array<Int>> {
        //require(list.size == rows * cols) { "List size doesn't match matrix size" }
        val matrix = Array(rows) { Array(cols) { 0 } }
        var index = 0
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                if (index < list.size) {
                    matrix[i][j] = list[index]
                    index++
                } else {
                    break
                }
            }
        }
        for ( f in matrix.indices){
          //  for (g in 0 .. 2){
                Log.d(javaClass.name, "${matrix[f][0]},${matrix[f][1]},${matrix[f][2]}")
            //}

        }

        return matrix
    }

/*
0 - 2 Primeira row
3 - 5 segunda row
6 - 8 terceira row

input / 3 < 1 => primeira row
          > 1 e < 2 => segunda row
          > 2 => terceira row



 */



}