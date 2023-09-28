package com.becker.hw_3_lists.task2

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Collections

class MainViewModel : ViewModel() {

    private val _contacts = MutableStateFlow<List<SomeList.Contact>>(emptyList())
    val contacts = _contacts.asStateFlow()

    private val _savedPosition = MutableStateFlow<Int?>(null)
    val savedPosition = _savedPosition.asStateFlow()

    fun putChosenPosition(position: Int) {
        _savedPosition.value = position
    }

    init {
        createContacts()
    }

    fun deleteSelectedItems(data: MutableList<Int>) {
        val oldList = _contacts.value.toMutableList()
        val dataSorted = data.sortedDescending()
        dataSorted.forEach { oldList.removeAt(it) }
        _contacts.value = oldList
    }

    fun changePosition(dragPosition: Int, targetPosition: Int) {
        val tempList = _contacts.value
        Collections.swap(tempList, dragPosition, targetPosition)
        tempList[targetPosition].selected = false
        tempList[dragPosition].selected = false
        _contacts.value = tempList
    }

    fun putNewListContacts(newListContacts: MutableList<SomeList.Contact>) {
        _contacts.value = newListContacts
    }

    private fun createContacts() {
        val newCreatedContacts = mutableListOf<SomeList.Contact>()
        for (i in 1..150) {
            val newContact = SomeList.Contact(
                id = i,
                name = "Name$i",
                secondName = "SecondName$i",
                phoneNumber = "+(1)155 $i"
            )
            newCreatedContacts.add(newContact)
        }
        _contacts.value = newCreatedContacts
    }

    fun addNewContact() {
        val oldContacts = _contacts.value.toMutableList()
        val randomId = (222..999999).random()
        val newContact = SomeList.Contact(
            id = randomId,
            name = "SomeName$randomId",
            secondName = "SecondName$randomId",
            phoneNumber = "+44(90) $randomId"
        )
        oldContacts.add(newContact)
        _contacts.value = oldContacts
    }
}