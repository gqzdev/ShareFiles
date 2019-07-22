package com.gqz.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gqz.bean.SourceBean;
import com.gqz.bean.UserBean;
import com.gqz.dao.SourceDao;
import com.gqz.dao.UserDao;
import com.gqz.util.BaseServlet;

public class SourceServlet extends BaseServlet {

	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @Title: mySource
	 * @Description: 查询当前用户的资源
	 * @author ganquanzhong
	 * @date 2018年3月10日 下午9:41:28
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void mySource(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		// 获得用户输入的页面
		String pn = request.getParameter("pageNumber");

		int pageNumber = 1; // 申请一个当前页面变量默认第1页

		// null "" 非数字 都不行
		if (pn != null && !"".equals(pn)) {
			try {
				pageNumber = Integer.parseInt(pn);
			} catch (NumberFormatException e) {
				pageNumber = 1;
			}
		}
		
		// 获取当前用户的会话 session
		HttpSession session = request.getSession();
		UserBean user = (UserBean) session.getAttribute("user");// 获取当前用的UserBean对象

		SourceDao sourceDao = new SourceDao();
		ArrayList<SourceBean> list = sourceDao.listByUserId(user,pageNumber);//根据用户获取该用户的资源
		
		int maxPage = sourceDao.pagingMaxByUid(user.getId());//获取自定uid下的资源数

		
		request.setAttribute("sourceList", list);
		request.setAttribute("maxPage", maxPage);// 将maxPage保存到request请求作用域
		request.setAttribute("pageNumber", pageNumber);// 将当前页面保存到request请求作用域
		request.getRequestDispatcher("my.jsp").forward(request, response);
	}

	
	/**
	 * 
	 * @Title: pagingAll
	 * @Description: 查询所有的资源 index.jsp(主页)
	 * @author ganquanzhong
	 * @date 2018-3-15 上午11:18:37
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void pagingAll(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 获得用户输入的页面
		String pn = request.getParameter("pageNumber");

		int pageNumber = 1; // 申请一个当前页面变量默认第1页

		// null "" 非数字 都不行
		if (pn != null && !"".equals(pn)) {
			try {
				pageNumber = Integer.parseInt(pn);
			} catch (NumberFormatException e) {
				pageNumber = 1;
			}
		}
		// System.out.println("----页码----第" + pageNumber + "页");
		SourceDao sourceDao = new SourceDao();
		List<SourceBean> sourceList = sourceDao.paging(pageNumber);

		int maxPage = sourceDao.pagingMax();

		request.setAttribute("sourceList", sourceList);
		request.setAttribute("maxPage", maxPage);// 将maxPage保存到request请求作用域
		request.setAttribute("pageNumber", pageNumber);// 将当前页面保存到request请求作用域

		// 跳转到显示所有资源的页面
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	
	/**
	 * 
	 * @Title: download
	 * @Description: 文件下载
	 * @author ganquanzhong
	 * @date 2018-3-14 上午10:59:57
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void download(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

//		System.out.println("您正在下载文件！！！");
		request.setCharacterEncoding("utf-8");

		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=GBK");


		// 如果用户未登录 提示用户登录再下载
		UserBean user = (UserBean) request.getSession().getAttribute("user");// 获取session中的user
		if (user == null) {
			request.setAttribute("msg", "你还未登录！不能下载！");
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return ;
		}

		int sourceId = Integer.parseInt(request.getParameter("id"));// 获取需要下载资源的id

		SourceDao sourceDao = new SourceDao();
		SourceBean source = sourceDao.findById(sourceId);// 根据资源id查询该资源对象

		// 如果资源不存在
		if (source == null) {
			request.setAttribute("msg", "该资源已被删除！请联系管理员email:gqzdev@gmail.com");
			request.getRequestDispatcher("SourceServlet.action?flag=pagingAll").forward(request, response);
			return;// 终止当前方法
		}

		// 判断用户的积分是否满足下载的条件 不满足提醒用户上传 **将数据库中获取积分
		UserDao userDao = new UserDao();
		user = userDao.findById(user.getId());

		if (user.getIntegral() < source.getIntegral()) {
			request.setAttribute("msg", "您的积分不够！请上分！你可以上传资源获得积分！");
			request.getRequestDispatcher("upload.jsp").forward(request, response);
			return;
		}

		/**
		 * 文件下载 jsp中实现文件下载的最简单的方式是在网页上做超级链接， 如：<a href="music/abc.mp3">点击下载</a>。
		 * 但是这样服务器上的目录资源会直接暴露给最终用户，会给网站带来一些不安全的因素。因此可以采用其它方式实现下载，
		 * 可以采用：1、RequestDispatcher的方式进行；2、采用文件流输出的方式下载。
		 * 
		 * 
		 */

