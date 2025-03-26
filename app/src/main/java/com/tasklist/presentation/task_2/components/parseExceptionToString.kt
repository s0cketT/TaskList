package com.tasklist.presentation.task_2.components

import com.tasklist.R
import com.tasklist.domain.model.ExceptionDomainModel

fun ExceptionDomainModel.parseToString(): Int {
    return when (this) {
        is ExceptionDomainModel.NoInternet -> R.string.error_no_internet
        is ExceptionDomainModel.Other -> R.string.error_api
    }
}