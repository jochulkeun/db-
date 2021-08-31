package text_08;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {

		

		Scanner sc = new Scanner(System.in);

		int lastArticleId = 0;

		System.out.println("==프로그램 시작==");
		while (true) {
			System.out.printf("명령어)");

			String command = sc.nextLine().trim();

			if (command.equals("system exit")) {
				System.out.println("==프로그램 종료==");
				break;
			}
			if (command.equals("article write")) {

				int id = lastArticleId + 1;

				System.out.printf("제목 :");
				String title = sc.nextLine();
				System.out.printf("내용 :");
				String body = sc.nextLine();

				System.out.printf("%d 번글이 생성되었습니다.\n", id);
				Article article = new Article(id, body, title);
				lastArticleId++;
				System.out.println(article);
				
				Connection conn = null;
				PreparedStatement pstat = null;

				try {
					Class.forName("com.mysql.jdbc.Driver");
					String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

					conn = DriverManager.getConnection(url, "root", "");
					System.out.println("연결 성공!");

					String sql = "INSERT INTO article";
					sql += " SET regDate = NOW()";
					sql += ", updateDate = NOW()";
					sql += ", title = \""+ title +"\"";
					sql += ", `body` = \""+ body +"\";";

//					System.out.println(sql);

					pstat = conn.prepareStatement(sql);
					int affectedRows = pstat.executeUpdate();

					System.out.println("affectedRows : " + affectedRows);

				} catch (ClassNotFoundException e) {
					System.out.println("드라이버 로딩 실패");
				} catch (SQLException e) {
					System.out.println("에러: " + e);
				} finally {
					try {
						if (conn != null && !conn.isClosed()) {
							conn.close();
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
			}
			if (command.startsWith("article modify ")) {

				int id = Integer.parseInt(command.split(" ")[2]);
				
				System.out.printf("==%d번 게시글 수정==\n",id);
				System.out.printf(" 새 제목 :");
				String title = sc.nextLine();
				System.out.printf("내용 :");
				String body = sc.nextLine();

				System.out.printf("%d 번글이 수정되었습니다.\n", id);
				Article article = new Article(id, body, title);
				lastArticleId++;
				System.out.println(article);
				
				Connection conn = null;
				PreparedStatement pstat = null;

				try {
					Class.forName("com.mysql.jdbc.Driver");
					String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

					conn = DriverManager.getConnection(url, "root", "");
					System.out.println("연결 성공!");

					String sql = "UPDATE article";
					sql += " SET updateDate = NOW()";
					sql += ", title = \""+ title +"\"";
					sql += ", `body` = \""+ body +"\"";
					sql += "WHERE id = " +id;
//					System.out.println(sql);

					pstat = conn.prepareStatement(sql);
					int affectedRows = pstat.executeUpdate();

					System.out.println("affectedRows : " + affectedRows);

				} catch (ClassNotFoundException e) {
					System.out.println("드라이버 로딩 실패");
				} catch (SQLException e) {
					System.out.println("에러: " + e);
				} finally {
					try {
						if (conn != null && !conn.isClosed()) {
							conn.close();
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
			}
			if (command.equals("article list")) {
				
				Connection conn = null;
				PreparedStatement pstat = null;
				ResultSet rs = null;
				
				List<Article> articles = new ArrayList<>();
				
				try {
					Class.forName("com.mysql.jdbc.Driver");
					String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

					conn = DriverManager.getConnection(url, "root", "");
					System.out.println("연결 성공!");

					String sql = "SELECT *";
					sql += " FROM article ";
					sql += " ORDER BY id DESC;";
					
					
					
					

//					System.out.println(sql);

					pstat = conn.prepareStatement(sql);
					rs = pstat.executeQuery(sql);
					
					while (rs.next()) {
						int id = rs.getInt("id");
						String title = rs.getString("title");
						String body = rs.getString("body");
						String regDate = rs.getString("regDate");
						String updateDate = rs.getString("updateDate");
						
						Article article = new Article( id,title,body,regDate,updateDate);
						articles.add(article);
					}
					

					

				} catch (ClassNotFoundException e) {
					System.out.println("드라이버 로딩 실패");
				} catch (SQLException e) {
					System.out.println("에러: " + e);
				} finally {
					try {
						if (rs != null && ! rs.isClosed()) {
							rs.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					try {
						if (conn != null && !conn.isClosed()) {
							conn.close();
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
					System.out.println("게시글이 없습니다.\n");
					continue;
				}
				System.out.println(" 번호  |  제목");
				
				for(Article article : articles) {
					System.out.printf("  %d   |  %s\n",article.id,article.title);
				}
			}

		}

		sc.close();
	}
}
