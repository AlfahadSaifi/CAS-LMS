package org.nucleus.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    @Autowired

    public SecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT username, password, enabled FROM users_final_project_batch_six WHERE username=?")
                .authoritiesByUsernameQuery("SELECT username, CONCAT('ROLE_', role) as role FROM authorities_final_project_batch_six WHERE username=?")
                .passwordEncoder(passwordEncoder());
    }
    //        create table users_final_project_batch_six(
//                username varchar(100) primary key,
//                password varchar(100) not null,
//                enabled number(1) not null
//        );
//        create table authorities_final_project_batch_six(
//                username varchar(100),
//                role varchar(100)
//        );
//
//        ALTER TABLE authorities_final_project_batch_six
//        ADD CONSTRAINT fk_username_authorities_final_project_batch_six
//        FOREIGN KEY (username)
//                REFERENCES users_final_project_batch_six(username);
//
//        insert into users_final_project_batch_six values ('maker', '$2a$10$P2rI4EMMz3N6wARCD88quO2CZCTyKGrVl/HkFS6hdxBFTH1iTRaHG', 1);
//        insert into users_final_project_batch_six values ('checker', '$2a$10$qh0o39qd8mU841UfVSKvve9eiGJjkuiG1SYAd6b.yQyk1FgWuXxT.', 1);
//        insert into users_final_project_batch_six values ('author', '$2a$10$Xt0Q9Zmd.03EugCuFoaGrOqTtxqiSQyUrA5xFNh2ZD8eDdjR4jctK', 1);
//
//        insert into authorities_final_project_batch_six values ('maker', 'MAKER');
//        insert into authorities_final_project_batch_six values ('checker', 'CHECKER');

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").hasAnyRole("MAKER", "CHECKER")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error=true")
                .and()
                .logout()
                .logoutSuccessUrl("/login?logout=true")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/access-denied")
                .and()
                .csrf().disable();
    }
}