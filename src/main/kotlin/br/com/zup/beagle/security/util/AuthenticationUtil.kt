package br.com.zup.beagle.security.util

import br.com.zup.beagle.constant.EXPIRATION_TIME
import br.com.zup.beagle.constant.HEADER_STRING
import br.com.zup.beagle.constant.SECRET
import br.com.zup.beagle.constant.TOKEN_PREFIX
import br.com.zup.beagle.security.data.AccountCredentials
import br.com.zup.beagle.security.data.UserData
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.client.HttpClientErrorException
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.collections.HashMap

object AuthenticationUtil {

	private val loggedUsers = HashMap<String, UserData>()
	private val logger = LoggerFactory.getLogger(AuthenticationUtil::class.java)

	fun addAuthentication(response: HttpServletResponse, username: String) {
		try {
			val token = Jwts.builder()
				.setSubject(username)
				.setExpiration(Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.compact()
			addTokenHeaderToResponse(token, response)
			addUserToLoggedUsersMap(username)
		} catch (exception: Exception) {
			this.logger.error("000000", exception)
			throwUnauthorizedException()
		}
	}

	private fun addTokenHeaderToResponse(token: String, response: HttpServletResponse) {
		val jwtToken = token.replace(TOKEN_PREFIX, StringUtils.EMPTY)
		response.addHeader(HEADER_STRING, "$TOKEN_PREFIX $jwtToken")
	}

	fun getAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication? {
		try {
			val token = request.getHeader(HEADER_STRING)
			if (token != null) {
				val user = Jwts.parser()
					.setSigningKey(SECRET)
					.parseClaimsJws(token.replace(TOKEN_PREFIX, StringUtils.EMPTY))
					.body
					.subject
				if (user != null) {
					addTokenHeaderToResponse(token, response)
					addUserToLoggedUsersMap(user)
					return CustomAuthentication(AccountCredentials(user))
				}
			}
			//todo remove after mobile implements login
			else {
				addTokenHeaderToResponse("teste", response)
				addUserToLoggedUsersMap("teste")
				return CustomAuthentication(AccountCredentials("teste"))
			}
		} catch (exception: Exception) {
			this.logger.error("000000", exception)
		}
		throwUnauthorizedException()
	}

	fun getCurrentUserData() = SecurityContextHolder.getContext().authentication.principal as UserData

	private fun addUserToLoggedUsersMap(username: String) = this.loggedUsers.putIfAbsent(username, UserData(username))

	fun getUserData(username: String) = this.loggedUsers[username]

	private fun throwUnauthorizedException(): Nothing = throw HttpClientErrorException(HttpStatus.UNAUTHORIZED)
}