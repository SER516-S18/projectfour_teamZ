package teamZ.project4.controllers.client;

import teamZ.project4.controllers.client.interfaces.ClientToolbarInterface;
import teamZ.project4.model.client.ClientModel;
import teamZ.project4.util.Log;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ClientToolbarController implements ClientToolbarInterface {

    private Component parentComponent;

    public ClientToolbarController(Component parentComponent) {
        this.parentComponent = parentComponent;
    }

    @Override
    public void handleConnectToServer() {
        if(ClientModel.get().isRunning()) {
            ClientModel.get().disconnect();
            try {
                long timeout = System.currentTimeMillis() + 1000L;
                while (ClientModel.get().isRunning() && System.currentTimeMillis() < timeout) {
                    Thread.sleep(100L);
                }
            } catch(InterruptedException e) {
                Log.w("Failed to sleep while disconnecting (" + e.getMessage() + ")", this.getClass());
            }
        } else {
            ClientModel.get().start();
            try {
                long timeout = System.currentTimeMillis() + 1000L;
                while (!ClientModel.get().isRunning() && System.currentTimeMillis() < timeout) {
                    Thread.sleep(100L);
                }
            } catch(InterruptedException e) {
                Log.w("Failed to sleep while connecting (" + e.getMessage() + ")", this.getClass());
            }
        }
    }

    @Override
    public void displayChangeHostDialog() {
        JTextField textfieldHost = new JTextField();
        JTextField textfieldPort = new JTextField();
        Object[] message = {
                "Host: ", textfieldHost,
                "Port: ", textfieldPort
        };
        int option = JOptionPane.showConfirmDialog(parentComponent, message, "Change host", JOptionPane.OK_CANCEL_OPTION);
        if (option != JOptionPane.OK_OPTION) {
            return;
        }

        try {
            String host = textfieldHost.getText();
            int port = Integer.parseInt(textfieldPort.getText());

            if (host.equalsIgnoreCase("localhost")) {
                ClientModel.get().setHostToLocalhost();
                ClientModel.get().setPort(port);
            } else {
                ClientModel.get().setHost(InetAddress.getByName(host));
                ClientModel.get().setPort(port);
            }
        } catch(NumberFormatException e) {
            Log.e("Invalid port specified (Must be numeric)", this.getClass());
        } catch(UnknownHostException e) {
            Log.e("Invalid host specified (" + e.getMessage() + ")", this.getClass());
        } catch(IllegalArgumentException e) {
            Log.e("Invalid port specified (" + e.getMessage() + ")", this.getClass());
        }
    }

}
