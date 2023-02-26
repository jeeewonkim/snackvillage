package Snacks.jsoupWebCrawling.User.Security;

import Snacks.jsoupWebCrawling.Repository.UserRepository;
import Snacks.jsoupWebCrawling.User.Service.PrincipalDetailsService;
import Snacks.jsoupWebCrawling.User.Service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;

import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private final CustomAuthenticationProvider customAuthenticationProvider;

    private final PrincipalDetailsService principalDetailsService;

    private final PasswordEncoder passwordEncoder;

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    private final ObjectMapper objectMapper;
    private final DataSource dataSource;

    private final UserRepository userRepository;

    private final UserServiceImpl userService;

    public SecurityConfig(CustomAuthenticationProvider customAuthenticationProvider, PrincipalDetailsService principalDetailsService, PasswordEncoder passwordEncoder, CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler, CustomAuthenticationFailureHandler customAuthenticationFailureHandler, ObjectMapper objectMapper, DataSource dataSource, UserRepository userRepository, UserServiceImpl userService) {
        this.customAuthenticationProvider = customAuthenticationProvider;
        this.principalDetailsService = principalDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
        this.customAuthenticationFailureHandler = customAuthenticationFailureHandler;
        this.objectMapper = objectMapper;
        this.dataSource = dataSource;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring().antMatchers("/css/**", "/script/**", "image/**", "/fonts/**", "lib/**");
    }


    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() //csrf 공격을 막아주는 옵션을 disable
                .cors().disable()
                //.httpBasic().disable()

                .formLogin().disable()
                .headers().frameOptions().disable()
                .and()
                .addFilterAfter(customAuthenticationFilter(), CsrfFilter.class);


/*
        http
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true);*/



        //http.authorizeRequests().anyRequest().rememberMe(); =>이거 있으면 로그아웃 403

    }
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.authenticationProvider(customAuthenticationProvider);
    }


    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter() throws Exception{
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter(objectMapper, customAuthenticationFailureHandler, customAuthenticationSuccessHandler, userService);
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;

    }
    @Bean
    public CustomAuthenticationProvider authenticationProvider(){
        return  new CustomAuthenticationProvider(passwordEncoder, principalDetailsService);
    }
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

}
