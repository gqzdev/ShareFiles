package com.gqz.bean;

import java.sql.Date;

/**
 * 
* @ClassName: UserBean
* @Description: TODO(这里用一句话描述这个类的作用)
* @author ganquanzhong
* @date 2018-3-6 上午08:35:26
 */
public class UserBean {
	private int id;
	private String username;
	private String password;
	private String phone;
	private int integral;
	private Date regtime;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getIntegral() {
		return integral;
	}
	public void setIntegral(int integral) {
		this.integral = integral;
	}
	public Date getRegtime() {
		return regtime;
	}
	public void setRegtime(Date regtime) {
		this.regtime = regtime;
	}
	
	
}
