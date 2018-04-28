package teamZ.project4.controllers.client;

import teamZ.project4.controllers.client.interfaces.ClientToolbarInterface;
import teamZ.project4.model.client.ClientModel;
import teamZ.project4.model.server.ServerModel;
import teamZ.project4.ui.client.ClientToolbarView;
import teamZ.project4.ui.client.ClientView;
import teamZ.project4.ui.server.ServerView;
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
                Log.w("Failed to sleep while disconnecting (" + e.getMessage() + ")", ClientToolbarView.class);
            }
        } else {
            ClientModel.get().start();
            try {
                long timeout = System.currentTimeMillis() + 1000L;
                while (!ClientModel.get().isRunning() && System.currentTimeMillis() < timeout) {
                    Thread.sleep(100L);
                }
            } catch(InterruptedException e) {
                Log.w("Failed to sleep while connecting (" + e.getMessage() + ")", ClientToolbarView.class);
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
            Log.e("Invalid port specified (Must be numeric)", ClientToolbarView.class);
        } catch(UnknownHostException e) {
            Log.e("Invalid host specified (" + e.getMessage() + ")", ClientToolbarView.class);
        } catch(IllegalArgumentException e) {
            Log.e("Invalid port specified (" + e.getMessage() + ")", ClientToolbarView.class);
        }
    }

    @Override
    public void openServerPanel() {
        if(ServerView.getInstance().isDisplayable()) {
            ServerView.getInstance().toFront();
            ServerView.getInstance().repaint();
        } else {
            ServerView.getInstance().init();
            int x = ClientView.getInstance().getX() - (ClientView.getInstance().getWidth() / 2) - (ServerView.getInstance().getWidth() / 2);
            if (x < ServerView.getInstance().getWidth() / 2) {
                x = ClientView.getInstance().getX() + (ClientView.getInstance().getWidth() / 2) + (ServerView.getInstance().getWidth() / 2);
            }
            if(x > Toolkit.getDefaultToolkit().getScreenSize().width - ServerView.getInstance().getWidth() / 2) {
                x = ClientView.getInstance().getX();
            }

            ServerView.getInstance().setLocation(
                    x, ClientView.getInstance().getLocation().getLocation().y
            );
        }

        // Try to connect the client in a moment
        new Thread(() -> {
            try {
                long timeout = System.currentTimeMillis() + 2000L;
                while(System.currentTimeMillis() < timeout && !ServerModel.get().isRunning()) {
                    Thread.sleep(100L);
                }
            } catch(InterruptedException e) {
                Log.w("Failed to wait while server starts up (" + e.getMessage() + ")", ClientToolbarView.class);
            }

            if(!ClientModel.get().isRunning())
                ClientModel.get().start();
        }).start();
    }
}
