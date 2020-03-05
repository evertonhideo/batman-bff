package br.com.zup.beagle.sample.models

data class Product(
        val id: String,
        val sku: String,
        val name: String,
        val shortDescription: String,
        val longDescription: String,
        val imageUrl: String,
        val price: Price
)