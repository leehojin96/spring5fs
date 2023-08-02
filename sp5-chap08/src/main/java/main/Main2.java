package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.DuplicateFormatFlagsException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import config.AppCtx;
import spring.MemberInfoPrinter;
import spring.MemberListPrinter;
import spring.MemberNotFoundException;
import spring.MemberService;
import spring.RegisterRequest;
import spring.WrongIdPasswordException;
import spring.WrongIdPwdNameException;

public class Main2 {

	private static AnnotationConfigApplicationContext ctx = null;

	public static void main(String[] args) throws IOException {

		ctx = new AnnotationConfigApplicationContext(AppCtx.class);

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while (true) {

			System.out.println("명령어를 입력하세요:");
			String command = reader.readLine();
			if (command.equalsIgnoreCase("exit")) {
				System.out.println("종료합니다.");
				break;
			} else if (command.startsWith("new ")) {
				processNewCommand(command.split(" "));
			} else if (command.startsWith("change ")) {
				processChangeCommand(command.split(" "));
			} else if (command.equals("list")) {
				processListCommand();
			} else if (command.startsWith("info")) {
				processInfoCommand(command.split(" "));
			} else if (command.startsWith("remove ")) {
				processRemoveCommand(command.split(" "));
			} else {
				printHelp();
			}
		}
		ctx.close();
	}

	private static void processRemoveCommand(String[] arg) {
		if (arg.length != 4) {
			printHelp();
			return;
		} 
		MemberService memberService = ctx.getBean("memberService", MemberService.class);
		try {
			memberService.removeMember(Long.valueOf(arg[1]) , arg[2], arg[3]);
			System.out.println("회원 정보가 삭제 되었습니다.\n");
		} catch (WrongIdPwdNameException e) {
			System.out.println("정보가 일치하지 않습니다.\n");
		} catch (NumberFormatException e) {
			System.out.println("정보가 일치하지 않습니다.\n");
		}
	}

	private static void processChangeCommand(String[] arg) {
		if (arg.length != 4) {
			printHelp();
			return;
		}

		MemberService memberService = ctx.getBean("memberService", MemberService.class);

		try {
			memberService.changePassword(arg[1], arg[2], arg[3]);
			System.out.println("암호를 변경했습니다.\n");
		} catch (WrongIdPasswordException e) {
			System.out.println("암호가 일치하지 않습니다.");
		} catch (MemberNotFoundException e) {
			System.out.println("존재하지 않는 이메일입니다.\n");
		}
	}

	private static void processInfoCommand(String[] arg) {
		if (arg.length != 2) {
			printHelp();
		}

		MemberInfoPrinter infoPrinter = ctx.getBean("infoPrinter", MemberInfoPrinter.class);
		infoPrinter.printMemberInfo(arg[1]);
	}

	private static void processListCommand() {
		MemberListPrinter listPrinter = ctx.getBean("listPrinter", MemberListPrinter.class);
		listPrinter.printAll();
	}

	private static void processNewCommand(String[] arg) {
		if (arg.length != 5) {
			printHelp();
			return;
		}
		MemberService memberService = ctx.getBean("memberService", MemberService.class);
		RegisterRequest req = new RegisterRequest();
		req.setEmail(arg[1]);
		req.setName(arg[2]);
		req.setPassword(arg[3]);
		req.setConfirmPassword(arg[4]);

		if (!req.isPasswordEqualToConfirmPassword()) {
			System.out.println("암호와 확인이 일치하지 않습니다.\n");
			return;
		}
		try {
			memberService.regist(req);
			System.out.println("등록했습니다.\n");
		} catch (DuplicateFormatFlagsException e) {
			System.out.println("이미 존재하는 이메일입니다.\n");
		}
	}

	private static void printHelp() {
		System.out.println();
		System.out.println("잘못된 명령입니다. 아래 명령어 사용법을 확인하세요.");
		System.out.println("명령어 사용법:");
		System.out.println("new 이메일 이름 암호 암호확인");
		System.out.println("change 이메일 현재비번 변경비번");
		System.out.println("info 이메일");
		System.out.println("remove 아이디 이메일 비밀번호");

		System.out.println();
	}

}
