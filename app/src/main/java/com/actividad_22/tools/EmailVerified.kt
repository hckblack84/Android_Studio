package com.actividad_22.tools

import java.util.regex.Pattern

class EmailVerified {

    val emailPattern: Pattern = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}\$"
    )

    fun isValidEmail(email:String): Boolean{

        val matcher = emailPattern.matcher(email)
        return matcher.matches()
    }


}