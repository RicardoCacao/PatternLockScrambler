package com.example.prototipotese

object TransformationAlgorithm {

    fun rotate90Degrees(matrix: Array<Array<Int>>): Array<Array<Int>> {
        val rows = matrix.size
        val cols = matrix[0].size
        var rotatedMatrix = Array(cols) { Array(rows) { 0 } }
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                rotatedMatrix[j][rows - i - 1] = matrix[i][j]
            }
        }

        return rotatedMatrix
    }

    fun rotate270Degrees(matrix: Array<Array<Int>>): Array<Array<Int>> {
        val rows = matrix.size
        val cols = matrix[0].size
        var copia = matrix
        var rotatedMatrix = Array(cols) { Array(rows) { 0 } }
        var rotatedResult = Array(cols) { Array(rows) { 0 } }
        for (t in 1..3) {
            for (i in 0 until rows) {
                for (j in 0 until cols) {
                    rotatedMatrix[j][rows - i - 1] = copia[i][j]
                }
            }
            rotatedResult = rotatedMatrix
            copia = rotatedMatrix
            rotatedMatrix = Array(cols) { Array(rows) { 0 } }
        }

        return rotatedResult
    }

    fun mirrorHorizontally() {
        TODO("ALL OF IT")
    }

    fun mirrorVertically(matrix: Array<Array<Int>>): Array<Array<Int>> {
        val rows = matrix.size
        val cols = matrix[0].size
        val mirroredMatrix = Array(rows) { Array(cols) { 0 } }
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                mirroredMatrix[i][j] = matrix[i][cols - j - 1]
            }
        }

        return mirroredMatrix
    }
}