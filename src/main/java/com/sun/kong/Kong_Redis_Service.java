package com.sun.kong;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.sun.kong.config.SecurityConfig;

@Service
public class Kong_Redis_Service {

    private final SecurityConfig securityConfig;

    private final JdbcTemplate jdbcTemplateDb1;
    private final JdbcTemplate jdbcTemplateDb2;
    private final JdbcTemplate jdbcTemplateDb3;
    private final StringRedisTemplate redisTemplate;
 
// DB Info
    String psql_01="kong-db-1";
    String psql_02="kong-db-2";
    String psql_03="kong-db-3";

// Redis 캐시 유지 시간
    int redis_Cache=86400;

// Redis 생성자 생성
    public Kong_Redis_Service(
            @Qualifier("jdbcTemplate-kong-db-1") JdbcTemplate jdbcTemplateDb1,
            @Qualifier("jdbcTemplate-kong-db-2") JdbcTemplate jdbcTemplateDb2,
            @Qualifier("jdbcTemplate-kong-db-3") JdbcTemplate jdbcTemplateDb3,
            StringRedisTemplate redisTemplate, SecurityConfig securityConfig) {
        		this.jdbcTemplateDb1 = jdbcTemplateDb1;
        		this.jdbcTemplateDb2 = jdbcTemplateDb2;
        		this.jdbcTemplateDb3 = jdbcTemplateDb3;
        		this.redisTemplate = redisTemplate;
        		this.securityConfig = securityConfig;
    		}
    // DB Server Template
    		@Autowired private SqlSessionTemplate sqlSessionTemplateDb1;
    		@Autowired private SqlSessionTemplate sqlSessionTemplateDb2;
    		@Autowired private SqlSessionTemplate sqlSessionTemplateDb3;
    

// 1. 전체 corp Cd 조회, Redis 저장 및 동기화
    public void syncCorpCdToRedis() {
    	String sync_Query = "SELECT DISTINCT corpCd FROM kong"; // corpCd 조회
        List<String> corpCdDb1 = jdbcTemplateDb1.queryForList(sync_Query, String.class);
        List<String> corpCdDb2 = jdbcTemplateDb2.queryForList(sync_Query, String.class);
        List<String> corpCdDb3 = jdbcTemplateDb3.queryForList(sync_Query, String.class);

        System.out.println("[INFO] ----- " + psql_01 +" Corp CD -----");
        for (String id : corpCdDb1) {
            System.out.println("[INFO]" + psql_01 + " : " + id);
        }

        System.out.println("[INFO] ----- " + psql_02 +" Corp CD -----");
        for (String id : corpCdDb2) {
            System.out.println("[INFO]" + psql_02 + " : " + id);
        }

        System.out.println("[INFO] ----- " + psql_03 +" Corp CD -----");
        for (String id : corpCdDb3) {
            System.out.println("[INFO]" + psql_03 + " : " + id);
        }
        
      // Redis Config 
        // CorpCd Check를 위한 정방향 Mapping (DB명 : CorpCD)
        // Connect DB Check를 위한 역방향 Mapping (CorpCD : DB명)
        corpCdDb1.forEach(id -> {
            redisTemplate.opsForValue().set(id, psql_01, redis_Cache, TimeUnit.SECONDS);
            redisTemplate.opsForValue().set("corpCd:" + id, psql_01, redis_Cache, TimeUnit.SECONDS);      
        });

        corpCdDb2.forEach(id -> {
            redisTemplate.opsForValue().set(id, psql_02, redis_Cache, TimeUnit.SECONDS);
            redisTemplate.opsForValue().set("corpCd:" + id, psql_02, redis_Cache, TimeUnit.SECONDS);
        });

        corpCdDb3.forEach(id -> {
            redisTemplate.opsForValue().set(id, psql_03, redis_Cache, TimeUnit.SECONDS);
            redisTemplate.opsForValue().set("corpCd:" + id, psql_03, redis_Cache, TimeUnit.SECONDS);
        });
    }
    
// 2. CorpCd 존재 여부 확인
    public Boolean corpCd_Check_In_Redis(String corpCd) {
        boolean exists = Boolean.TRUE.equals(redisTemplate.hasKey(corpCd));
        return exists;
    }

    
// 3. DB Info Check
 	public String getDBInfo(String corpCd) { 		
 	    String redisKey = "corpCd:" + corpCd;	// 역방향 Mapping 조회
 	    String dbInfo = redisTemplate.opsForValue().get(redisKey);

 	// 역방향 Mapping Check
 	    if (dbInfo == null) {
 	        System.out.println("[WARN] Redis에 해당 corpCd가 존재하지 않습니다: " + corpCd);
 	        return null;
 	    }
 		return dbInfo;
 	}


 	
}