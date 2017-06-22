package com.torch.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;

/**
 * 框架工具类
 * @author Yang.Le
 * 
 */
public class CommonFun {
//	private static final Logger log = CommonFun.getLog(CommonFun.class);
	private static final String X_REAL_IP = "X-Real-IP";

	/**
	 * 判断Object是否为空
	 * @param o
	 * @return
	 */
	public static boolean isNe(Object o) {
		if (o == null)
			return true;
		if (o instanceof String) {
			if (o.toString().trim().length() == 0) {
				return true;
			}
		} else if (o.getClass().isArray()) {
			if(o.getClass() == byte[].class){
				byte[] bt = (byte[])o;
				if(bt.length==0){
					return true;
				}else{
					return false;
				}
			}else if(o.getClass() == char[].class){
				char[] bt = (char[])o;
				if(bt.length==0){
					return true;
				}else{
					return false;
				}
			}
			if(o instanceof Object[]){
				Object[] to = (Object[]) o;
				for (int i = 0; i < to.length; i++) {
					if (!isNe(to[i])) {
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}

	

	/**
	 * 加载配置文件
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static Properties loadPropertiesFile(String file) throws IOException {
		Properties p = new Properties();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			p.load(fis);
			String charset = p.getProperty("charset");
			if (charset != null) {
				for (Enumeration<?> e = p.propertyNames(); e.hasMoreElements();) {
					String key = (String) e.nextElement();
					p.setProperty(key, new String(p.getProperty(key).getBytes("ISO-8859-1"), charset));
				}
			}
		} catch (IOException iex) {
			throw iex;
		}finally{
			if (fis != null) {
				fis.close();
			}
		}
		return p;
	}



	/**
	 * 根据连接IP和端口生成唯一的UUID，长度32
	 * 
	 * @return String
	 */
	public static synchronized String getSessionID(String ip, int port) {
		String id = UUID.nameUUIDFromBytes((ip + port).getBytes()).toString();
		id = id.replaceAll("-", "");
		id = id.toUpperCase();
		return id;
	}

//	/**
//	 * 获取参数键-值对
//	 * @param request
//	 * @return
//	 */
//	public static Param requestMap(HttpServletRequest request) {
//		Param map = new Param();
//		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
//		if (isMultipart) {
//			requestMultipartMap(request, map);
//		}
//		Enumeration<String> names = request.getParameterNames();
//		while (names.hasMoreElements()) {
//			String name = names.nextElement();
//			String[] values = request.getParameterValues(name);
//			if (values != null && values.length == 1) {
//				map.put(name, values[0]);
//			} else {
//				map.put(name, values);
//			}
//		}
//
//		return map;
//	}
//
//	@SuppressWarnings("unchecked")
//	private static void requestMultipartMap(HttpServletRequest request, HashMap<String, Object> map) {
//		FileItemFactory factory = new DiskFileItemFactory();
//		ServletFileUpload upload = new ServletFileUpload(factory);
//
//		List<FileItem> items = null;
//		try {
//			items = upload.parseRequest(request);
//		} catch (FileUploadException e) {
//			log.error(e.getMessage());
//		}
//		if (items == null) {
//			return;
//		}
//
//		HashMap<String, List<Object>> listMap = new HashMap<String, List<Object>>();
//		Iterator<FileItem> it = items.iterator();
//		while (it.hasNext()) {
//			FileItem item = it.next();
//
//			String name = item.getFieldName();
//			Object value = null;
//			if (item.isFormField() == true) {
//				name = item.getFieldName();
//				try {
//					value = new String(item.get(), "utf-8");
//				} catch (UnsupportedEncodingException e) {
//					;
//				}
//			} else {
//				value = new FileItemImpl(item);
//			}
//
//			if (listMap.containsKey(name) == false) {
//				listMap.put(name, new ArrayList<Object>());
//			}
//
//			List<Object> list = listMap.get(name);
//			list.add(value);
//		}
//
//		Iterator<String> its = listMap.keySet().iterator();
//		while (its.hasNext()) {
//			String name = its.next();
//			List<Object> list = listMap.get(name);
//
//			if (list.size() == 1) {
//				map.put(name, list.get(0));
//			} else {
//				map.put(name, list.toArray());
//			}
//		}
//	}

	private static class FileItemImpl implements FileItem {
		private static final long serialVersionUID = 1L;

		private FileItem item = null;

		public FileItemImpl(FileItem item) {
			super();
			this.item = item;
		}

		@Override
		public void delete() {
			item.delete();
		}

		@Override
		public byte[] get() {
			return item.get();
		}

		@Override
		public String getContentType() {
			return item.getContentType();
		}

		@Override
		public String getFieldName() {
			return item.getFieldName();
		}

		@Override
		public InputStream getInputStream() throws IOException {
			return item.getInputStream();
		}

		@Override
		public String getName() {
			String name = item.getName();
			return name.substring(name.lastIndexOf('\\') + 1, name.length());
		}

		@Override
		public OutputStream getOutputStream() throws IOException {
			return item.getOutputStream();
		}

		@Override
		public long getSize() {
			return item.getSize();
		}

		@Override
		public String getString() {
			return item.getString();
		}

		@Override
		public String getString(String encoding) throws UnsupportedEncodingException {
			return item.getString(encoding);
		}

		@Override
		public boolean isFormField() {
			return item.isFormField();
		}

		@Override
		public boolean isInMemory() {
			return item.isInMemory();
		}

		@Override
		public void setFieldName(String name) {
			item.setFieldName(name);
		}

		@Override
		public void setFormField(boolean state) {
			item.setFormField(state);
		}

		@Override
		public void write(File file) throws Exception {
			item.write(file);
		}

	}
	
	public static <T> T mapToBean(Class<T> type, Map<String, Object> map)
			throws IntrospectionException, IllegalAccessException, InstantiationException, InvocationTargetException {
		BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
		T obj = type.newInstance(); // 创建 JavaBean 对象

		// 给 JavaBean 对象的属性赋值
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();

			if (map.containsKey(propertyName)) {
				// 下面一句可以 try 起来,这样当一个属性赋值失败的时候就不会影响其他属性赋值.
				Object value = map.get(propertyName);

				Object[] args = new Object[1];
				args[0] = value;
				
				if(propertyName.equalsIgnoreCase("id")){
					args[0] = Long.valueOf(value.toString());
				}

				descriptor.getWriteMethod().invoke(obj, args);
			}
		}
		return obj;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> beanToMap(Object bean) throws IntrospectionException, IllegalAccessException, InvocationTargetException {
		Class type = bean.getClass();
		Map returnMap = new HashMap();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);

		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if (result != null) {
					returnMap.put(propertyName, result);
				} else {
					returnMap.put(propertyName, "");
				}
			}
		}
		return returnMap;
	}

	public static boolean inArray(String[] array, String target) {
		if (array == null)
			return false;
		if (array.length == 0)
			return false;
		for (String s : array) {
			if (s == null) {
				if (target == null) {
					return true;
				}
				continue;
			}
			if (s.equals(target)) {
				return true;
			}
		}
		return false;
	}

	public static String joinArray(Object[] array, String split) {
		if (array == null)
			return null;
		if (split == null) {
			split = ",";
		}
		StringBuilder rtn = new StringBuilder();
		for (Object s : array) {
			if (s == null) {
				continue;
			}
			rtn.append(s);
			rtn.append(split);
		}
		return rtn.toString().substring(0, rtn.length() - split.length());
	}

//	public static Map<String, String> getUserAgent(HttpServletRequest req) {
//		Map<String, String> sys = new HashMap<String, String>();
//		UserAgent userAgent = UserAgent.parseUserAgentString(req.getHeader("User-Agent"));
//		sys.put("os", userAgent.getOperatingSystem().getName());
//		sys.put("type", userAgent.getBrowser().getName());
//		eu.bitwalker.useragentutils.Version version = userAgent.getBrowserVersion();
//		sys.put("version", version == null ? "" : version.getVersion());
//		return sys;
//	}
//
//
//
//	public static Logger getLog(Class<?> clazz) {
//		return LoggerFactory.getLogger(clazz);
//	}
//
//	public static String getRequestIp(HttpServletRequest req) {
//		if (req == null) {
//			return "";
//		}
//		String ip = req.getRemoteAddr();
//		String rip = req.getHeader(X_REAL_IP);
//		if (!CommonFun.isNe(rip)) {
//			ip = rip;
//		}
//		if (CommonFun.isNe(ip)) {
//			ip = "null";
//		}
//		return ip;
//	}
//	
//	/** 
//     * 获取当前网络ip 
//     * @param request 
//     * @return 
//     */  
//    public static String getIpAddr(HttpServletRequest request){  
//        String ipAddress = request.getHeader("x-forwarded-for");  
//        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
//            ipAddress = request.getHeader("Proxy-Client-IP");  
//        }  
//        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
//            ipAddress = request.getHeader("WL-Proxy-Client-IP");  
//        }  
//        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
//        	ipAddress = request.getHeader("HTTP_CLIENT_IP");
//        }  
//        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
//        	ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");  
//        }  
//        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
//            ipAddress = request.getRemoteAddr();  
//            if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1") || ipAddress.equals("127.0.1.1")){  
//                //根据网卡取本机配置的IP  
//                InetAddress inet=null;  
//                try {  
//                    inet = InetAddress.getLocalHost();  
//                } catch (UnknownHostException e) {  
//                    e.printStackTrace();  
//                }  
//                ipAddress= inet.getHostAddress();  
//            }  
//        }  
//        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割  
//        if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15  
//            if(ipAddress.indexOf(",")>0){  
//                ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));  
//            }  
//        }  
//        return ipAddress;   
//    }

	public static String getLocalIP() {
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		byte[] ipAddr = addr.getAddress();
		String ipAddrStr = "";
		for (int i = 0; i < ipAddr.length; i++) {
			if (i > 0) {
				ipAddrStr += ".";
			}
			ipAddrStr += ipAddr[i] & 0xFF;
		}
		// System.out.println(ipAddrStr);
		return ipAddrStr;
	}
	

}
