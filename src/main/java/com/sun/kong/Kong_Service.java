package com.sun.kong;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sun.kong.config.DataSourceConfig;
import com.sun.kong.config.SecurityConfig;

@Service
public class Kong_Service {

    private final DataSourceConfig dataSourceConfig;

    private final SecurityConfig securityConfig;
    
	@Autowired private Kong_Mapper kong_Mapper;
	@Autowired private Kong_Redis_Service kong_redis_service;
	
    @Autowired
    @Qualifier("sqlSessionTemplateDb1")
    private SqlSessionTemplate sqlSessionTemplateDb1;

    @Autowired
    @Qualifier("sqlSessionTemplateDb2")
    private SqlSessionTemplate sqlSessionTemplateDb2;

    @Autowired
    @Qualifier("sqlSessionTemplateDb3")
    private SqlSessionTemplate sqlSessionTemplateDb3;

    Kong_Service(SecurityConfig securityConfig, DataSourceConfig dataSourceConfig) {
        this.securityConfig = securityConfig;
        this.dataSourceConfig = dataSourceConfig;
    }
	
	// 1. DB Session Check
    SqlSessionTemplate getSqlSessionTemplateByDbName(String dbInfo) {
    	System.out.println("DB Session Check Service Start");
        switch (dbInfo) {
            case "kong-db-1": return sqlSessionTemplateDb1;
            case "kong-db-2": return sqlSessionTemplateDb2;
            case "kong-db-3": return sqlSessionTemplateDb3;
            default: throw new IllegalArgumentException("지원하지 않는 DB명: " + dbInfo);
        }
    }
    // 2. DB Query
    
    
    
// 2. Login
	public String login(String corpCd, String memberId, String password) {
		Kong_DTO login_dto = new Kong_DTO(); 
		String respone = null;
		String dbInfo = null;
		
	// DB에 데이터 존재 여부 유효성 체크 	
		dbInfo = kong_redis_service.getDBInfo(corpCd);
		
		System.out.println("Redis 역방향 dbInfo 조회 : " + dbInfo);
		
		if (dbInfo == null)
			return "DB에 등록되지 않은 정보입니다./ DB Info 조회 오류";
		
	// Parameter 값 확인
		System.out.println("Parameter Check corpCd : " + corpCd);
		System.out.println("Parameter Check memberId : " + memberId);
		System.out.println("Parameter Check password : " + password);
		
		
	// 데이터가 존재하는 DB로 Session 설정	
		SqlSessionTemplate selectedTemplate = getSqlSessionTemplateByDbName(dbInfo);
		
	//	System.out.println("DB Session : " + selectedTemplate);
	
	// 해당 DB Seesion으로 동적 Mapper 선언
		Kong_Mapper dynamicMapper = selectedTemplate.getMapper(Kong_Mapper.class);

	// Login Mapper 호출	
		login_dto = dynamicMapper.loginCheck(corpCd, memberId, password);
		
	
		if (login_dto != null)
			respone = "Login Success / Info Check / DB : " + dbInfo + " / corpCd : " + corpCd + " / memberId : " + memberId;
		
		else
			respone = "Login Faild / Info Check / DB : " + dbInfo + " / corpCd : " + corpCd + " / memberId : " + memberId;
		
		return respone;
	}
	

	
	
	
	
}