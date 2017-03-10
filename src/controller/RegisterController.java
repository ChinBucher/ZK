package controller;

import com.jfinal.core.Controller;

public class RegisterController extends Controller{
	public void index(){
		render("/user/register.html");
	}
}
