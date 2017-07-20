package com.torch.interfaces.common.security;

import java.util.HashMap;
import java.util.Map;

import com.torch.domain.model.user.User;
import lombok.Data;

/**
 * Created by yuanj on 9/21/16.
 */
@Data
public class Session {

    private static final ThreadLocal<Session> threadSession = new ThreadLocal<Session>();

    private Long userId;

    private String username;

    public static Session buildSession() {
        Session session = new Session();
        return session;
    }

    public static Session buildSession(User user) {
        Session session = new Session();
        session.userId = user.getId();
        session.username = user.getName();
        return session;
    }

    public Session withUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Session withUsername(String username) {
        this.username = username;
        return this;
    }

    public static void persistenceCurrentSession(Session session) {
//        Session profile = threadSession.get();
//        if (profile == null) {
        	threadSession.set(session);
//        }
    }

    public static Session get() {    
        return threadSession.get();    
    }    
    
    public static Long getUserId() {
        return threadSession.get().userId;
    }
    
    public String getUserIdToString() {
        return userId.toString();
    }

    public static String getUsername() {
        return threadSession.get().username;
    }

    public Map<String, Object> getOtherMaps() {
        Map<String, Object> map = new HashMap<>();
        map.put("username", this.username);
        return map;
    }

    public static void remove(){  
    	if (threadSession!=null) {
    		threadSession.remove();  
		}
    	
    }  
}
