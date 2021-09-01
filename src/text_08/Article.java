package text_08;

import java.time.LocalDateTime;
import java.util.Map;

public class Article extends Object{
	int id;
	String title;
	String body;
	LocalDateTime regDate;
	LocalDateTime updateDate;
	
	public Article(int id, String title, String body) {
		this.id = id;
		this.title = title;
		this.body = body;
	}
	public Article(int id,String title,String body, LocalDateTime regDate, LocalDateTime updateDate) {
		this.id = id;
		this.title = title;
		this.body = body;
		this.regDate = regDate;
		this.updateDate = updateDate;
	}
	public Article(Map<String, Object> articleMap) {
		this.id = (int) articleMap.get("id");
		this.title = (String) articleMap.get("title");
		this.body = (String) articleMap.get("body");
		this.regDate = (LocalDateTime) articleMap.get("regDate");
		this.updateDate = (LocalDateTime) articleMap.get("updateDate");
	
	}
	@Override
	public String toString() {
		return "Article [id=" + id + ", title=" + title + ", body=" + body + ", regDate=" + regDate + ", updateDate="
				+ updateDate + "]";
	}
	
	

}
