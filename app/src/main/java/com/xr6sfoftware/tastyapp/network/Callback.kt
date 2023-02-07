package com.xr6sfoftware.tastyapp.network

interface Callback<T> {

    fun onSuccess(result: T)

    fun onFailure(exception: Exception)

}