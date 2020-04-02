package br.com.zup.beagle.screen

import br.com.zup.beagle.action.Navigate
import br.com.zup.beagle.action.NavigationType
import br.com.zup.beagle.constant.*
import br.com.zup.beagle.ext.unitPercent
import br.com.zup.beagle.ext.unitReal
import br.com.zup.beagle.widget.core.*
import br.com.zup.beagle.widget.layout.Container
import br.com.zup.beagle.widget.layout.NavigationBar
import br.com.zup.beagle.widget.layout.Screen
import br.com.zup.beagle.widget.layout.ScreenBuilder
import br.com.zup.beagle.widget.ui.*

class CheckoutScreenBuilder : ScreenBuilder {

	override fun build() = Screen(
		navigationBar = NavigationBar(
			title = "Success", style = GRADIENT,
			showBackButton = false
		),
		content = Container(
			children = listOf(
				Container(
					children = listOf(
						Image(name = "SuccessEmoji").applyFlex(
							Flex(
								size = Size(
									width = 200.unitReal(),
									height = 200.unitReal()
								)
							)
						),
						Text(text = "Order successfully!", textColor = PINK, style = H1, alignment = TextAlignment.CENTER)
							.applyFlex(flex = Flex(
								margin = EdgeValue(top = 50.unitReal()))),
						Text(text = "All right with your order.", textColor = BLACK, style = H1, alignment = TextAlignment.CENTER)
					)
				).applyFlex(Flex(
					justifyContent = JustifyContent.CENTER,
					alignItems = Alignment.CENTER,
					grow = 1.0
				)),
				Button(
					text = "Go to Home",
					style = PRIMARY_BUTTON,
					action = Navigate(
						path = HOME_ENDPOINT,
						type = NavigationType.POP_TO_VIEW,
						shouldPrefetch = false
					)
				).applyFlex(Flex(
					size = Size(height = 70.unitReal(), width = 90.unitPercent()),
					margin = EdgeValue(bottom = 14.unitReal()),
					alignSelf = Alignment.CENTER
				))
			)
		).applyFlex(Flex(
			justifyContent = JustifyContent.SPACE_BETWEEN,
			alignItems = Alignment.CENTER,
			grow = 1.0
		))
	)
}