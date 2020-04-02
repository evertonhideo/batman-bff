package br.com.zup.beagle.screen

import br.com.zup.beagle.action.Navigate
import br.com.zup.beagle.action.NavigationType
import br.com.zup.beagle.constant.*
import br.com.zup.beagle.core.Appearance
import br.com.zup.beagle.dto.response.ProductResponse
import br.com.zup.beagle.ext.unitPercent
import br.com.zup.beagle.ext.unitReal
import br.com.zup.beagle.util.assembleEndpoint
import br.com.zup.beagle.util.assemblePrice
import br.com.zup.beagle.widget.QuantitySelector
import br.com.zup.beagle.widget.core.*
import br.com.zup.beagle.widget.form.Form
import br.com.zup.beagle.widget.form.FormInput
import br.com.zup.beagle.widget.form.FormMethodType
import br.com.zup.beagle.widget.form.FormSubmit
import br.com.zup.beagle.widget.layout.*
import br.com.zup.beagle.widget.ui.Button
import br.com.zup.beagle.widget.ui.NetworkImage
import br.com.zup.beagle.widget.ui.Text
import br.com.zup.beagle.widget.ui.TextAlignment

class ProductScreenBuilder(private val product: ProductResponse) : ScreenBuilder {
	companion object {
		private val margin = 30.unitReal()
	}

	override fun build() = Screen(
		navigationBar = NavigationBar(title = this.product.name, style = GRADIENT),
		content = Form(
			path = assembleEndpoint(CART_ENDPOINT, mapOf(SKU_PARAM to product.sku)),
			method = FormMethodType.POST,
			child = Container(
				listOf(
					createProductContainer(),
					createAddCartFormSubmit()
				)
			).applyFlex(
				Flex(
					justifyContent = JustifyContent.SPACE_BETWEEN,
					grow = 1.0
				)
			).applyAppearance(Appearance(backgroundColor = WHITE))
		)
	)

	private fun createProductContainer() = Container(
		listOf(
			createProductInfo(),
			createQuantityContainer()
		)
	).applyFlex(
		Flex(
			grow = 1.0,
			justifyContent = JustifyContent.FLEX_START
		)
	)

	private fun createProductInfo() = Container(
		listOf(
			createImage(),
			createNameAndPrice(),
			Text(
				text = "A deliciously creamy Coffee Kick Frappé, topped with irresistible whipped cream and indulgent Coffee Drizzle. Perfect combination.",
				style = H2,
				alignment = TextAlignment.CENTER,
				textColor = "919191"
			).applyFlex(Flex(
				margin = EdgeValue(horizontal = margin, top = margin)
			))
		)
	).applyFlex(
		Flex(
			grow = 1.0,
			justifyContent = JustifyContent.FLEX_START
		)
	)

	private fun createQuantityContainer() = Container(
		listOf(
			FormInput(
				child = QuantitySelector().applyFlex(Flex(
					margin = EdgeValue(
						top = UnitValue(value = 1.0, type = UnitType.REAL),
						bottom = UnitValue(value = 1.0, type = UnitType.REAL)
					)
				)).applyAppearance(Appearance(backgroundColor = "#FFFFFF")),
				name = "quantity",
				validator = QUANTITY_VALIDATOR,
				required = true
			)
		)
	).applyFlex(
		Flex(
			justifyContent = JustifyContent.CENTER,
			alignItems = Alignment.STRETCH,
			size = Size(maxHeight = 80.unitReal()),
			margin = EdgeValue(top = 20.unitReal(), bottom = 20.unitReal())
		)
	).applyAppearance(Appearance(backgroundColor = "#C1C1C1"))

	private fun createImage() = NetworkImage(product.imageUrl, ImageContentMode.FIT_CENTER)
		.applyFlex(
			Flex(
				alignSelf = Alignment.CENTER,
				size = Size(
					width = 60.unitPercent(),
					height = 40.unitPercent()
				),
				margin = EdgeValue(top = 20.unitReal())
			)
		)

	private fun createNameAndPrice() = Container(
		listOf(
			Text(
				text = product.name,
				style = H1,
				textColor = BLACK,
				alignment = TextAlignment.LEFT
			).applyFlex(flex = Flex(grow = 1.0)),
			Text(
				text = assemblePrice(product.price),
				style = H1,
				textColor = PINK,
				alignment = TextAlignment.RIGHT
			).applyFlex(flex = Flex(grow = 1.0))
		)
	).applyFlex(
		Flex(
			flexDirection = FlexDirection.ROW,
			justifyContent = JustifyContent.SPACE_BETWEEN,
			margin = EdgeValue(horizontal = margin, top = margin)
		)
	)

	private fun createAddCartFormSubmit() = FormSubmit(
		child = Button(text = "Add to cart", style = PRIMARY_BUTTON).applyFlex(Flex(
			size = Size(height = UnitValue(value = 70.0, type = UnitType.REAL)),
			margin = EdgeValue(
				start = 14.unitReal(),
				end = 14.unitReal(),
				bottom = 25.unitReal()
			)
		)),
		enabled = false
	)
}