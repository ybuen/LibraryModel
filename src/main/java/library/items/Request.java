package library.items;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import library.clients.Client;
import library.enums.RequestType;

public class Request {
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
    public static final String INITIAL_FULFILL_DATE = "N/A";

    public final String id;
    public final Client requestedUser;
    public final RequestType requestType;
    public final ItemRegistry itemRegistry;
    private final String dateRequested;
    private String dateFulfilled;

    public Request(Client client, String id, RequestType requestType, ItemRegistry itemRegistry) {
        this.id = id;
        this.requestedUser = client;
        this.requestType = requestType;
        this.itemRegistry = itemRegistry;
        this.dateRequested = getDateTime();
        this.dateFulfilled = INITIAL_FULFILL_DATE;
    }

    public void fulfill() {
        this.dateFulfilled = getDateTime();

        if (!this.itemRegistry.available) {
            System.out.println("Could not be fulfilled (Item unavailable), request has been placed in queue instead");
            System.out.println("You will be able to take the item once it is your turn\n");
            this.itemRegistry.requestQueue.add(this);
            return;
        }

        this.itemRegistry.available = false;

        this.itemRegistry.requestHistory.add(this);
        System.out.println("Request has been fulfilled\n");
    }

    @Override
    public String toString() {
        String endPhrase = "Request was fulfilled on " + this.dateFulfilled;
        if (this.dateFulfilled.equals(INITIAL_FULFILL_DATE)) {
            endPhrase = "Request has not been fulfilled by an admin";
        }

        return "ID: "+id+" "+requestedUser.username+" requested for "+itemRegistry.toShorthandString()+" to be "
                +requestType+ " on "+dateRequested+" "+endPhrase;
    }

    public String getDateRequested() {
        return this.dateRequested;
    }

    public String getDateFulfilled() {
        return this.dateFulfilled;
    }

    private static String getDateTime() {
        return LocalDateTime.now().format(TIME_FORMATTER);
    }
}
