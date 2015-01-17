package com.example.geeo.fastapp.fastapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class VelhaActivity extends ActionBarActivity {

    TextView tv_player1;
    TextView tv_score1;
    String player1Name;
    TextView tv_player2;
    TextView tv_score2;
    String player2Name;
    long player1Score;
    long player2Score;
    int turn;
    final static int PLAYER1 = 1;
    final static int PLAYER2 = 2;
    final static int NOTHING = 0;
    int[][] viewsIds;
    LinearLayout tictacContainer;
    int stateTag = 12;
    int emptyRooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_velha);
        getSupportActionBar().hide();
        tv_player1 = (TextView) findViewById(R.id.tv_player1_name);
        tv_player2 = (TextView) findViewById(R.id.tv_player2_name);
        player1Name = getIntent().getExtras().getString("Player1");
        player2Name = getIntent().getExtras().getString("Player2");
        tv_player1.setText(player1Name);
        tv_player2.setText(player2Name);
        tv_score1 = (TextView) findViewById(R.id.tv_player1_score);
        tv_score2 = (TextView) findViewById(R.id.tv_player2_score);
        tictacContainer = (LinearLayout) findViewById(R.id.tic_tac_container);
        //mapeando os ids das views em um array, pra poder manipular melhor
        //nao achei um jeito melhor de fazer isso:/
        viewsIds = new int[3][3];
        viewsIds[0][0] = R.id.v_00;
        viewsIds[0][1] = R.id.v_01;
        viewsIds[0][2] = R.id.v_02;
        viewsIds[1][0] = R.id.v_10;
        viewsIds[1][1] = R.id.v_11;
        viewsIds[1][2] = R.id.v_12;
        viewsIds[2][0] = R.id.v_20;
        viewsIds[2][1] = R.id.v_21;
        viewsIds[2][2] = R.id.v_22;


        //fazer o jogo da velha ficar em um quadrado
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        ViewGroup.LayoutParams lp = tictacContainer.getLayoutParams();
        lp.height = 90 * width / 100;
        lp.width = lp.height;
        tictacContainer.setLayoutParams(lp);
        //inicia o jogo
        player1Score = 0;
        player2Score = 0;
        turn = PLAYER1;


        restart();

    }

    public void refresh() {//deve ser chamado sempre para atualizar os elementos visuais
        tv_score1.setText("" + player1Score);
        tv_score2.setText("" + player2Score);

    }


    public void restart() {//chamar sempre em uma nova rodada
        View v;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                v = findViewById(viewsIds[i][j]);
                v.setTag(NOTHING);//zera o estado da casa
                ((ImageView) v).setImageResource(0);
            }
        }
        emptyRooms = 9;

        refresh();
    }

    public void played(View v) {

        int tag = (Integer) v.getTag();
        if (tag == NOTHING) {//so é alterada caso a casa ainda nao tenha sido usada
            emptyRooms--;
            switch (turn) {
                case PLAYER1:
                    ((ImageView) v).setImageResource(R.drawable.tic_tac_o);
                    v.setTag(PLAYER1);
                    break;
                case PLAYER2:
                    ((ImageView) v).setImageResource(R.drawable.tic_tac_x);
                    v.setTag(PLAYER2);
                    break;
            }
            boolean win = isEndOfGame(v.getId());

            if (win) {
                if (turn == PLAYER2) player2Score++;
                if (turn == PLAYER1) player1Score++;
                alert(turn);
            } else if (emptyRooms == 0) {
                player1Score++;
                player2Score++;
                alert(NOTHING);
            } else {
                turn = 1 + (turn % 2);//troca de turno

            }
        }

    }

    public boolean isEndOfGame(int viewId) {
        int a = 0, b = 0;//posicao da view
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (viewId == viewsIds[i][j]) {
                    a = i;
                    b = j;
                }
            }
        }

        boolean win = true;
        int tag = (Integer) findViewById(viewsIds[a][b]).getTag();
        //verifica a linha
        for (int k = 0; k < 3; k++) {
            if (tag != (Integer) findViewById(viewsIds[a][k]).getTag()) {
                win = false;
            }

        }
        //verifica a coluna
        if (!win) {
            win = true;
            for (int k = 0; k < 3; k++) {
                if (tag != (Integer) findViewById(viewsIds[k][b]).getTag()) {//coluna
                    win = false;
                }
            }
        }
        //verifica as diagonais

        if (!win && a == b) {
            win = true;
            for (int k = 0; k < 3; k++) {
                if (tag != (Integer) findViewById(viewsIds[k][k]).getTag()) {//coluna
                    win = false;
                }
            }
        }

        if (!win && a == (2 - b)) {
            win = true;
            for (int k = 0; k < 3; k++) {
                if (tag != (Integer) findViewById(viewsIds[k][2 - k]).getTag()) {//coluna
                    win = false;
                }
            }
        }
        return win;
    }

    public void alert(int whatHappened) {
        String title;
        if (whatHappened == NOTHING) {
            title = getString(R.string.draw);
        } else if (whatHappened == PLAYER1) {
            title = player1Name + " " + getString(R.string.has_won);
        } else {
            title = player2Name + " " + getString(R.string.has_won);
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(VelhaActivity.this);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(player1Name + " " + player1Score + " x " + player2Score + " " + player2Name);
        alertDialogBuilder.setPositiveButton(getResources().getString(R.string.action_ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                restart();
            }
        });


        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // cancel the alert box and put a Toast to the user
                finish();

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert
        alertDialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_velha, menu);
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
}

