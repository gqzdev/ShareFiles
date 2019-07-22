package com.gqz.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * 
* @ClassName: BaseServlet
* @Description: TODO(这里用一句话描述这个类的作用)
* @author ganquanzhong
* @date 2018-3-6 下午03:33:30
* 
* 这是一个基础serlvet类，是为其他的serlvet类服务的
 */
public class BaseServlet extends HttpServlet {
	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/
	private static final long serialVersionUID = 1L;

	/**
	 * 通过反射技术去简化serlvet中的操作
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");//POST
		
		String flag =request.getParameter("flag");
		//当前serlvet类的描述对象
		Class claxx =this.getClass();
		
		try {
			Method method=claxx.getDeclaredMethod(flag, HttpServletRequest.class,HttpServletResponse.class);
			
			method.setAccessible(true);//屏蔽掉 访问时java语言检测
			
			method.invoke(this, request,response);//调用此方法
			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
