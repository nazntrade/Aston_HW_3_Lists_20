package com.becker.hw_3_lists.task2

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class SomeListAdapter(
    callback: (data: MutableList<Int>) -> Unit,
    onItemClick: (position: Int) -> Unit,
    showMenuDelete: (Boolean) -> Unit
) : AsyncListDifferDelegationAdapter<SomeList>(SomeListDiffUtilCallback()) {

    private val contactAdapterDelegate =
        ContactAdapterDelegate(callback, onItemClick, showMenuDelete)

    fun clearSelected() {
        contactAdapterDelegate.clearSelected()
    }

    init {
        delegatesManager
            .addDelegate(contactAdapterDelegate)
//          .addDelegate(AdvertisementDelegate(onItemClick))
    }

    class SomeListDiffUtilCallback : DiffUtil.ItemCallback<SomeList>() {
        override fun areItemsTheSame(oldItem: SomeList, newItem: SomeList): Boolean {
            return when {
                oldItem is SomeList.Contact && newItem is SomeList.Contact -> oldItem.id == newItem.id
                oldItem is SomeList.Advertisement && newItem is SomeList.Advertisement -> oldItem.id == newItem.id
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: SomeList, newItem: SomeList): Boolean {
            return oldItem == newItem
        }
    }
}