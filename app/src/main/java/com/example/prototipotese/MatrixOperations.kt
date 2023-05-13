package com.example.prototipotese

import android.util.Log


object MatrixOperations {

    fun listToMatrix(list: List<Int>, rows: Int, cols: Int): Array<Array<Int>> {
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
        val result = Array(rows) { Array(cols) { 0 } }
        var count: Int = 1
        for (h in list.indices) {
            if (list[h] / cols < 1.0) {
                var row = 0
                //Log.d(javaClass.name, "${list[h]} is row: $row")

                val col = list[h] % cols
                result[row][col] = count
                //Log.d(javaClass.name, "${list[h]} is column: $col")

            } else if (list[h] >= 1.0 && list[h] / cols < 2.0) {
                var row = 1
                //Log.d(javaClass.name, "${list[h]} is row: $row")

                val col = list[h] % cols
                //Log.d(javaClass.name, "Doing math of ${list[h]} % $cols which equals in $col")
                result[row][col] = count
                //Log.d(javaClass.name, "${list[h]} is column: $col")
            } else if (list[h] / cols >= 2.0) {
                var row = 2
                //Log.d(javaClass.name, "${list[h]} is row: $row")

                val col = list[h] % cols
                result[row][col] = count
                //Log.d(javaClass.name, "${list[h]} is column: $col")
            }
            count += 1


        }
        for (f in result.indices) {
            Log.d(
                javaClass.name,
                "################# ${result[f][0]},${result[f][1]},${result[f][2]} ################# "
            )
        }
/*
0 1 2 Primeira row
3 4 5 segunda row
6 7 8 terceira row

input / 3 < 1 => primeira row
          > 1 e < 2 => segunda row
          > 2 => terceira row

 */


/*        for (f in matrix.indices) {
            //  for (g in 0 .. 2){
            Log.d(
                javaClass.name,
                "################# ${matrix[f][0]},${matrix[f][1]},${matrix[f][2]} ################# "
            )
            //}

        }

        for (f in matrix1.indices) {
            //  for (g in 0 .. 2){
            Log.d(
                javaClass.name,
                "!!!!!!!!!!!!!!!!! ${matrix1[f][0]},${matrix1[f][1]},${matrix1[f][2]} !!!!!!!!!!!!!!!!! "
            )
            //}

        }

        var resu: FloatArray = floatArrayOf(1.2F)
        //Matrix.rotateM(resu,1,matrix1,0, 90F, 0.0F, 0.0F, 0.0F)
        //println(resu.contentToString())

        val rotatedMatrix = rotateMatrixplus90(matrix1)
        //println(rotatedMatrix.contentDeepToString())
        for (f in rotatedMatrix.indices) {
            Log.d(
                javaClass.name,
                "################# ${rotatedMatrix[f][0]},${rotatedMatrix[f][1]},${rotatedMatrix[f][2]} ################# "
            )
        }


        val rotatedMatrix2 = rotateMatrixminus90(matrix1)
        //println(rotatedMatrix2.contentDeepToString())
        for (f in rotatedMatrix2.indices) {
            Log.d(
                javaClass.name,
                "!!!!!!!!!!!!!!!!! ${rotatedMatrix2[f][0]},${rotatedMatrix2[f][1]},${rotatedMatrix2[f][2]} !!!!!!!!!!!!!!!!! "
            )

        }*/
/*        for (f in matrix1.indices) {
            //  for (g in 0 .. 2){
            Log.d(
                javaClass.name,
                "!!!!!!!!!!!!!!!!! ${matrix1[f][0]},${matrix1[f][1]},${matrix1[f][2]} !!!!!!!!!!!!!!!!! "
            )
            //}

        }*/

/*        val mirrorMatrix = mirrorMatrixVert(matrix1)
        for (f in mirrorMatrix.indices) {
            Log.d(
                javaClass.name,
                "################# ${mirrorMatrix[f][0]},${mirrorMatrix[f][1]},${mirrorMatrix[f][2]} ################# "
            )
        }*/
        return result
    }

    fun rotateMatrixplus90(matrix: Array<Array<Int>>): Array<Array<Int>> {
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

    fun rotateMatrixminus90(matrix: Array<Array<Int>>): Array<Array<Int>> {
        val rows = matrix.size
        val cols = matrix[0].size
        var copier = matrix
        var rotatedMatrix = Array(cols) { Array(rows) { 0 } }
        var rotatedResult = Array(cols) { Array(rows) { 0 } }
        for (t in 1..3) {
            for (i in 0 until rows) {
                for (j in 0 until cols) {
                    rotatedMatrix[j][rows - i - 1] = copier[i][j]
                }
            }
            rotatedResult = rotatedMatrix
            copier = rotatedMatrix
            rotatedMatrix = Array(cols) { Array(rows) { 0 } }
        }

        return rotatedResult
    }

    fun mirrorMatrixVert(matrix: Array<Array<Int>>): Array<Array<Int>> {
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