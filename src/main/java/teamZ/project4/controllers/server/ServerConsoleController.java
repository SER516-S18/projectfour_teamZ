package teamZ.project4.controllers.server;

import teamZ.project4.controllers.server.interfaces.ServerConsoleInterface;

import javax.swing.*;

public class ServerConsoleController implements ServerConsoleInterface {
    @Override
    public void clearConsole(JTextArea textareaLog, JTextField textfieldInput) {
        textareaLog.setText("");
        textfieldInput.setText("");
    }
}
