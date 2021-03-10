package by.victor.jwd.bean;

import java.time.LocalDate;

public class News {
	private int id;
	private String title;
	private String brief;
	private String content;
	private LocalDate date;
	
	public News(int id, String title, String brief) {
		super();
		this.id = id;
		this.title = title;
		this.brief = brief;
	}
	

	public News(int id, String title, String brief, String content, LocalDate date) {
		super();
		this.id = id;
		this.title = title;
		this.brief = brief;
		this.content = content;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
}
