package com.jonliapps.morningheartratemonitor.utils

import java.util.*

var idGenerated: String = ""
    get() {
        field = UUID.randomUUID().toString()
        return field
    }

sealed class Result<out R> {
    data class Success<out T>(val data: T): Result<T>()
    data class Error(val exception: Exception): Result<Nothing>()
    data class Canceled(val exception: Exception): Result<Nothing>()
}

