package br.com.zup.beagle.widget

import br.com.zup.beagle.annotation.RegisterWidget

@RegisterWidget
data class ProductCard (
	val name: String,
	val description: String,
	val image: String,
	val value: String
) : Widget()