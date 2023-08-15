package com.example.fixitfolks.ui.providers.utils


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fixitfolks.R
import com.example.fixitfolks.databinding.RowBinding
import com.example.fixitfolks.models.Providers
import java.util.*

class ProviderAdapter(private val providerList: List<Providers>) : RecyclerView.Adapter<ProviderAdapter.ProviderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProviderViewHolder {
        val binding = RowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProviderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProviderViewHolder, position: Int) {
        val provider = providerList[position].provider
        val binding = holder.binding

        if (position == 0) {
            binding.providerImage.setImageResource(R.drawable.provider1)
        } else {
            binding.providerImage.setImageResource(R.drawable.rac)
        }
        binding.providerTitle.text = provider.provider_title
        binding.tvProviderName.text = provider.provider_username
        binding.tvProviderLocation.text = "${provider.landmark}, ${provider.address}"
        binding.providerRating.text = provider.rating.toString()
        binding.providerPriceRange.text = "${provider.min_price} onwards."

        val services = providerList[position].services.take(3).joinToString(" | ")
        binding.tvProviderServices.text = services
    }

    override fun getItemCount(): Int {
        return providerList.size
    }

    inner class ProviderViewHolder(val binding: RowBinding) : RecyclerView.ViewHolder(binding.root)
}
