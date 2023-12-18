package es.uca.iw;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import es.uca.iw.views.login.LoginView;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends VaadinWebSecurity {
    public static final String LOGOUT_URL = "/logout";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth.requestMatchers(new AntPathRequestMatcher("/images/*.png")).permitAll());
        
        // http.csrf().ignoringRequestMatchers(new AntPathRequestMatcher("/api/*"));
        // http.authorizeRequests()
        //     .requestMatchers(
        //         new AntPathRequestMatcher("/imgages/*"),
        //         new AntPathRequestMatcher("/swagger-ui.html"),
        //         new AntPathRequestMatcher("/swagger-ui/**"))
        //     .permitAll()
        //     .requestMatchers(
        //         new AntPathRequestMatcher("/api/**"))
        //     .authenticated();

        super.configure(http); 
        setLoginView(http, LoginView.class, LOGOUT_URL);
    }
}
