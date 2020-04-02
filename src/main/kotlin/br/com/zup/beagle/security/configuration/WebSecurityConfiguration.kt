package br.com.zup.beagle.security.configuration

import br.com.zup.beagle.constant.LOGIN_ENDPOINT
import br.com.zup.beagle.security.filter.JWTAuthenticationFilter
import br.com.zup.beagle.security.filter.JWTLoginFilter
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class WebSecurityConfiguration(private val objectMapper: ObjectMapper) : WebSecurityConfigurerAdapter() {

	override fun configure(http: HttpSecurity) {
		http.csrf()
			.disable()
			.authorizeRequests()
			.antMatchers(LOGIN_ENDPOINT).permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.addFilterBefore(
				JWTLoginFilter(authenticationManager = authenticationManager(), objectMapper = this.objectMapper),
				UsernamePasswordAuthenticationFilter::class.java
			)
			.addFilterBefore(JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
	}
}