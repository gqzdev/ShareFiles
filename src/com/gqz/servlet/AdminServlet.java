package com.gqz.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gqz.bean.AdminBean;
import com.gqz.dao.AdminDao;
import com.gqz.util.BaseServlet;

public class AdminServlet extends BaseServlet {

	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/
	private static final long serialVersionUID = 1L;

	/**
	 * 
	* @Title: login
	* @Description: TODO(用户登录)
	* @author ganquanzhong
	* @date  2018-3-6 上午11:39:03
	* @param request
	* @param response
	* @throws ServletException
	* @throws IOException
	 */
	protected void login(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		//解决乱码问题
		
		request.setCharacterEncoding("UTF-8");
		//设置服务器输出的编码为UTF-8
		response.setCharacterEncoding("UTF-8");
		//告诉浏览器输出的内容是html,并且以utf-8的编码来查看这个内容。
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		//1.获取JSP页面用户注册表单输入的参数
		String username=request.getParameter("username").trim();//用户名
		String password=request.getParameter("password").trim();//密码		
		
		//2.将参数封装到userBean对象中
		AdminBean adminBean=new AdminBean();
		adminBean.setUsername(username);
		adminBean.setPassword(password);
		
		//3.调用UserDao方法，进入数据库的DML操作
		AdminDao adminDao=new AdminDao();
		AdminBean admin = adminDao.login(adminBean); //调用UserDao中的用户注册方法
		
		
		if (admin!=null) {
			out.print("<script>alert('登录成功!');window.location.href='SourceServlet.action?id=1&flag=CheckSource'</script>");
		}else{
			out.print("<script>alert('用户名或密码错误！');window.location.href='http://ganquanzhong.top/ShareFiles/manager/login.jsp'</script>");
		}
//		System.out.println(admin.getUsername()+"  "+admin.getPassword());
	}

}
