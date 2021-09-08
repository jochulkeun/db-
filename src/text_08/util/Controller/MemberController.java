package text_08.util.Controller;

import java.sql.Connection;
import java.util.Scanner;

import text_08.util.DBUtil;
import text_08.util.SecSql;
import text_08.util.Service.MemberService;

public class MemberController extends Controller {
	private MemberService memberService;

	public MemberController(Connection conn, Scanner scanner) {
		super(scanner);
		memberService = new MemberService(conn);

	}

	public void join(String cmd) {
		String loginId;
		String loginPw;
		String loginPwConfirm;
		String name;
		while (true) {
			System.out.println("== 회원가입 ==");
			System.out.printf("로그인 아이디 : ");
			loginId = scanner.nextLine();

			if (loginId.length() == 0) {
				System.out.println("로그인 아이디를 확인하세요");
				continue;
			}

			boolean isLoginIdDup = memberService.isLoginIdDup(loginId);

			if (isLoginIdDup) {
				System.out.printf("%s는 사용중인 아이디 입니다.\n", loginId);
				continue;
			}

			break;
		}

		while (true) {

			System.out.printf("로그인 비밀번호 : ");
			loginPw = scanner.nextLine().trim();

			if (loginPw.length() == 0) {
				System.out.println("로그인 비밀번호를 확인하세요");
				continue;
			}
			boolean loginPwConfirmIsSame = true;
			while (true) {

				System.out.printf("로그인 비번확인 : ");
				loginPwConfirm = scanner.nextLine();

				if (loginPwConfirm.length() == 0) {
					System.out.println("로그인 비밀번호를 확인하세요");
					continue;
				}
				if (loginPw.equals(loginPwConfirm) == false) {
					System.out.println("로그인 비밀번호를 확인하세요");
					loginPwConfirmIsSame = false;
					break;

				}
				break;
			}
			if (loginPwConfirmIsSame) {
				break;
			}

		}
		while (true) {
			System.out.print("이름 :");
			name = scanner.nextLine().trim();
			if (name.length() == 0) {
				System.out.printf("이름을 입력해 주세요 : ");
				continue;
			}
			break;
		}
		memberService.join(loginId,loginPw,name);

		System.out.printf("%s님 환영합니다.\n", name);
	}

}
