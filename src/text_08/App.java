package text_08;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;


import text_08.util.Controller.ArticleController;
import text_08.util.Controller.MemberController;

public class App {
	public void run() {
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.printf("명령어) ");
			String cmd = scanner.nextLine();
			cmd = cmd.trim();

			// DB 연결 시작
			Connection conn = null;
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.err.println("예외 : MySQL 드라이버 클래스가 없습니다.");
				System.out.println("프로그램을 종료합니다.");
				break;
			}

			String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
			try {
				conn = DriverManager.getConnection(url, "root", "");

				int actionResult = doAction(conn, scanner, cmd);

				if (actionResult == -1) {
					break;
				}
			} catch (SQLException e) {
				System.err.println("예외 : DB에 연결할 수 없습니다.");
				System.out.println("프로그램을 종료합니다.");
				break;
			} finally {
				try {
					if (conn != null && !conn.isClosed()) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			// DB 연결 끝
		}

		scanner.close();
	}

	private int doAction(Connection conn, Scanner scanner, String cmd) {

		MemberController memberController = new MemberController();
		memberController.setConn(conn);
		memberController.setScanner(scanner);
		ArticleController articleController = new ArticleController();
		articleController.setConn(conn);
		articleController.setConn(scanner);

		if (cmd.equals("member join")) {
			memberController.join(cmd);
		}
		if (cmd.equals("article write")) {
			articleController.doWrite(cmd);
		} else if (cmd.startsWith("article detail ")) {
			articleController.detail(cmd);
		} else if (cmd.startsWith("article delete ")) {
			articleController.delete(cmd);
		} else if (cmd.startsWith("article modify ")) {
			articleController.domodify(cmd);
		} else if (cmd.equals("article list")) {
			articleController.dolist(cmd);
		} else if (cmd.equals("system exit")) {
			System.out.println("== 프로그램 종료 ==");
			return -1;
		}

		return 0;
	}

}
