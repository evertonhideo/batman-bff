package br.com.zup.beagle.security.util

import br.com.zup.beagle.security.data.AccountCredentials
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import java.util.*

class CustomAuthentication(private val credentials: AccountCredentials) : Authentication {

	private val userData = AuthenticationUtil.getUserData(this.credentials.username)!!

	override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
		return Collections.emptyList()
	}

	override fun setAuthenticated(authenticated: Boolean) {
		//do nothing
	}

	override fun getName() = this.userData.name

	override fun getCredentials() = this.credentials

	override fun getPrincipal() = this.userData

	override fun isAuthenticated() = true

	override fun getDetails() = this.userData
}

