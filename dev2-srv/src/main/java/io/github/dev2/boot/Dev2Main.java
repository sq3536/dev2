package io.github.dev2.boot;

import io.github.dev2.config.liquibase.MasterProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(exclude = {
 org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
	    org.springframework.boot.autoconfigure.security.oauth2.OAuth2AutoConfiguration.class,
		DruidDataSourceAutoConfigure.class
	})
@ComponentScans({
@ComponentScan(basePackages = { "io.github.dev2" }

				),
})
@EnableAsync
@MapperScan("io.github.dev2.*.mapper")
@EnableConfigurationProperties({ MasterProperties.class})
public class Dev2Main extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(Dev2Main.class);
                builder.run(args) ;
	}

}