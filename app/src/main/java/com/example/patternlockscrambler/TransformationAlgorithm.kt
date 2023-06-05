package com.example.patternlockscrambler

import android.util.Log

private var TAG = "Transformation Algorithm"

object TransformationAlgorithm {

    /**
     * Rotates a given 2D matrix (represented as an array of arrays) 90 degrees clockwise.
     *
     * @param matrix The input 2D matrix (array of arrays of Int) to be rotated.
     * @return A new 2D matrix (array of arrays of Int) representing the rotated input matrix.
     */
    fun rotate90Degrees(matrix: Array<Array<Int>>): Array<Array<Int>> {

        // Determine the dimensions of the input matrix
        val rows = matrix.size
        val cols = matrix[0].size

        // Initialize a new 2D matrix (array of arrays) with reversed dimensions
        val result = Array(cols) { Array(rows) { 0 } }

        // Iterate over the elements of the input matrix
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                // Assign the rotated element to the corresponding position in the result matrix
                result[j][rows - i - 1] = matrix[i][j]
            }
        }

        // Return the rotated matrix
        return result
    }

    /**
     * Rotates a given 2D matrix (represented as an array of arrays) 270 degrees clockwise.
     *
     * @param matrix The input 2D matrix (array of arrays of Int) to be rotated.
     * @return A new 2D matrix (array of arrays of Int) representing the rotated input matrix.
     */
    fun rotate270Degrees(matrix: Array<Array<Int>>): Array<Array<Int>> {

        // Determine the dimensions of the input matrix
        val rows = matrix.size
        val cols = matrix[0].size

        // Initialize the result of the input matrix
        val result = Array(cols) { Array(rows) { 0 } }

        // Rotate the matrix 90 degrees counterclockwise three times (equivalent to 270 degrees)
        // Iterate over the elements of the current matrix
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                // Assign the rotated element to the corresponding position in the result matrix
                result[cols - j - 1][i] = matrix[i][j]
            }
        }
        return result
    }


    /**
     * Mirrors a given 2D matrix (represented as a nested array of integers) vertically by reflecting its elements along the horizontally central axis.
     *
     * @param matrix A 2D matrix represented as a nested array of integers. The matrix is expected to have a non-zero number of rows and columns.
     * @return A new 2D matrix, which is the input matrix mirrored horizontally.
     */
    fun mirrorHorizontally(matrix: Array<Array<Int>>): Array<Array<Int>> {
        val rows = matrix.size
        val cols = matrix[0].size
        val result = Array(rows) { Array(cols) { 0 } }
        for (i in 0 until rows) {
            result[i] = matrix[rows - i - 1]
        }
        return result
    }

    /**
     * Mirrors a given 2D matrix (represented as a nested array of integers) vertically by reflecting its elements along the vertical central axis.
     *
     * @param matrix A 2D matrix represented as a nested array of integers. The matrix is expected to have a non-zero number of rows and columns.
     * @return A new 2D matrix, which is the input matrix mirrored vertically.
     */
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
                } else {
                    columns.plus(",${result[f][g]}")
                }


            }
            Log.d(TAG, "44444444444444444 $columns 44444444444444444")
        }
        Log.d(javaClass.name, result.contentDeepToString())

        return result
    }
}