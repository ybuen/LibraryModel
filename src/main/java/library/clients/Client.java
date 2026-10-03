package library.clients;

import library.Library;
import library.services.InputHelper;

public abstract class Client {

    public static final String universalMenu = "Selection Menu:\n1- View items\n2- Search items\n";

    public final String username;
    private final String password;
    final Library library; // PACKAGE DEFAULT

    public Client(String username, String password, Library library) {
        this.username = username;
        this.password = password;
        this.library = library;
    }

    public boolean checkUsername(String username){
        return (username.equals(this.username));
    }

    public boolean checkPassword(String password) {
        return (password.equals(this.password));
    }

    public abstract void promptMainMenu();

    public boolean processSelection(int selection) {
        if (selection == 1) {
            viewLibraryItems();
            return true;
        }
        else if (selection == 2) {
            searchLibraryItems();
            return true;
        }
        return false;
    }

    public void viewLibraryItems() {
        library.printItems();
    }

    public void searchLibraryItems() {
        library.searchItems(this, InputHelper.getInput("Enter parameter to search: "));
    }

    public boolean logOut() {
        System.out.println("Successfully logged out\n");
        return false;
    }

}
