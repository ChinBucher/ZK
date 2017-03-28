package controller;

import interceptor.UserInterceptor;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

public class LoginController extends Controller{
	@Before(UserInterceptor.class)
	public void index(){
		render("login.html");
	}

}
