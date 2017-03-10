package interceptor;

import model.User;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

public class UserInterceptor implements Interceptor{

	public void intercept(Invocation inv) {
		// TODO Auto-generated method stub
		Controller controller = inv.getController();
		User loginUser = controller.getSessionAttr("loginUser");
		if(loginUser != null ){
			inv.invoke();
		}else{
			controller.redirect("/src");
		}
	}

}
