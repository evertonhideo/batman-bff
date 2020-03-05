package br.com.zup.beagle.sample.widget

import br.com.zup.beagle.annotation.RegisterWidget
import br.com.zup.beagle.widget.Widget

@RegisterWidget
data class ProductWidget(
        val ID: String,
        val sku: String,
        val name: String,
        val shortDescription: String,
        val longDescription: String,
        val imageUrl: String,
        val price: String
) : Widget()