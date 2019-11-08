package com.gqz.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
* @ClassName: ConnectionDB
* @Description: TODO(这里用一句话描述这个类的作用)
* @author ganquanzhong
* @date 2018-3-6 上午08:07:13
 */
public class ConnectionDB {
	
	private String url="jdbc:mysql://127.0.0.1:3306/sharefiles?characterEncoding=utf-8&useSSL=true";//数据库连接的地址
	private String name="root";//数据库连接的用户名
	private String password="ganquanzhong";//数据库连接的密码
	
	
	private Connection conn;
	
	
	//打开数据库连接
	public Connection openConn(){
		try {
			//加载驱动
			Class.forName("org.gjt.mm.mysql.Driver");
			//通过驱动管理类获取连接对象
			
			conn=DriverManager.getConnection(url,name,password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println("========打开数据库连接=========");
		return conn;
	}
	
	//关闭连接
	public void close(Connection conn,ResultSet rs,PreparedStatement ps){
		// 释放资源
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e2) {
			// TODO: handle exception
		}
		
		//System.out.println("========关闭数据库连接=========");
	}
	
	//测试数据库链接
	/*public static void main(String[] args) {
		ConnectionDB db=new ConnectionDB();
		
		System.out.println(db.openConn());
	}*/
	
}
