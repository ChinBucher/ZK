package controller;

import interceptor.UserInterceptor;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

public class LoginController extends Controller{
	/**
	 * 使用validator 判断 入口actionkey 来进行跳转 
	 * 不会出现管理页面紊乱
	 */
	@Before(UserInterceptor.class)
	public void index(){
		render("login.html");
	}

}
