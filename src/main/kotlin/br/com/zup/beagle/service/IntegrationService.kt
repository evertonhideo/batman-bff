package br.com.zup.beagle.service

import br.com.zup.beagle.action.Navigate
import br.com.zup.beagle.action.NavigationType
import br.com.zup.beagle.constant.HOME_ENDPOINT
import br.com.zup.beagle.constant.PRIMARY_BUTTON
import br.com.zup.beagle.screen.ErrorScreenBuilder
import br.com.zup.beagle.widget.layout.ScreenBuilder
import br.com.zup.beagle.widget.ui.Button
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class IntegrationService {
	companion object {
		private const val HOME_BUTTON = "Home"

		private val logger = LoggerFactory.getLogger(IntegrationService::class.java)
		private val button = Button(
			text = HOME_BUTTON,
			style = PRIMARY_BUTTON,
			action = Navigate(path = HOME_ENDPOINT, type = NavigationType.SWAP_VIEW)
		)
	}

	fun handleIntegration(button: Button = Companion.button, createBuilderFromIntegration: () -> ScreenBuilder) = try {
		createBuilderFromIntegration.invoke()
	} catch (exception: Exception) {
		logger.error("000000", exception)
		ErrorScreenBuilder(button)
	}
}