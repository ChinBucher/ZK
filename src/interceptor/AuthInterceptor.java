/**
 * 全局拦截器
 * 拦截非管理员用户的权限
 */
package interceptor;

import model.User;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;


public class AuthInterceptor implements Interceptor{

	public void intercept(Invocation inv) {
		// TODO Auto-generated method stub
//		管理员的拦截器，你不是管理员，不配进去
		Controller c = inv.getController();
		System.out.println("AuthInterceptor_inv.c: " + c);
		User loginUser = c.getSessionAttr("loginUser");
		System.out.println("loginUser: " + loginUser);
		
		inv.invoke();
		
		if(loginUser == null){
			System.out.println("AuthInterceptor_null");
			c.redirect("/login");
		}else{
			System.out.println("AuthInterceptor_notnull");
		}
	}

}
