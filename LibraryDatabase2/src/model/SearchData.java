package model;

public class SearchData {
	private String book_id; // ISBN
	private String title;
	private String authorFirstName;
	private String authorMiddleInitial;
	private String authorLastName;
	private String authorFullName;
	
	public String getBook_id() {
		return book_id;
	}
	public void setBook_id(String book_id) {
		this.book_id = book_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthorFirstName() {
		return authorFirstName;
	}
	public void setAuthorFirstName(String authorFirstName) {
		this.authorFirstName = authorFirstName;
	}
	public String getAuthorMiddleInitial() {
		return authorMiddleInitial;
	}
	public void setAuthorMiddleInitial(String authorMiddleInitial) {
		this.authorMiddleInitial = authorMiddleInitial;
	}
	public String getAuthorLastName() {
		return authorLastName;
	}
	public void setAuthorLastName(String authorLastName) {
		this.authorLastName = authorLastName;
	}
	public String getAuthorFullName() {
		return authorFullName;
	}
	public void setAuthorFullName(String authorFullName) {
		this.authorFullName = authorFullName;
	}
	
}
