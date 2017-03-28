package controller;

import interceptor.AuthInterceptor;
import model.Active;
import model.News;
import model.Source;
import model.Statute;
import model.User;

import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;

public class AdminController extends Controller{
	@ActionKey("/admin")
	@Before(AuthInterceptor.class)
	public void admin(){
		setAttr("userPage", User.user.paginate(getParaToInt(0, 1), 10));
		setAttr("srcPage", Source.src.paginate(getParaToInt(0, 1), 10));
		
		setAttr("statPage", Statute.stat.paginate(getParaToInt(0, 1), 10));
		setAttr("actPage", Active.act.paginate(getParaToInt(0, 1), 10));
		setAttr("newsPage", News.news.paginate(getParaToInt(0, 1), 10));
		
		render("/page/admin.html");
//		render("/admin.html");
	}

}
