/**
 * demo 暂未用
 */
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
		
		inv.invoke();
		
		if(loginUser != null ){
			System.out.println("UserInterceptor_user not null");
			controller.redirect("/admin");
		}else{
			System.out.println("UserInterceptor_user null");
		}
	}

}
