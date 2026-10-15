package part01;

import java.util.ArrayList;

// addImage(ImageRecord image) - Adds specified record to album and sorts album by date.
// sortByDate() -- Sorts an album by date ascending.
// getFirst() -- return first record in an album
// getPrevious -- returns previous record in an album
// getNext -- returns next record in an album

public class ImageAlbum {

	private ArrayList<ImageRecord> images;
	private int currentIndex;

	public ImageAlbum() { //constructor
		images = new ArrayList<ImageRecord>();
		currentIndex = -1;
	}

	public void addImage(ImageRecord image) {
		images.add(image);
		sortByDate(); // keep album sorted
	}

	// bubble sort (earliest date first)
	private void sortByDate() {
	    boolean swapped = true;
	    while (swapped) {
	        swapped = false;
	        for (int i = 0; i < images.size() - 1; i++) {
	            if (images.get(i).getDateTaken().isAfter(images.get(i + 1).getDateTaken())) { //checks if swap is needed
	        // temporarily make a new list(temp) to swap the values
	                ArrayList<ImageRecord> temp = new ArrayList<ImageRecord>();
	                for (int j = 0; j < images.size(); j++) {
	                    if (j == i)          temp.add(images.get(i + 1)); //place the latter element first
	                    else if (j == i + 1) temp.add(images.get(i)); // place the former element after
	                    else                 temp.add(images.get(j));
	                }
	                images = temp;
	                swapped = true;
	            }
	        }
	    }
	}

	public ImageRecord getFirst() {
		if (images.size() == 0) {
			return null;
		}
		currentIndex = 0;
		return images.get(currentIndex);
	}
	public ImageRecord getNext() {

		if (images.size() == 0) {
			return null;
		}
		//return null if getFirst() hasn't been called yet.
		if (currentIndex == -1) {
			return null;
		}
		if (currentIndex + 1 >= images.size()) {
			return null;
		}
		currentIndex++;
		return images.get(currentIndex);
	}

	public ImageRecord getPrevious() {
		if (images.size() == 0) {
			return null;
		}
		if (currentIndex - 1 < 0) {
			return null;
		}
		currentIndex--;
		return images.get(currentIndex);
	}
	public int size() {
		return images.size();
	}
}

