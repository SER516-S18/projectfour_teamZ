package teamZ.project4.controllers.server;

import teamZ.project4.controllers.server.interfaces.ServerToolbarInterface;
import teamZ.project4.model.server.ServerModel;
import teamZ.project4.util.Log;

import javax.swing.*;
import java.awt.*;

public class ServerToolbarController implements ServerToolbarInterface {

    private Component parentComponent;

    public ServerToolbarController(Component parentComponent) {
        this.parentComponent = parentComponent;
    }

    @Override
    public void displayChangePortDialog() {
        String input = JOptionPane.showInputDialog(parentComponent, "Enter port number", "Change port number", JOptionPane.PLAIN_MESSAGE);
        try {
            ServerModel.get().setPort(Integer.parseInt(input));

            if(ServerModel.get().isRunning()) {
                ServerModel.get().shutdown();
                long timeout = System.currentTimeMillis() + 1000L;
                while(ServerModel.get().isRunning() && System.currentTimeMillis() < timeout) {
                    Thread.sleep(100L);
                }
                ServerModel.get().start();
            }
        } catch(NumberFormatException e) {
            Log.e("Invalid port specified (Must be numeric)", this.getClass());
        } catch(IllegalArgumentException e) {
            Log.e("Invalid port specified (" + e.getMessage() + ")", this.getClass());
        } catch (InterruptedException e) {
            Log.w("Failed to sleep between restarting server from port change (" + e.getMessage() + ")", this.getClass());
        }
    }

    @Override
    public void changeServerState() {
        if(ServerModel.get().isRunning()) {
            ServerModel.get().shutdown();
            try {
                long timeout = System.currentTimeMillis() + 1000L;
                while (ServerModel.get().isRunning() && System.currentTimeMillis() < timeout) {
                    Thread.sleep(100L);
                }
            } catch(InterruptedException e) {
                Log.w("Failed to sleep while shutting down server (" + e.getMessage() + ")", this.getClass());
            }
        } else {
            ServerModel.get().start();
            try {
                long timeout = System.currentTimeMillis() + 1000L;
                while (!ServerModel.get().isRunning() && System.currentTimeMillis() < timeout) {
                    Thread.sleep(100L);
                }
            } catch(InterruptedException e) {
                Log.w("Failed to sleep while starting server (" + e.getMessage() + ")", this.getClass());
            }
        }
    }
}
