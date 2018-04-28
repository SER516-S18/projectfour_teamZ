package teamZ.project4.controllers.server.interfaces;

public interface ServerToolbarInterface {
    /**
     * Displays a prompt to change the port to host the server on
     */
    void displayChangePortDialog();

    /**
     * Starts up or shuts down the server
     */
    void changeServerState();
}
