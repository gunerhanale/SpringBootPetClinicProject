package com.javaegitimleri.petclinic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled=true,prePostEnabled=true,jsr250Enabled=true)   //Bu annostasyon method bazlı yetkilendirme için eklendi
public class SecurityConfiguration extends AbstractSecurityConfiguration {

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/**/favicon.ico", "/css/**", "/js/**", "images/**", "/webjars/**", "/login.html")
				.permitAll(); // Bazı uzantılar public edildi
		
		http.authorizeRequests().antMatchers("/actuator/**").access("hasRole('ADMIN')"); // Web sayfalarının
																							// yetkilendirilmesi
		http.authorizeRequests().anyRequest().authenticated(); // Web sayfalarının yetkilendirilmesi, geriye kalan tüm
																// sayfalar sadece auth. bağlandı.
		http.formLogin().loginPage("/login.html").loginProcessingUrl("/login")
				.failureUrl("/login.html?loginFailed=true"); // login sayfasını özelleştirdik
		http.rememberMe().userDetailsService(userDetailsService);

//		http.httpBasic();     rest api çağrıları için web servis headerında authentication için eklendi ama sadece GET metodları için çalışıyor.  
//							Daha sonra POST,DELETE ve PUT metotlarında güvenlik için csrf den dolayı bir Token beklediğinden ve güvenlik için tek başına 
//							yeterli olmadığından, buradan RestSecurityConfiguration class'ına immagrate ettik

	}

}
