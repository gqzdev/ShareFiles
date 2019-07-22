package com.gqz.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.gqz.db.ConnectionDB;

public class BaseDao {
	public Connection conn; //数据库连接对象
	public ResultSet rs; //返回结果集
	public PreparedStatement ps; //预编译
	public ConnectionDB db;//数据库连接对象
	
	//定义构造方法  初始化时生成一个ConnectionDB对象
	public BaseDao(){
		db=new ConnectionDB();
	}
}
