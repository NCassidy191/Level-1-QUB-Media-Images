package part01;

import java.util.ArrayList;
import java.time.LocalDate;

//ImageManager contains all the methods used to search for a specific field e.g date, title, Id.
//All searches use a linear search, bar ID, which uses a binary search due to its sorted nature.

//addImage() -- used in main to create an image using user inputs.

//getAllimages() -- creates an album of all images stored in the program.

//initialise() -- Contains the example data thats loaded up in the main program.


public class ImageManager {

	private ArrayList<ImageRecord> images;

	public ImageManager() { //Constructor
		images = new ArrayList<ImageRecord>(); 
	}

	public void addImage(ImageRecord image) {
		images.add(image);
	}

	
	//Search algorithms
	
	public ImageRecord searchId(int id) {      //binary search as Id's will always be sorted.(auto-incrementing)
		int low = 0;
		int high = images.size() - 1;

		while (low <= high) {
			int mid = (low + high) / 2; //Find middle
			int midId = images.get(mid).getId();

			if (midId == id) {
				return images.get(mid); // found
			} else if (midId < id) {
				low = mid + 1; // target is in the upper half
			} else {
				high = mid - 1; // target is in the lower half
			}
		}
		return null; // not found

	}

	public ImageAlbum searchTitle(String str) {  //Search used for titles
		ImageAlbum results = new ImageAlbum();
		for (int i = 0; i < images.size(); i++) {
			if (images.get(i).getTitle().toLowerCase().contains(str.toLowerCase())) {
				results.addImage(images.get(i));
			}
		}

		return results;
	}

	public ImageAlbum searchDescription(String str) {  //Search used for descriptions
		ImageAlbum results = new ImageAlbum();
		for (int i = 0; i < images.size(); i++) {
			if (images.get(i).getDescription().toLowerCase().contains(str.toLowerCase())) {
				results.addImage(images.get(i));
			}
		}
		return results;
	}

	public ImageAlbum searchGenre(ImageType type) { //Search used for descriptions
		ImageAlbum results = new ImageAlbum();
		for (int i = 0; i < images.size(); i++) {
			if (images.get(i).getGenre() == type) {
				results.addImage(images.get(i));
			}
		}
		return results;
	}

	public ImageAlbum searchDates(LocalDate start, LocalDate end) { //Search for Dates
		ImageAlbum results = new ImageAlbum();  
		for (int i = 0; i < images.size(); i++) {
			LocalDate date = images.get(i).getDateTaken();
			if ((date.isEqual(start) || date.isAfter(start)) && (date.isEqual(end) || date.isBefore(end))) { //Start and end dates must not overlap
				results.addImage(images.get(i));
			}
		}

		return results;
	}

	public ImageAlbum getAllImages() { //Returns an ImageAlbum with all images in the system.
		ImageAlbum results = new ImageAlbum();
		for (int i = 0; i < images.size(); i++) {
			results.addImage(images.get(i));
		}

		return results;
	}

	public void initialise() {
		addImage(new ImageRecord("Andromeda Galaxy", "Image of the Andromeda galaxy.", ImageType.ASTRONOMY,
				LocalDate.of(2023, 1, 1), "Andromeda.png"));

		addImage(new ImageRecord("Lanyon QUB", "An image of the QUB Lanyon building.", ImageType.ARCHITECTURE,
				LocalDate.of(2023, 2, 1), "LanyonQUB.png"));

		addImage(new ImageRecord("Kermit Plays Golf", "An image of Kermit the frog playing golf", ImageType.SPORT,
				LocalDate.of(2023, 3, 1), "KermitGolf.png"));

		addImage(new ImageRecord("Mourne Mountains", "A panoramic view of the Mourne mountains.", ImageType.LANDSCAPE,
				LocalDate.of(2023, 4, 1), "Mournes.png"));

		addImage(new ImageRecord("Homer Simpson", "Homer Simpson - A portrait of the man.", ImageType.PORTRAIT,
				LocalDate.of(2023, 3, 1), "Homer.png"));

		addImage(new ImageRecord("Red Kite", "A Red Kite bird of prey in flight.", ImageType.NATURE,
				LocalDate.of(2023, 4, 1), "RedKite.png"));

		addImage(new ImageRecord("Central Park", "An overhead view of Central Park New York USA.", ImageType.AERIAL,
				LocalDate.of(2023, 5, 1), "CentralPark.png"));

		addImage(new ImageRecord("Apples", "A bunch of apples.", ImageType.FOOD, LocalDate.of(2023, 6, 1),
				"Apples.png"));

		addImage(new ImageRecord("Programming Meme", "A Chat GPT programming meme.", ImageType.OTHER,
				LocalDate.of(2023, 7, 1), "ChatGPT.png"));

	}
}