import java.util.HashMap;

public class MovieSet{
	private String title;
	private String year;
	private String country;
	private String genre;
	private String summary;
	private Artist director;
	private String role;
	
	private HashMap<String,Artist> actors;
	
	public MovieSet(){
		actors = new HashMap<String, Artist>();
	}
	
	public String getRole(){
		return role;
	}
	public void setRole(String role){
		this.role = role;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Artist getDirector() {
		return director;
	}
	public void setDirector(Artist director) {
		this.director = director;
	}
	public HashMap<String,Artist> getActors() {
		return actors;
	}
	public void setActors(HashMap<String,Artist> actors) {
		this.actors = actors;
	}
	
}