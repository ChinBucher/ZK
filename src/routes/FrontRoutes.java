/**
 * 前端路由
 */
package routes;

import com.jfinal.config.Routes;

import controller.AboutController;
import controller.ActiveController;
import controller.HelloController;
import controller.IndexController;
import controller.NewsController;
import controller.ServicesController;
import controller.StatuteController;
import controller.SubjectController;

public class FrontRoutes extends Routes{
	@Override
	public void config() {
		// TODO Auto-generated method stub
		
		add("/", IndexController.class);
//		重新结合前端页面配置路由
		add("about", AboutController.class);
		add("subject", SubjectController.class);
		add("services", ServicesController.class);
		
		add("statute", StatuteController.class);
		add("active", ActiveController.class);
		add("news", NewsController.class);
		
		add("hello", HelloController.class);
//		add("/user", UserController.class);
//		add("/src", SourceController.class);
//		add("/login", LoginController.class, "/user");
//		add("/register", RegisterController.class);
//		add("logout", LogoutController.class, "/user");
	}

}
