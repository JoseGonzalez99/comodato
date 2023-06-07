package bo.com.tigo.comodato;


import bo.com.tigo.comodato.shared.util.AESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    @Value("${basic.auth.username}")
    private String username;

    @Value("${basic.auth.password}")
    private String password;

    @Value("${basic.auth.encrypted}")
    private boolean isEncrypted;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").authenticated()
                .anyRequest().permitAll()
                .and()
                .httpBasic()
                .and()
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringRequestMatchers(new AntPathRequestMatcher("/**"));
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        /**Se verifica si los datos estan encriptados**/
        if (isEncrypted) {
            String decryptedUsername = AESUtil.decrypt(username, "3C0mm3rc3_R3n0_*");
            String decryptedPassword = AESUtil.decrypt(password, "3C0mm3rc3_R3n0_*");

            auth.inMemoryAuthentication()
                    .withUser(decryptedUsername)
                    .password("{noop}" + decryptedPassword)
                    .roles("USER");
        } else {
            auth.inMemoryAuthentication()
                    .withUser(username)
                    .password("{noop}" + password)
                    .roles("USER");
        }
    }
}

