package com.tasklist.presentation.Navigation

sealed class Screens(val route: String) {

    object Home : Screens("home")
    object Task1 : Screens("Task1")
    object Task1Success : Screens("success")

    object Task2PostApi : Screens("Task2-PostApi")
    object Task2Comments : Screens("Task2-Comments")

}
