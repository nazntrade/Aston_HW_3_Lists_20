package com.becker.hw_3_lists.task2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.becker.hw_3_lists.databinding.FragmentPopUpBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

class PopUpFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentPopUpBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPopUpBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nameEditText = binding.nameEditText
        val secondNameEditText = binding.secondNameEditText
        val phoneEditText = binding.numberEditText
        val editBtn = binding.editBtn
        val deleteBtn = binding.deleteBtn

        val currentContacts = viewModel.contacts.value.toMutableList()
        var currentListPosition = 0
        var currentContactId = 0

        lifecycleScope.launch {
            viewModel.savedPosition.collect {
                if (it != null) {
                    currentListPosition = it
                    currentContactId = currentContacts[it].id
                    nameEditText.setText(currentContacts[it].name)
                    secondNameEditText.setText(currentContacts[it].secondName)
                    phoneEditText.setText(currentContacts[it].phoneNumber)
                }
            }
        }

        editBtn.setOnClickListener {
            val tempContact = SomeList.Contact(
                id = currentContactId,
                name = nameEditText.text.toString(),
                secondName = secondNameEditText.text.toString(),
                phoneNumber = phoneEditText.text.toString()
            )
            currentContacts.set(index = currentListPosition, tempContact)
            viewModel.putNewListContacts(currentContacts)
            dismiss()
        }

        deleteBtn.setOnClickListener {
            currentContacts.removeAt(currentListPosition)
            viewModel.putNewListContacts(currentContacts)
            dismiss()
        }
    }
}