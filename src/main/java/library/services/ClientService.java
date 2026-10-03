package library.services;

import library.clients.Admin;
import library.clients.Client;
import library.clients.Member;
import library.clients.Staff;
import library.Library;

import java.util.ArrayList;

public class ClientService {
    private final ArrayList<Client> clients;
    private final Library library;

    public ClientService(Library library) {
        this.library = library;
        this.clients = new ArrayList<Client>();
        createAdminClient();
    }

    public void createAdminClient() {
        String[] info = getNewClientInfo(Admin.class.getSimpleName());

        Client newClient = new Admin(info[0], info[1], library);
        this.clients.add(newClient);
        System.out.println("Account registration successful\n");
    }

    public void createStaffClient() {
        String[] info = getNewClientInfo(Staff.class.getSimpleName());

        Client newClient = new Staff(info[0], info[1], library);
        this.clients.add(newClient);
        System.out.println("Account registration successful\n");
    }

    public void createMemberClient() {
        String[] info = getNewClientInfo(Member.class.getSimpleName());

        Client newClient = new Member(info[0], info[1], library);
        this.clients.add(newClient);
        System.out.println("Account registration successful\n");
    }

    public Client logIntoClient() {
        Client client = null;

        while (true) {
            client = attemptLogin();
            if (client != null) {
                break;
            }
            promptClientRegistration();
        }

        return client;
    }

    public void promptClientRegistration() {
        String str = InputHelper.getInput("Would you like to register an account (y/n)?");
        if (str.equalsIgnoreCase("y")) {
            createMemberClient();
        }
        System.out.println();
    }

    private Client attemptLogin() {
        System.out.println("Login to your account");
        String user = InputHelper.getInput("Enter username: ");
        Client client = this.searchUsername(user);

        if (client == null) {
            System.out.println("Account not found\n");
            return null;
        }

        String pwd = InputHelper.getInput("Enter password: ");

        if (client.checkPassword(pwd)) {
            System.out.println("Login for " + getClientType(client) + " \"" + user + "\" was successful\n");
            return client;
        }

        System.out.println("Login Invalid\n");
        return null;
    }

    private String[] getNewClientInfo(String clientType) {
        String user = "";
        System.out.println("Signing up new " + clientType + " client");

        while (true) {
            user = InputHelper.getInput("Enter username: ");
            if (searchUsername(user) == null) {
                break;
            }
            System.out.println("Username already exists!");
        }

        String pw = InputHelper.getInput("Enter password: ");

        return new String[]{user, pw};
    }

    private String getClientType(Client client){
        return client.getClass().getSimpleName();
    }

    public Client searchUsername(String username) {
        for (Client client:this.clients) {
            if (client.checkUsername(username)){
                return client;
            }
        }
        return null;
    }

    public boolean checkIfClientExists(Client client) {
        boolean bool = clients.contains(client);
        if (!bool) {
            System.out.println("Client is not registered with this library.\n");
        }
        return bool;
    }

    public boolean checkForInvalidPermission(Client client, Class<?> permittedClass) {
        if (!checkIfClientExists(client)) {
            return true;
        }

        if (permittedClass.isInstance(client)) {
            return false;
        }

        System.out.println("Invalid permissions!\n");
        return true;
    }

    public boolean checkForInvalidPermission(Client client, Class<?>[] permissions) {
        if (!checkIfClientExists(client)) {
            return true;
        }

        for (Class<?> permittedClass:permissions) {
            if (permittedClass.isInstance(client)) {
                return false;
            }
        }

        System.out.println("Invalid permissions!\n");
        return true;
    }
}
