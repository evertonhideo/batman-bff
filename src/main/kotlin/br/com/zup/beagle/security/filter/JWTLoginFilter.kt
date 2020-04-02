package br.com.zup.beagle.security.filter

import br.com.zup.beagle.action.Navigate
import br.com.zup.beagle.action.NavigationType
import br.com.zup.beagle.constant.HOME_ENDPOINT
import br.com.zup.beagle.constant.LOGIN_ENDPOINT
import br.com.zup.beagle.security.data.AccountCredentials
import br.com.zup.beagle.security.util.AuthenticationUtil
import br.com.zup.beagle.security.util.CustomAuthentication
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTLoginFilter(defaultFilterProcessesUrl: String = LOGIN_ENDPOINT,
					 authenticationManager: AuthenticationManager,
					 private val objectMapper: ObjectMapper) : AbstractAuthenticationProcessingFilter(defaultFilterProcessesUrl) {

	init {
		setAuthenticationManager(authenticationManager)
	}

	override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
		val credentials: AccountCredentials = this.objectMapper
			.readValue(request.inputStream, AccountCredentials::class.java)
		AuthenticationUtil.addAuthentication(response, credentials.username)
		return CustomAuthentication(credentials)
	}

	override fun successfulAuthentication(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain, authResult: Authentication) {
		response.status = HttpServletResponse.SC_OK
		this.objectMapper.writeValue(response.writer,
			Navigate(
				type = NavigationType.ADD_VIEW,
				path = HOME_ENDPOINT
			)
		)
	}
}