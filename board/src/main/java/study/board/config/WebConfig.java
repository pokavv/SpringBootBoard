package study.board.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import study.board.interceptor.LoginCheckInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor()) // 인터셉터 등록
                .order(1) // 호출 순서 지정
                .addPathPatterns("/**") // 적용할 URL 패턴
                .excludePathPatterns(
                        "/", "/adduser", "/login", "logout", "/board", "/*.ico", "/css/**", "/error"
                ); // 인터셉터를 적용하지 않을 예외 패턴 지정
    }
}

/*
- PathPattern matches URL paths using the following rules:
 1. ? matches one character
 2. * matches zero or more characters within a path segment
 3. ** matches zero or more path segments until the end of the path
 4. {spring} matches a path segment and captures it as a variable named "spring"
 5. {spring:[a-z]+} matches the regexp [a-z]+ as a path variable named "spring"
 6. {*spring} matches zero or more path segments until the end of the path and captures it as a variable named "spring"

 출처: docs.spring.io
 */