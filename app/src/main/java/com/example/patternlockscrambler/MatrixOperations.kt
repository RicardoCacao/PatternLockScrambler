package com.example.patternlockscrambler

import android.util.Log


object MatrixOperations {

    /**
     * Converts a list of Ints into a 2D array of Ints.
     * List has to have distinct values
     *
     * @param list The List of Ints to be converted into a matrix
     * @param rows height of the pattern area
     * @param cols width of the pattern area
     * @return 2D array of Ints
     */
    fun listToMatrix(list: List<Int>,
                     rows: Int,
                     cols: Int
    ): Array<Array<Int>> {
        val matrix = Array(rows) { Array(cols) { 0 } }

        for (i in list.indices) {
            val position = list[i] // Adjust position to zero-based index
            val row = position / rows
            val col = position % cols
            matrix[row][col] = i + 1 // Assign the order of selection to the matrix

        }

        for (f in matrix.indices) {
            var columns: String = ""
            for (g in 0 until matrix[f].size) {
                columns = if (g == 0) {
                    columns.plus("${matrix[f][g]}")
                } else {
                    columns.plus(",${matrix[f][g]}")
                }
            }
        }
        return matrix
    }
}