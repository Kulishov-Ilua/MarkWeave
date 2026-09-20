package ru.kulishov.markweave.model

import kotlinx.datetime.LocalDateTime

data class CustomData(
    val intList:List<Int>? = null,
    val stringList:List<String>? = null,
    val boolList: List<Boolean>? = null,
    val byteArray: ByteArray? = null,
    val dateTimeList:List<LocalDateTime>? =null
)
