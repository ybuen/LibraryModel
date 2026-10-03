package library.items;

import library.enums.RequestType;

import java.util.ArrayList;

public class ItemRegistry {
    public final String id;
    public final Item item;
    boolean available; // DEFAULT, PACKAGE PRIVATE
    final ArrayList<Request> requestHistory; // DEFAULT, PACKAGE PRIVATE
    final ArrayList<Request> requestQueue;

    public ItemRegistry(String id, Item item) {
        this.id = id;
        this.item = item;
        this.available = true;
        this.requestHistory = new ArrayList<Request>();
        this.requestQueue = new ArrayList<Request>();
    }

    @Override
    public String toString() {
        String str = "Unavailable";
        if (this.getAvailability()) {
            str = "Available";
        }

        return "id:" + this.id + ": " + this.item.toString() + ", is " + str;
    }

    public Request getActiveRequest(boolean printError) {
        if (available) {
            if (printError) {
                System.out.println("No active request. (Book has already been returned/is available) \n");
            }
            return null;
        }

        return requestHistory.getLast();
    }

    public void printRequestHistory() {
        Request current = getActiveRequest(false);
        int historySize = requestHistory.size();
        System.out.println("All past request(s) ("+historySize+"):");
        if (current != null) {
            System.out.print("(Currently Active)>");
        }

        for (int i = historySize - 1; i >= 0; i--) {
            System.out.println(requestHistory.get(i).toString());
        }
        System.out.println();
    }

    public void printRequestQueue() {
        int queueSize = requestQueue.size();
        System.out.println("All queued request(s) ("+queueSize+"):");
        for (Request request:requestQueue) {
            System.out.println(request.toString());
        }
        System.out.println();
    }

    public boolean getAvailability() {
        return this.available;
    }

    public void setAvailable() {
        this.available = true;
    }

    public String toShorthandString() {
        return "\"" + this.item.getTitle() + "\" (" + this.id + ")";
    }

    public void updateQueue() {
        if (requestQueue.isEmpty()) {
            return;
        }

        Request firstRequest = requestQueue.getFirst();
        requestQueue.removeFirst();

        firstRequest.fulfill();
        System.out.println(this.toShorthandString()+" has been lended to \""+firstRequest.requestedUser.username+
                "\" via queue\n");

    }

}
