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

class ErrorScreenBuilder(private val button: Button) : ScreenBuilder {

	override fun build() = Screen(
		navigationBar = NavigationBar(
			title = "Success", style = GRADIENT
		),
		content = Container(
			children = listOf(
				Image(name = "FailEmoji").applyFlex(
					Flex(
						size = Size(
							width = 200.unitReal(),
							height = 200.unitReal()
						)
					)
				),
				Text(text = "Oops!", textColor = PINK, style = H1, alignment = TextAlignment.CENTER)
					.applyFlex(flex = Flex(margin = EdgeValue(top = 50.unitReal()))),
				Text(text = "Something wrong happened.", textColor = BLACK, style = H1, alignment = TextAlignment.CENTER),
				Button(
					text = "Go to Home",
					style = PRIMARY_BUTTON,
					action = Navigate(
						path = HOME_ENDPOINT,
						type = NavigationType.SWAP_VIEW,
						shouldPrefetch = false
					)
				).applyFlex(Flex(
					position = EdgeValue(bottom = 1.unitReal()),
					positionType = FlexPositionType.ABSOLUTE,
					size = Size(width = 95.unitPercent(), height = 70.unitReal())
				))
			)
		).applyFlex(Flex(
			justifyContent = JustifyContent.FLEX_START,
			alignItems = Alignment.CENTER,
			alignContent = Alignment.CENTER,
			grow = 1.0,
			margin = EdgeValue(top = 150.unitReal())
		))
	)
}