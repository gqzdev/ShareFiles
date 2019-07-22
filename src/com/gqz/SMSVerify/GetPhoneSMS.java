package com.gqz.SMSVerify;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import net.sf.json.JSONObject;

/**
 * 
 *  @ClassName: GetPhoneSMS    @Description: 获取短信验证码类 (这里用一句话描述这个类的作用) 
 *  @author ganquanzhong    @date 2017年7月6日 下午2:29:41      
 */
public class GetPhoneSMS {
	//用户ID fc66ee9dbe414868b16ad3368fd9e5de
	private static final String ACCOUNT_SID = "fc66ee9dbe414868b16ad3368fd9e5de";// 定义静态变量都是大写
	
	//用户密匙 de61dd189901441bbfe3d0c79bd028fd
	private static final String AUTH_TOKEN = "de61dd189901441bbfe3d0c79bd028fd";
	
	//请求地址 https://api.miaodiyun.com/20150822/industrySMS/sendSMS
	private static final String REST_URL = "https://api.miaodiyun.com/20150822/industrySMS/sendSMS";

	
	/**
	 * 
	* @Title: getResult
	* @Description: 获取发送给用户短信上的验证码
	* @author ganquanzhong
	* @date  2018年4月7日 下午1:24:23
	* @param to 需要发送短信的手机号码
	* @return 返回验证码
	 */
	public static String getResult(String to) {

		// 随机验证码
		String randNum = RandomUtil.getRandom();

		// 短信内容 "【ForFuture Group】验证码:"+randNum+"通往ForFuture心灵的密匙，打死都不要告诉别人哦！"
//		String SMSContent = "【ForFuture Group】验证码:" + randNum + ",通往ForFuture心灵的密匙，打死都不要告诉别人哦！";
		
		String SMSContent = "【未来集团】验证码：" + randNum + ",通往我心灵的密匙，打死都不要告诉别人哦！";

		// 获取请求参数
		String args = QueryUtil.getArguments(ACCOUNT_SID, AUTH_TOKEN, SMSContent, to);
		OutputStreamWriter out = null;
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		// 创建网络对象
		try {
			URL url = new URL(REST_URL);
			// 打开网络链接
			URLConnection connection = url.openConnection();
			// 设置链接参数
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setConnectTimeout(50000);
			connection.setReadTimeout(10000);
			// 提交数据
			out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			out.write(args);
			out.flush();
			// 设置网络请求
			// 读取返回数据
			br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JSONObject jsonObject = JSONObject.fromObject(sb.toString());
		
		//请求失败
		if (!jsonObject.get("respCode").equals("00000")) {
			return "error";
		}
		return randNum;
	}
}
