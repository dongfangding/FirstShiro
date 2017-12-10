package com.ddf.training.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/user")
@Controller
public class UserController {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 通过shiro来验证登陆, 必须要配置这个请求可以匿名访问 anon
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping(value="/loginByShiro", method=RequestMethod.POST)
	public ModelAndView loginByShiro(@RequestParam String username, @RequestParam String password) {
		log.info("-----------------login request .. username: {} and password: {}", username, password);
		// 先获得当前Subject对象
		Subject subject = SecurityUtils.getSubject();
		// 判断是否认证过,如果没有认证过，则执行登陆认证
		if (!subject.isAuthenticated()) {
			// 封装对象
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			// 允许remeberMe。remember不等于认证。但可以在下次打开浏览器的时候跳过登陆
			// token.setRememberMe(true);
			// 执行登陆方法。这个方法会到自己实现的realm中去校验。UserRealm
			try {
				// 执行登录. 
				subject.login(token);
				// 登陆成功后，返回/pages/list.jsp。不知道为什么shiroFilter配置的successUrl无效 
				ModelAndView mv = new ModelAndView();
				mv.setViewName("list");
				return mv;
            } // ... catch more exceptions here (maybe custom ones specific to your application?
            // 所有认证时异常的父类. 
            catch (AuthenticationException ae) {
                //unexpected condition?  error?
            	System.out.println("登录失败: " + ae.getMessage());
            }
			
		}
		return null;
	}
}
