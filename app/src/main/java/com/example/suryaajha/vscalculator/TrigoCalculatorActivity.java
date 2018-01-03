package com.example.suryaajha.vscalculator;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class TrigoCalculatorActivity extends AppCompatActivity {

    private EditText inputArea = null ;
    private int cursorLocation = 0 ;
    private StringBuilder builder = null ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advancedtrigo_calculator);
        Intent intent = this.getIntent() ;
        if ( intent != null ) {
            EditText inputArea = (EditText)findViewById(R.id.editPanel) ;
            inputArea.setText(intent.getStringExtra("TEXT"));
            inputArea.setSelection(intent.getIntExtra("CURSOR_LOCATION",0));
        }
    }

    public void onClickButtonHandler ( View view ) {
        String text = (String)((Button)view ).getText() ;
        inputArea = (EditText)findViewById(R.id.editPanel) ;
        int cursorLocation = inputArea.getSelectionStart() ;
        cursorLocation = inputArea.getSelectionStart() ;
        builder = new StringBuilder ( inputArea.getText().toString() );
        builder.append(text) ;
        inputArea.setText(builder);
        inputArea.setSelection( cursorLocation + text.length());
    }

    public void hideSoftKeyBoard ( View view ) {
        inputArea = (EditText)findViewById(R.id.editPanel) ;
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE) ;
        if ( inputMethodManager != null )
        inputMethodManager.hideSoftInputFromWindow(inputArea.getWindowToken(),0) ;
    }

    public void changeToBasicMode ( View view ) {
        EditText editText = ( EditText ) findViewById(R.id.editPanel) ;
        Intent intent = new Intent(this,BasicCalculatorActivity.class) ;
        intent.putExtra("TEXT" , editText.getText().toString()) ;
        intent.putExtra("CURSOR_LOCATION" , editText.getSelectionStart()) ;
        startActivity(intent) ;
    }

    public void changeToExtrasMode ( View view ) {
        EditText editText = ( EditText ) findViewById(R.id.editPanel) ;
        Intent intent = new Intent(this,ExtrasCalculatorActivity.class) ;
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
