package com.ddf.training.service.impl;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Service;

import com.ddf.training.service.FirstRoleService;

@Service
public class FirstRoleServiceImpl implements FirstRoleService {
	
	@RequiresRoles({"admin"})
	@Override
	public void responsePicByAdmin() {
		
	}
}
