package teamZ.project4.controllers.server;

import teamZ.project4.controllers.server.interfaces.ServerTransmitInterface;
import teamZ.project4.model.server.ServerModel;

import javax.swing.*;
import java.awt.*;

public class ServerTransmitController implements ServerTransmitInterface {

    private Component parentComponent;

    public ServerTransmitController(Component parentComponent) {
        this.parentComponent = parentComponent;
    }

    @Override
    public void sendData(JButton buttonSend) {
        if(ServerModel.get().isPacketRepeatMode()) {
            ServerModel.get().sendPacketsToggle();
        } else {
            ServerModel.get().sendPacketIndividual();
        }

        updateSendButtonText(buttonSend);
    }

    @Override
    public void repeatCheckToggle(JCheckBox checkboxRepeat, JButton buttonSend) {
        if(!ServerModel.get().isRepeatingPackets()) {
            // If not already repeating, just change the mode
            ServerModel.get().setPacketRepeatMode(checkboxRepeat.isSelected());
        } else {
            // If already repeating, turn off, then change the mode
            ServerModel.get().sendPacketsToggle();
            ServerModel.get().setPacketRepeatMode(false);
        }
        updateSendButtonText(buttonSend);
    }

    @Override
    public void updateSendButtonText(JButton buttonSend) {
        if(buttonSend == null)
            return;

        if(ServerModel.get().isPacketRepeatMode()) {
            if(ServerModel.get().isRepeatingPackets()) {
                buttonSend.setText("Stop");
            } else {
                buttonSend.setText("Start");
            }
        } else {
            buttonSend.setText("Send");
        }

        parentComponent.validate();
        parentComponent.repaint();
    }
}
