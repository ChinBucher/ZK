/**
 * 验证器 demo
 */
package validator;

import model.Source;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class SourceValidator extends Validator{

	@Override
	protected void handleError(Controller c) {
		// TODO Auto-generated method stub
		c.keepModel(Source.class);
		
		String actionKey = getActionKey();
		System.out.println("actionkey: " +actionKey);
		if(actionKey.equals("/statute/upload")){
			c.render("/src/statuteAdd.html");
		}else if(actionKey.equals("/statute/update")){
			c.render("/src/statuteEdit.html");
		}
	}

	@Override
	protected void validate(Controller c) {
		// TODO Auto-generated method stub
		validateRequiredString("statute.title", "titleMsg", "请输入标题");
		validateRequiredString("statute.datec", "datecMsg", "请输入政策发布日期");
		validateRequiredString("content", "contentMsg", "请输入政策内容");
	}
	

}
