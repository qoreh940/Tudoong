package com.chch.tudoong.ui.main

data class TudoongItem(
    val content : String,
    val checked : Boolean = false
)

enum class EditMode {
    VIEW,
    EDIT,
    DELETE,
    ADD
}