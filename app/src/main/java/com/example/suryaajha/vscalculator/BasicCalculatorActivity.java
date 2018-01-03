package com.example.suryaajha.vscalculator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.udojava.evalex.Expression;

import java.math.BigDecimal;

public class BasicCalculatorActivity extends Activity {

    EditText inputArea = null ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_calculator);
        final EditText edittext = (EditText) findViewById(R.id.editPanel);
        Intent intent = this.getIntent() ;
        if ( intent != null ) {
            edittext.setText(intent.getStringExtra("TEXT"));
            edittext.setSelection(intent.getIntExtra("CURSOR_LOCATION",0));
        }
    }
    public void onClickButtonHandler ( View view ) {
        inputArea = ( EditText ) findViewById( R.id.editPanel ) ;  // Reference to inputArea
        Button clickedButton = ( Button ) view ; // Reference to clicked Button
        char   clickedButtonCharacter = clickedButton.getText().toString().charAt(0) ;
        int cursorLocation = inputArea.getSelectionStart() ; // Get the cursor location

        insertAfterCursor(inputArea , clickedButtonCharacter , cursorLocation ); // set the character

    }
    public void onClickResult ( View view ) {
        inputArea = ( EditText ) findViewById( R.id.editPanel ) ;

        StringBuilder builder = new StringBuilder(inputArea.getText().toString()) ;
        for ( int i = 0 ; i < builder.length() ; i++ ) {
            if ( builder.charAt(i) == '\u00d7' ) {
                builder.setCharAt(i,'*');
            }
            else if ( builder.charAt(i) == '\u00f7' ) {
                builder.setCharAt(i,'/');
            }
        }
        Expression exp = new Expression(new String(builder)) ;
        BigDecimal result = null ;
        String resultInString = null ;
        try {
             result = exp.eval();
        }catch( Expression.ExpressionException e ) {
            resultInString = "Maths Error" ;
            System.out.println(result);
        }
        if ( resultInString == null ) {
            resultInString = String.valueOf(result);
        }
        inputArea.setText(resultInString);
        inputArea.setSelection(resultInString.length());
    }

    public void insertAfterCursor ( EditText inputArea , char clickedButtonCharacter , int cursorLocation  ) {

        String inputAreaString = inputArea.getText().toString() ;
        String beforeCursor = inputAreaString.substring(0,cursorLocation) ;
        String afterCursor  = inputAreaString.substring(cursorLocation) ;
        inputArea.setText( beforeCursor + clickedButtonCharacter + afterCursor  ) ;
        inputArea.setSelection(cursorLocation+1);

    }
    public void hideSoftKeyBoard ( View view ) {
        inputArea = (EditText)findViewById(R.id.editPanel) ;
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE) ;
        if ( inputMethodManager != null )
            inputMethodManager.hideSoftInputFromWindow(inputArea.getWindowToken(),0) ;
    }


    private boolean isOperator ( char character ) {
        if ( ( character == '+' ) | ( character == '-') | ( character == '\u00d7' ) | ( character == '\u00f7') ) {
            return  true ;
        }
        return false ;
    }

    public void onClickOperator ( View view ) {
        inputArea = ( EditText ) findViewById(R.id.editPanel) ;
        char character = ( ( Button ) view ).getText().toString().charAt(0) ;
        OperatorChecker checker = new OperatorChecker(inputArea) ;
        if ( character == '\u00d7' ) { // \u00d7 is unicode character for *
            checker.multiplyOperator();
        }
        else if ( character == '+' ) {
            System.out.println("Plus") ;
            checker.plusOperator() ;
        }
        else if ( character == '-' ) {
            checker.minusOperator();
        }
        else if ( character == '\u00f7' ) { // \u00f7 is unicode character for /
            checker.divideOperator() ;
        }
    }

    // Setting Layout
    public void changeToTrigoMode ( View view ) {
        EditText editText = ( EditText ) findViewById(R.id.editPanel) ;
        Intent intent = new Intent(this,TrigoCalculatorActivity.class) ;
        intent.putExtra("TEXT" , editText.getText().toString()) ;
        intent.putExtra("CURSOR_LOCATION" , editText.getSelectionStart()) ;
        startActivity(intent) ;
    }

    public void backspaceFunction ( View view ) {
        inputArea = ( EditText ) findViewById(R.id.editPanel ) ;
        if ( inputArea.getSelectionStart() != 0 ) {
            StringBuilder builder = new StringBuilder(inputArea.getText().toString()) ;
            int cursorLocation = inputArea.getSelectionStart() ;
            builder.deleteCharAt(cursorLocation-1) ;
            inputArea.setText(builder);
            inputArea.setSelection(cursorLocation-1);
        }
    }

}
