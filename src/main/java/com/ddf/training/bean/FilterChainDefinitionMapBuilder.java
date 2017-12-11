package com.ddf.training.bean;

import java.util.LinkedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 用于实例化工厂bean。把系统所有的权限加载出来。
 * @author Administrator
 *
 */
public class FilterChainDefinitionMapBuilder {
private Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	public LinkedHashMap<String, String> buildFilterChainDefinitionMap() {
		log.info("............FilterChainDefinitionMapBuilder [buildFilterChainDefinitionMap] init ............");
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("/pages/login.jsp", "anon");
		map.put("/user/loginByShiro", "anon");
		map.put("/pages/unauthorized.jsp", "anon");
		map.put("/pages/logout.jsp", "logout");
		map.put("/pages/list.jsp", "authc");
		map.put("/pages/admin.jsp", "authc,roles[admin]");
		map.put("/**", "authc");
		return map;
	}
}
