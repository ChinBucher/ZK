package controller;

import com.jfinal.core.Controller;

public class HelloController extends Controller {
	public void index(){
		render("/editor/index.html");
//		render("/kindeditor/demo.html");
	}

}
