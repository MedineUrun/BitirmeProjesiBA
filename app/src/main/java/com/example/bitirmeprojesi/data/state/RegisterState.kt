package com.example.bitirmeprojesi.data.state

import com.example.bitirmeprojesi.data.entitiy.User

sealed class RegisterState {
    object Idle:RegisterState()
    object Loading: RegisterState()
    class Success(val user: User): RegisterState()
    class Error(val throwable: Throwable): RegisterState()
}