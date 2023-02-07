package com.xr6sfoftware.tastyapp.utils

object InputValidator {

    fun validateInput(userInput: String) : Boolean {
        return (userInput.length < 15 ) && (userInput.isNotEmpty())
    }

}