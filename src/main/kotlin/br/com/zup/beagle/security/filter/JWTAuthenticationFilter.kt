package br.com.zup.beagle.security.filter

import br.com.zup.beagle.constant.ERROR_ENDPOINT
import br.com.zup.beagle.constant.LOGIN_ENDPOINT
import br.com.zup.beagle.security.util.AuthenticationUtil
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthenticationFilter(private val ignoredPathList: List<String> = listOf(LOGIN_ENDPOINT, ERROR_ENDPOINT)) : GenericFilterBean() {

	override fun doFilter(request: ServletRequest, response: ServletResponse, filterChain: FilterChain) {
		if (!this.ignoredPathList.contains((request as HttpServletRequest).requestURI)) {
			val authentication = AuthenticationUtil
				.getAuthentication(request, response as HttpServletResponse)
			SecurityContextHolder.getContext().authentication = authentication
		}
		filterChain.doFilter(request, response)
	}
}