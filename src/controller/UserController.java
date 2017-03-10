package controller;

import validator.UserValidator;
import model.User;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;

public class UserController extends Controller{
	public void index(){
		setAttr("userPage", User.user.paginate(getParaToInt(0, 1), 10));
		render("/user/admin.html");
	}
	
	//已有前端验证，暂不用@Before
	public void register() {
		System.out.println("register");
		String name = getPara("user.name"),
				  pwd   = getPara("user.pwd"),
				  role    = getPara("user.role");
		
		String sql = "select * from user where name = ? and pwd = ? ;";		
		User user = User.user.findFirst(sql,name,pwd);
		System.out.println(user);
		
		if(user == null){
			System.out.println("可添加");
			User u = getModel(User.class);
			System.out.println("u: "+u);
			getModel(User.class).save();
			setAttr("code", 1);
		}else{
			setAttr("code", 0);
		}
		renderJson();
	}
	
	public void login(){
		String loginName = getPara("loginName");
		String pwd = getPara("password");
		String user_pwd = "";
		System.out.println("loginName?"+loginName);
		System.out.println("password?"+pwd);
		String sql = "select * from user where name = ? and pwd = ?;";
		User nowUser = User.user.findFirst(sql,loginName,pwd);
		System.out.println("nowUser: "+nowUser);
		
        if (nowUser == null) {
        	setAttr("code",0);
        }else{
        	user_pwd = nowUser.get("pwd");
        	System.out.println("pwd: "+user_pwd);
            if(user_pwd.equals(pwd)){
            	setAttr("code",1);
            	setAttr("name",nowUser.get("name"));
            }else{
            	setAttr("code",0);
            }
          //set login session
            setSessionAttr("loginUser", nowUser);
        }
        int role = nowUser.get("role");
        //int role = getSessionAttr("userRole");
		System.out.println("role:"+role);
        renderJson();
	}
	
	public void logout(){
		removeSessionAttr("loginUser");
		setAttr("role",null);
		redirect("/");
	}
	
	//用户详情
	public void detail(){
/* blog 系统完成后使用
 
		//得到当前用户
		User userNow = getSessionAttr("loginUser");
		System.out.println("userNow:"+userNow);
		//得到当前用户的博客
		int author_id = userNow.get("id");
		System.out.println("author_id:"+author_id);
		//String sql = "select * from blog where author_id = ?;";
		//Blog blog = Blog.me.findFirst(sql,id);
		List<Blog> blog = Blog.me.find("select * from blog where author_id = "+author_id+";");
		
		setAttr("user",userNow);
		setAttr("blogPage", blog);
*/		render("/user/detail.html");
	}
	
//	管理员的高级功能 简化版
	public void delete() {
		User.user.deleteById(getParaToInt());
		redirect("/user");
	}
	
	public void edit() {
		setAttr("user", User.user.findById(getParaToInt()));
	}
	
	@Before(UserValidator.class)
	public void update() {
		User u = getModel(User.class);
		System.out.println(u);
//		u.update();
		Db.update("update user set pwd=?, name=? where Id=?",u.getPwd(), u.getName(), u.getId());
//		getModel(User.class).update();
		redirect("/user");
	}

}
