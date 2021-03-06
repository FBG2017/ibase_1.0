package org.ibase4j.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.ibase4j.core.base.AbstractController;
import org.ibase4j.core.base.Parameter;
import org.ibase4j.core.config.Resources;
import org.ibase4j.core.exception.LoginException;
import org.ibase4j.core.support.Assert;
import org.ibase4j.core.support.HttpCode;
import org.ibase4j.core.support.login.LoginHelper;
import org.ibase4j.core.util.SecurityUtil;
import org.ibase4j.core.util.WebUtil;
import org.ibase4j.model.SysUser;
import org.ibase4j.provider.ISysProvider;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 用户登录
 * 
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:11:21
 */
@RestController
@Api(value = "登录接口", description = "登录接口")
public class LoginController extends AbstractController<ISysProvider> {

	public String getService() {
		return "sysUserService";
	}

	// 登录
	@ApiOperation(value = "用户登录")
	@PostMapping("/login")
	public Object login(@ApiParam(required = true, value = "登录帐号和密码") @RequestBody SysUser sysUser, ModelMap modelMap,
			HttpServletRequest request) {
		sysUser.setAccount("admin");
		sysUser.setPassword("111111");
		System.out.println("管理员来到登录接口");
		Assert.notNull(sysUser.getAccount(), "ACCOUNT");
		Assert.notNull(sysUser.getPassword(), "PASSWORD");
		//!
//		
//		System.out.println("login的值为多少"+login);
		if (LoginHelper.login(sysUser.getAccount(), SecurityUtil.encryptPassword(sysUser.getPassword()))) {
			request.setAttribute("msg", "[" + sysUser.getAccount() + "]登录成功.");
			return setSuccessModelMap(modelMap);
		}
		//modelMap.addAttribute("", attributeValue)
		request.setAttribute("msg", "[" + sysUser.getAccount() + "]登录失败.");
		throw new LoginException(Resources.getMessage("LOGIN_FAIL"));
	}

	// 登出
	@ApiOperation(value = "用户登出")
	@PostMapping("/logout")
	public Object logout(ModelMap modelMap) {
		System.out.println("用户登出");
		Long id = WebUtil.getCurrentUser();
		if (id != null) {
			provider.execute(new Parameter("sysSessionService", "delete").setId(id));
		}
		SecurityUtils.getSubject().logout();
		return setSuccessModelMap(modelMap);
	}

	// 注册
	@ApiOperation(value = "用户注册")
	@PostMapping("/regin")
	public Object regin(ModelMap modelMap, @RequestBody SysUser sysUser) {
		System.out.println("来到注册接口");
		Assert.notNull(sysUser.getAccount(), "ACCOUNT");
		Assert.notNull(sysUser.getPassword(), "PASSWORD");
		sysUser.setPassword(SecurityUtil.encryptPassword(sysUser.getPassword()));
		provider.execute(new Parameter("sysUserService", "update").setModel(sysUser));
		if (LoginHelper.login(sysUser.getAccount(), sysUser.getPassword())) {
			return setSuccessModelMap(modelMap);
		}
		throw new IllegalArgumentException(Resources.getMessage("LOGIN_FAIL"));
	}

	// 没有登录
	@ApiOperation(value = "没有登录")
	@RequestMapping(value = "/unauthorized", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
	public Object unauthorized(ModelMap modelMap)
			throws Exception {
		System.out.println("因为没有权限");
		return setModelMap(modelMap, HttpCode.UNAUTHORIZED);
	}

	// 没有权限
	@ApiOperation(value = "没有权限")
	@RequestMapping(value = "/forbidden", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
	public Object forbidden(ModelMap modelMap) {
		return setModelMap(modelMap, HttpCode.FORBIDDEN);
	}
}
