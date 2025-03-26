package com.tasklist.domain.common


sealed class Resource<out T, out V>(
    open val data: T? = null,
    open val exception: V? = null
) {
    data class Success<out T,out V>(override val data: T) : Resource<T, V>()
    data class Error<out T, out V>(override val exception: V) : Resource<T, V>()

}