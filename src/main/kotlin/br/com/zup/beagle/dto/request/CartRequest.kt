package br.com.zup.beagle.dto.request

data class CartRequest(
        val customerId: String,
        val item: ItemRequest
)

data class ItemRequest(
        val sku: String,
        val quantity: Int
)