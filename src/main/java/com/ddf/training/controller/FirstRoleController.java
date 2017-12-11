package com.ddf.training.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ddf.training.service.FirstRoleService;

@Controller
@RequestMapping("/firstRole")
public class FirstRoleController {
	
	@Autowired
	private FirstRoleService firstRoleService;
	
	/**
	 * 只有admin角色的人才能下载，才能调用这个方法
	 * @param request
	 * @param response
	 */
	@RequestMapping("/responsePicByAdminRole")
	public void responsePicByAdminRole(HttpServletRequest request, HttpServletResponse response) {
		// 这个只有admin角色的人才能调用这个service。
		firstRoleService.responsePicByAdmin();
		File file = new File(request.getServletContext().getRealPath("/pic/userIcon.png"));
		FileInputStream infis = null;
		OutputStream out = null;
		try {
			response.setContentType("image/png");
			response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(file.getName(), "UTF-8"));
			out = response.getOutputStream();
			infis = new FileInputStream(file);
			byte []buf = new byte[1024];
			while((infis.read(buf)) != -1) {
				out.write(buf);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (infis != null) {
				try {
					infis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
