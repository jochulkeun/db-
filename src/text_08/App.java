package text_08;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
		int lastArticleId = 0;

		if (cmd.equals("article write")) {
			int id = lastArticleId + 1;
			String title;
			String body;

			System.out.println("== 게시글 생성 ==");
			System.out.printf("제목 : ");
			title = scanner.nextLine();
			System.out.printf("내용 : ");
			body = scanner.nextLine();

			PreparedStatement pstat = null;
 
			try {
				String sql = "INSERT INTO article";
				sql += " SET regDate = NOW()";
				sql += ", updateDate = NOW()";
				sql += ", title = \"" + title + "\"";
				sql += ", `body` = \"" + body + "\";";

				pstat = conn.prepareStatement(sql);
				pstat.executeUpdate();

			} catch (SQLException e) {
				System.out.println("에러: " + e);
			} finally {
				try {
					if (pstat != null && !pstat.isClosed()) {
						pstat.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}

			lastArticleId++;
		} else if (cmd.startsWith("article modify ")) {
			int id = Integer.parseInt(cmd.split(" ")[2]);
			String title;
			String body;

			System.out.printf("== %d번 게시글 수정 ==\n", id);
			System.out.printf("새 제목 : ");
			title = scanner.nextLine();
			System.out.printf("새 내용 : ");
			body = scanner.nextLine();

			PreparedStatement pstat = null;

			try {
				String sql = "UPDATE article";
				sql += " SET updateDate = NOW()";
				sql += ", title = \"" + title + "\"";
				sql += ", `body` = \"" + body + "\"";
				sql += " WHERE id = " + id;

				pstat = conn.prepareStatement(sql);
				pstat.executeUpdate();

			} catch (SQLException e) {
				System.out.println("에러: " + e);
			} finally {
				try {
					if (pstat != null && !pstat.isClosed()) {
						pstat.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}

			System.out.printf("%d번 게시글이 수정되었습니다.\n", id);
		} else if (cmd.equals("article list")) {
			System.out.println("== 게시물 리스트 ==");

			// JDBC select 를 통해서 articles 의 내용을 채운다.
			PreparedStatement pstat = null;
			ResultSet rs = null;

			List<Article> articles = new ArrayList<>();

			try {
				String sql = "SELECT *";
				sql += " FROM article";
				sql += " ORDER BY id DESC;";

				pstat = conn.prepareStatement(sql);
				rs = pstat.executeQuery(sql);

				while (rs.next()) {
					int id = rs.getInt("id");
					String title = rs.getString("title");
					String body = rs.getString("body");
					String regDate = rs.getString("regDate");
					String updateDate = rs.getString("updateDate");

					Article article = new Article(id, title, body, regDate, updateDate);
					articles.add(article);
				}

			} catch (SQLException e) {
				System.out.println("에러: " + e);
			} finally {
				try {
					if (rs != null && !rs.isClosed()) {
						rs.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

				try {
					if (pstat != null && !pstat.isClosed()) {
						pstat.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (articles.size() == 0) {
				System.out.println("게시물이 존재하지 않습니다.");
				return 0;
			}

			System.out.println("번호 / 제목");

			for (Article article : articles) {
				System.out.printf("%d / %s\n", article.id, article.title);
			}
		} else if (cmd.equals("system exit")) {
			System.out.println("== 프로그램 종료 ==");
			return -1;
		}

		return 0;
	}

}
