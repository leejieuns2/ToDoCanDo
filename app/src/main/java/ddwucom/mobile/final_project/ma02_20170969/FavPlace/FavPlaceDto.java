package ddwucom.mobile.final_project.ma02_20170969.FavPlace;

import java.io.Serializable;

public class FavPlaceDto implements Serializable {

	private long id;
	private String todoDate; // 날짜
	private String todoTime; // 시간
	private String title; // 할 일
	private String location; // 위치
	private String category; // 할 일 카테고리
	private String memo; // 추가 기재내용
	private String link; // 참고할 블로그 게시물 주소

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTodoDate() { return todoDate; }

	public void setTodoDate(String todoDate) { this.todoDate = todoDate; }

	public String getTodoTime() {
		return todoTime;
	}

	public void setTodoTime(String todoTime) {
		this.todoTime = todoTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
}
