package teamZ.project4.ui.server;

import teamZ.project4.constants.ColorConstants;
import teamZ.project4.constants.TextConstants;
import teamZ.project4.controllers.server.ServerToolbarController;
import teamZ.project4.listeners.ServerListener;
import teamZ.project4.model.server.ServerModel;
import teamZ.project4.util.Log;

import javax.swing.*;
import java.awt.*;

/**
 * Toolbar for the ServerView
 * @author  David Henderson (dchende2@asu.edu)
 */
public class ServerToolbarView extends JMenuBar {
    private JMenu menu;
    private JMenuItem menuItemStateChange;
    private JLabel labelSessions;
    private JButton buttonStatus;
    private ServerToolbarController controller;

    /**
     * Constructor for ServerToolbarView, the toolbar at the top of the Server UI
     */
    public ServerToolbarView() {

        controller = new ServerToolbarController(this);

        ServerModel.get().addListener(new ServerListener() {
            @Override
            public void started() {
                menuItemStateChange.setText("Stop server");
                buttonStatus.setForeground(ColorConstants.INDICATOR_ON_BRIGHT);
            }

            @Override
            public void shutdown() {
                menuItemStateChange.setText("Start server");
                buttonStatus.setForeground(ColorConstants.INDICATOR_OFF);
            }

            @Override
            public void clientConnected() {
                labelSessions.setText(Integer.toString(ServerModel.get().getClientsCount()));
            }

            @Override
            public void clientDisconnected() {
                labelSessions.setText(Integer.toString(ServerModel.get().getClientsCount()));
            }

            @Override
            public void packetSent() { }

            @Override
            public void packetRepeatingToggled() { }

            @Override
            public void packetRepeatingModeChanged() { }
        });

        JMenuItem menuItem;

        this.add(Box.createHorizontalStrut(8));

        menu = new JMenu(Character.toString((char) 0x2630));
        menu.setFont(TextConstants.LARGE_FONT);
        this.add(menu);

        menuItem = new JMenuItem("Change port");
        menuItem.addActionListener(e -> controller.displayChangePortDialog());
        menu.add(menuItem);

        menuItemStateChange = new JMenuItem(ServerModel.get().isRunning() ? "Stop server" : "Start server");
        menuItemStateChange.addActionListener(e -> controller.changeServerState());
        menu.add(menuItemStateChange);

        this.add(Box.createHorizontalStrut(32));

        JLabel promptSessions = new JLabel("Clients: ");
        promptSessions.setFont(TextConstants.DEFAULT_FONT);
        this.add(promptSessions);

        labelSessions = new JLabel(Integer.toString(ServerModel.get().getClientsCount()));
        labelSessions.setFont(TextConstants.DEFAULT_FONT);
        this.add(labelSessions);

        this.add(Box.createHorizontalStrut(32));

        JLabel promptStatus = new JLabel("Status:");
        promptStatus.setFont(TextConstants.DEFAULT_FONT);
        this.add(promptStatus);

        buttonStatus = new JButton(Character.toString((char) 0x2022));
        buttonStatus.setFont(new Font("Monospaced", Font.PLAIN, TextConstants.DEFAULT_FONT.getSize() * 2));
        buttonStatus.setOpaque(false);
        buttonStatus.setContentAreaFilled(false);
        buttonStatus.setBorderPainted(false);
        buttonStatus.setFocusPainted(false);
        buttonStatus.setForeground(ColorConstants.INDICATOR_OFF);
        buttonStatus.addActionListener(e -> controller.changeServerState());
        this.add(buttonStatus);

        // Create a timer to blink if on or off
        Timer timer = new Timer(1000, e2 -> {
            if(ServerModel.get().isRunning()) {
                if(buttonStatus.getForeground() == ColorConstants.INDICATOR_ON_DIM) {
                    buttonStatus.setForeground(ColorConstants.INDICATOR_ON_BRIGHT);
                } else {
                    buttonStatus.setForeground(ColorConstants.INDICATOR_ON_DIM);
                }
            } else {
                buttonStatus.setForeground(ColorConstants.INDICATOR_OFF);
            }
        });
        timer.start();
    }
}
