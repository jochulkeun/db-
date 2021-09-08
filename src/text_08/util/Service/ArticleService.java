package text_08.util.Service;

import java.sql.Connection;
import java.util.List;

import text_08.Article;
import text_08.util.Dao.ArticleDao;

public class ArticleService {
	private ArticleDao articleDao;
	public ArticleService(Connection conn) {
		articleDao = new ArticleDao(conn);
	}
	public int add(String title, String body) {
		return articleDao.add(title,body);
	}
	public boolean articleExists(int id) {
		return articleDao.articleExists(id);
	}
	public void delete(int id) {
		articleDao.delete(id);
		
	}
	public Article getArticleById(int id) {
		return articleDao.getArticleById(id);
	}
	public void update(int id, String title, String body) {
		articleDao.update(id,title,body);
		
	}
	public List<Article> getArticles() {
		return articleDao.getArticles();
	}
	

}
