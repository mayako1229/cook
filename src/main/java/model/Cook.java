package model;

import java.time.LocalDate;

public class Cook {
	private int id;	//id
	private LocalDate cookDate;	//日付	
	private String menuTitle;	//タイトル	
	private String detail;	//詳細

	public Cook() {
	}

	public Cook(int id, LocalDate cookDate, String menuTitle, String detail) {
		this.id = id;
		this.cookDate = cookDate;
		this.menuTitle = menuTitle;
		this.detail = detail;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getCookDate() {
		return cookDate;
	}

	public void setCookDate(LocalDate cookDate) {
		this.cookDate = cookDate;
	}

	public String getMenuTitle() {
		return menuTitle;
	}

	public void setMenuTitle(String menuTitle) {
		this.menuTitle = menuTitle;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	@Override
	public String toString() {
		return "Cook [id=" + id + ", cookDate=" + cookDate + ", menuTitle=" + menuTitle + ", detail=" + detail + "]";
	}

}
