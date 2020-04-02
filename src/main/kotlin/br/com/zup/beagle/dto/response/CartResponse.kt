package br.com.zup.beagle.dto.response

data class CartResponse(
        val id: String,
        val customerId: String,
        val status: String,
        val items: List<ItemResponse>,
        val total: PriceResponse
)

data class ItemResponse(
        val id: String,
        val name: String,
        val imageUrl: String,
        val price: Long,
        val scale: Int,
        val currencyCode: String,
        val quantity: Int
)
