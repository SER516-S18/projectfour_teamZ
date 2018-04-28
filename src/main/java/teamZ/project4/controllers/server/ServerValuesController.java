package teamZ.project4.controllers.server;

import teamZ.project4.controllers.server.interfaces.ServerValuesInterface;
import teamZ.project4.model.EmostatePacketBuilder;
import teamZ.project4.model.Emotion;
import teamZ.project4.model.Expression;
import teamZ.project4.util.Log;

import javax.swing.*;
import java.text.ParseException;

public class ServerValuesController implements ServerValuesInterface {
    @Override
    public void comboExpressionFaceEyesChange(Expression previousExpressionEyes, EmostatePacketBuilder emostatePacketBuilder,
                                              JCheckBox checkboxEye, JComboBox comboExpressionFaceEyes) {
        // Reset previous value, if applicable
        if (previousExpressionEyes != null) {
            emostatePacketBuilder.setExpression(previousExpressionEyes, false);
        }

        checkboxEye.setSelected(emostatePacketBuilder.getExpressionBoolean((Expression) comboExpressionFaceEyes.getSelectedItem()));
        previousExpressionEyes = (Expression) comboExpressionFaceEyes.getSelectedItem();
    }

    @Override
    public void changeExpression(EmostatePacketBuilder emostatePacketBuilder,JComboBox comboExpressionFaceEyes,
                                  Boolean value) {
        emostatePacketBuilder.setExpression((Expression) comboExpressionFaceEyes.getSelectedItem(), value);
    }

    @Override
    public void comboExpressionFaceUpperChange(Expression previousExpressionFaceUpper,
                                               EmostatePacketBuilder emostatePacketBuilder, JSpinner spinnerUpperFace,
                                               JComboBox comboExpressionFaceUpper) {
        // Reset previous value, if applicable
        if (previousExpressionFaceUpper != null) {
            emostatePacketBuilder.setExpression(previousExpressionFaceUpper, 0f);
        }
        spinnerUpperFace.setValue(emostatePacketBuilder.getExpressionFloating((Expression) comboExpressionFaceUpper.getSelectedItem()).doubleValue());
        previousExpressionFaceUpper = (Expression) comboExpressionFaceUpper.getSelectedItem();
    }

    @Override
    public void comboExpressionFaceLowerChange(Expression previousExpressionFaceLower, EmostatePacketBuilder emostatePacketBuilder, JSpinner spinnerLowerFace, JComboBox comboExpressionFaceLower) {
        // Reset previous value, if applicable
        if (previousExpressionFaceLower != null) {
            emostatePacketBuilder.setExpression(previousExpressionFaceLower, 0.0f);
        }

        spinnerLowerFace.setValue(emostatePacketBuilder.getExpression((Expression) comboExpressionFaceLower.getSelectedItem()).doubleValue());
        previousExpressionFaceLower = (Expression) comboExpressionFaceLower.getSelectedItem();
    }

    @Override
    public void handleComboExpressionChange(EmostatePacketBuilder emostatePacketBuilder, JComboBox combo, JSpinner spinner) {
        try {
            spinner.commitEdit();
            emostatePacketBuilder.setExpression((Expression) combo.getSelectedItem(), (float) ((double) spinner.getValue()));
        } catch(ParseException e) {
            Log.w("Failed to parse " + combo.getSelectedItem() + " value", this.getClass());
        }
    }

    @Override
    public void handleSpinnerEmotionChange(EmostatePacketBuilder emostatePacketBuilder, JSpinner spinnerEmotion,
                                           JComboBox comboEmotion) {
        spinnerEmotion.setValue(emostatePacketBuilder.getEmotion((Emotion) comboEmotion.getSelectedItem()).doubleValue());
    }

    @Override
    public void handleComboEmotionChange(EmostatePacketBuilder emostatePacketBuilder, JComboBox combo, JSpinner spinner) {
        try {
            spinner.commitEdit();
            emostatePacketBuilder.setEmotion((Emotion) combo.getSelectedItem(), (float) ((double) spinner.getValue()));
        } catch(ParseException e) {
            Log.w("Failed to parse " + combo.getSelectedItem() + " value", this.getClass());
        }
    }
}
