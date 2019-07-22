package com.gqz.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.gqz.bean.SourceBean;
import com.gqz.bean.UserBean;
import com.gqz.dao.SourceDao;

/**
 * 
* @ClassName: UploadFileServlet
* @Description: 上传
* @author ganquanzhong
* @date 2019年1月6日 下午8:34:50
 */
public class UploadFileServlet extends HttpServlet {

	
	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/
	private static final long serialVersionUID = 1L;


	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
//		System.out.println("----文件开始上传!-----");
		
		//获取会话对象
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();//获取serlvet的内置对象
		
		HttpSession session =request.getSession();
		
		UserBean user=(UserBean) session.getAttribute("user");
		
		//如果用户名没有登录，不能上传文件 请完成登录操作之后再上传文件
		if (user==null ) {
			out.print("<script>alert('您还未登录，不能上传文件！请先登录');window.location.href='login.jsp';</script>;");
			//request.getRequestDispatcher("login.jsp").forward(request, response);
			return ;
		}
		
		SourceBean sourceBean=new SourceBean();
		
		//获取application的路劲
		ServletContext application = request.getSession().getServletContext();
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(5*2*1024);//设置文件的大小
		
		//生成一个临时文件
		new File("c:/temp").mkdirs();
		//设置文件上传的临时目录
		factory.setRepository(new File("c:/temp"));
		
		ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
		
		try {
			//将request对象转换成list集合 我们需要的文件和字段信息存储在此集合中
			List<?> item = servletFileUpload.parseRequest(request);
			
			for (int i = 0; i < item.size(); i++) {
				FileItem fileItem=(FileItem)item.get(i);
				
				//上传文件大于50MB 终止上传
				if ((int)fileItem.getSize()>102428800) {
					out.print("<script>alert('您选择的文件大于100MB，不能上传！');window.location.href='upload.jsp'</script>");
					return ;
				}
				
				//进行判断操作  1.文本  2.文件
				if (fileItem.isFormField()) {
					//如果是文本
					if ("sname".equals(fileItem.getFieldName())) {
						sourceBean.setSname(new String(fileItem.getString().getBytes("iso-8859-1"),"utf-8"));
					}
					if ("tag".equals(fileItem.getFieldName())) {
						sourceBean.setTag(new String(fileItem.getString().getBytes("iso-8859-1"),"utf-8"));
					}
					if ("integral".equals(fileItem.getFieldName())) {
						sourceBean.setIntegral(Integer.parseInt(fileItem.getString()));
					}
					if ("summary".equals(fileItem.getFieldName())) {
						sourceBean.setSummary(new String(fileItem.getString().getBytes("iso-8859-1"),"utf-8"));
					}
				}else{
					//如果是文件  并且判断  上传文件是否选择  如果没有选择就不上传
					if ( !fileItem.getName().equals("")) {
						//当选择上传文件时
						sourceBean.setSize((int)fileItem.getSize());//保存文件的大小
						String  suffix=fileItem.getName().substring(fileItem.getName().lastIndexOf("."));//获取文件的后缀(即是文件的类型)
						
						String newName=getRandomFileName()+suffix;//新的文件名
						
						File temp=new File(application.getRealPath("/")+"upload/"+user.getUsername());//生成一个用户名的目录
						
						temp.mkdirs();//创建目录  如果有则不创建 没有则创建
						
						File file=new File(application.getRealPath("/")+"upload/"+user.getUsername()+"/"+newName);//生成需要保存的file
						
					
						fileItem.write(file); //写入
						
						sourceBean.setSpath("upload/"+user.getUsername()+"/"+newName);//保存文件路径
						
					}else {
						//提示 选择需要上传的文件
						out.print("<script>alert('请选择要上传的文件！');window.location.href='upload.jsp'</script>;");
						return ;
					}
				}
				
			}
			
			SourceDao sourceDao=new SourceDao();
			sourceBean.setUploadtime(new Timestamp(System.currentTimeMillis()));//设置上传文件的时间
			sourceBean.setUid(user.getId());
			
			sourceDao.UploadFilesServlet(sourceBean);//调用sourceDao的方法 将数据存入数据库
			
			out.print("<script>alert('上传成功! 等待系统审核,审核通过则可以获得积分');window.location.href='SourceServlet.action?flag=mySource'</script>;");
			//request.getRequestDispatcher("my.jsp").forward(request, response);
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

    /** 
     * 生成随机文件名：当前年月日时分秒+五位随机数 
     *  
     * @return 
     */  
    public static String getRandomFileName() {  
  
        SimpleDateFormat simpleDateFormat;  
        simpleDateFormat = new SimpleDateFormat("yyyyMMdd");  
        Date date = new Date();  
        String str = simpleDateFormat.format(date);  
  
        Random random = new Random();  
  
        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数  
  
        return str+rannum;// 当前年月日时分秒+五位随机数 
    }

}
