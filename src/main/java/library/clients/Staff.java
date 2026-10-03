package library.clients;

import library.Library;
import library.enums.RequestType;
import library.services.InputHelper;

public class Staff extends Client{

    private static final String staffMenu = "3- View item information\n4- Make staff request\n5- Return item\n6- Logout";
    private static final String requestSelectionMenu = "1- Take for employee use\n2- Take to rent out\n" +
            "3- Take to remove temporarily\n4- Take to dispose of";
    private static final RequestType[] requestTypeArray = new RequestType[]
            {RequestType.USED_FOR_EMPLOYEE, // PLACEHOLDER
            RequestType.USED_FOR_EMPLOYEE,
            RequestType.RENTED,
            RequestType.TEMPORARILY_REMOVED,
            RequestType.DISPOSED};

    public Staff(String username, String password, Library library) {
        super(username, password, library);
    }

    @Override
    public void promptMainMenu() {
        boolean selectionSucceeded = true;

        while (selectionSucceeded) {
            selectionSucceeded = processSelection(InputHelper.getIntInput(universalMenu + staffMenu));
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
        } else if (selection == 4) {
            makeLibraryStaffRequest();
        } else if (selection == 5) {
            returnLibraryItem();
        } else if (selection == 6) {
            return false;
        }


        return true;
    }

    public void printLibraryItemInformation() {
        String requestId = InputHelper.getInput("Enter registry id of item to check: ");

        library.printRegistryInformation(this, requestId);
    }

    public void makeLibraryStaffRequest() {
        String registryId = InputHelper.getInput("Enter registry id of item to make request:");
        RequestType requestType = RequestType.BORROWED; // Placeholder

        while (true) {
            System.out.println("Enter number that corresponds with the type of request you want to file:");
            int requestSelection = InputHelper.getIntInput(requestSelectionMenu);

            if (1 <= requestSelection && requestSelection <= 4) {
                requestType = requestTypeArray[requestSelection];
                break;
            }

            System.out.println("Invalid selection!\n");
        }

        library.createStaffRequest(this, requestType, registryId);
    }

    public void returnLibraryItem() {
        String registryId = InputHelper.getInput("Enter registry id of item to return:");
        library.returnItem(this, registryId);
    }
}
