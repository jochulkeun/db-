package text_08;

public class Article {
	int id;
	String title;
	String body;
	String regDate;
	String updateDate;
	public Article(int id, String title, String body) {
		this.id = id;
		this.title = title;
		this.body = body;
	}
	public Article(int id,String title,String body, String regDate, String updateDate) {
		this.id = id;
		this.title = title;
		this.body = body;
		this.regDate = regDate;
		this.updateDate = updateDate;
	}
	@Override
	public String toString() {
		return "Article [id=" + id + ", title=" + title + ", body=" + body + ", regDate=" + regDate + ", updateDate="
				+ updateDate + "]";
	}
	
	

}
