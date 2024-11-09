package com.example.mytetris.helpers


fun array2dOfByte(sizeOuter: Int, sizeInner: Int): Array<ByteArray> =
    /***
     * функция создания массива байтов
     * первая строчка принимает кол-во строк
     * вторая строчка - кол-во столбцов
     * создаваемого массива
     */
    Array(sizeOuter) { ByteArray(sizeInner) }