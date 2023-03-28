package com.example.prototipotese

import android.util.Log

object TransformationAlgorithm {

    fun rotate90Degrees(matrix: Array<Array<Int>>): Array<Array<Int>> {
        val rows = matrix.size
        val cols = matrix[0].size
        var result = Array(cols) { Array(rows) { 0 } }
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                result[j][rows - i - 1] = matrix[i][j]
            }
        }

        for (f in result.indices) {
            var columns: String = ""
            for (g in 0 until result[f].size) {
                columns = if (g == 0) {
                    columns.plus("${result[f][g]}")
                }else{
                    columns.plus(",${result[f][g]}")
                }

            }
            Log.d(javaClass.name, "99999999999999999 $columns 99999999999999999")
        }
        return result
    }

    fun rotate270Degrees(matrix: Array<Array<Int>>): Array<Array<Int>> {
        val rows = matrix.size
        val cols = matrix[0].size
        var copia = matrix
        var rotatedMatrix = Array(cols) { Array(rows) { 0 } }
        var result = Array(cols) { Array(rows) { 0 } }
        for (t in 1..3) {
            for (i in 0 until rows) {
                for (j in 0 until cols) {
                    rotatedMatrix[j][rows - i - 1] = copia[i][j]
                }
            }
            result = rotatedMatrix
            copia = rotatedMatrix
            rotatedMatrix = Array(cols) { Array(rows) { 0 } }
        }

        for (f in result.indices) {
            var columns: String = ""
            for (g in 0 until result[f].size) {
                columns = if (g == 0) {
                    columns.plus("${result[f][g]}")
                }else{
                    columns.plus(",${result[f][g]}")
                }

            }
            Log.d(javaClass.name, "22222222222222222 $columns 22222222222222222")
        }
        return result
    }

    fun mirrorHorizontally(matrix: Array<Array<Int>>): Array<Array<Int>> {
        val rows = matrix.size
        val cols = matrix[0].size
        val result = Array(rows) { Array(cols) { 0 } }
        for (i in 0 until rows) {
            result[i] = matrix[rows - i - 1]
        }
/*
0 1 2 Primeira row
3 4 5 segunda row
6 7 8 terceira row
*/
        for (f in result.indices) {
            var columns: String = ""
            for (g in 0 until result[f].size) {
                columns = if (g == 0) {
                    columns.plus("${result[f][g]}")
                }else{
                    columns.plus(",${result[f][g]}")
                }
            }
            Log.d(javaClass.name, "33333333333333333 $columns 33333333333333333")
        }
        return result
    }

    fun mirrorVertically(matrix: Array<Array<Int>>): Array<Array<Int>> {
        val rows = matrix.size
        val cols = matrix[0].size
        val result = Array(rows) { Array(cols) { 0 } }
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                result[i][j] = matrix[i][cols - j - 1]
            }
        }

        for (f in result.indices) {
            var columns: String = ""
            for (g in 0 until result[f].size) {
                columns = if (g == 0) {
                    columns.plus("${result[f][g]}")
                }else{
                    columns.plus(",${result[f][g]}")
                }

//                Log.d(
//                    javaClass.name,
//                    "################# ${result[f][0]},${result[f][1]},${result[f][2]} #################"
//                )
                //Log.d(javaClass.name, result.contentDeepToString())
            }
            Log.d(javaClass.name, "44444444444444444 $columns 44444444444444444")
        }
        return result
    }
}