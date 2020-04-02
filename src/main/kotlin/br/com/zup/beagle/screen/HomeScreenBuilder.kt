package br.com.zup.beagle.screen

import br.com.zup.beagle.action.Navigate
import br.com.zup.beagle.action.NavigationType
import br.com.zup.beagle.constant.*
import br.com.zup.beagle.core.Appearance
import br.com.zup.beagle.core.ServerDrivenComponent
import br.com.zup.beagle.dto.response.ProductResponse
import br.com.zup.beagle.ext.unitPercent
import br.com.zup.beagle.ext.unitReal
import br.com.zup.beagle.util.assemblePrice
import br.com.zup.beagle.widget.GradientWidget
import br.com.zup.beagle.widget.ProductCard
import br.com.zup.beagle.widget.core.*
import br.com.zup.beagle.widget.layout.*
import br.com.zup.beagle.widget.navigation.Touchable
import br.com.zup.beagle.widget.ui.Text
import br.com.zup.beagle.widget.ui.TextAlignment

class HomeScreenBuilder(private val products: List<ProductResponse>) : ScreenBuilder {
	companion object {
		private const val TITLE = "Coffee"
		private const val SUBTITLE = "The Science of Delicious."
		private const val DESCRIPTION = "Amazing coffees from around the world!"
		private const val CART_TEXT = "Carrinho"
		private const val RATIO = 188.0 / 249.0

		private val fullRelative = 100.unitPercent()
		private val smallMargin = 5.5.unitReal()
		private val largeMargin = 14.unitReal()
	}

	override fun build() = Screen(
		navigationBar = NavigationBar(
			title = TITLE,
			navigationBarItems = listOf(createCartIcon()),
			style = GRADIENT,
			showBackButton = false
		),
		content = Container(listOf(createProductCards()))
			.applyAppearance(Appearance(backgroundColor = GRAY)),
		safeArea = SafeArea(
			bottom = false
		)
	)

	private fun createProductCards() = ScrollView(
		children = listOf(createGradientWithText()) +
			this.products.mapIndexed(this::createProductCard).chunked(2).mapIndexed(this::createRowContainer),
		scrollBarEnabled = false
	)

	private fun createGradientWithText() = Container(
		listOf(
			GradientWidget()
				.applyFlex(
					Flex(
						positionType = FlexPositionType.ABSOLUTE,
						size = Size(width = fullRelative, height = fullRelative)
					)
				),
			createTexts()
		)
	).applyFlex(
		Flex(
			size = Size(width = fullRelative, height = 100.unitReal())
		)
	)

	private fun createTexts() = Container(
		listOf(
			Text(text = SUBTITLE, style = H1, alignment = TextAlignment.CENTER, textColor = WHITE),
			Text(text = DESCRIPTION, style = H3, alignment = TextAlignment.CENTER, textColor = WHITE)
		)
	).applyFlex(
		Flex(
			alignSelf = Alignment.CENTER,
			alignContent = Alignment.CENTER,
			justifyContent = JustifyContent.CENTER,
			positionType = FlexPositionType.ABSOLUTE,
			size = Size(width = fullRelative, height = fullRelative)
		)
	)

	private fun createProductCard(index: Int, product: ProductResponse) = Touchable(
		action = Navigate(
			path = PRODUCT_ENDPOINT.replace(ID_PLACEHOLDER, product.id.toString()),
			type = NavigationType.ADD_VIEW,
			shouldPrefetch = false
		),
		child = ProductCard(
			name = product.name,
			description = product.shortDescription,
			image = product.imageUrl,
			value = assemblePrice(product.price)
		).applyFlex(
			Flex(
				margin = EdgeValue(
					left = if (index % 2 == 0) largeMargin else smallMargin,
					right = if (index % 2 == 0) smallMargin else largeMargin
				),
				size = Size(aspectRatio = RATIO),
				grow = 1.0
			)
		).applyAppearance(Appearance(backgroundColor = WHITE))
	)

	private fun createCartIcon() = NavigationBarItem(
		image = "Cart",
		text = "Carrinho",
		action = Navigate(path = SUMMARY_ENDPOINT, type = NavigationType.ADD_VIEW)
	)

	private fun createRowContainer(index: Int, components: List<ServerDrivenComponent>) = Container(components)
		.applyFlex(
			Flex(
				flexDirection = FlexDirection.ROW,
				justifyContent = JustifyContent.SPACE_BETWEEN,
				size = Size(width = fullRelative),
				margin = if (index == 0) {
					EdgeValue(top = largeMargin, bottom = smallMargin)
				} else {
					EdgeValue(vertical = smallMargin)
				}
			)
		)
}
