package com.example.mytetris.models

import com.example.mytetris.helpers.array2dOfByte

class Frame(private val width: Int) {

    val data: ArrayList<ByteArray> = ArrayList()

    fun addRow(byteStr: String): Frame {
        /***
         * функция обрабатывает строку,
         * преобразуя каждый элемент в
         * байтовое представление
         * и добавляя его в данные
         */
        val row = ByteArray(byteStr.length)
        for (idx in byteStr.indices) {
            row[idx] = "${byteStr[idx]}".toByte()
        }
        data.add(row)
        return this
    }

    fun as2dByteArray(): Array<ByteArray> {
        /***
         * функия преобразует список массива данных
         * в байтовый массив
         */
        val bytes = array2dOfByte(data.size, width)
        return data.toArray(bytes)
    }

}