package io.github.dev2.config;

import io.github.dev2.security.service.AuthenticationUserService;
import io.github.dev2.security.web.AuthenticationEntryPoint;
import io.github.dev2.security.web.AuthorizationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = false)
public class Dev2SecurityConfig extends WebSecurityConfigurerAdapter
{

    @Autowired
    private AuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private AuthenticationUserService userDetailsService;

    /**
     * 自定义基于JWT的安全过滤器
     */
    @Autowired
    AuthorizationTokenFilter authenticationTokenFilter;

    @Value("${dev2.jwt.header:Authorization}")
    private String tokenHeader;

    @Value("${dev2.custompermitpath:custompermit}")
    private String custompermitpath;

    @Value("${dev2.customanonymouspath:customanonymous}")
    private String customanonymouspath;

    @Value("${dev2.auth.path:login/login}")
    private String loginPath;

    @Value("${dev2.file.uploadpath:upload}")
    private String uploadpath;

    private final String defaultdownloadpath="file/download/{id}";
    @Value("${dev2.file.downloadpath:"+defaultdownloadpath+"}")
    private String downloadpath;

    @Value("${dev2.file.previewpath:file/preview}")
    private String previewpath;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoderBean());
    }

    @Bean
	GrantedAuthorityDefaults grantedAuthorityDefaults() {
        // Remove the ROLE_ prefix
        return new GrantedAuthorityDefaults("");
    }

    @Bean
    public PasswordEncoder passwordEncoderBean() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity

                // 禁用 CSRF
                .csrf().disable()

                // 授权异常
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()

                // 不创建会话
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                // 过滤请求
                .authorizeRequests()
                .antMatchers(
                        HttpMethod.GET,
                        "/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/**/*.ico",
                        "/**/assets/**",
                        "/**/css/**",
                        "/**/fonts/**",
                        "/**/js/**",
                        "/**/img/**",
                        "/"
                ).permitAll()

                .antMatchers( HttpMethod.POST,"/"+loginPath).permitAll()
                .antMatchers("/websocket/**").permitAll()
                .antMatchers("/ocr/**").permitAll()
                // 文件操作
                .antMatchers("/"+downloadpath).permitAll()
                .antMatchers("/**/"+uploadpath).permitAll()
                .antMatchers("/"+previewpath+"/**").permitAll()

                .antMatchers("/"+custompermitpath+"/**").permitAll()
                .antMatchers("/"+customanonymouspath+"/**").anonymous()
                .antMatchers("/**/codelist/**").permitAll()
                .antMatchers("/**/getappdata").permitAll()


                // 系统监控
                .antMatchers("/actuator/**").anonymous()

                // swagger start
                .antMatchers("/swagger-ui.html").anonymous()
                .antMatchers("/swagger-resources/**").anonymous()
                .antMatchers("/webjars/**").anonymous()
                .antMatchers("/*/api-docs").anonymous()
                // swagger end

                // 接口限流测试
                .antMatchers("/test/**").anonymous()
                .antMatchers(HttpMethod.OPTIONS, "/**").anonymous()

                .antMatchers("/druid/**").permitAll()
                // 所有请求都需要认证
                .anyRequest().authenticated()
                // 防止iframe 造成跨域
                .and().headers().frameOptions().disable();

        httpSecurity
                .addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
