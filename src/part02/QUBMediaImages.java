package part02;

import java.awt.Color;
import java.awt.Font;
import java.time.LocalDate;
import javax.swing.ImageIcon;
import console.Console;
import part01.ImageRecord;
import part01.ImageManager;
import part01.ImageAlbum;
import part01.ImageType;


// printMenuHeader() -- Clears the menu console and displays the main menu options.
// getMenuChoice() -- Reads and returns the user's menu choice, returns -1 if input is invalid.
// addImage(ImageManager manager) -- Prompts the user for image details and adds a new image to the manager.
// search(ImageManager manager) -- Displays the search sub-menu and handles all search options.
// searchById(ImageManager manager) -- Searches for an image by ID and displays the result.
// searchByDateRange(ImageManager manager) -- Searches for images within a date range and displays results.
// displayAll(ImageManager manager) -- Retrieves and displays all images in the system.
// printAlbum(ImageAlbum album) -- Iterates through an album and prints each record to the results console.
// printRecord(ImageRecord record) -- Prints all details of a single image record including its thumbnail.
// loadImage(String path) -- Attempts to load an image from the given path, returns null if not found.
// pauseAndReturn() -- Waits for the user to press Enter then returns to the main menu.

public class QUBMediaImages {

	// Two consoles - one for the menu on the left, one for showing results on the right
	private static Console menuConsole = new Console(true);
	private static Console resultsConsole = new Console(false);

	// Font setup for titles and general text
	private static Font titleFont = new Font("Courier", Font.BOLD, 22);
	private static Font menuFont = new Font("Courier", Font.PLAIN, 16);
	private static Font resultsFont = new Font("Courier", Font.PLAIN, 14);

	// Colour scheme for the UI   // I choose these colours from an online hexadecimal colour picker.
	private static Color background = new Color(20, 20, 40); // dark navy background
	private static Color titles = new Color(220, 80, 80);   // red for titles
	private static Color standard = Color.WHITE;            // white for standard text
	private static Color success = new Color(100, 220, 100); // green for success messages
	private static Color errors = new Color(255, 200, 50);  // yellow for errors
	private static Color prompts = new Color(100, 220, 255); // blue for prompts

	public static void main(String[] args) {

		// Position the menu console on the left
		menuConsole.setSize(500, 700);
		menuConsole.setLocation(0, 0);
		menuConsole.setVisible(true);
		menuConsole.setBgColour(background);
		menuConsole.setFont(titleFont);
		menuConsole.setColour(titles);

		// Position and style the results console on the right
		resultsConsole.setSize(700, 700);
		resultsConsole.setLocation(505, 0);
		resultsConsole.setVisible(true);
		resultsConsole.setBgColour(background);
		resultsConsole.setFont(titleFont);
		resultsConsole.setColour(titles);
		resultsConsole.println("======== RESULTS ================");
		resultsConsole.setFont(resultsFont);
		resultsConsole.setColour(standard);
		resultsConsole.println("\nAwaiting search results.");

		ImageManager manager = new ImageManager();
		manager.initialise(); // load the default sample images on startup

		printMenuHeader();

		int choice;

		do {
			choice = getMenuChoice();

			switch (choice) {

			case 1:
				addImage(manager);
				break;

			case 2:
				search(manager);
				break;

			case 3:
				displayAll(manager);
				break;

			case 4:
				menuConsole.setColour(errors);
				menuConsole.println("\nQuitting QUB Media Images. Goodbye!");
				break;

			default:
				menuConsole.setColour(errors);
				menuConsole.println("Invalid option. Please try again.");
			}

		} while (choice != 4);

		System.exit(0);
	}

	// prints the main menu header including the QUB logo 
	private static void printMenuHeader() {
		menuConsole.clear();

		ImageIcon logo = loadImage("Images/QUBLogo.png");

		if (logo != null) {
			menuConsole.println(logo);
		}

		menuConsole.setFont(titleFont);
		menuConsole.setColour(titles);
		menuConsole.println("----------------------------------------");
		menuConsole.println("       QUB MEDIA IMAGES         ");
		menuConsole.println("----------------------------------------");
		menuConsole.setFont(menuFont);
		menuConsole.setColour(standard);
		menuConsole.println("");
		menuConsole.println("  1. Add Image");
		menuConsole.println("  2. Search");
		menuConsole.println("  3. Display All");
		menuConsole.println("  4. Quit");
		menuConsole.println("");
		menuConsole.println("================================");
	}

