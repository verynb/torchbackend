package com.torch.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义参数传递类
 * @author Yang.Le
 *
 */
public class Param extends HashMap<String, Object>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Param(){
		
	}
	public Param(int i){
		super(i);
	}
	public static Param blankParam(){
		return new Param(0);
	}
	public Param(Map<String,Object> map){
		this.putAll(map);
	}
	public Param(String key,Object value){
		this.put(key, value);
	}
	public Param p(String key,Object value){
		this.put(key, value);
		return this;
	}
	
}
