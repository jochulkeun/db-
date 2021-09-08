package text_08.util.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class Article extends Object{
	public int id;
	public String title;
	public String body;
	public LocalDateTime regDate;
	public LocalDateTime updateDate;
	
	
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
