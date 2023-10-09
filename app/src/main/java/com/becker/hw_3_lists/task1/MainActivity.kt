package com.becker.hw_3_lists.task1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.becker.hw_3_lists.R
import com.becker.hw_3_lists.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val countryList = listOf(
        Country(1, "Russia", R.drawable.ru),
        Country(2, "Antarctica", R.drawable.aq),
        Country(3, "Canada", R.drawable.ca),
        Country(4, "United States", R.drawable.us),
        Country(5, "China", R.drawable.cn),
        Country(6, "Brazil", R.drawable.br),
        Country(7, "Australia", R.drawable.au),
        Country(8, "India", R.drawable.`in`),
        Country(9, "Argentina", R.drawable.ar),
        Country(10, "Kazakhstan", R.drawable.kz),
        Country(11, "Algeria", R.drawable.dz),
        Country(12, "DR Congo", R.drawable.cd),
        Country(13, "Greenland", R.drawable.grnl),
        Country(14, "Saudi Arabia", R.drawable.sa),
        Country(15, "Mexico", R.drawable.mx),
        Country(16, "Indonesia", R.drawable.id),
        Country(17, "Sudan", R.drawable.sd),
        Country(18, "Libya", R.drawable.ly),
        Country(19, "Iran", R.drawable.ir),
        Country(20, "Mongolia", R.drawable.mn),
        Country(21, "Peru", R.drawable.pe),
        Country(22, "Chad", R.drawable.td),
        Country(23, "Niger", R.drawable.ne),
        Country(24, "Angola", R.drawable.ao),
        Country(25, "Mali", R.drawable.ml),
        Country(26, "South Africa", R.drawable.za),
        Country(27, "Colombia", R.drawable.co),
        Country(28, "Ethiopia", R.drawable.et),
        Country(29, "Bolivia", R.drawable.bo),
        Country(30, "Mauritania", R.drawable.mr)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAdapter()
    }

    private fun setAdapter() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = CountryAdapter(countryList)
    }
}