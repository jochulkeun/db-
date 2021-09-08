package text_08.util.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class Member {
	public int id;
	public String loginId;
	public String loginPw;
	public LocalDateTime regDate;
	public LocalDateTime updateDate;
	public String name;
	
	
	public Member(Map<String, Object> memberMap) {
		this.id = (int) memberMap.get("id");
		this.loginId = (String) memberMap.get("loginId");
		this.loginPw = (String) memberMap.get("loginPw");
		this.regDate = (LocalDateTime) memberMap.get("regDate");
		this.updateDate = (LocalDateTime) memberMap.get("updateDate");
		this.name= (String) memberMap.get("name");
	}
	@Override
	public String toString() {
		return "Member [id=" + id + ", loginId=" + loginId + ", loginPw=" + loginPw + ", regDate=" + regDate
				+ ", updateDate=" + updateDate + ", name=" + name + "]";
	}
	
	

}
