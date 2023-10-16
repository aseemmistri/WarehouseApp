package in.nareshit.raghu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	    @Bean
	    DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder);
		return provider;
	}
	
	    @Bean
	    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();	
	}
	
	/*@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
	    .authorizeRequests()
		.requestMatchers("/user**").hasAuthority("ADMIN")
		.requestMatchers("/uom**","/om**","/st**","/wh**","/part").hasAnyAuthority("ADMIN","APPUSER")
		.requestMatchers("/po**","/grn**","/so**","/shipping**").hasAuthority("APPUSER")
		.anyRequest().authenticated()
	    .and()
		.formLogin()
		.defaultSuccessUrl("/uom/all", true)
		.and()
		.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
		return httpSecurity.build();
		
	}*/
	    @Bean
	    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		.authorizeHttpRequests((request)-> request
				.requestMatchers("/user/showLogin","/user/showForgotPwd","/user/newPwdGen").permitAll()
				.requestMatchers("/user/showactivateUserotp","/user/activatebyOtp","/payment/PaymentPage").permitAll()
				.requestMatchers("/user**","/user/createOrder").hasAuthority("ADMIN")
				.requestMatchers("/uom**","/om**","/st**","/wh**","/part**").hasAnyAuthority("ADMIN","APPUSER")
				.requestMatchers("/po**","/grn**","/so**","/shipping").hasAuthority("APPUSER")
				.anyRequest().authenticated())
		.formLogin((form)-> form
				.loginPage("/user/showLogin")//to display login page
				.loginProcessingUrl("/login")//<form action="">
				.defaultSuccessUrl("/user/setup", true)//on login success
				.failureUrl("/user/showLogin?error"))//on login fail
		.logout((logout)->logout //logout activation
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))//login link
				.logoutSuccessUrl("/user/showLogin?success"));//on logout success
		
		http.authenticationProvider(daoAuthenticationProvider());
		
		return http.build();
	}
	
	
	

}
