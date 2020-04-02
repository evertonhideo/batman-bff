package br.com.zup.beagle.screen

import br.com.zup.beagle.action.Navigate
import br.com.zup.beagle.action.NavigationType
import br.com.zup.beagle.constant.*
import br.com.zup.beagle.core.Appearance
import br.com.zup.beagle.core.ServerDrivenComponent
import br.com.zup.beagle.dto.response.CartResponse
import br.com.zup.beagle.dto.response.ItemResponse
import br.com.zup.beagle.ext.unitPercent
import br.com.zup.beagle.ext.unitReal
import br.com.zup.beagle.util.assemblePrice
import br.com.zup.beagle.widget.core.*
import br.com.zup.beagle.widget.layout.*
import br.com.zup.beagle.widget.ui.Button
import br.com.zup.beagle.widget.ui.NetworkImage
import br.com.zup.beagle.widget.ui.Text
import java.math.BigDecimal

class SummaryScreenBuilder(private val cartResponse: CartResponse) : ScreenBuilder {

	override fun build() = Screen(
		navigationBar = NavigationBar(
			title = "Summary", showBackButton = true, style = GRADIENT
		),
		content = Container(
			children = renderSummary()
		).applyFlex(Flex(
			grow = 1.0,
			justifyContent = JustifyContent.SPACE_BETWEEN))
	)

	private fun renderSummary(): List<ServerDrivenComponent> {
		val totalValue = this.cartResponse.items.map { BigDecimal.valueOf(it.price, it.scale) }
			.fold(BigDecimal.ZERO, BigDecimal::add)
		return listOf(
			createScrollViewContainer(),
			createButtonsContainer()
		)
	}

	private fun createScrollViewContainer() = Container(listOf(
		ScrollView(
			children = this.cartResponse.items.map(this::createScrollViewItem)
		)
	)).applyFlex(Flex(
		grow = 1.0)
	)

	private fun createScrollViewItem(itemResponse: ItemResponse) = Container(
		children = listOf(
			Container(
				children = listOf(
					Container(
						children = listOf(
							NetworkImage(path = itemResponse.imageUrl)
						)
					).applyFlex(
						Flex(
							size = Size(
								width = 120.unitReal(), height = 120.unitReal()
							),
							justifyContent = JustifyContent.CENTER,
							alignItems = Alignment.CENTER,
							margin = EdgeValue(left = 25.unitReal(), right = 25.unitReal())
						)
					),
					Container(
						listOf(
							Text(
								text = itemResponse.name,
								textColor = BLACK,
								style = H1
							),
							Text(
								text = assemblePrice(itemResponse.price, itemResponse.scale, itemResponse.currencyCode),
								textColor = PINK,
								style = H2
							),
							Text(
								text = "Your order is ready",
								textColor = ORANGE,
								style = H2
							)
						)
					)
				)
			).applyFlex(
				Flex(
					flexDirection = FlexDirection.ROW,
					size = Size(height = UnitValue(value = 180.0, type = UnitType.REAL)),
					justifyContent = JustifyContent.FLEX_START,
					alignItems = Alignment.CENTER,
					margin = EdgeValue(bottom = 1.unitReal())
				)
			).applyAppearance(Appearance(backgroundColor = WHITE))
		)
	).applyAppearance(appearance = Appearance(backgroundColor = GRAY))

	private fun createButtonsContainer() = Container(
		children = listOf(
			Button(
				"Pay",
				style = PRIMARY_BUTTON,
				action = Navigate(
					path = CHECKOUT_ENDPOINT,
					type = NavigationType.ADD_VIEW,
					shouldPrefetch = false
				)
			).applyFlex(Flex(
				size = Size(height = 70.unitReal(), width = 90.unitPercent())
			)),
			Button(
				"Continue Shopping",
				style = SECONDARY_BUTTON,
				action = Navigate(
					type = NavigationType.POP_VIEW
				)
			).applyFlex(Flex(
				size = Size(height = 70.unitReal(), width = 90.unitPercent())
			))
		)
	).applyFlex(Flex(
		justifyContent = JustifyContent.SPACE_EVENLY,
		alignItems = Alignment.CENTER,
		size = Size(height = 170.unitReal()),
		shrink = 0.0
	))
}