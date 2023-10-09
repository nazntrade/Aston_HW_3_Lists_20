package com.becker.hw_3_lists.task2

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SmoothScroller
import com.becker.hw_3_lists.R
import com.becker.hw_3_lists.databinding.ActivitySecondBinding
import kotlinx.coroutines.launch

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    private var customMenu: Menu? = null
    private val viewModel: MainViewModel by viewModels()
    private var contactAdapter: SomeListAdapter? = null
    private var selectedPositions = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()
        binding.addNewContactButton.setOnClickListener {
            addNewContactAndScroll()
        }
        swipeElements()
    }

    private fun swipeElements() {
        val helper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.DOWN or ItemTouchHelper.UP,
            0
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val dragPosition = viewHolder.bindingAdapterPosition
                val targetPosition = target.bindingAdapterPosition
                viewModel.changePosition(dragPosition, targetPosition)
                contactAdapter?.notifyItemMoved(dragPosition, targetPosition)
                contactAdapter?.notifyItemChanged(dragPosition)
                contactAdapter?.notifyItemChanged(targetPosition)
                selectedPositions.clear()
                contactAdapter?.clearSelected()
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                TODO("Not yet implemented")
            }
        })
        helper.attachToRecyclerView(binding.recyclerView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        customMenu = menu
        menuInflater.inflate(R.menu.custom_menu, customMenu)
        showDeleteMenu(false)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mDelete -> delete()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showDeleteMenu(show: Boolean) {
        customMenu?.findItem(R.id.mDelete)?.isVisible = show
    }

    private fun delete() {
        viewModel.deleteSelectedItems(selectedPositions)
        selectedPositions.clear()
        contactAdapter?.clearSelected()
    }

    private fun getSelectedPositions(data: MutableList<Int>) {
        selectedPositions = data
    }

    private fun addNewContactAndScroll() {
        viewModel.addNewContact()
        val smoothScroller: SmoothScroller = object : LinearSmoothScroller(applicationContext) {
            override fun getVerticalSnapPreference(): Int {
                return SNAP_TO_START
            }
        }
        smoothScroller.targetPosition = viewModel.contacts.value.size - 1
        binding.recyclerView.layoutManager?.startSmoothScroll(smoothScroller)
    }

    private fun initAdapter() {
        contactAdapter =
            SomeListAdapter(::getSelectedPositions, ::showPopUpFragment, ::showDeleteMenu)
        with(binding.recyclerView) {
            adapter = contactAdapter
            layoutManager = LinearLayoutManager(context)
        }
        lifecycleScope.launch {
            viewModel.contacts.collect {
                contactAdapter?.items = it
            }
        }
    }

    private fun showPopUpFragment(position: Int) {
        val popupWindow = PopUpFragment()
        popupWindow.setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogTheme)
        popupWindow.enterTransition = com.google.android.material.R.id.animateToStart
        popupWindow.exitTransition = com.google.android.material.R.id.animateToEnd
        popupWindow.show(this.supportFragmentManager, POPUP_WINDOW_TAG)
        viewModel.putChosenPosition(position)
    }

    override fun onDestroy() {
        super.onDestroy()
        contactAdapter = null
    }

    companion object {
        private const val POPUP_WINDOW_TAG = "POP_UP"
    }
}