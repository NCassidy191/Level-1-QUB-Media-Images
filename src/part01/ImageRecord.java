package part01;

import java.time.LocalDate;

public class ImageRecord {
// DECLARATION
	private static int nextId = 1;
	private int id;
	private String title;
	private String description;
	private ImageType genre;
	private LocalDate dateTaken;
	private String thumbnail;

//CONTRUCTOR
	public ImageRecord(String title, String description, ImageType genre, LocalDate dateTaken, String thumbnail) {
		this.id = nextId;
		nextId++;
		this.title = title;
		this.description = description;
		this.genre = genre;
		this.dateTaken = dateTaken;
		this.thumbnail = thumbnail;
	}

// GETTERS
	public int getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public String getDescription() {
		return description;
	}
	public ImageType getGenre() {
		return genre;
	}
	public LocalDate getDateTaken() {
		return dateTaken;
	}
	public String getThumbnail() {
		return thumbnail;
	}

// SETTERS
	public void setTitle(String title) {
		this.title = title;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setGenre(ImageType genre) {
		this.genre = genre;
	}
	public void setDateTaken(LocalDate dateTaken) {
		this.dateTaken = dateTaken;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	
	public String toString() {
		return "ID: " + id + " | Title: " + title + " | Description: " + description + " | Genre: " + genre
				+ " | Date Taken: " + dateTaken + " | Thumbnail: " + thumbnail;
	}

}

