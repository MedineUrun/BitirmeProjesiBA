package com.example.bitirmeprojesi.data.state

import com.example.bitirmeprojesi.data.entitiy.User

sealed class LoginState {

    object Idle:LoginState()
    object Loading:LoginState()
    class Result(val user: User):LoginState()
    class Error(val throwable: Throwable):LoginState()

}