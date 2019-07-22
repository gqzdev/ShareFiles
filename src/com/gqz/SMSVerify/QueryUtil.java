package com.gqz.SMSVerify;


//快捷键Ctrl+shift+O 快速处理package
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 
* @ClassName: QueryUtil  
* @Description: 发送验证码工具类(这里用一句话描述这个类的作用) 
* @author ganquanzhong  
* @date 2017年7月6日 下午5:19:47  
*   
 */
public class QueryUtil {
	
	/**
	 * 
	* @Title: getArguments
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author ganquanzhong
	* @date  2018年4月7日 下午1:09:14
	* @param ACCOUNT_SID 用户ID
	* @param AUTH_TOKEN 用户密匙
	* @param SMSContent 短信内容
	* @param to  手机号码
	* @return
	 */
	public static String getArguments(String ACCOUNT_SID,  String AUTH_TOKEN, String SMSContent, String to){
		//时间戳
		String timestamp =getTimestamp();
		//签名验证
		String sig=MD5(ACCOUNT_SID,AUTH_TOKEN,timestamp);
		
		//拼接参数 (用户ID  + 短信内容  + 手机号码  + 时间戳 +签名验证 )
		String prams="accountSid="+ACCOUNT_SID
				+"&smsContent="+SMSContent
				+"&to="+to
				+"&timestamp="+timestamp
				+"&sig="+sig;
		return prams;
	}
	
	/**
	 * 
	* @Title: getTimestamp
	* @Description: 获取时间戳
	* @author ganquanzhong
	* @date  2018年4月7日 下午1:27:45
	* @return
	 */
	public static String getTimestamp(){
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
		return format.format(new Date());
	}
	
	/**
	 * 
	* @Title: MD5
	* @Description: MD5加密
	* @author ganquanzhong
	* @date  2018年4月7日 下午1:28:17
	* @param args
	* @return
	 */
	public static String MD5(String...args){//MD5加密
		StringBuffer result=new StringBuffer();
		if(args==null||args.length==0){
			return "";
		}else{//增强for循环
			StringBuffer sb=new StringBuffer();
			for(String strs:args){
				sb.append(strs);
			}
//			System.out.println("加密前："+sb.toString());
			try {
				MessageDigest digest = MessageDigest.getInstance("MD5");
				//将要加密的字符串转换成字节数组
				byte[] bytes=digest.digest(sb.toString().getBytes());
				//循环遍历字节数组进行加密
				for (byte bt: bytes) {
					//转化成无符号整数参数所表示的十六进制
					String hex=Integer.toHexString(bt&0xff);
					if(hex.length()==1){
						result.append("0"+hex);
					}else{
						result.append(hex);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
//			System.out.println("加密后："+result.toString());
			return result.toString();
		}	
	}
}
