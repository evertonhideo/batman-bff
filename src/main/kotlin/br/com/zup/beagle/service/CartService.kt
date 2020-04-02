package br.com.zup.beagle.service

import br.com.zup.beagle.action.Action
import br.com.zup.beagle.action.Navigate
import br.com.zup.beagle.action.NavigationType
import br.com.zup.beagle.action.ShowNativeDialog
import br.com.zup.beagle.api.HackathonCartApi
import br.com.zup.beagle.constant.ID_PLACEHOLDER
import br.com.zup.beagle.constant.PRODUCT_ENDPOINT
import br.com.zup.beagle.dto.request.CartRequest
import br.com.zup.beagle.dto.request.ItemRequest
import br.com.zup.beagle.security.data.UserData
import br.com.zup.beagle.security.util.AuthenticationUtil
import feign.FeignException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.net.HttpURLConnection

@Service
class CartService(private val cartApi: HackathonCartApi) {
	companion object {
		private const val BACK_LABEL = "Back"

		private val logger = LoggerFactory.getLogger(CartService::class.java)
	}

	fun addToCart(sku: String, quantity: Int): Action {
		val userData = AuthenticationUtil.getCurrentUserData()
		val cartId = userData.cartId
		val item = ItemRequest(sku, quantity)

		if (cartId.isNullOrEmpty()) {
			createCart(userData, item)
			return createSuccessDialog(quantity, sku)
		}

		val foundCart = try {
			cartApi.getCart(cartId)
			true
		} catch (e: FeignException) {
			if (e.status() == HttpURLConnection.HTTP_NOT_FOUND) {
				false
			} else {
				return createErrorDialog("Could not find cart...", e)
			}
		}

		return try {
			if (foundCart) {
				logger.info("cart encontrado")
				cartApi.addItemToCart(cartId, item)
			} else {
				logger.info("cart nao encontrado")
				createCart(userData, item)
			}

			createSuccessDialog(quantity, sku)
		} catch (e: Exception) {
			createErrorDialog("Could not add your $quantity $sku...", e)
		}
	}

//	private fun createSuccessDialog(quantity: Int, sku: String) =
//		ShowNativeDialog(title = "Success", message = "Added $quantity $sku to your cart.", buttonText = BACK_LABEL)

	private fun createSuccessDialog(quantity: Int, sku: String) =
		Navigate(
			type = NavigationType.POP_VIEW
		)

	private fun createErrorDialog(reason: String, exception: Exception): ShowNativeDialog {
		logger.error(reason, exception)
		return ShowNativeDialog(title = "Error", message = reason, buttonText = BACK_LABEL)
	}

	private fun createCart(userData: UserData, item: ItemRequest) {
		val response = cartApi.createCart(CartRequest(userData.name, item))
		userData.cartId = response.id.toString()
	}
}