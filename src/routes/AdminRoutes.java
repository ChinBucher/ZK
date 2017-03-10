package routes;

import com.jfinal.config.*;

import controller.AdminController;

public class AdminRoutes extends Routes{

	@Override
	public void config() {
		// TODO Auto-generated method stub
		add("/admin", AdminController.class);
		
	}

}
