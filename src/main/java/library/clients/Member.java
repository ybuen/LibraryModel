package library.clients;

import library.Library;
import library.services.InputHelper;

public class Member extends Client{

    private static final String memberMenu = "3- Borrow item\n4- Return item\n5- Logout";


    public Member(String username, String password, Library library) {
        super(username, password, library);
    }

    @Override
    public void promptMainMenu() {
        boolean selectionSucceeded = true;

        while (selectionSucceeded) {
            selectionSucceeded = processSelection(InputHelper.getIntInput(universalMenu + memberMenu));
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
            borrowLibraryItem();
        } else if (selection == 4) {
            returnLibraryItem();
        } else if (selection == 5) {
            return false;
        }

        return true;
    }

    public void borrowLibraryItem() {
        String registryId = InputHelper.getInput("Enter registry id of item to borrow:");
        library.borrowItem(this, registryId);
    }

    public void returnLibraryItem() {
        String registryId = InputHelper.getInput("Enter registry id of item to return:");
        library.returnItem(this, registryId);
    }
}
