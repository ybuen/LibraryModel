package library.clients;

import library.Library;
import library.items.*;
import library.services.InputHelper;

public class Admin extends Client{

    private static final String adminMenu = "3- View item information\n4- Add item\n5- View all requests\n" +
            "6- Fulfill request\n7- Register new staff\n8- Logout";
    private static final String itemSelectionMenu = "1- Book\n2- Magazine\n3- Movie\n4- Audio book";

    public Admin(String username, String password, Library library) {
        super(username, password, library);
    }

    @Override
    public void promptMainMenu() {
        boolean selectionSucceeded = true;

        while (selectionSucceeded) {
            selectionSucceeded = processSelection(InputHelper.getIntInput(universalMenu + adminMenu));
        }
    }

    @Override
    public boolean processSelection(int selection) {
        System.out.println();
        boolean universalSelectionSucceeded = super.processSelection(selection);
        if (universalSelectionSucceeded) {
            return true;
        }
        if (selection == 3) {
            printLibraryItemInformation();
        }
        else if (selection == 4) {
            addLibraryItem();
        }
        else if (selection == 5) {
            printLibraryRequests();
        }
        else if (selection == 6) {
            fulfillLibraryRequest();
        }
        else if (selection == 7) {
            registerNewLibraryStaff();
        }
        else if (selection == 8) {
            return logOut();
        }

        return true;
    }

    public void addLibraryItem() {
        System.out.println("Select item type (Any number other than 1-4 will create a generic item class):");
        int itemSelection = InputHelper.getIntInput(itemSelectionMenu);

        String title = InputHelper.getInput("Enter item title: ");
        String author = InputHelper.getInput("Enter item author: ");
        String genre = InputHelper.getInput("Enter item genre: ");
        String rating = InputHelper.getInput("Enter item rating: ");

        Item item;

        if (itemSelection == 1) {
            item = new Book(title, author, genre, rating);
        }
        else if (itemSelection == 2) {
            item = new Magazine(title, author, genre, rating);
        }
        else if (itemSelection == 3) {
            item = new Movie(title, author, genre, rating);
        }
        else if (itemSelection == 4) {
            item = new AudioBook(title, author, genre, rating);
        }
        else {
            item = new Item(title, author, genre, rating);
        }

        library.addItem(this, item);
    }

    public void printLibraryRequests() {
        library.printRequests(this);
    }

    public void printLibraryItemInformation() {
        String requestId = InputHelper.getInput("Enter registry id of item to check: ");

        library.printRegistryInformation(this, requestId);
    }

    public void fulfillLibraryRequest() {
        String requestId = InputHelper.getInput("Enter request id to fulfill: ");

        library.fulfillRequest(this, requestId);
    }

    public void registerNewLibraryStaff() {
        library.registerNewStaff(this);
    }


}
