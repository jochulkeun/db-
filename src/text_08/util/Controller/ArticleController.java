package text_08.util.Controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import text_08.Article;
import text_08.util.DBUtil;
import text_08.util.SecSql;

public class ArticleController {
	private Connection conn;
	private Scanner scanner;
	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public void setConn(Scanner scanner) {
		this.scanner = scanner;
	}
	public void doWrite( String cmd) {
		String title;
		String body;

		System.out.println("== 게시글 생성 ==");
		System.out.printf("제목 : ");
		title = scanner.nextLine();
		System.out.printf("내용 : ");
		body = scanner.nextLine();

		SecSql sql = new SecSql();

		sql.append("INSERT INTO article");
		sql.append(" SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", title = ?", title);
		sql.append(", `body` = ?", body);

		int id = DBUtil.insert(conn, sql);

		System.out.printf("%d번 게시물이 생성되었습니다.\n", id);
		
	}

	public void detail(String cmd) {
		int id = Integer.parseInt(cmd.split(" ")[2]);

		System.out.printf("== %d번 게시글 상세보기 ==\n", id);

		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("WHERE id = ?", id);
		Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);

		if (articleMap.isEmpty()) {
			System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
			return ;
		}

		Article article = new Article(articleMap);

		System.out.printf("번호 : %d\n", article.id);
		System.out.printf("작성날짜 : %s\n", article.regDate);
		System.out.printf("수정날짜 : %s\n", article.updateDate);
		System.out.printf("제목 : %s\n", article.title);
		System.out.printf("내용 : %s\n", article.body);

	
		
	}

	public void delete(String cmd) {
		int id = Integer.parseInt(cmd.split(" ")[2]);

		SecSql sql = new SecSql();
		sql.append("SELECT COUNT(*) AS cnt");
		sql.append("FROM article");
		sql.append("WHERE id =?", id);

		int articleExists = DBUtil.selectRowIntValue(conn, sql);
		if (articleExists == 0) {
			System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
			return ;
		}

		System.out.printf("== %d번 게시글 삭제 ==\n", id);
		sql = new SecSql();
		sql.append("DELETE FROM article");
		sql.append("WHERE id =?", id);

		DBUtil.delete(conn, sql);
		System.out.printf("%d번 게시글이 삭제되었습니다.\n", id);

		
	}

	public void domodify(String cmd) {
		int id = Integer.parseInt(cmd.split(" ")[2]);
		String title;
		String body;

		System.out.printf("== %d번 게시글 수정 ==\n", id);
		System.out.printf("새 제목 : ");
		title = scanner.nextLine();
		System.out.printf("새 내용 : ");
		body = scanner.nextLine();

		SecSql sql = new SecSql();
		sql.append("UPDATE article");
		sql.append("SET updateDate = NOW()");
		sql.append(",title = ?", title);
		sql.append(",`body`= ?", body);
		sql.append("WHERE id =?", id);

		DBUtil.update(conn, sql);
		System.out.printf("%d번 게시글이 수정되었습니다.\n", id);

	
		
	}

	public void dolist(String cmd) {
		System.out.println("== 게시물 리스트 ==");

		List<Article> articles = new ArrayList<>();

		SecSql sql = new SecSql();

		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("ORDER BY id DESC");

		List<Map<String, Object>> articlesListMap = DBUtil.selectRows(conn, sql);

		for (Map<String, Object> articleMap : articlesListMap) {
			articles.add(new Article(articleMap));
		}

		if (articles.size() == 0) {
			System.out.println("게시물이 존재하지 않습니다.");
			return ;
		}

		System.out.println("번호 / 제목");

		for (Article article : articles) {
			System.out.printf("%d / %s\n", article.id, article.title);
		}
		
	}

	

	

}
