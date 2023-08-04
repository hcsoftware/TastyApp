package com.xr6sfoftware.tastyapp.utils

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class InputValidatorTest {

    @Test
    fun whenInputIsValid() {

        val usrInput = "less than 15"
        val result = InputValidator.validateInput(usrInput)
        assertTrue(result)

    }

    @Test
    fun whenInputIsInvalidEmpty() {

        val usrInput = ""
        val result = InputValidator.validateInput(usrInput)
        assertFalse(result)

    }

    @Test
    fun whenInputIsInvalidMoreThan15Chars() {

        val usrInput = "a lot more than 15 chars"
        val result = InputValidator.validateInput(usrInput)
        assertFalse(result)

    }


}