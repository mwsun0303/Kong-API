package com.sun.kong;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import io.lettuce.core.dynamic.annotation.Param;

@Mapper
public interface Kong_Mapper {
// login Check
	Kong_DTO loginCheck(@Param("corpCd") String corpCd, @Param("memberId") String memberId, @Param("password")String password);

// health Check
	ArrayList<Kong_DTO> healthCheck();

}