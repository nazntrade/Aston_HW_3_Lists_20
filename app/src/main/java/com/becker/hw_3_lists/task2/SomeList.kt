package com.becker.hw_3_lists.task2

sealed class SomeList {
    data class Contact(
        val id: Int,
        val name: String,
        val secondName: String,
        val phoneNumber: String,
        var selected: Boolean = false
    ) : SomeList()

    data class Advertisement(
        val id: Int,
        val ageGroup: String,
        val contentLink: String,
        var selected: Boolean = false
    ) : SomeList()
}