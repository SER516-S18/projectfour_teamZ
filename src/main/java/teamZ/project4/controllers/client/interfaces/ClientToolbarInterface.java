package teamZ.project4.controllers.client.interfaces;

public interface ClientToolbarInterface {
    /**
     * Connects to the server or disconnects if connected
     */
    void handleConnectToServer();

    /**
     * Prompts the user to input a new host/port input
     */
    void displayChangeHostDialog();

    /**
     * Opens the server composer UI, if not open, otherwise refocuses it. Attempts connection if starting up server.
     */
    void openServerPanel();
}