		// 1、RequestDispatcher的方式进行
		
		/*String filedownloadPath = source.getSpath(); // 即将下载的文件的相对路径
		String filedisplay = source.getSname()
				+ source.getSpath().substring(
						source.getSpath().lastIndexOf(".")); // 下载文件时显示的文件保存名称
		response.setContentType("application/x-download"); // 设置为下载application/x-download
		// response.setContentType("application/x-msdownload");
		// //设置为下载application/x-msdownload
		// response.setContentType("application/octet-stream");
		// //设置为下载application/octet-stream
		response.addHeader("Content-Disposition", "attachment;filename="
				+ new String(filedisplay.getBytes("utf-8"), "ISO-8859-1"));// 设置响应头信息
		try {
			RequestDispatcher rd = request
					.getRequestDispatcher(filedownloadPath);
			if (rd != null) {
				rd.forward(request, response);
			}
			response.flushBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		}*/

		
		// 2、采用文件流输出的方式下载

		ServletContext application = request.getSession().getServletContext();
		// 即将下载的文件的绝对路径
		String filedownloadPath = application.getRealPath("/")+source.getSpath(); 
		// 下载文件时显示的文件保存名称
		
		//如果文件不存在 ，则提示用户文件不存在 请联系管理员
		if (!new File(filedownloadPath).exists()) {
			request.setAttribute("msg", "此文件已下架！请联系管理员！email:gqzdev@gmail.com");
			request.getRequestDispatcher("SourceServlet.action?flag=pagingAll").forward(request, response);
			return ;
		}
		String filedisplay = source.getSname()+ source.getSpath().substring(source.getSpath().lastIndexOf("."));
		// 设置为下载application/x-download
		response.setContentType("application/x-download");
		// 设置响应头信息
		response.addHeader("Content-Disposition", "attachment;filename="+ new String(filedisplay.getBytes("utf-8"), "ISO-8859-1"));
		
		InputStream is = null;
		OutputStream os = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		//从服务器上读取文件到InputStream流中    文件到程序的过程
		bis = new BufferedInputStream(new FileInputStream(new File(filedownloadPath)));
		//将流从程序中输出   response.getOutputStream
		bos = new BufferedOutputStream(response.getOutputStream());

		byte[] b = new byte[1024];
		int len = -1;
		while ((len = bis.read(b)) != -1) {
			bos.write(b, 0, len);
		}

		//关闭流
		bos.close();
		os.close();
		bis.close();
		is.close();

