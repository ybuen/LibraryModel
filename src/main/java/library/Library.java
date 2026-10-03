package library;

import library.clients.Admin;
import library.clients.Client;


import library.clients.Member;
import library.clients.Staff;
import library.enums.RequestType;

import library.items.*;
import library.services.ClientService;
import library.services.IndexService;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Library {
    // ATTRIBUTES and FINALS

    private static final Class<?>[] staffAndAdminClasses = new Class<?>[] {Admin.class, Staff.class};

    private final IndexService INDEXSERVICE;
    private final ClientService CLIENTSERVICE;
    private final ArrayList<ItemRegistry> itemRegistries;
    private final ArrayList<Request> pendingRequests;

    // CONSTRUCTOR
    public Library() {
        this.INDEXSERVICE = new IndexService();
        this.itemRegistries = new ArrayList<ItemRegistry>();
        this.pendingRequests = new ArrayList<Request>();
        this.CLIENTSERVICE = new ClientService(this);
    }

    public void start() throws Exception{
        Scanner scanner = new Scanner(new File("testData.txt"));
        loadTestData(scanner); // LOAD TEST DATA
        scanner.close();

        while (true) {
            Client client = CLIENTSERVICE.logIntoClient();
            client.promptMainMenu();
        }
    }

    // TEST DATE METHOD ------------------------------------------------------------------------------------------------

    private void loadTestData(Scanner scanner) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (line.isEmpty()) {
                continue;
            }

            String[] data = line.split("\\|");

            String type = data[0];
            String title = data[1];
            String author = data[2];
            String genre = data[3];
            String rating = data[4];

            Item item;

            if (type.equals("B")) {
                item = new Book(title, author, genre, rating);
            } else if (type.equals("MAG")) {
                item = new Magazine(title, author, genre, rating);
            } else if (type.equals("MOV")) {
                item = new Movie(title, author, genre, rating);
            } else if (type.equals("AB")) {
                item = new AudioBook(title, author, genre, rating);
            } else {
                System.out.println("Invalid item type: " + type);
                continue;
            }

            String newId = INDEXSERVICE.getNewIndex(item.getPrefix());

            ItemRegistry newRegistry = new ItemRegistry(newId, item);
            itemRegistries.add(newRegistry);
        }
    }


    // UNIVERSAL METHODS -----------------------------------------------------------------------------------------------
    public void returnItem(Client client, String registryId) {
        if (!CLIENTSERVICE.checkIfClientExists(client)) {
            return;
        }

        ItemRegistry registry = getRegistryFromId(registryId);
        if (registry == null) {
            return;
        }

        Request currentRequest = registry.getActiveRequest(true);
        if (currentRequest == null) {
            return;
        }
        if (currentRequest.requestType.equals(RequestType.DISPOSED)) {
            System.out.println("Return failed, item has already been disposed!\n");
            return;
        }

        if (!currentRequest.requestedUser.equals(client)) {
            System.out.println("Return failed (Invalid identity)\n");
            return;
        }

        registry.setAvailable();
        System.out.println("Item successfully returned\n");

        registry.updateQueue();
    }

    public void searchItems(Client client, String parameter) {
        if (!CLIENTSERVICE.checkIfClientExists(client)) {
            return;
        }

        ArrayList<ItemRegistry> matches = new ArrayList<ItemRegistry>();

        for (ItemRegistry registry: itemRegistries) {
            if (checkForMatchingParameters(registry, parameter)) {
                matches.add(registry);
            }
        }

        System.out.println("Found "+matches.size()+" match(es):");
        for (ItemRegistry match: matches) {
            System.out.println(match.toString());
        }
        System.out.println();
    }

    // SHARED ADMIN AND STAFF METHODS ----------------------------------------------------------------------------------
    public void printRegistryInformation(Client client, String registryId) {
        if (CLIENTSERVICE.checkForInvalidPermission(client, staffAndAdminClasses)) {
            return;
        }

        ItemRegistry registry = getRegistryFromId(registryId);
        if (registry == null) {
            return;
        }

        System.out.println(registry +"\n");
        registry.printRequestHistory();
        registry.printRequestQueue();
    }

    // ADMIN METHODS ---------------------------------------------------------------------------------------------------
    public void addItem(Client client, Item item) {
        if (CLIENTSERVICE.checkForInvalidPermission(client, Admin.class)) {
            return;
        }
        String newId = INDEXSERVICE.getNewIndex(item.getPrefix());

        ItemRegistry newRegistry = new ItemRegistry(newId, item);
        itemRegistries.add(newRegistry);

        System.out.println("Successfully added item\n");
    }

    public void printRequests(Client client) {
        if (CLIENTSERVICE.checkForInvalidPermission(client, Admin.class)) {
            return;
        }
        int requestsLength = pendingRequests.size();

        System.out.println("All staff request(s) ("+requestsLength+"):");
        for (Request request:pendingRequests) {
            System.out.println(request.toString());
        }
        System.out.println();
    }

    public void registerNewStaff(Client client) {
        if (CLIENTSERVICE.checkForInvalidPermission(client, Admin.class)) {
            return;
        }

        CLIENTSERVICE.createStaffClient();
    }

    public void fulfillRequest(Client client, String requestId) {
        if (CLIENTSERVICE.checkForInvalidPermission(client, Admin.class)) {
            return;
        }

        Request request = getRequestFromId(requestId);
        if (request == null) {
            return;
        }

        request.fulfill();
        pendingRequests.remove(request);
    }

    // STAFF METHODS ---------------------------------------------------------------------------------------------------
    public void createStaffRequest(Client client, RequestType requestType, String registryId) {
        if (CLIENTSERVICE.checkForInvalidPermission(client, Staff.class)) {
            return;
        }

        ItemRegistry registry = getRegistryFromId(registryId);
        if (registry == null) {
            return;
        }

        String newId = INDEXSERVICE.getNewIndex("R");
        Request newRequest = new Request(client, newId, requestType, registry);

        pendingRequests.add(newRequest);

        System.out.println("Request submitted\n");
    }

    // MEMBER METHODS --------------------------------------------------------------------------------------------------
    public void borrowItem(Client client, String registryId) {
        if (CLIENTSERVICE.checkForInvalidPermission(client, Member.class)) {
            return;
        }

        ItemRegistry registry = getRegistryFromId(registryId);
        if (registry == null) {
            return;
        }

        String newId = INDEXSERVICE.getNewIndex("R");
        Request newRequest = new Request(client, newId, RequestType.BORROWED, registry);
        newRequest.fulfill();
    }

    // HELPER METHODS --------------------------------------------------------------------------------------------------

    public ItemRegistry getRegistryFromId(String registryId) {
        for (ItemRegistry registry:itemRegistries) {
            if (registry.id.equals(registryId)) {
                return registry;
            }
        }

        System.out.println("Item not found! \n");
        return null;
    }

    public Request getRequestFromId(String requestId) {
        for (Request request:pendingRequests) {
            if (request.id.equals(requestId)) {
                return request;
            }
        }

        System.out.println("Request not found! \n");
        return null;
    }

    public void printItems() {
        System.out.println("All library item(s) ("+itemRegistries.size()+"): ");
        int registriesLength = this.itemRegistries.size();

        for (int i = 0; i < registriesLength; i ++) {
            System.out.println((i + 1) + ". " + itemRegistries.get(i).toString());
        }
        System.out.println();
    }

    public boolean checkForMatchingParameters(ItemRegistry registry, String parameter) {
        Item item = registry.item;

        return (parameter.equalsIgnoreCase(registry.id) || parameter.equalsIgnoreCase(item.getAuthor())
        || parameter.equalsIgnoreCase(item.getTitle()) || parameter.equalsIgnoreCase(item.getGenre()));
    }
}
