package com.sun.kong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sun.kong.config.SecurityConfig;

@RestController
@RequestMapping("/api")
public class Kong_API_Controller {
	private final SecurityConfig security_Config;

	@Autowired
	com.sun.kong.Kong_Redis_Service kong_redis_service;
	@Autowired
	com.sun.kong.Kong_Service kong_service;

	// WAS No, WAS별 동작 Check용, WAS 동작엔 지장 없음
	String serverNo = "3번 Server";

	Kong_API_Controller(SecurityConfig security_Config) {
		this.security_Config = security_Config;
	}

	// Health Check
	@GetMapping("/health")
	public String healthCheck() {
		String result = null;
		return result = kong_service.healthCheck();
	}

	// Redis Member ID 동기화
	@GetMapping("/sync")
	public ResponseEntity<String> sync() {
		System.out.println("Redis Sync Controller 호출");
		kong_redis_service.syncCorpCdToRedis();
		return ResponseEntity.ok("Sync to Redis complete.");
	}

	// 로그인
	@GetMapping("/login")
	public String Login(@RequestParam("corpCd") String corpCd,
			@RequestParam("memberId") String memberId,
			@RequestParam("password") String password) {
		// 파라미터 값 확인
		System.out.println("corpCd: " + corpCd + ", memberId: " + memberId + ", password: " + password);

		String respone = null;

		// Redis DB 정보 체크
		Boolean corpCd_Check = kong_redis_service.corpCd_Check_In_Redis(corpCd);

		System.out.println(serverNo + corpCd + " ID 존재 체크 : " + corpCd_Check);

		// corpCd 존재 확인 시 비밀번호 일치 확인
		if (corpCd_Check)
			respone = kong_service.login(corpCd, memberId, password);
		else
			respone = "등롣된 회원이 아닙니다.";

		System.out.println("Login Controller End : " + respone);
		return serverNo + " " + respone;

	}
}