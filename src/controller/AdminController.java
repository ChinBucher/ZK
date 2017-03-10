package controller;

import model.Source;
import model.User;

import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;

public class AdminController extends Controller{
	@ActionKey("/admin")
	public void admin(){
		setAttr("userPage", User.user.paginate(getParaToInt(0, 1), 10));
		setAttr("srcPage", Source.src.paginate(getParaToInt(0, 1), 10));
		
		render("/user/admin.html");
	}

}
