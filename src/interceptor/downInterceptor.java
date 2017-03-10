package interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

public class downInterceptor implements Interceptor{

	public void intercept(Invocation inv) {
		// TODO Auto-generated method stub
//		inv.getController().setAttr("url", ai.getActionKey() + ai.getController().getPara());
//		Controller c = inv.getController();
//		String actionKey = inv.getActionKey();
//		c.setAttr("action", actionKey + c.getPara());
		inv.invoke();
	}

}
