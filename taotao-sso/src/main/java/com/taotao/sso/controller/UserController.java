package com.taotao.sso.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.tools.classfile.Annotation.element_value;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	/**
	 * 从url中接收两个参数，调用Service进行校验，在调用Service之前，先对参数进行校验
	 * @param param
	 * @param type
	 * @param callback
	 * @return
	 */
	@RequestMapping("/check/{param}/{type}")
	@ResponseBody
	public Object checkData(@PathVariable String param, @PathVariable Integer type, String callback) {
		TaotaoResult result = null;
		
		//参数有效性校验
		if (StringUtils.isBlank(param)) {
			result = TaotaoResult.build(400, "校验内容不能为空");
		}
		if (type == null) {
			result = TaotaoResult.build(400, "校验内容类型不能为空");
		}
		if (type != 1 && type != 2 && type != 3) {
			result = TaotaoResult.build(400, "校验内容类型错误");
		}
		if (result != null) {
			if (callback != null) {
				MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
				mappingJacksonValue.setJsonpFunction(callback);
				return mappingJacksonValue;
			}else {
				return result;
			}
		}
		
		try {
			result = userService.checkData(param, type);
		} catch (Exception e) {
			// TODO: handle exception
			result = TaotaoResult.build(500,ExceptionUtil.getStackTrace(e));
		}
		
		if (callback != null) {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		}else {
			return result;
		}
	}
	
	//创建用户
	@RequestMapping(value="/register", method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult createUser(TbUser user) {
		try {
			TaotaoResult result = userService.createUser(user);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return TaotaoResult.build(400, ExceptionUtil.getStackTrace(e));
		}
	}
	
	//用户登录
	@RequestMapping(value="/login", method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult userLogin(String username, String password,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			TaotaoResult result = userService.userLogin(username, password, request, response);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	@RequestMapping("/token/{token}")
	@ResponseBody
	public Object getUserByToken(@PathVariable String token, String callback) {
		TaotaoResult result = null;
		try {
			result = userService.getUserByToken(token);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result = TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		
		//判断是否为jsonp调用
		if (StringUtils.isBlank(callback)) {
			return result;
		}else {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
			//设置回调方法
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		}
	}
	
	/**
	 * 安全退出
	 * @param token
	 * @param callback
	 * @return
	 */
	@RequestMapping("/logout/{token}")
	@ResponseBody
	public Object logoutByToken(@PathVariable String token, String callback) {
		//根据token在redis中删除对应的数据
		TaotaoResult result = userService.logoutByToken(token);
		if (StringUtils.isBlank(callback)) {
			return result;
		}else {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		}
	}
}
