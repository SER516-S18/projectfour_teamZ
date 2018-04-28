package teamZ.project4.controllers.server.interfaces;

import javax.swing.*;

public interface ServerTransmitInterface {
    /**
     * Called when send button is clicked
     */
    void sendData(JButton buttonSend);

    /**
     * Called when repeat checkBox is toggled
     */
    void repeatCheckToggle(JCheckBox checkboxRepeat, JButton buttonSend);

    /**
     * Updates the send text depending on the repeat packet mode
     */
    void updateSendButtonText(JButton buttonSend);
}
