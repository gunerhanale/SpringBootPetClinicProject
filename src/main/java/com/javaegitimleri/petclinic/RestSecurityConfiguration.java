package com.javaegitimleri.petclinic;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Order(value = 1)
@Configuration
public class RestSecurityConfiguration extends AbstractSecurityConfiguration {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		super.configure(http);    Bu standart conf dosyası ekstra ilave bizim rest servsiler için istemediğimiz(for instance formLogin etc.) özellikler katıyor
		
		http.antMatcher("/rest/**"); // Artık bu RestSecurityConfiguration'u sadece rest prefix'i ile başlayan request url lerinde geçerli olacak
		http.authorizeRequests().antMatchers("/rest/**").access("hasRole('EDITOR')"); // rest Web Servisleri yetkilendirilmesi
		http.csrf().disable();
		http.httpBasic();
	}

}
