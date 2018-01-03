package com.example.suryaajha.vscalculator;

import android.widget.EditText;

public class OperatorChecker {
    private EditText inputArea = null ;
    private StringBuilder builder = null ;
    private int currentPositionOfCursor = 0 ;
    private int beforeCurrentPositionCursor = 0 ;
    private char operatorCharacter ;
    private char characterBeforeCursorPosition ;
    public OperatorChecker ( EditText editText ) {
        this.inputArea = editText ;
    }

    public void overrideLastCharacter () {
        currentPositionOfCursor = inputArea.getSelectionStart() ;
        builder = new StringBuilder(inputArea.getText().toString());
        if (currentPositionOfCursor != 0) {
            beforeCurrentPositionCursor = currentPositionOfCursor - 1;
            characterBeforeCursorPosition = builder.charAt(currentPositionOfCursor - 1);
            if ( characterBeforeCursorPosition == '-' && operatorCharacter == '+' ) {
                builder.deleteCharAt(beforeCurrentPositionCursor) ;
                inputArea.setText(builder);
                inputArea.setSelection(beforeCurrentPositionCursor);
            }
            else if ((characterBeforeCursorPosition == '+') || (characterBeforeCursorPosition == '-') || (characterBeforeCursorPosition == '\u00d7') || (characterBeforeCursorPosition == '\u00f7')) {

                builder.deleteCharAt(beforeCurrentPositionCursor);
                builder.insert(beforeCurrentPositionCursor , operatorCharacter ) ;
                inputArea.setText(builder) ;
                inputArea.setSelection(currentPositionOfCursor);
            }
            else {
                builder.insert(currentPositionOfCursor,operatorCharacter) ;
                inputArea.setText(builder);
                inputArea.setSelection(currentPositionOfCursor + 1);
            }
        }

    }
    public void multiplyOperator ( ) {
        operatorCharacter = '\u00d7' ; // unicode character for *
        overrideLastCharacter();
    }
    public void divideOperator () {
        operatorCharacter = '\u00f7' ; // unicode character for /
        overrideLastCharacter() ;
    }
    public void plusOperator () {
        operatorCharacter = '+' ;
        overrideLastCharacter() ;
    }
    public void minusOperator () {
        operatorCharacter = '-' ;
        currentPositionOfCursor = inputArea.getSelectionStart() ;
        if ( currentPositionOfCursor != 0 ) {
            characterBeforeCursorPosition = inputArea.getText().toString().charAt(currentPositionOfCursor - 1);
            if (characterBeforeCursorPosition == '+' || characterBeforeCursorPosition == '-') {
                overrideLastCharacter();
            } else {
                builder = new StringBuilder(inputArea.getText().toString());
                builder.append(operatorCharacter);
                inputArea.setText(builder);
                inputArea.setSelection(currentPositionOfCursor + 1);
            }
        }
        else if ( currentPositionOfCursor == 0 ) {
            builder = new StringBuilder(inputArea.getText().toString()) ;
            builder.append('-') ;
            inputArea.setText(builder);
            inputArea.setSelection(1);

        }
    }
}
