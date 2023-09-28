package com.becker.hw_3_lists.task2

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.becker.hw_3_lists.databinding.ItemContactBinding
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

open class ContactAdapterDelegate(
    private val callback: (data: MutableList<Int>) -> Unit,
    private val onItemClick: (position: Int) -> Unit,
    private val showMenuDelete: (Boolean) -> Unit
) : AbsListItemAdapterDelegate<SomeList.Contact, SomeList, ContactAdapterDelegate.ContactHolder>() {

    private var isEnable = false
    private val itemSelectedList = mutableListOf<Int>()

    private fun sendDataToCaller(data: MutableList<Int>) {
        callback(data)
    }

    override fun isForViewType(
        item: SomeList,
        items: MutableList<SomeList>,
        position: Int
    ): Boolean {
        return item is SomeList.Contact
    }

    override fun onCreateViewHolder(parent: ViewGroup): ContactHolder {
        return ContactHolder(
            ItemContactBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onItemClick
        )
    }

    override fun onBindViewHolder(
        item: SomeList.Contact,
        holder: ContactHolder,
        payloads: MutableList<Any>
    ) {
        holder.bindContact(item)
    }

    private fun selectItem(
        checkView: ImageView,
        currentPosition: Int,
        currentItem: SomeList.Contact
    ) {
        isEnable = true
        itemSelectedList.add(currentPosition)
        sendDataToCaller(itemSelectedList)
        currentItem.selected = true
        showMenuDelete(true)
        checkView.isVisible = true
    }

    inner class ContactHolder(
        binding: ItemContactBinding,
        onItemClick: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private val id = binding.idTextView
        private val name = binding.nameTextView
        private val secondName = binding.secondNameTextView
        private val phone = binding.phoneNumberTextView
        private val checkView = binding.checkImageView
        private lateinit var currentItem: SomeList.Contact
        private var currentPosition = bindingAdapterPosition

        init {
            binding.root.setOnLongClickListener {
                currentPosition = bindingAdapterPosition
                selectItem(checkView, currentPosition, currentItem)
                true
            }
            binding.root.setOnClickListener {
                currentPosition = bindingAdapterPosition
                if (itemSelectedList.isNotEmpty()) {
                    if (itemSelectedList.contains(currentPosition)) {
                        itemSelectedList.remove(currentPosition)
                        checkView.isVisible = false
                        currentItem.selected = false
                        if (itemSelectedList.isEmpty()) {
                            showMenuDelete(false)
                            isEnable = false
                        }
                    } else if (isEnable) {
                        selectItem(checkView, currentPosition, currentItem)
                    }
                } else {
                    onItemClick(currentPosition)
                }
            }
        }

        fun bindContact(contact: SomeList.Contact) {
            currentItem = contact
            id.text = contact.id.toString()
            name.text = contact.name
            secondName.text = contact.secondName
            phone.text = contact.phoneNumber
            checkView.isVisible = contact.selected
        }
    }

    fun clearSelected() {
        itemSelectedList.clear()
        isEnable = false
        showMenuDelete(false)
    }
}