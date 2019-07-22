package com.gqz.dao;

import java.sql.SQLException;

import com.gqz.bean.AdminBean;



public class AdminDao extends BaseDao{
	/**
	 * 
	* @Title: manager  login
	* @Description: TODO(用户登录) 登录成功保存该用户的UserBean对象信息
	* @author ganquanzhong
	* @date  2018年3月6日 下午8:42:08
	* @param username
	* @param password
	* @return
	 */
	public AdminBean login(AdminBean adminBean) {
		//SQL语句   查询用户是否存在
		String sql = "select * from sf_admin where username=? and password=?"; //查询数据库中是否存在该用户 
		conn = db.openConn();// 调用db对象的方法，返回一个数据库连接
		AdminBean admin=null;//申明一个user对象
		
		try {
			// 创建一个PrepredStatement,对象来将参数化的SQL语句发送到数据库
			ps = conn.prepareStatement(sql);
			//设置参数
			ps.setString(1,adminBean.getUsername());
			ps.setString(2, adminBean.getPassword());
			
			// 将sql语句传入特定的数据库，并且执行此sql语句，并返回一个结果集
		    rs= ps.executeQuery();	
		    
		    while(rs.next()){
		    	admin=new AdminBean();//初始化user对象
		    	admin.setId(rs.getInt("id"));
		    	admin.setUsername(rs.getString("username"));
		    	admin.setPassword(rs.getString("password"));
		    	admin.setDate(rs.getDate("date"));
		    	admin.setRemark(rs.getString("remark"));
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.close(conn, rs, ps);//调用关闭方法
		}
		return admin;
	}
}
