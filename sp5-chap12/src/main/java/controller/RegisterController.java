package controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import spring.DuplicateMemberException;
import spring.MemberRegisterService;
import spring.RegisterRequest;

@Controller
public class RegisterController {

	@Autowired
	MemberRegisterService memberRegisterService;

	public void setMemberRegisterService(MemberRegisterService memberRegisterService) {
		this.memberRegisterService = memberRegisterService;
	}

	@RequestMapping("/register/step1")
	public String handleStep1() {
		return "register/step1";
	}

	@PostMapping("/register/step2")
	public String handleStep2(@RequestParam(value = "agree", defaultValue = "false") Boolean agree, Model model) {
		if (!agree) {
			return "register/step1";
		}
		model.addAttribute("registerRequest", new RegisterRequest());
		return "register/step2";
	}

	@GetMapping("/register/step2")
	public String handleStep2Get() {
		return "redirect:step1";
	}

	@PostMapping("/register/step3")
	public String handleStep3(@Valid RegisterRequest regReq, Errors errors) {
		//1.글로벌 Validator 을 통해 파라미터에 @Valid애노테이션을 붙이므로 아래 행은 생략 가능
		//2.검증은 메서드가 실행되기 전에 이뤄진다.
		//3.Errors 파라미터가 없고 에러 발생시 익셉션 발생
		//new RegisterRequestValidator().validate(regReq, errors);
		if (errors.hasErrors()) 
			return "register/step2";
		
		try {
			memberRegisterService.regist(regReq);
			return "register/step3";
		} catch (DuplicateMemberException ex) {
			errors.rejectValue("email", "duplicate");
			return "register/step2";
		}
	}
	
	/** Bean Validator 사용을 위해 주석처리

	//이 애노테이션은 글로벌 Validator의 범위를 지정할 수 있다.
	//이 컨트롤러에서 모든 메서드를 실행 시킬때마다 아래 메서드를 실행한 후 실행 하게 된다.
	//binder.setValidator()로 인해 기존에 설정 되어있는 글로벌 Validator를 삭제하고 파라미터로 전달된 Validator로 글로벌 Validator 설정된다.
	//binder.addValidator()메서드는 실행시 기존에 있던 Validator를 유지한채 그 뒤에 추가하는 방식이다.
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new RegisterRequestValidator());
	}
	
	**/
	
}
