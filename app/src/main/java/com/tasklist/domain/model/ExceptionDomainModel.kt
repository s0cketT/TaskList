package com.tasklist.domain.model

sealed class ExceptionDomainModel(ex: Throwable) : Throwable(ex) {
    override val cause: Throwable = ex

    class Other(ex: Throwable) : ExceptionDomainModel(ex)
    class NoInternet(ex: Throwable) : ExceptionDomainModel(ex)

}