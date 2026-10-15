package part01;

import java.util.Scanner;

import java.time.LocalDate;

public class QUBImages {

	private static void printAlbum(ImageAlbum album) { // Method that should print all image records in an album.
		ImageRecord current = album.getFirst();
		if (current == null) {
			System.out.println("No matching images found.");
		} else {
			while (current != null) {
				System.out.println(current);
				current = album.getNext();
			}
		}
	}

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);

		ImageManager manager = new ImageManager();
		manager.initialise();// Adds sample data

		String mainTitle = "QUB Images"; // Startup menu options
		String mainOptions[] = { "Add image", "Search", "Display all", "Quit" };

		String searchTitle = "QUB Images - Search for an image"; // Search sub-menu options
		String searchOptions[] = { "unique id", "title", "description", "type", "date range", "Return" };

		Menu qubMenu = new Menu(mainTitle, mainOptions); // Menu1 for startup
		Menu searchMenu = new Menu(searchTitle, searchOptions); // menu 2 for searching

		int choice;
		int searchChoice;

		do {

			choice = qubMenu.getUserChoice();

			switch (choice) {

			case 1: // Adds an image
				System.out.println("Selected Option 1 : Add an image");

				// ID not required as auto-incrementing.

				System.out.println("Enter Title:");
				String title = input.nextLine();

				System.out.println("Enter Description:");
				String description = input.nextLine();

				System.out.println("Enter Genre (e.g. ASTRONOMY, SPORT, NATURE):");
				String genreInput = input.nextLine();
				ImageType genre;
				try {
					genre = ImageType.valueOf(genreInput.toUpperCase());// Allows lower case answers
				} catch (IllegalArgumentException e) {
					System.out.println("Invalid genre entered. Image not added.");
					break;
				}

				System.out.println("Enter Date Taken (yyyy-mm-dd):");
				String dateInput = input.nextLine();
				LocalDate dateTaken;
				try {
					dateTaken = LocalDate.parse(dateInput);
				} catch (Exception e) {
					System.out.println("Invalid date format. Image not added.");
					break;
				}

				System.out.println("Enter Thumbnail:");
				String thumbnail = input.nextLine();

				ImageRecord newImage = new ImageRecord(title, description, genre, dateTaken, thumbnail);// Image is
																										// created
				manager.addImage(newImage);
				System.out.println("Image added successfully.");
				break;

			case 2:
				System.out.println("Selected Option 2 : Search for an image");

				do {

					searchChoice = searchMenu.getUserChoice(); // Opens sub-menu

					switch (searchChoice) {

					case 1:
						System.out.println("Selected Option 1 : Search by unique ID.");

						ImageAlbum imagesStored = manager.getAllImages(); // This code returns amount of ImageRecords in
																			// system (aka range of id's)
						int count = 0;
						ImageRecord current = imagesStored.getFirst();
						while (current != null) {
							count++;
							current = imagesStored.getNext();
						}

						System.out.println("Enter the id you would like to search. There are currently " + count
								+ " images stored.");

						int targetId = 0;
						try {

							targetId = input.nextInt();
							input.nextLine(); // Clear input buffer
						} catch (NumberFormatException e) {
							System.out.println("Invalid ID entered. Please enter a number.");
							break;
						}

						ImageRecord result = manager.searchId(targetId);

						if (result != null) {
							System.out.println("Image Found:");
							System.out.println(result);
						} else {
							System.out.println("No image found with that ID.");
						}

						break;

					case 2:
						System.out.println("Selected Option 2 : Search by image title. ");
						System.out.println("Enter the title you would like to search.");
						String targetTitle = input.nextLine();
						ImageAlbum results = manager.searchTitle(targetTitle);

						printAlbum(results);
						break;

					case 3:
						System.out.println("Selected Option 3 : Search by image description.");
						System.out.println("Enter the description you would like to search.");
						String targetDescription = input.nextLine();
						results = manager.searchDescription(targetDescription);
						printAlbum(results);
						break;

					case 4:
						System.out.println("Selected option 4: Search by image type.");
						System.out.println("Enter the image type you would like to search.");
						String targetInput = input.nextLine();
						ImageType targetType = null;
						try {
							targetType = ImageType.valueOf(targetInput.toUpperCase());
						} catch (IllegalArgumentException e) {
							System.out.println("Invalid image type entered.");
							break;
						}
						results = manager.searchGenre(targetType);
						printAlbum(results);
						break;

					case 5:
						System.out.println("Selected option 5: Search by date range");
						System.out.println("Enter the first date to search between.(yyyy-mm-dd)");
						String startInput = input.nextLine();
						System.out.println("Enter end date (yyyy-mm-dd): ");
						String endInput = input.nextLine();
						LocalDate startDate = null;
						LocalDate endDate = null;
						try {
							startDate = LocalDate.parse(startInput);
							endDate = LocalDate.parse(endInput);
						} catch (Exception e) {
							System.out.println("Invalid date format. Please use yyyy-mm-dd.");
							break;
						}

						// display the results of the date search
						results = manager.searchDates(startDate, endDate);
						printAlbum(results);
						break;

					case 6:
						System.out.println("Selected option 6: Return");
						break;

					default:
						System.out.println("Invalid option");
					}

				} while (searchChoice != 6);
				break;

			case 3:
				System.out.println("Selected Option 3 : Display all images");

				ImageAlbum album = manager.getAllImages();
				printAlbum(album);

				break;

			case 4:
				System.out.println("Selected option 4: Quitting the program ");
				break;

			default:
				System.out.println("Invalid option");
			}

		} while (choice != 4);

		input.close();
	}

}