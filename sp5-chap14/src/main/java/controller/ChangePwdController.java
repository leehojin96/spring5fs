package controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import spring.AuthInfo;
import spring.ChangePasswordService;
import spring.WrongIdPasswordException;

@Controller
@RequestMapping("/edit/changePassword")
public class ChangePwdController {

	private ChangePasswordService changePasswordService;

	public void setChangePasswordService(ChangePasswordService changePasswordService) {
		this.changePasswordService = changePasswordService;
	}

	@GetMapping
	public String form(@ModelAttribute("command")ChangePwdCommand pwdCmd,HttpSession session) {
		/**
		 * 인터셉터 구현으로 인해 각 컨트롤에 구현 안해도 됨
		AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
		if(authInfo==null)
			return "redirect:/login";
		**/
		return "edit/changePwdForm";
	}

	@PostMapping
	public String submit(@ModelAttribute("command")ChangePwdCommand pwdCmd, Errors errors, HttpSession session) {
		new ChangePwdCommandValidator().validate(pwdCmd, errors);
		if (errors.hasErrors())
			return "edit/changePwdForm";

		AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");

		try {
			changePasswordService.changePassword(authInfo.getEmail(), pwdCmd.getCurrentPassword(),
					pwdCmd.getNewPassword());
			return "edit/changedPwd";
		} catch (WrongIdPasswordException e) {
			errors.rejectValue("currentPasswrod", "notMatching");
			return "edit/changePwdForm";
		}
	}
}
