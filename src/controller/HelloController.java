package controller;

import com.jfinal.core.Controller;

public class HelloController extends Controller {
	public void index(){
		renderText("Guide: \n"
				+ "/index->index.html\n"
				+ "/login->login.html\n"
				+ "/src->src.html\n"
				+ "/detail->detail.html");
	}

}
