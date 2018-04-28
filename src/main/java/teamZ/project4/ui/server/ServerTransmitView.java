package teamZ.project4.ui.server;

import teamZ.project4.constants.ColorConstants;
import teamZ.project4.constants.TextConstants;
import teamZ.project4.controllers.server.ServerTransmitController;
import teamZ.project4.listeners.ServerListener;
import teamZ.project4.model.server.ServerModel;
import teamZ.project4.util.Log;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.ParseException;

/**
 * UI for setting the packet transmit settings
 * @author  David Henderson (dchende2@asu.edu)
 */
public class ServerTransmitView extends JPanel {
    private JButton buttonSend;
    private JCheckBox checkboxRepeat;
    private JSpinner spinnerInterval;
    private ServerTransmitController controller;

    /**
     * Constructor for ServerTransmitView, the input for how to send packets
     */
    public ServerTransmitView() {

        controller = new ServerTransmitController(this);

        this.init();

        ServerModel.get().addListener(new ServerListener() {
            @Override
            public void started() { }

            @Override
            public void shutdown() { }

            @Override
            public void clientConnected() { }

            @Override
            public void clientDisconnected() { }

            @Override
            public void packetSent() { }

            @Override
            public void packetRepeatingToggled() {
                controller.updateSendButtonText(buttonSend);
            }

            @Override
            public void packetRepeatingModeChanged() { }
        });
    }

    /**
     * Initializer for UI
     */
    private void init() {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setBorder(new EmptyBorder(8, 8, 8, 8));
        this.setBackground(ColorConstants.BACKGROUND_BLUE);

        buttonSend = new JButton("Invalid");
        buttonSend.setFont(TextConstants.LARGE_FONT);
        buttonSend.addActionListener(e -> {
            controller.sendData(buttonSend);
        });
        this.add(buttonSend);

        this.add(Box.createHorizontalStrut(16));

        checkboxRepeat = new JCheckBox("Repeat");
        checkboxRepeat.setFont(TextConstants.LARGE_FONT);
        checkboxRepeat.setOpaque(false);
        checkboxRepeat.addActionListener(e -> {
            controller.repeatCheckToggle(checkboxRepeat,buttonSend);
        });
        this.add(checkboxRepeat);

        this.add(Box.createHorizontalStrut(16));

        double start = ServerModel.get().getAutoRepeatInterval() / 1000d;
        spinnerInterval = new JSpinner( new SpinnerNumberModel(start, 0.01d, 1440d, 0.25d) );
        spinnerInterval.setVisible(true);
        spinnerInterval.setBorder(null);
        spinnerInterval.setMaximumSize(new Dimension(128, 128));
        spinnerInterval.addChangeListener(event -> {
            try {
                spinnerInterval.commitEdit();
                long interval = (long) ((double) spinnerInterval.getValue() * 1000d);
                ServerModel.get().setAutoRepeatInterval(interval);
            } catch (ParseException e) {
                Log.w("Failed to parse interval spinner input", ServerTransmitView.class);
            }
        });
        spinnerInterval.setFont(TextConstants.LARGE_FONT);
        this.add(spinnerInterval);

        this.add(Box.createHorizontalStrut(8));

        JLabel promptIntervalSeconds = new JLabel("sec");
        promptIntervalSeconds.setFont(TextConstants.LARGE_FONT);
        this.add(promptIntervalSeconds);

        controller.updateSendButtonText(buttonSend);
    }
}
