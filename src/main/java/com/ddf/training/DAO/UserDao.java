package com.ddf.training.DAO;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ddf.training.entity.User;
import com.ddf.training.util.ConstantsUtil;


public class UserDao {
	private static Logger log = LoggerFactory.getLogger(UserDao.class);
	/** 用户 */
	private static Map<String, User> userMap = new LinkedHashMap<>(); 
	/** 权限数据 */
	private static Map<String, String[]> roleMap = new LinkedHashMap<>();
	private static final UserDao userDao = new UserDao();
	private UserDao() {}
	
	public static UserDao getInstance() {
		return userDao;
	}
	/**
	 * 初始化一条用户记录
	 */
	static {
		// admin 123456
		userMap.put("admin", new User("1", "admin", ConstantsUtil.saltMD5("admin", "123456")+"", "管理员"));
		userMap.put("ddf", new User("1", "ddf", ConstantsUtil.saltMD5("ddf", "123456")+"", "普通用户"));
		// 对应着数据库中的三条记录
		roleMap.put("admin", new String[]{"admin", "user"});
		roleMap.put("ddf", new String[]{"user"});
		log.info(".........初始化用户和角色信息...........");
		log.info("用户列表{}", userMap.values());
		StringBuilder sbl = new StringBuilder();
		for (Map.Entry<String, User> user : userMap.entrySet()) {
			sbl.append("用户[").append(user.getValue().getUsername()).append("];   ");
			String roleList[] = roleMap.get(user.getValue().getUsername());
			sbl.append("对应权限列表: [");
			for (String role : roleList) {
				sbl.append(role).append(",");
			}
			sbl.append("]");
		}
		log.info("角色列表: {}", sbl.toString());
	}
	
	public Collection<User> getAll() {
		return userMap.values();
	}
	
	public User getUserById(String id) {
		return userMap.get(id);
	}
	
	/**
	 * 根据用户名和密码获得用户
	 * @param username
	 * @param password
	 * @return
	 */
	public User getUser(String username, String password) {
		Collection<User> userList = getAll();
		for(User us : userList) {
			if(us.getUsername().equals(username) && us.getPassword().equals(password)) {
				return us;
			}
		}
		return null;
	}
	
	/**
	 * 验证是否存在相同的用户名
	 * @param username
	 * @return
	 */
	public boolean userIsExist(String username) {
		Collection<User> userList = getAll();
		for(User us : userList) {
			if(us.getUsername().equals(username)) {
				return true;
			}
		}
		return false;
	}
	
	public User getUserByName(String username) {
		return userMap.get(username);
	} 
	
	
	/**
	 * 根据用户名返回用户的角色
	 * @param username
	 * @return
	 */
	public List<String> getRolesByUserName(String username) {
		return Arrays.asList(roleMap.get(username));
	}
}
