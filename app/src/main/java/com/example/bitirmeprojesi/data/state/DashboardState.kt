package com.example.bitirmeprojesi.data.state

sealed class DashboardState {
    object Idle:DashboardState()
    object Empty:DashboardState()
    class Success(val categories: List<String>):DashboardState()
    object Error:DashboardState()
}