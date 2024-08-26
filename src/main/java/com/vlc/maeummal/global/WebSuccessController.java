package com.vlc.maeummal.global;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSuccessController {

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        // 간단한 헬스 체크 구현
        // 추가적인 로직을 통해 데이터베이스 연결 상태, 외부 서비스 상태 등을 확인할 수도 있습니다.
        
        // 헬스 체크가 성공하면 200 OK 상태를 반환
        return new ResponseEntity<>("Health Check OK", HttpStatus.OK);
    }
}

