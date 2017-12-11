package com.ddf.training.shiro;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ddf.training.DAO.UserDao;
import com.ddf.training.entity.User;
import com.ddf.training.util.ConstantsUtil;

public class UserRealm extends AuthorizingRealm {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	/**
	 * 执行Subject.login之后，shiro会自动来找Realm中自己实现的认证方法。经过实验，如果有多个Realm，并且其中一个登陆成功后
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		log.info("-----登陆认证： UserRealm----doGetAuthenticationInfo");
		// UsernamePasswordToken是实现了HostAuthenticationToken这个接口，HostAuthenticationToken实现了AuthenticationToken这个接口
		// ，所以可以强制转换为UsernamePasswordToken，来获得LoginController提交的UsernamePasswordToken对象封装的用户名和密码
		UsernamePasswordToken loginToken = (UsernamePasswordToken) token;
		String username = loginToken.getUsername();
		Object []loginTokenInfo = {loginToken.getUsername(), loginToken.getPassword(), loginToken.getPrincipal(), loginToken.getCredentials()};
		log.info("获得到UsernamePasswordToken信息 username: [{}], password: [{}], Principal: [{}], Credentials: [{}]", loginTokenInfo);
		User user = UserDao.getInstance().getUserByName(username);
		if (user == null) {
			throw new UnknownAccountException("用户不存在!");
		}
		// 构建 AuthenticationInfo 对象并返回. 通常使用的实现类为: SimpleAuthenticationInfo
		//1). principal: 认证的实体信息. 可以是 username, 也可以是数据表对应的用户的实体类对象. 
		Object principal = user;
		//2). credentials: 密码. 
		String loginPassword = new String(loginToken.getPassword());
		Object credentials = ConstantsUtil.saltMD5(username, loginPassword);
		//3). realmName: 当前 realm 对象的 name. 调用父类的 getName() 方法即可
		String realmName = getName();
		//4). 盐值. 
		ByteSource credentialsSalt = ByteSource.Util.bytes(username);
		String []saltInfo = {user.getUsername(), user.getPassword(), username, credentials.toString()};
		log.info("数据库的用户名：[{}], 密码: [{}], 当前用户名: [{}], 加密后密码：[{}]", saltInfo);
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName);
		log.info("返回的认证实体内容: {}", info.toString());
		return info;
	}

	
	/**
	 * 授权会被 shiro 回调的方法.每次需要访问需要角色权限的资源时，会自动调用这个方法来判断用户的角色是否能够匹配上
	 * @param principals
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		log.info("..............获得当前用户的权限数据-- UserRealm doGetAuthorizationInfo..........");
		//1. 从 PrincipalCollection 中来获取登录用户的信息
		User principal = (User) principals.getPrimaryPrincipal();
		log.info("...........当前登陆用户: [{}]", principal);
		//2. 利用登录的用户的信息来用户当前用户的角色或权限(可能需要查询数据库)
		Set<String> roleSet = new HashSet<>();
		UserDao userDao = UserDao.getInstance();
		List<String> roleList = userDao.getRolesByUserName(principal.getUsername());
		log.info("........当前登陆用户： [{}], 对应角色列表: [{}]", principal, roleList.toString());
		// 把当前用户的权限放入Set中
		if (roleList != null && roleList.size() > 0) {
			for (String role : roleList) {
				roleSet.add(role);
			}
		}
		//3. 创建 SimpleAuthorizationInfo, 并设置其 reles 属性.
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleSet);
		
		//4. 返回 SimpleAuthorizationInfo 对象. 
		return info;
	}
}
