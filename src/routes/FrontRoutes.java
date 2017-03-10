package routes;

import com.jfinal.config.Routes;

import controller.HelloController;
import controller.IndexController;
import controller.LoginController;
import controller.RegisterController;
import controller.SourceController;
import controller.UserController;

public class FrontRoutes extends Routes{
	@Override
	public void config() {
		// TODO Auto-generated method stub
		add("/hello", HelloController.class);
		
		add("/", IndexController.class);
		add("/user", UserController.class);
		add("/src", SourceController.class);
		
		add("/login", LoginController.class, "/user");
		add("/register", RegisterController.class);
//		add("logout", LogoutController.class, "/user");
		
	}

}
