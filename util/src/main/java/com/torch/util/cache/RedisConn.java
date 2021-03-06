package com.torch.util.cache;
  
import org.springframework.boot.context.properties.ConfigurationProperties;  
import org.springframework.stereotype.Component;  
 
/**
 * 
 * 
 * @author service#yangle.org.cn
 * @date 2017年1月17日 下午1:59:23 
 *
 */
@Component  
@ConfigurationProperties(prefix = "spring.redis")  
public class RedisConn {  
      
    private String host;  
      
    private int port;  
      
    private int timeout;  
    
    private String password;
  
  
    public String getHost() {  
        return host;  
    }  
  
    public void setHost(String host) {  
        this.host = host;  
    }  
  
    public int getPort() {  
        return port;  
    }  
  
    public void setPort(int port) {  
        this.port = port;  
    }  
  
    public int getTimeout() {  
        return timeout;  
    }  
  
    public void setTimeout(int timeout) {  
        this.timeout = timeout;  
    }  
  
    @Override  
    public String toString() {  
        return "Redis [localhost=" + host + ", port=" + port + ", timeout=" + timeout + ", password=" + password + "]";  
    }

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}  
      
  
}  