package text_08.util.Controller;

import java.sql.Connection;

import java.util.List;

import java.util.Scanner;

import text_08.util.Service.ArticleService;
import text_08.util.dto.Article;

public class ArticleController extends Controller {

	private ArticleService articleService;

	public ArticleController(Connection conn, Scanner scanner) {
		super(scanner);
		articleService = new ArticleService(conn);
	}

	public void doWrite(String cmd) {
		String title;
		String body;

		System.out.println("== 게시글 생성 ==");
		System.out.printf("제목 : ");
		title = scanner.nextLine();
		System.out.printf("내용 : ");
		body = scanner.nextLine();

		int id = articleService.add(title, body);

		System.out.printf("%d번 게시물이 생성되었습니다.\n", id);

	}

	public void detail(String cmd) {
		int id = Integer.parseInt(cmd.split(" ")[2]);

		System.out.printf("== %d번 게시글 상세보기 ==\n", id);
		
		Article article = articleService.getArticleById(id);

		if (article == null) {
			System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
			return;
		}

		System.out.printf("번호 : %d\n", article.id);
		System.out.printf("작성날짜 : %s\n", article.regDate);
		System.out.printf("수정날짜 : %s\n", article.updateDate);
		System.out.printf("제목 : %s\n", article.title);
		System.out.printf("내용 : %s\n", article.body);

	}

	public void delete(String cmd) {
		int id = Integer.parseInt(cmd.split(" ")[2]);

		boolean articleExists = articleService.articleExists(id);
		if (articleExists == false) {
			System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
			return;
		}

		articleService.delete(id);

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

		articleService.update(id, title, body);
		System.out.printf("%d번 게시글이 수정되었습니다.\n", id);

	}

	public void dolist(String cmd) {
		System.out.println("== 게시물 리스트 ==");

		List<Article> articles = articleService.getArticles();

		if (articles.size() == 0) {
			System.out.println("게시물이 존재하지 않습니다.");
			return;
		}

		System.out.println("번호 / 제목");

		for (Article article : articles) {
			System.out.printf("%d / %s\n", article.id, article.title);
		}

	}

}
