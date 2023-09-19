package com.example.bitirmeprojesi.data.state

sealed class UserUpdateState{
    object Idle:UserUpdateState()
    object Success:UserUpdateState()
    object UserNotFound:UserUpdateState()
    object UserAlreadyExist:UserUpdateState()
    class Error(val throwable: Throwable):UserUpdateState()
}