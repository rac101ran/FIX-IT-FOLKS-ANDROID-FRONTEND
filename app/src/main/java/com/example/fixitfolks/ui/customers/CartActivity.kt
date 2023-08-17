package com.example.fixitfolks.ui.customers

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fixitfolks.GenericAdapter
import com.example.fixitfolks.R
import com.example.fixitfolks.databinding.ActivityCartBinding
import com.example.fixitfolks.databinding.OrderCardItemBinding
import com.example.fixitfolks.models.*
import com.example.fixitfolks.network.SocketManager
import com.example.fixitfolks.network.SocketManager.mSocket
import com.example.fixitfolks.ui.providers.utils.ProgressBarLoading
import com.example.fixitfolks.viewModel.CustomerExplorerViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class CartActivity : AppCompatActivity() {
    private lateinit var cartActivityBinding: ActivityCartBinding
    private lateinit var customerExplorerViewModel: CustomerExplorerViewModel
    private lateinit var progressBarLoading: ProgressBarLoading
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cartActivityBinding = ActivityCartBinding.inflate(layoutInflater)

        customerExplorerViewModel = ViewModelProvider(this)[CustomerExplorerViewModel::class.java]

        progressBarLoading = ProgressBarLoading(this)




        setContentView(cartActivityBinding.root)

        val providerList: MutableList<Provider> = mutableListOf()

        val sharedPreferences = this.getSharedPreferences("admin", Context.MODE_PRIVATE)
        val providerJsonMap = sharedPreferences.getString("order providers", null)
        val type = object : TypeToken<HashMap<Int, Provider>>() {}.type
        val providerMap: HashMap<Int, Provider> = Gson().fromJson(providerJsonMap, type)

        // Log.e("message",providerMap.toString())

        var subTotal = 0.0
        var tax = 0.0
        val deliveryCharge = 10
        for ((key, value) in providerMap) {
            providerList.add(value)
            subTotal += (value.orders * value.min_price)
        }
        tax = subTotal * 0.18

        cartActivityBinding.deliveryId.text = deliveryCharge.toString()
        cartActivityBinding.subtotalId.text = subTotal.toString()
        cartActivityBinding.serviceTaxId.text = tax.toString()
        cartActivityBinding.totalOrderId.text = (subTotal + tax + deliveryCharge).toString()

        val adapter = GenericAdapter(
            providerList,
            bindingInflater = { layoutInflater, parent, attachToParent ->
                OrderCardItemBinding.inflate(layoutInflater, parent, attachToParent)
            },
            onBind = { binding, item ->
                val cartBinding = binding as OrderCardItemBinding
                cartBinding.providerAndItemId.text = item.provider_title
                cartBinding.qtyCardId.text = item.orders.toString()
                cartBinding.pricePerQtyId.text = item.min_price.toString()
                cartBinding.priceTotItem.text = (item.min_price * item.orders).toString()
                cartBinding.incQty.setOnClickListener {
                    item.orders++
                    cartBinding.priceTotItem.text = (item.min_price * item.orders).toString()
                    cartBinding.qtyCardId.text = item.orders.toString()
                    subTotal += item.min_price
                    tax = subTotal * 0.18
                    cartActivityBinding.subtotalId.text = subTotal.toString()
                    cartActivityBinding.serviceTaxId.text = tax.toString()
                    cartActivityBinding.totalOrderId.text =
                        (subTotal + tax + deliveryCharge).toString()
                }
                cartBinding.decQty.setOnClickListener {
                    if (item.orders > 0) item.orders--
                    cartBinding.priceTotItem.text = (item.min_price * item.orders).toString()
                    cartBinding.qtyCardId.text = item.orders.toString()
                    subTotal -= item.min_price
                    tax = subTotal * 0.18
                    cartActivityBinding.subtotalId.text = subTotal.toString()
                    cartActivityBinding.serviceTaxId.text = tax.toString()
                    cartActivityBinding.totalOrderId.text =
                        (subTotal + tax + deliveryCharge).toString()
                }
            },
        )

        val linearLayoutManager = LinearLayoutManager(this)
        cartActivityBinding.cartRecyclerViewId.layoutManager = linearLayoutManager
        cartActivityBinding.cartRecyclerViewId.adapter = adapter


        cartActivityBinding.orderNowId.setOnClickListener {

            progressBarLoading.startLoadingDialog()


            val customer =
                this.getSharedPreferences("admin", Context.MODE_PRIVATE).getInt("user_id", -1)
            val provider = providerList[0].provider_id
            val item = providerList[0].provider_item



            SocketManager.setSocket()
            SocketManager.establishConnection()

            val requestObject = JSONObject()
            requestObject.put("customer", JSONObject().put("customer_id", customer))
            requestObject.put("provider", JSONObject().put("provider_id", provider))
            requestObject.put("item", JSONObject().put("item_id", item))
            requestObject.put("order_cost", (subTotal + tax + deliveryCharge).toInt())

            mSocket?.emit("CUSTOMER_REQUEST",requestObject)

            sharedPreferences.edit().putString("order providers", null).apply()


            customerExplorerViewModel.viewModelScope.launch {
                delay(4000)
                try {
                    val apiResponse = customerExplorerViewModel.createOrderEventRequest(
                        customer,
                        provider,
                        item,
                        (subTotal + tax + deliveryCharge).toInt()
                    )
                    if (apiResponse != null && apiResponse.status.lowercase() == "success") {
                        // RETURN TO EXPLORER MAIN PAGE
                        withContext(Dispatchers.Main) {
                            Toast.makeText(applicationContext, "Order created successfully", Toast.LENGTH_SHORT).show()
                        }
                        sharedPreferences.edit().putString("order providers", null).apply()
                        progressBarLoading.dismissDialog()
                        startActivity(Intent(applicationContext, Homepage::class.java))
                    } else {
                        progressBarLoading.dismissDialog()
                        withContext(Dispatchers.Main) {
                            Toast.makeText(applicationContext, "An error occurred", Toast.LENGTH_SHORT).show()
                        }
                        startActivity(Intent(applicationContext, Homepage::class.java))
                    }
                } catch (e: Exception) {
                    progressBarLoading.dismissDialog()
                    Log.e("error:?", e.message.toString())
                    startActivity(Intent(applicationContext, Homepage::class.java))
                }
            }
        }

    }
}












//            val items : MutableList<Int> = mutableListOf()
//
//            items.add(item)
//
//            customerExplorerViewModel.viewModelScope.launch(Dispatchers.IO) {
//                try {
//                    val response = customerExplorerViewModel.createCustomerForItem(items,customer)
//                    if((response == null) || (response.status != "success")) {
//                         startActivity(Intent(this@CartActivity, Homepage::class.java))
//                    }
//                }catch(e : Exception) {
//                    Log.e("error:",e.message.toString())
//                }
//            }