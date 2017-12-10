package com.ddf.training.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConstantsUtil {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	public static Object saltMD5(String saltKey, String password) {
		String hashAlgorithmName = "MD5";
		Object credentials = password;
		Object salt = ByteSource.Util.bytes(saltKey);;
		int hashIterations = 1024;
		Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
		return result;
	}
	
	
	@Test
	public void testsaltMD5() {
		System.out.println(saltMD5("admin", "123456"));
		log.info("加密后: {}", saltMD5("admin", "123456"));
	}

}
