package com.example.animalplatform;



import com.example.animalplatform.session.config.SessionUserCheckResolver;
import com.example.animalplatform.session.config.SessionUserInterceptor;
import com.example.animalplatform.utils.ObjectMappingUtil;
import com.example.animalplatform.utils.xssFilter.XSSFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AnimalPlatformWebConfig implements WebMvcConfigurer {

    //외부와 데이터 통신시 암호화를 위한 객체
//    private final Encryption encryption;
    private final ObjectMappingUtil objectMappingUtil;

    private static final String[] EXTERNAL_API_PATH = {
            // 외부 API Path
//            "/api/v1/ums/sendPush",
    };

    private static final String[] SESSION_API_PATH = {
            // 로그인 session이 필요한 API Path
//            "/api/v1/family/**",

    };

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new SessionUserCheckResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 인증 토큰 절차가 필요한 외부 제공 API Path를 기입
//        registry.addInterceptor(new ExternalApiRequestInterceptor(encryption, objectMappingUtil))
//                .addPathPatterns(EXTERNAL_API_PATH);
        // 로그인이 필요한 REST API 의 Path를 기입하여 로그인을 체크
        registry.addInterceptor(new SessionUserInterceptor(objectMappingUtil))
                // NOTE : 경로추가를 하지 않을 경우 전체 경로에 적용된다
                .addPathPatterns(SESSION_API_PATH);
    }

    // XSS Filter
//    @Bean
//    public MappingJackson2HttpMessageConverter jsonEscapeConverter(HtmlCharacterEscapes htmlCharacterEscapes) {
//        ObjectMapper copy = objectMapper.copy();
//        copy.getFactory().setCharacterEscapes(htmlCharacterEscapes);
//        return new MappingJackson2HttpMessageConverter(copy);
//    }

    @Bean
    public FilterRegistrationBean<XSSFilter> xssFilter() {
        FilterRegistrationBean<XSSFilter> filter = new FilterRegistrationBean<>();
        filter.setFilter(new XSSFilter());
        // 필터링할 경로
        filter.addUrlPatterns("/*");
        // 예외경로 추가
//        filter.addInitParameter("exclusions", "/api/v1/aimmed/*");
        return filter;
    }

//    @Bean
//    public RestTemplate restTemplate() {
//        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
//
//        HttpClient client = HttpClientBuilder.create()
//                .setMaxConnTotal(50)
//                .setMaxConnPerRoute(20)
//                .build();
//
//        factory.setHttpClient(client);
//        factory.setConnectTimeout(15000);
//        factory.setReadTimeout(15000);
//
//        return new RestTemplate(factory);
//    }
}
