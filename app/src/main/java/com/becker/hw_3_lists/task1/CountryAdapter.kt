package com.becker.hw_3_lists.task1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.becker.hw_3_lists.databinding.ItemCountryBinding

class CountryAdapter(
    private val countries: List<Country>
) : RecyclerView.Adapter<CountryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCountryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = countries.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(countries[position])
    }

    class ViewHolder(
        private val binding: ItemCountryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Country) {
            binding.apply {
                countryFlagImage.load(item.countryFlag)
                countryNameText.text = item.name
                idTextView.text = item.id.toString()
            }
        }
    }
}