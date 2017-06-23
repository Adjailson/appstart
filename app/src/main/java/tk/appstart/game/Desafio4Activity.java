package tk.appstart.game;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import tk.appstart.start.R;

/**
 * Created by Adjailson on 07/06/2017.
 */

public class Desafio4Activity extends AppCompatActivity {

    private Button btPlay;
    private boolean acaoA, acaoB;
    private Spinner sp1,sp2;

    private Timer tempo;
    private int segundos = 0;
    private int passos = 0;
    private TableLayout tabela;
    private TableRow[] row = new TableRow[4];
    private ImageView[][] objetos = new ImageView[4][6];
    private Random random = new Random();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_desafio4);
        getSupportActionBar().hide();

        tabela = (TableLayout) findViewById(R.id.table_minimundo);
        gerarMinimundo();
        btPlay = (Button) findViewById(R.id.btDesafio4);

        String[] vars1 = {"(2 >= 3)","(10 < 5)","2==(10//4)"};
        String[] vars2 = {"(2 == 2)and(3 > 4)","(4 != 2)or(4 == 2)","not(2==2)"};
        ArrayAdapter adapter1 = new ArrayAdapter<String>(this,
                R.layout.estilo_spinner_operadores, vars1);
        ArrayAdapter adapter2 = new ArrayAdapter<String>(this,
                R.layout.estilo_spinner_operadores, vars2);

        sp1 = (Spinner) findViewById(R.id.opera_1);
        sp1.setAdapter(adapter1);
        sp2 = (Spinner) findViewById(R.id.opera_2);
        sp2.setAdapter(adapter2);
        // Eventos spinner:
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView parent, View view, int p, long id) {
                if(p == 2){
                    acaoA = true;
                }else{
                    acaoA = false;
                }
            }
            public void onNothingSelected(AdapterView parent){}
        });
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView parent, View view, int p, long id) {
                if(p == 1){
                    acaoB = true;
                }else{
                    acaoB = false;
                }
            }
            public void onNothingSelected(AdapterView parent){}
        });
    }

    private void play(){
        String txtBotao = ((Button)btPlay).getText().toString();
        if(txtBotao.equals("Executar")){
            btPlay.setEnabled(false);
            new MediaPlayer().create(getApplicationContext(), R.raw.start).start();
            avance(7);
        }else if (txtBotao.equals("Recarregar")){
            resetar();
        }else if (txtBotao.equals("Próximo")){
            startActivity(new Intent(this, Desafio5Activity.class));
            this.finish();
        }
    }

    private void resetar(){
        this.segundos = 0;
        this.tempo.cancel();
        this.tabela.removeAllViews();
        btPlay.setText("Executar");
        gerarMinimundo();
    }
    public void btVerificar(View view) {
        play();
    }
    /**
     * Matriz do minimundo:
     * 0 0 0 0 0 0
     * 0 0 0 0 0 0
     * 0 0 0 0 0 0
     * 0 0 0 0 0 0
     * Matriz 4x6
     * [0][0] [0][1] [0][2] [0][3] [0][4] [0][5]
     * [1][0] [1][1] [1][2] [1][3] [1][4] [1][5]
     * [2][0] [2][1] [2][2] [2][3] [2][4] [2][5]
     * [3][0] [3][1] [3][2] [3][3] [3][4] [3][5]
     * */
    private void gerarMinimundo(){
        int valor = 0;
        for (int linha = 0; linha < row.length; linha++) {
            row[linha] = new TableRow(this);
            for (int coluna = 0; coluna < objetos.length + 2; coluna++) {
                objetos[linha][coluna] = new ImageView(this);
                valor = random.nextInt(2);
                if(!(linha == 2)) { //Definindo um caminho sem blocos e caixas
                    if (valor == 1) {
                        objetos[linha][coluna].setImageResource(R.drawable.bloco);
                    } else {
                        objetos[linha][coluna].setImageResource(R.drawable.caixa);
                    }
                }
                row[linha].addView(objetos[linha][coluna], 75, 75);
            }
            tabela.addView(row[linha], 450, TableRow.LayoutParams.WRAP_CONTENT);
        }
        // Colocando os personagens nas posições da matriz
        objetos[1][1].setImageResource(0);
        objetos[1][0].setImageResource(R.drawable.passaro_avance);
        objetos[2][4].setImageResource(R.drawable.porco_malvado);
    }

    /**
     * Método de animação do passaro em movimento
     * */
    private void avance(int passo){
        this.passos = passo;
        tempo = new Timer();
        tempo.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        caminhar();
                    }
                });
            }
        },1000,1000);
    }
    /**
     * Método para acionar passos e objetivos conquistados
     * */
    private void caminhar(){
        segundos++;
        if(acaoA == true && acaoB == true){
            if(segundos == 1){
                objetos[1][0].setImageResource(R.drawable.passaro_inicial);
            }else if(segundos == 2){
                objetos[1][0].setImageResource(0);
            }else if(segundos == 3){
                objetos[2][0].setImageResource(R.drawable.passaro_avance);
            }else if(segundos > 3){
                objetos[2][segundos-4].setImageResource(0);
                objetos[2][segundos-3].setImageResource(R.drawable.passaro_avance);
            }
        }else if((acaoA == false && acaoB == false) || (acaoA == false && acaoB == true)){
            if(segundos == 1){
                objetos[1][0].setImageResource(0);
            }else if(segundos == 2){
                objetos[1][1].setImageResource(R.drawable.passaro_avance);
            }else if(segundos == 3){
                new MediaPlayer().create(getApplicationContext(), R.raw.batida).start();
            }else if(segundos > 3){
                this.tempo.cancel();
                new MediaPlayer().create(getApplicationContext(), R.raw.falha).start();
                btPlay.setText("Recarregar");
                btPlay.setEnabled(true);
            }

        }else if(acaoA == true && acaoB == false){
            if(segundos == 1){
                objetos[1][0].setImageResource(R.drawable.passaro_inicial);
            }else if(segundos == 2){
                objetos[1][0].setImageResource(0);
            }else if(segundos == 3){
                objetos[2][0].setImageResource(R.drawable.passaro_inicial);
                new MediaPlayer().create(getApplicationContext(), R.raw.batida).start();
            }else if(segundos > 3){
                this.tempo.cancel();
                new MediaPlayer().create(getApplicationContext(), R.raw.falha).start();
                btPlay.setText("Recarregar");
                btPlay.setEnabled(true);
            }
        }
        if(segundos == passos){
            tempo.cancel();
            if(segundos == passos && passos == 7){
                new MediaPlayer().create(getApplicationContext(), R.raw.vitoria).start();
                btPlay.setText("Próximo");
                btPlay.setEnabled(true);
            }else{
                new MediaPlayer().create(getApplicationContext(), R.raw.falha).start();
                btPlay.setText("Recarregar");
                btPlay.setEnabled(true);
            }
        }
    }
}
