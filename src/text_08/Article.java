package text_08;

public class Article {
	int id;
	String title;
	String body;
	public Article(int id, String body, String title) {
		this.id = id;
		this.body = body;
		this.title = title;
	}
	@Override
	public String toString() {
		return "Article [id=" + id + ", title=" + title + ", body=" + body + "]";
	}

}
