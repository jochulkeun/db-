package text_08.util.Controller;

import java.sql.Connection;
import java.util.Scanner;

import text_08.util.DBUtil;
import text_08.util.SecSql;

public class MemberController {
	private Connection conn;
	private Scanner scanner;
	
	public void setConn(Connection conn) {
		this.conn = conn;
		
	}

	public void setScanner(Scanner scanner) {
		
		this.scanner = scanner;
	}
	public void join( String cmd) {
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
			SecSql sql = new SecSql();

			sql.append("SELECT COUNT(*) > 0");
			sql.append("FROM member");
			sql.append("WHERE loginId = ?", loginId);
			
			
			boolean isloginIdDup = DBUtil.selectRowBooleanValue(conn, sql);
			
			if (isloginIdDup) {
				System.out.printf("%s는 사용중인 아이디 입니다.\n",loginId);
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
			if(loginPwConfirmIsSame) {
				break;
			}
		
		}
		while(true) {
			System.out.print("이름 :");
			name = scanner.nextLine().trim();
			if(name.length() == 0) {
				System.out.printf("이름을 입력해 주세요 : ");
				continue;
			}
		break;
		}
		SecSql sql = new SecSql();

		sql.append("INSERT INTO member");
		sql.append(" SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", loginId = ?", loginId);
		sql.append(", loginPw = ?", loginPw);
		sql.append(", `name` = ?", name);
		

		int id = DBUtil.insert(conn, sql);

		System.out.printf("%s님 환영합니다.\n", name);
	}

	

}
