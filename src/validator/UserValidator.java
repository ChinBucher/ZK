package validator;


import model.User;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

/**
 * UserValidator.
 */
public class UserValidator extends Validator {
	
	protected void validate(Controller controller) {
		validateRequiredString("user.name", "nameMsg", "请输入用户姓名!");
		validateRequiredString("user.pwd", "pwdMsg", "请输入用户密码!");
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(User.class);
		
		/*
		 * 获取actionKey是保证通用性
		 * 1.注册时：actionkey: /user/save  输入正确后返回
		 * 2.修改时：actionkey: /user/update  输入正确后返回
		 */
		
		String actionKey = getActionKey();
		if (actionKey.equals("/user/save"))
			controller.render("register.html");
		else if (actionKey.equals("/user/update"))
			controller.render("edit.html");
	}
}
