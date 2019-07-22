package com.gqz.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gqz.bean.SourceBean;
import com.gqz.bean.UserBean;

public class SourceDao extends BaseDao{

	/**
	 * 
	* @Title: UploadFilesServlet
	* @Description: 文件上传类
	* @author ganquanzhong
	* @date  2018年3月9日 下午6:09:51
	* @param source
	 */
	public void UploadFilesServlet(SourceBean source){
		
		String sql="insert into sf_source values(0,?,?,?,?,?,?,?,?,?,1)"; //插入文件信息到数据库sf_source 
		conn=db.openConn();//打开数据库连接
		
		try {
			ps=conn.prepareStatement(sql);
			
			//设置参数
			ps.setString(1, source.getSname());
			ps.setString(2, source.getSpath());
			ps.setInt(3, source.getSize());
			ps.setTimestamp(4, source.getUploadtime());
			ps.setString(5, source.getTag());
			ps.setInt(6, source.getIntegral());
			ps.setInt(7, source.getDcount());
			ps.setString(8, source.getSummary());
			ps.setInt(9, source.getUid());
			//执行
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			db.close(conn, rs, ps);
		}
	}
	
	
	/**
	 * 
	* @Title: listByUserId
	* @Description: 查询指定id的资源source
	* @author ganquanzhong
	* @date  2018年3月10日 下午9:48:24
	* @param user
	 */
	public ArrayList<SourceBean> listByUserId(UserBean user,int pagingNumber){
		
		ArrayList<SourceBean> list=new ArrayList<SourceBean>();//创建SourceBean对象集合
		
	//	String sql="select * from sf_source where uid=?";//从sf_source数据库中查询指定uid资源
		
		//从sf_source数据库中查询指定uid资源  并且将author名存入
		String sql="select * from sf_source where isDel=0 and uid=? and uid limit ?,10";
		
		conn=db.openConn();//打开数据连接
		
		
		try {
			ps=conn.prepareStatement(sql);//预编译sql语句
			
			System.out.println("--用户id为:--"+user.getId());
			//设置参数
			ps.setInt(1, user.getId());
			ps.setInt(2, (pagingNumber-1)*12);
			
			rs = ps.executeQuery();
			
			while(rs.next()){
				SourceBean sourceBean=new SourceBean();//初始化sourceBean对象
				sourceBean.setId(rs.getInt("id"));
				sourceBean.setSname(rs.getString("sname"));
				sourceBean.setSpath(rs.getString("spath"));
				sourceBean.setSize(rs.getInt("size"));
				sourceBean.setUploadtime(rs.getTimestamp("uploadtime"));
				sourceBean.setTag(rs.getString("tag"));
				sourceBean.setIntegral(rs.getInt("integral"));
				sourceBean.setDcount(rs.getInt("dcount"));
				sourceBean.setSummary(rs.getString("summary"));
				sourceBean.setUid(rs.getInt("id"));
				
				list.add(sourceBean);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			db.close(conn, rs, ps);
		}
		
		return list;
	}
	
	
	/**
	 * 
	* @Title: paging
	* @Description: 分页查询 已审核的
	* @author ganquanzhong
	* @date  2018-3-13 上午09:21:09
	* @param pagingNumber
	* @return
	 */
	public List<SourceBean> paging(int pagingNumber){
		//查询所有资源信息，并且分页展示 ，每一显示10条数据
	//	String sql="select * from sf_source limit ?,12";
		
		//从sf_source数据库中查询指定uid资源  并且将author名存入
		String sql="select  s.*,  u.username as author from  sf_source s  left join sf_user u on s.uid=u.id where s.isDel=0 and s.uid limit ?,10";
		
		conn=db.openConn();//打开数据库连接
		
		List<SourceBean> sourceList=new ArrayList<SourceBean>();
		
		try {
			ps = conn.prepareStatement(sql);
			//设置参数
			ps.setInt(1, (pagingNumber-1)*10);
			
			rs = ps.executeQuery();
			while(rs.next()){
				SourceBean source=new SourceBean();
				source.setId(rs.getInt("id"));
				source.setSname(rs.getString("sname"));
				source.setSpath(rs.getString("spath"));
				source.setSize(rs.getInt("size"));
				source.setUploadtime(rs.getTimestamp("uploadtime"));
				source.setTag(rs.getString("tag"));
				source.setIntegral(rs.getInt("integral"));
				source.setDcount(rs.getInt("dcount"));
				source.setSummary(rs.getString("summary"));
				source.setUid(rs.getInt("uid"));
				source.setAuthor(rs.getString("author"));
				//将资源对象 ：source 一一保存到sourceList集合中
				sourceList.add(source);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			db.close(conn, rs, ps);
		}
		return sourceList;
	}
	
	
	/**
	 * 
	* @Title: pagingMax
	* @Description: 求最大分页数
	* @author ganquanzhong
	* @date  2018-3-13 上午09:17:10
	* @return
	 */
	public int pagingMax(){
		String sql="select count(*) from sf_source where isDel=0";
		conn=db.openConn();
		int i=0;
		
		try {
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while( rs.next()){
				i = rs.getInt(1);
			}
			
			if (i%10==0) {
				i=i/10;
			}else {
				i=i/10+1;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			db.close(conn, rs, ps);
		}
		return i;
	}
	
	//所有资源的最大页数
	public int CheckPageMax(){
		String sql="select count(*) from sf_source;";
		conn=db.openConn();
		int i=0;
		
		try {
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while( rs.next()){
				i = rs.getInt(1);
			}
			
			if (i%10==0) {
				i=i/10;
			}else {
				i=i/10+1;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			db.close(conn, rs, ps);
		}
		return i;
	}
	
	/**
	 * 
	* @Title: pagingMaxByUid
	* @Description: 根据用户的uid查询资源数
	* @author ganquanzhong
	* @date  2018-3-16 上午10:42:23
	* @return
	 */
	public int pagingMaxByUid(int id){
		String sql="select count(*) from sf_source where isDel=0 and uid=?";
		conn=db.openConn();
		int i=0;
		
		try {
			ps=conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs=ps.executeQuery();
			while( rs.next()){
				i = rs.getInt(1);
			}
			
			if (i%10==0) {
				i=i/10;
			}else {
				i=i/10+1;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			db.close(conn, rs, ps);
		}
		return i;
	}
	
	
	/**
	 * 
	* @Title: findById
	* @Description: 根据资源的id查询该资源的对象sourceBean
	* @author ganquanzhong
	* @date  2018-3-14 上午11:15:16
	* @param id
	* @return
	 */
	public SourceBean findById(int id){
		
		SourceBean source=null;//申明一个对象
	//	String sql="select * from sf_source where id=? ";
		String sql="select  s.*,  u.username as author from  sf_source s  left join sf_user u on s.uid=u.id where isDel=0 and s.id=?";
		conn=db.openConn();
		try {
			ps=conn.prepareStatement(sql);
			ps.setInt(1, id); //设置查询参数
			rs=ps.executeQuery();
			while(rs.next()){
				source=new SourceBean(); //初始化对象
				source.setId(rs.getInt("id"));
				source.setSname(rs.getString("sname"));
				source.setSpath(rs.getString("spath"));
				source.setSize(rs.getInt("size"));
				source.setUploadtime(rs.getTimestamp("uploadtime"));
				source.setTag(rs.getString("tag"));
				source.setIntegral(rs.getInt("integral"));
				source.setDcount(rs.getInt("dcount"));
				source.setSummary(rs.getString("summary"));
				source.setUid(rs.getInt("uid"));
				source.setAuthor(rs.getString("author"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			db.close(conn, rs, ps);
		}
		
		return source;
	}
	
	/**
	 * 
	* @Title: updateDcount
	* @Description: 更新下载次数
	* @author ganquanzhong
	* @date  2018-3-15 下午02:37:49
	* @param id
	 */
	public void updateDcount(int id){
		
		String sql="update sf_source set dcount=dcount+1 where id=?";
		
		conn=db.openConn();
		try {
			ps=conn.prepareStatement(sql);
			
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			db.close(conn, rs, ps);
		}
	}
	
	/**
	 * 
	* @Title: deleteById
	* @Description: 通过id删除资源
	* @author ganquanzhong
	* @date  2018-3-15 下午03:54:19
	* @param id
	 */
	public void deleteById(int id){
		//String sql="delete from sf_source where id =?";
		String sql="update sf_source set isDel=1 where id =?";
		
		conn=db.openConn();
		
		try {
			ps=conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			db.close(conn, rs, ps);
		}
		
	}
	
	//审核新闻
	public void check(int id){
		//String sql="delete from sf_source where id =?";
		String sql="update sf_source set isDel=0 where id =?";
		
		conn=db.openConn();
		
		try {
			ps=conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			db.close(conn, rs, ps);
		}
	}
	
	/**
	 * 
	* @Title: pagingCheck
	* @Description: 分页查询 已审核的
	* @author ganquanzhong
	* @date  2018-3-13 上午09:21:09
	* @param pagingNumber
	* @return
	 */
	public List<SourceBean> sourceManager(int pagingNumber){
		//查询所有资源信息，并且分页展示 ，每一显示5条数据
	//	String sql="select * from sf_source limit ?,10";
		
		//从sf_source数据库中查询指定uid资源  并且将author名存入
		String sql="select  s.*,  u.username as author from  sf_source s  left join sf_user u on s.uid=u.id where s.uid limit ?,10";
		
		conn=db.openConn();//打开数据库连接
		
		List<SourceBean> sourceList=new ArrayList<SourceBean>();
		
		try {
			ps = conn.prepareStatement(sql);
			//设置参数
			ps.setInt(1, (pagingNumber-1)*10);
			
			rs = ps.executeQuery();
			while(rs.next()){
				SourceBean source=new SourceBean();
				source.setId(rs.getInt("id"));
				source.setSname(rs.getString("sname"));
				source.setSpath(rs.getString("spath"));
				source.setSize(rs.getInt("size"));
				source.setUploadtime(rs.getTimestamp("uploadtime"));
				source.setTag(rs.getString("tag"));
				source.setIntegral(rs.getInt("integral"));
				source.setDcount(rs.getInt("dcount"));
				source.setSummary(rs.getString("summary"));
				source.setUid(rs.getInt("uid"));
				source.setIsDel(rs.getInt("isDel"));
				source.setAuthor(rs.getString("author"));
				//将资源对象 ：source 一一保存到sourceList集合中
				sourceList.add(source);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			db.close(conn, rs, ps);
		}
		return sourceList;
	}
	
}
