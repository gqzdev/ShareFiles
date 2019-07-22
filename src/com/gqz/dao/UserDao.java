package com.gqz.dao;

import java.sql.SQLException;

import com.gqz.bean.UserBean;

/**
 * 
 * @ClassName: UserDao
 * @Description: TODO(user表的DAO方法，Data Access Object)
 * @author ganquanzhong
 * @date 2018-3-6 上午09:28:26
 */

public class UserDao extends BaseDao{
	/**
	 * 
	* @Title: register
	* @Description: TODO(注册方法)
	* @author ganquanzhong
	* @date  2018-3-6 上午10:28:10
	* @param userBean
	 */
	public int register(UserBean userBean) {
		//SQL语句  用户注册
		String sql = "insert into sf_user values(0,?,?,?,?,?)"; // 插入新用户到数据库sf_user表

		conn = db.openConn();// 调用db对象的方法，返回一个数据库连接
		
		int result=0;
		try {
			// 创建一个PrepredStatement,对象来将参数化的SQL语句发送到数据库
			ps = conn.prepareStatement(sql);

			// 将sql语句中的？问号占位符，赋上相应的值
			ps.setString(1, userBean.getUsername());
			ps.setString(2, userBean.getPassword());
			ps.setString(3, userBean.getPhone());
			ps.setInt(4, userBean.getIntegral());
			ps.setDate(5, userBean.getRegtime());

			// 将sql语句传入特定的数据库，并且执行此sql语句
			result=ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.close(conn, rs, ps);//调用关闭方法
		}
		return result;
	}
	
	
	/**
	 * 
	* @Title: login
	* @Description: TODO(用户登录) 登录成功保存该用户的UserBean对象信息
	* @author ganquanzhong
	* @date  2018年3月6日 下午8:42:08
	* @param username
	* @param password
	* @return
	 */
	public UserBean login(UserBean userbean) {
		//SQL语句   查询用户是否存在
		String sql = "select * from sf_user where username=? and password=?"; //查询数据库中是否存在该用户 
		conn = db.openConn();// 调用db对象的方法，返回一个数据库连接
		UserBean user=null;//申明一个user对象
		
		try {
			// 创建一个PrepredStatement,对象来将参数化的SQL语句发送到数据库
			ps = conn.prepareStatement(sql);

			//设置参数
			ps.setString(1,userbean.getUsername());
			ps.setString(2, userbean.getPassword());
			
			// 将sql语句传入特定的数据库，并且执行此sql语句，并返回一个结果集
		    rs= ps.executeQuery();	
		    
		    while(rs.next()){
		    	user=new UserBean();//初始化user对象
		    	user.setId(rs.getInt("id"));
		    	user.setUsername(rs.getString("username"));
		    	user.setPassword(rs.getString("password"));
		    	user.setPhone(rs.getString("phone"));
		    	user.setIntegral(rs.getInt("integral"));
		    	user.setRegtime(rs.getDate("regtime"));
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.close(conn, rs, ps);//调用关闭方法
		}
		return user;
	}
	
	
	/**
	 * 
	* @Title: checkName
	* @Description: 检查用户名是否存在，是否可用
	* @author ganquanzhong
	* @date  2018-3-8 下午02:36:02
	* @param username
	* @return
	* 返回1，则
	 */
	public int checkName(String username){
		String sql="select count(*) from sf_user where username=?";
		
		conn=db.openConn();//打开数据库连接
		
		try {
			ps=conn.prepareStatement(sql);
			ps.setString(1, username);
			
			rs=ps.executeQuery();
			while(rs.next()){
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			db.close(conn, rs, ps);//关闭数据库
		}
		return 0;
		
	}
	
	/**
	 * 
	* @Title: addIntegral
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author ganquanzhong
	* @date  2018-3-14 上午10:26:16
	* @param id 根据user的uid
	 */
	public int addIntegral(int integral,int id){
		int result=0;
		String sql="update sf_user set integral =integral +? where id =?";
		conn=db.openConn();
		try {
			ps=conn.prepareStatement(sql);
			
			ps.setInt(1, integral);
			ps.setInt(2, id);
			
			result= ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			db.close(conn, rs, ps);
		}
		return result;
	}
	
	/**
	 * 
	* @Title: updateMarks
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author ganquanzhong
	* @date  2018-3-15 上午10:21:46
	* @param id
	* @param integral
	* @param b 判断真假  --真加分  --假减分
	* @return
	 */
	public int updateMarks(int id,int integral,boolean b){
		
		int result=0;
		String sql="";
		
		if (b) {
			sql="update sf_user set integral =integral +? where id =?";
		}else {
			sql="update sf_user set integral =integral -? where id =?";
		}
		conn=db.openConn();
		
		try {
			ps=conn.prepareStatement(sql);
			
			ps.setInt(1, integral);
			ps.setInt(2, id);
			
			result= ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			db.close(conn, rs, ps);
		}
		return result;
	}
	
	
	/**
	 * 
	* @Title: findById
	* @Description: 根据用户的id 获取用户信息
	* @author ganquanzhong
	* @date  2018-3-15 上午09:31:48
	* @param id
	* @return
	 */
	public UserBean findById(int id){
		String sql = "select * from sf_user where id=?"; //查询数据库中是否存在该用户 
		conn = db.openConn();// 调用db对象的方法，返回一个数据库连接
		UserBean user=null;//申明一个user对象
		
		try {
			// 创建一个PrepredStatement,对象来将参数化的SQL语句发送到数据库
			ps = conn.prepareStatement(sql);

			//设置参数
			ps.setInt(1,id);
			
			// 将sql语句传入特定的数据库，并且执行此sql语句，并返回一个结果集
		    rs= ps.executeQuery();	
		    
		    while(rs.next()){
		    	user=new UserBean();//初始化user对象
		    	user.setId(rs.getInt("id"));
		    	user.setUsername(rs.getString("username"));
		    	user.setPassword(rs.getString("password"));
		    	user.setPhone(rs.getString("phone"));
		    	user.setIntegral(rs.getInt("integral"));
		    	user.setRegtime(rs.getDate("regtime"));
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.close(conn, rs, ps);//调用关闭方法
		}
		return user;
	}
	
}
