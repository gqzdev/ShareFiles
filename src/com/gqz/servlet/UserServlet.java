package com.gqz.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.gqz.SMSVerify.GetPhoneSMS;
import com.gqz.bean.UserBean;
import com.gqz.dao.UserDao;
import com.gqz.util.BaseServlet;

public class UserServlet extends BaseServlet {

	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/
	private static final long serialVersionUID = 1L;
	public String SMSCode="";//全局变量
	
	
	//发送短信验证码
	protected void SMSVerify(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int i=0;
		request.setCharacterEncoding("UTF-8");
		//设置服务器输出的编码为UTF-8
		response.setCharacterEncoding("UTF-8");
		//告诉浏览器输出的内容是html,并且以utf-8的编码来查看这个内容。
		response.setContentType("text/html;charset=utf-8");
		String phone=request.getParameter("phone").trim();//手机号码
		
		//获取手机号码后。发送短信验证
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		if (p.matcher(phone).matches()) {
//			System.out.println("手机号码为"+phone+"成功发送验证码");
			SMSCode = GetPhoneSMS.getResult(phone);//后台 +手机收到的验证码
		}else {
			i=0;
//			System.out.println("后台+手机号码不合法");
		}
		if (!SMSCode.equals("")) {
			i=1;
		}
		PrintWriter out = response.getWriter();
		out.print(i);
	}
	
	
	/**
	 * 
	* @Title: register
	* @Description: TODO(用户注册)
	* @author ganquanzhong
	* @date  2018-3-6 上午11:37:58
	* @param request
	* @param response
	* @throws ServletException
	* @throws IOException
	 */
	protected void register(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//设置服务器输出的编码为UTF-8
		response.setCharacterEncoding("UTF-8");
		//告诉浏览器输出的内容是html,并且以utf-8的编码来查看这个内容。
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		//1.获取JSP页面用户注册表单输入的参数
		String inputCode = request.getParameter("VerifyCode").trim();//获取注册页面的验证码VerifyCode
		String vCode = (String)request.getSession().getAttribute("vCode");//获取验证码图片中session的vCode的值
//		System.out.println("输入图片验证码："+inputCode);
//		System.out.println("图片生成验证码："+vCode);
		
		String username=request.getParameter("username").trim();//用户名,解决中文乱码的问题
		String password=request.getParameter("password").trim();//密码
		String phone=request.getParameter("phone").trim();//手机号码
		String inputSMSVerifyCode=request.getParameter("SMSVerifyCode").trim();//前台+用户输入的手机号码验证码
		
//		//获取手机号码后。发送短信验证
//		String SMSVerifyCode = GetPhoneSMS.getResult(phone);//后台 +手机收到的验证码
		
//		System.out.println("注册信息为:"+"账户："+username+"     密码： "+password+"      手机号码：  "+phone
//				+"   前端验证"+inputSMSVerifyCode+"   ***      "
//				+"   后端验证为"+SMSCode);
		
		//2.将参数封装到userBean对象中
		UserBean userBean=new UserBean();
		userBean.setUsername(username);
		userBean.setPassword(password);
		userBean.setPhone(phone);
		
		/**
		  request 和  response的跳转方式的区别
		  	1.request.getRequestDispatcher("index.jsp").forward(request, response);
		  	页面的请求转发，转发之后，地址栏(不变)为先前的页面的URL，先前在request和response存入的信息会传递给跳转后的页面
		  	
		  	2.response.sendRedirect("index.jsp");
		  	页面的重定向，重定向之后，地址栏（更新）为跳转页面的URL，先前在request和response存入的信息不会传递给跳转后的页面
		 */
		//判断验证码是否正确
		if ( inputSMSVerifyCode.equals("") ||  !inputSMSVerifyCode.equalsIgnoreCase(SMSCode) ){
			out.print("<script>alert('手机验证码有误！！请重新输入');window.location.href='register.jsp'</script>");
//			System.out.println("手机验证码错误！");
			
		}else if ( inputCode.equals("") ||  !inputCode.equalsIgnoreCase(vCode) ) {
			out.print("<script>alert('验证码有误！！请重新输入');window.location.href='register.jsp'</script>");
//			System.out.println("验证码错误！");

		}else {
						
			//3.调用UserDao方法，进入数据库的DML操作
			UserDao userDao=new UserDao();
			
			int result = userDao.register(userBean);//调用UserDao中的用户注册方法
			if (result!=0) {
				request.setAttribute("username", username);
				request.setAttribute("msg", "注册成功，请登录！");
				out.print("<script>alert('注册成功! 请登录');</script>");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}else {
				out.print("<script>alert('注册失败!');window.location.href='register.jsp'</script>");
			}
		}
	}
	
	
	
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
		
		//1.获取JSP页面用户注册表单输入的参数
		String username=request.getParameter("username").trim();//用户名
		String password=request.getParameter("password").trim();//密码
		
		if ("".equals(username) || username==null) {
			request.setAttribute("msg", "用户名不能为空！");
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return ;
		}
		
		//2.将参数封装到userBean对象中
		UserBean userbean=new UserBean();
		userbean.setUsername(username);
		userbean.setPassword(password);
		
		//3.调用UserDao方法，进入数据库的DML操作
		UserDao userDao=new UserDao();
		UserBean user = userDao.login(userbean); //调用UserDao中的用户注册方法
		
		
		PrintWriter out = response.getWriter();
		if (user!=null) {
			//登录成功，则设置session会话
			request.getSession().setAttribute("user",user);
			System.out.println(userbean.getUsername()+"登录成功!");
			out.print("<script>alert('登录成功!');window.location.href='SourceServlet.action?id=1&flag=pagingAll'</script>");
		}else{
			//登录失败，提示错误并且跳转重新登录
			request.setAttribute("msg", "用户名或密码错误！");
			request.getRequestDispatcher("login.jsp").forward(request, response);
			System.out.println(userbean.getUsername()+userbean.getPassword()+"登录失败! 用户名或密码错误！");
		}
	}
	
	
	/**
	 * 
	* @Title: exit
	* @Description: 退出登录
	* @author ganquanzhong
	* @date  2018-3-8 上午09:04:28
	* @param request
	* @param response
	* @throws ServletException
	* @throws IOException
	 */
	protected void exit(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html,charset=utf-8");
		
		HttpSession session =request.getSession();
		UserBean user = (UserBean)session.getAttribute("user");
		
		if (user!=null) {
			PrintWriter out = response.getWriter();
			out.print("<script>alert('您确定退出登录！')</script>");
			session.removeAttribute("user");
		}
		//页面重定向到登录页面
		response.sendRedirect("login.jsp");
	}
	
	//ajax检测用户名是否存在
	protected void checkName(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		//获取参数
		String username = request.getParameter("username").trim();
		//调用userdao的方法检查用户名是否存在
		UserDao userDao=new UserDao();
		int i = userDao.checkName(username);
		//输出流
		PrintWriter out = response.getWriter();
		//System.out.println("账号："+username);
		out.print(i);
	}
	
	/**
	 * 
	* @Title: userInfo
	* @Description: 查询用户信息
	* @author ganquanzhong
	* @date  2018年3月16日 下午9:26:37
	* @param request
	* @param response
	* @throws ServletException
	* @throws IOException
	 */
	protected void userInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html,charset=utf-8");
		
		UserBean user=(UserBean)request.getSession().getAttribute("user");
		UserDao userDao=new UserDao();
		user = userDao.findById(user.getId());
		
		request.setAttribute("user", user);
		request.getRequestDispatcher("myInfo.jsp").forward(request, response);
	}
}
