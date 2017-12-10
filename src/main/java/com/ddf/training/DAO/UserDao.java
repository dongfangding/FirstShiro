package com.ddf.training.DAO;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import com.ddf.training.entity.User;
import com.ddf.training.util.ConstantsUtil;


public class UserDao {
	private static Map<String, User> userMap = new LinkedHashMap<>();
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
}
