package teamZ.project4.controllers.server.interfaces;

import teamZ.project4.model.EmostatePacketBuilder;
import teamZ.project4.model.Expression;

import javax.swing.*;

public interface ServerValuesInterface {
    /**
     * Called when comboExpressionFaceEyes button is clicked
     */
    void comboExpressionFaceEyesChange(Expression previousExpressionEyes, EmostatePacketBuilder emostatePacketBuilder,
                                       JCheckBox checkboxEye, JComboBox comboExpressionFaceEyes);

    /**
     * Called when checkboxEye is clicked
     */
    void changeExpression(EmostatePacketBuilder emostatePacketBuilder,JComboBox comboExpressionFaceEyes,
                           Boolean value);

    /**
     * Called when comboExpressionFaceUpper button is clicked
     */
    void comboExpressionFaceUpperChange(Expression previousExpressionFaceUpper,
                                        EmostatePacketBuilder emostatePacketBuilder, JSpinner spinnerUpperFace,
                                        JComboBox comboExpressionFaceUpper);

    /**
     * Called when comboExpressionFaceLower button is clicked
     */
    void comboExpressionFaceLowerChange(Expression previousExpressionFaceLower,
                                        EmostatePacketBuilder emostatePacketBuilder, JSpinner spinnerLowerFace,
                                        JComboBox comboExpressionFaceLower);

    /**
     * Handles combo expression change to save to builder
     */
    void handleComboExpressionChange(EmostatePacketBuilder emostatePacketBuilder, JComboBox combo, JSpinner spinner);

    /**
     * Handles spinner emotion change
     */
    void handleSpinnerEmotionChange(EmostatePacketBuilder emostatePacketBuilder, JSpinner spinnerEmotion,
                                    JComboBox comboEmotion);

    /**
     * Handles combo emotion change to save to builder
     */
    void handleComboEmotionChange(EmostatePacketBuilder emostatePacketBuilder, JComboBox combo, JSpinner spinner);
}
