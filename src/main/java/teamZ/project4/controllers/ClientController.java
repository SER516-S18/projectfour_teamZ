package teamZ.project4.controllers;

import teamZ.project4.controllers.interfaces.ClientInterface;
import teamZ.project4.model.client.ClientModel;

public class ClientController implements ClientInterface {
    @Override
    public void initClientModel() {
        // Start the client model if it isn't running
        if(!ClientModel.get().isRunning())
            ClientModel.get().start();
    }
}