		// 文件下载完毕 对积分操作
		userDao.updateMarks(user.getId(), source.getIntegral(), false);// 扣分
		userDao.updateMarks(source.getUid(), 1, true);// 加分
		sourceDao.updateDcount(source.getId());// 下载成功后，下载次数加1
	}
	
	/**
	 * 
	* @Title: delete
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author ganquanzhong
	* @date  2018-3-15 下午03:55:54
	* @param request
	* @param response
	* @throws ServletException
	* @throws IOException
	 */
	public void delete(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//获取资源id
		String id=request.getParameter("id");
		int pageNumber=Integer.parseInt(request.getParameter("pageNumber"));
		
		SourceDao sourceDao=new SourceDao();
		
		//通过资源id将这一条信息查询出来，因为需要此资源的文件路径才能进行文件的删除
		SourceBean source = sourceDao.findById(Integer.parseInt(id));
		//得到application对象
		ServletContext application =request.getSession().getServletContext();
		//找到该路径下的文件资源
		File temp=new File(application.getRealPath("/")+source.getSpath());
		//删除文件  如果文件删除成功返回true
		temp.delete();
		
		//删除数据库中的资源
		sourceDao.deleteById(Integer.parseInt(id));
		
		int maxPage=sourceDao.pagingMax();
	
		PrintWriter out =response.getWriter();
		out.print("<script>alert('删除成功！');</script>");
		
		if (  pageNumber>maxPage) {
			  pageNumber  = maxPage;
		}
		//重定向
		response.sendRedirect("SourceServlet.action?flag=pagingAll&pageNumber="+pageNumber);
		//request.getRequestDispatcher("").forward(request, response);
	}
	
	/**
	 * 
	* @Title: showSource
	* @Description: 资源详情
	* @author ganquanzhong
	* @date  2018-3-16 上午10:58:49
	* @param request
	* @param response
	* @throws ServletException
	* @throws IOException
	 */
	public void showSource(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//获取资源的id
		int id = Integer.parseInt(request.getParameter("id"));
		//保存当前的页码
		int pageNumber=Integer.parseInt(request.getParameter("pageNumber"));
		
		SourceDao sourceDao=new SourceDao();
		SourceBean source = sourceDao.findById(id);
		
		request.setAttribute("source", source);
		request.setAttribute("pageNumber", pageNumber);
		
		request.getRequestDispatcher("sourceInfo.jsp").forward(request, response);
	}
	
	
	/**
	 * 
	 * @Title: CheckSource
	 * @Description: 审核资源列表
	 * @author ganquanzhong
	 * @date 2018-3-15 上午11:18:37
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void CheckSource(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 获得用户输入的页面
		String pn = request.getParameter("pageNumber");

		int pageNumber = 1; // 申请一个当前页面变量默认第1页

		// null "" 非数字 都不行
		if (pn != null && !"".equals(pn)) {
			try {
				pageNumber = Integer.parseInt(pn);
			} catch (NumberFormatException e) {
				pageNumber = 1;
			}
		}
		// System.out.println("----页码----第" + pageNumber + "页");
		SourceDao sourceDao = new SourceDao();
		List<SourceBean> sourceList = sourceDao.sourceManager(pageNumber);

		int maxPage = sourceDao.CheckPageMax();

		request.setAttribute("sourceList", sourceList);
		request.setAttribute("maxPage", maxPage);// 将maxPage保存到request请求作用域
		request.setAttribute("pageNumber", pageNumber);// 将当前页面保存到request请求作用域

		// 跳转到显示所有资源的页面
		request.getRequestDispatcher("/manager/index.jsp").forward(request, response);
	}
	
	/**
	 * 
	* @Title: check
	* @Description: 审核资源
	* @author ganquanzhong
	* @date  2018年4月5日 下午2:02:26
	* @param request
	* @param response
	* @throws ServletException
	* @throws IOException
	 */
	public void check(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//获取资源id
		String id=request.getParameter("id");
		int uid=Integer.parseInt(request.getParameter("uid"));//获取用户id，审核后加分操作
		int pageNumber=Integer.parseInt(request.getParameter("pageNumber"));
		
		SourceDao sourceDao=new SourceDao();	
		UserDao userDao=new UserDao();
		
		//审核资源，用户积分增加10分
		userDao.addIntegral(3,uid);
		//通过id审核资源
		sourceDao.check(Integer.parseInt(id));
		
		int maxPage=sourceDao.pagingMax();
	
		PrintWriter out =response.getWriter();
		out.print("<script>alert('通过审核！');</script>");
		
		if (  pageNumber>maxPage) {
			  pageNumber  = maxPage;
		}
		//重定向
		response.sendRedirect("SourceServlet.action?flag=CheckSource&pageNumber="+pageNumber);
	}

}
