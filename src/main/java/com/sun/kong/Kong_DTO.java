package com.sun.kong;

//CREATE TABLE kong (
//		  corpCd TEXT NOT NULL,
//		  memberId TEXT NOT NULL,
//		  username TEXT NOT NULL,
//		  password TEXT NOT NULL,
//		  PRIMARY KEY (corp_Cd)
//		);

public class Kong_DTO {
	private String corpCd;
	private String memberId;
	private String username;
	private String password;
	
	public String getCorpCd() {
		return corpCd;
	}
	public String getMemberId() {
		return memberId;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}

	public void setCorpCd(String corpCd) {
		this.corpCd = corpCd;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}