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
}
