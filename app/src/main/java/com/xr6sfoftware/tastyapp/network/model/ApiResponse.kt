package com.xr6sfoftware.tastyapp.network.model

data class ApiResponse<T> (
    var error: String?,
    val values: T?
)