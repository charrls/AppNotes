package com.example.inwakeapp.navigation


sealed class Screen(val route: String) {

    object Home : Screen("home")

    object ListNotesModule : Screen("list_notes")
    object RegisterNote : Screen("register_notes")
    object ModifierNote : Screen("modifier_notes")


}