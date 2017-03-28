/**
 * 后端路由配置
 */
package routes;

import com.jfinal.config.Routes;

import controller.AdminController;
import controller.LoginController;
import controller.RegisterController;
import controller.SourceController;
import controller.UserController;

public class AdminRoutes extends Routes{

	@Override
	public void config() {
		// TODO Auto-generated method stub
		add("login", LoginController.class, "/user"); //自写
//		add("/login", LoginController.class, "/page"); //框架
		add("admin", AdminController.class);
		
//		add("/register", RegisterController.class);
		add("user", UserController.class);
//		add("/src", SourceController.class);
	}

}
