package com.example.pz_1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final int ROWS_NUMBER = 4;

    private TableLayout numbersTableLayout;
    private TextView textViewDescription;
    private Button buttonAction;
    private CheckBox checkBoxEraseAll;
    private Map<Integer, String> cachedCellColours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pz_1);

        cachedCellColours = new HashMap<>();

        buttonAction = findViewById(R.id.buttonAction);
        buttonAction.setOnClickListener(this);

        textViewDescription = findViewById(R.id.textViewDescription);
        if(savedInstanceState!=null)
            textViewDescription.setText(savedInstanceState.getString("textDescription"));

        checkBoxEraseAll = findViewById(R.id.checkBoxEraseAll);

        numbersTableLayout = findViewById(R.id.tableLayoutNumbers);
        initializeTableLayout(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Set<Integer> keys = cachedCellColours.keySet();
        for(int key:keys){
            outState.putString(String.valueOf(key), cachedCellColours.get(key));
        }

        outState.putString("textDescription", textViewDescription.getText().toString());
    }

    @Override
    public void onClick(View v) {

        if(checkBoxEraseAll.isChecked()) {
            eraseTableCells();
            textViewDescription.setText(getString(R.string.text_description));
        }else {
            changeBackgroundCellColour();
        }
    }

    private void initializeTableLayout(Bundle savedInstanceState){

        TableRow tableRow = null;
        for(int i = 0; i < ROWS_NUMBER * ROWS_NUMBER; i++){

            if(tableRow == null)
                tableRow = new TableRow(this);

            TextView textView = new TextView(this);
            textView.setText(String.valueOf(i + 1));
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setPadding(0, 15, 0, 15);

            if(savedInstanceState != null && savedInstanceState.containsKey(String.valueOf(i + 1))) {
                String color = savedInstanceState.getString(String.valueOf(i + 1));
                textView.setBackgroundColor(Color.parseColor(color));
                cachedCellColours.put(i + 1, color);
            }

            tableRow.addView(textView);

            if((i + 1) % ROWS_NUMBER == 0){
                numbersTableLayout.addView(tableRow);
                tableRow = null;
            }
        }
    }

    private void changeBackgroundCellColour(){
        int rowIndex = getRandomNumberInRange(0, ROWS_NUMBER);
        int colIndex = getRandomNumberInRange(0, ROWS_NUMBER);

        TextView view = (TextView) getTableCell(rowIndex, colIndex);

        String newColor = getRandomColor();
        view.setBackgroundColor(Color.parseColor(newColor));

        int cellNumber = Integer.parseInt(view.getText().toString());
        cachedCellColours.put(cellNumber, newColor);

        String changingText = String.format(Locale.getDefault(),"Changed color of cell number --> %d", Integer.parseInt(view.getText().toString()));
        textViewDescription.setText(changingText);
    }

    private void eraseTableCells(){

        for (int i = 0; i < ROWS_NUMBER; i++){
            for(int j = 0; j < ROWS_NUMBER; j++){
                TextView view = (TextView) getTableCell(i, j);
                view.setBackgroundColor(getResources().getColor(R.color.white));
            }
        }
        cachedCellColours.clear();

    }

    private String getRandomColor(){

        String[] colors = getResources().getStringArray(R.array.randomColors);
        int randomIndex = getRandomNumberInRange(0, colors.length - 1);

        return colors[randomIndex];
    }

    @Nullable
    private View getTableCell(int rowNumber, int colNumber){

        if(rowNumber >= numbersTableLayout.getChildCount() || rowNumber < 0)
            return null;
        TableRow row = (TableRow) numbersTableLayout.getChildAt(rowNumber);

        if(colNumber >= row.getChildCount() || colNumber < 0)
            return null;

        return row.getChildAt(colNumber);
    }

    private int getRandomNumberInRange(int min, int max){
        return (int)(Math.random() * (max - min) + min);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}