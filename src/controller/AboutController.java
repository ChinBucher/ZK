package controller;

import com.jfinal.core.Controller;

public class AboutController extends Controller{
	public void index(){
		render("/about.html");
	}

}
