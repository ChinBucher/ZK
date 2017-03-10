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
		if(actionKey.equals("/src/up")){
			c.render("add.html");
		}else if(actionKey.equals("/src/update")){
			c.render("srcList.html");
		}
	}

	@Override
	protected void validate(Controller c) {
		// TODO Auto-generated method stub
		validateRequiredString("src.title", "titleMsg", "请输入文章名");
	}

}
