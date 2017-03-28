/**
 * 下载拦截器
 * 对于空文件的处理
 */
package interceptor;

import model.Statute;

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
		/*
		 * 此处代码针对statute修改
		 */
		Controller c = inv.getController();
		int id = c.getParaToInt();
		System.out.println("id: " + id);
		String n = Statute.stat.docPath(id);
		System.out.println("n : "+n);
		System.out.println("length: "+n.length());
		
		inv.invoke();
		
//		if( n.length()==0 ){
//			String actionKey = inv.getActionKey();
//			System.out.println("actionKey: " + actionKey);
//			c.redirect("/statute");
//		}
//		if(n.length() <= 1){
//			c.setAttr("isfile", 0);
//			
//		}else{
//			c.setAttr("id", id);
//		}
	}

}
