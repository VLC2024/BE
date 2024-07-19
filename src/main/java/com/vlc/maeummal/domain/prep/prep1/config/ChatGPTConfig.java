package com.vlc.maeummal.domain.prep.prep1.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

/**
 * ChatGPT에서 사용하는 환경 구성
 *
 * @author : yu
 * @fileName : RestTemplate
 * @since : 07/17/24
 */
@Configuration
public class ChatGPTConfig {

    @Value("${openai.api.key}")
    private String secretKey;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate template = new RestTemplate();
        template.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add(
                    "Authorization"
                    ,"Bearer "+secretKey);
            return execution.execute(request,body);
        });
        return template;
    }

//    @Bean
//    public HttpHeaders httpHeaders() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + secretKey);
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        return headers;
//    }
}