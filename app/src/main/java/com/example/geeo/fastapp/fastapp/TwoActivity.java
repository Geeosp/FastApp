package com.example.geeo.fastapp.fastapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class TwoActivity extends ActionBarActivity {
    EditText et_player1_name;
    EditText et_player2_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        this.et_player1_name = (EditText)findViewById(R.id.et_player1_name);
        this.et_player2_name = (EditText)findViewById(R.id.et_player2_name);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_two, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void openVelha(View v){
        Intent intent = new Intent(this, VelhaActivity.class);
        String player1Name = et_player1_name.getText().toString();
        String player2Name = et_player2_name.getText().toString();
        intent.putExtra("Player1",player1Name );
        intent.putExtra("Player2",player2Name );
        startActivity(intent);
    }
}
