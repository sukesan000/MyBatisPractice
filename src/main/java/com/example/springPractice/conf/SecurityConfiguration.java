package com.example.springPractice.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//configurationを行うクラス
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //ユーザ情報はインメモリで管理
        //ハッシュ化されたパスワードをメモリで管理
        auth.inMemoryAuthentication()
                .withUser("root").password(passwordEncoder().encode("password")).roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //アクセス制御
        //全てのリクエストに対して認証済み（ログイン済み）でないとアクセスできない
        //httpBasic パスワードを要求するダイアログを出す
        http.authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    //パスワードをハッシュ化（暗号化？）
    //@Bean あらゆる場所からこのメソッドを呼び出せるよ！
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
