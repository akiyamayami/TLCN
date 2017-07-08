package com.tlcn.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.tlcn.security.CustomAccessDeniedHandler;
import com.tlcn.security.MySimpleUrlAuthenticationSuccessHandler;
import com.tlcn.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
    private UserService userDetailsService;
	
	@Autowired
	private MySimpleUrlAuthenticationSuccessHandler mySimpleUrlAuthenticationSuccessHandler;
	
	@Autowired
	private CustomAccessDeniedHandler customAccessDeniedHandler;
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
	
	@Override
    protected void configure(final HttpSecurity http) throws Exception {
		http
		.csrf().disable()
			.authorizeRequests()
			.antMatchers("/login").permitAll()
			.antMatchers("/").hasAuthority("FIND_PROPOSAL")
			.antMatchers("/confirm-proposal").hasAuthority("CONFIRM_PROPOSAL")
			.antMatchers("/change-proposal").hasAuthority("CHANGE_PROPOSAL")
			.antMatchers("/create-proposal").hasAuthority("CREATE_PROPOSAL")
			.antMatchers("/check-stt-cars","/find-cars").hasAuthority("CHECK_OR_FIND_CARS")
			.antMatchers("/invalidSession*").anonymous()
		.and()
		.formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/")
			.failureUrl("/login?error=true")
			.successHandler(mySimpleUrlAuthenticationSuccessHandler)
			.permitAll()
        .and()
		.logout()
			.logoutUrl("/j_spring_security_logout")
			.logoutSuccessUrl("/logout?logSucc=true")
	        .invalidateHttpSession(false)
	        .deleteCookies("JSESSIONID")
        .permitAll()
        .and()
		.sessionManagement()
	        .invalidSessionUrl("/invalidSession")
	        .maximumSessions(1).sessionRegistry(sessionRegistry()).and()
            .sessionFixation().none()
        .and()
        .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler);
	}
	
	
	@Bean
    public PasswordEncoder passwordEncoder() {
	        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	        System.out.println(passwordEncoder.encode("123456"));
	        return passwordEncoder;
    }
	@Bean
	public AccessDeniedHandler accessDeniedHandler(){
	    return new CustomAccessDeniedHandler();
	}
	@Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
}