	// reads the user's menu choice and returns -1 if they enter something invalid
	private static int getMenuChoice() {
		menuConsole.setColour(prompts);
		menuConsole.print("Enter choice: ");
		menuConsole.setColour(standard);
		String input = menuConsole.readLn().trim();
		try {
			return Integer.parseInt(input);
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	// prompts the user for image details and adds a new image to the manager
	private static void addImage(ImageManager manager) {
		menuConsole.clear();
		menuConsole.setFont(titleFont);
		menuConsole.setColour(titles);
		menuConsole.println("======== ADD IMAGE ==============");
		menuConsole.setFont(menuFont);
		menuConsole.setColour(standard);

		menuConsole.setColour(prompts);
		menuConsole.print("Title: ");
		menuConsole.setColour(standard);
		String title = menuConsole.readLn();

		menuConsole.setColour(prompts);
		menuConsole.print("Description: ");
		menuConsole.setColour(standard);
		String description = menuConsole.readLn();

		menuConsole.setColour(prompts);
		menuConsole.println("Genre (ASTRONOMY/ARCHITECTURE/SPORT/LANDSCAPE/PORTRAIT/NATURE/AERIAL/FOOD/OTHER): ");
		menuConsole.setColour(standard);
		String genreInput = menuConsole.readLn();
		ImageType genre = null;
		try {
			genre = ImageType.valueOf(genreInput.toUpperCase().trim());
		} catch (IllegalArgumentException e) {
			menuConsole.setColour(errors);
			menuConsole.println("Invalid genre. Image not added.");
			pauseAndReturn();
			return;
		}

		menuConsole.setColour(prompts);
		menuConsole.print("Date Taken (yyyy-mm-dd): ");
		menuConsole.setColour(standard);
		String dateInput = menuConsole.readLn();
		LocalDate dateTaken = null;
		try {
			dateTaken = LocalDate.parse(dateInput.trim());
		} catch (Exception e) {
			menuConsole.setColour(errors);
			menuConsole.println("Invalid date format. Image not added.");
			pauseAndReturn();
			return;
		}

		menuConsole.setColour(prompts);
		menuConsole.print("Thumbnail filename: ");
		menuConsole.setColour(standard);
		String thumbnail = menuConsole.readLn();
		if (!thumbnail.toLowerCase().endsWith(".png") && !thumbnail.toLowerCase().endsWith(".jpg")) {
			menuConsole.setColour(errors);
			menuConsole.println("Thumbnail must be a .png or .jpg file. Image not added.");
			pauseAndReturn();
			return;
		}

		manager.addImage(new ImageRecord(title, description, genre, dateTaken, thumbnail));
		menuConsole.setColour(success);
		menuConsole.println("\nImage added successfully!");
		pauseAndReturn();
	}

	// displays the search sub-menu and handles all search options
	private static void search(ImageManager manager) {
		int searchChoice;

		do {
			menuConsole.clear();
			menuConsole.setFont(titleFont);
			menuConsole.setColour(titles);
			menuConsole.println("======== SEARCH =================");
			menuConsole.setFont(menuFont);
			menuConsole.setColour(standard);
			menuConsole.println("  1. Search by Unique ID");
			menuConsole.println("  2. Search by Title");
			menuConsole.println("  3. Search by Description");
			menuConsole.println("  4. Search by Type");
			menuConsole.println("  5. Search by Date Range");
			menuConsole.println("  6. Return to Main Menu");
			menuConsole.println("================================");

			menuConsole.setColour(prompts);
			menuConsole.print("Enter choice: ");
			menuConsole.setColour(standard);
			String input = menuConsole.readLn().trim();
			searchChoice = -1;
			try {
				searchChoice = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				searchChoice = -1;
			}

			switch (searchChoice) {

			case 1:
				searchById(manager);
				break;

			case 2:
				menuConsole.setColour(prompts);
				menuConsole.print("Enter title to search: ");
				menuConsole.setColour(standard);
				String targetTitle = menuConsole.readLn();
				printAlbum(manager.searchTitle(targetTitle));
				break;

			case 3:
				menuConsole.setColour(prompts);
				menuConsole.print("Enter description to search: ");
				menuConsole.setColour(standard);
				String targetDesc = menuConsole.readLn();
				printAlbum(manager.searchDescription(targetDesc));
				break;

			case 4:
				menuConsole.setColour(prompts);
				menuConsole.println("Enter type (ASTRONOMY/ARCHITECTURE/SPORT/LANDSCAPE/PORTRAIT/NATURE/AERIAL/FOOD/OTHER): ");
				menuConsole.setColour(standard);
				String typeInput = menuConsole.readLn();
				try {
					ImageType targetType = ImageType.valueOf(typeInput.toUpperCase().trim());
					printAlbum(manager.searchGenre(targetType));
				} catch (IllegalArgumentException e) {
					menuConsole.setColour(errors);
					menuConsole.println("Invalid image type entered.");
				}
				break;

			case 5:
				searchByDateRange(manager);
				break;

			case 6:
				printMenuHeader();
				break;

			default:
				menuConsole.setColour(errors);
				menuConsole.println("Invalid option.");
			}

		} while (searchChoice != 6);
	}

	// searches for an image by ID and displays the result
	private static void searchById(ImageManager manager) {
		ImageAlbum all = manager.getAllImages();
		int count = 0;
		ImageRecord current = all.getFirst();
		while (current != null) {
			count++;
			current = all.getNext(); // count contains the amount of images currently stored in the ImageManager
		}

		menuConsole.setColour(prompts);
		menuConsole.print("Enter ID (1-" + count + "): ");
		menuConsole.setColour(standard);
		String input = menuConsole.readLn().trim();
		try {
			int targetId = Integer.parseInt(input);
			ImageRecord result = manager.searchId(targetId);
			resultsConsole.clear();
			resultsConsole.setFont(titleFont);
			resultsConsole.setColour(titles);
			resultsConsole.println("======== SEARCH RESULT ==========");
			resultsConsole.setFont(resultsFont);
			if (result != null) {
				printRecord(result);
			} else {
				resultsConsole.setColour(errors);
				resultsConsole.println("No image found with ID: " + targetId);
			}
		} catch (NumberFormatException e) {
			resultsConsole.setColour(errors);
			resultsConsole.println("Invalid ID entered. Please enter a number.");
		}
	}

	// searches for images within a date range and displays results
	private static void searchByDateRange(ImageManager manager) {
		menuConsole.setColour(prompts);
		menuConsole.print("Enter start date (yyyy-mm-dd): ");
		menuConsole.setColour(standard);
		String startInput = menuConsole.readLn().trim();

		menuConsole.setColour(prompts);
		menuConsole.print("Enter end date (yyyy-mm-dd): ");
		menuConsole.setColour(standard);
		String endInput = menuConsole.readLn().trim();

		try {
			LocalDate startDate = LocalDate.parse(startInput);
			LocalDate endDate = LocalDate.parse(endInput);
			printAlbum(manager.searchDates(startDate, endDate));
		} catch (Exception e) {
			resultsConsole.setColour(errors);
			resultsConsole.println("Invalid date format. Please use yyyy-mm-dd.");
		}
	}

	// retrieves and displays all images in the system
	private static void displayAll(ImageManager manager) {
		printAlbum(manager.getAllImages());
	}

	// iterates through an album and prints each record to the results console
	private static void printAlbum(ImageAlbum album) {
		resultsConsole.clear();
		resultsConsole.setFont(titleFont);
		resultsConsole.setColour(titles);
		resultsConsole.println("======== RESULTS ================");
		resultsConsole.setFont(resultsFont);

		ImageRecord current = album.getFirst();
		if (current == null) {
			resultsConsole.setColour(errors);
			resultsConsole.println("No matching images found.");
		} else {
			while (current != null) {
				printRecord(current);
				current = album.getNext();
			}
		}
	}

	// prints all the details of a single image record, and will attempt to show the image too
	private static void printRecord(ImageRecord record) {
		resultsConsole.setColour(titles);
		resultsConsole.println("--------------------------------");
		resultsConsole.setColour(prompts);
		resultsConsole.print("ID: ");
		resultsConsole.setColour(standard);
		resultsConsole.println(record.getId());
		resultsConsole.setColour(prompts);
		resultsConsole.print("Title: ");
		resultsConsole.setColour(standard);
		resultsConsole.println(record.getTitle());
		resultsConsole.setColour(prompts);
		resultsConsole.print("Description: ");
		resultsConsole.setColour(standard);
		resultsConsole.println(record.getDescription());
		resultsConsole.setColour(prompts);
		resultsConsole.print("Genre: ");
		resultsConsole.setColour(standard);
		resultsConsole.println(record.getGenre());
		resultsConsole.setColour(prompts);
		resultsConsole.print("Date Taken: ");
		resultsConsole.setColour(standard);
		resultsConsole.println(record.getDateTaken());

		// load and display the thumbnail ----> if not there, shows filename instead
		ImageIcon thumbnail = loadImage("Images/" + record.getThumbnail());
		if (thumbnail != null) {
			resultsConsole.println(thumbnail);
		} else {
			resultsConsole.setColour(prompts);
			resultsConsole.print("Thumbnail: ");
			resultsConsole.setColour(standard);
			resultsConsole.println(record.getThumbnail());
		}
	}

	// attempts to load an image from the given path, returns null if not found
	private static ImageIcon loadImage(String path) {
		try {
			String userDir = System.getProperty("user.dir");
			ImageIcon icon = new ImageIcon(userDir + "/" + path);
			if (icon.getIconWidth() > 0) {
				return icon;
			}
		} catch (Exception e) {
			// image wasn't found, return null and handled where it is called.
		}
		return null;
	}

	// waits for the user to press enter before going back to the main menu
	private static void pauseAndReturn() {
		menuConsole.setColour(standard);
		menuConsole.print("\nPress Enter to return to menu...");
		menuConsole.readLn();
		printMenuHeader();
	}

}