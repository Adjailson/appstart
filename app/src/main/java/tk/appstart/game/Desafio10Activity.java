package tk.appstart.game;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import tk.appstart.start.MensagemDialog;
import tk.appstart.start.R;
import tk.appstart.start.ValidaVariavel;

/**
 * Created by Adjailson on 17/06/2017.
 */

public class Desafio10Activity extends AppCompatActivity {

    private Button btPlay;
    private EditText valor1, valor2;
    ValidaVariavel variavel = new ValidaVariavel();

    private Timer tempo;
    private int segundos = 0;
    private int passos = 0;
    private int repeticoes = 0;
    private TableLayout tabela;
    private TableRow[] row = new TableRow[4];
    private ImageView[][] objetos = new ImageView[4][6];
    private Random random = new Random();
    private MediaPlayer toque1, toque2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_desafio10);
        getSupportActionBar().hide();

        tabela = (TableLayout) findViewById(R.id.table_minimundo);
        gerarMinimundo();
        btPlay = (Button) findViewById(R.id.btDesafio10);
        valor1 = (EditText) findViewById(R.id.valor1);
        valor2 = (EditText) findViewById(R.id.valor2);

        TextView simbolo = (TextView) findViewById(R.id.operador_especial);
        simbolo.setText(" <= ");

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
                if (valor == 1) {
                    objetos[linha][coluna].setImageResource(R.drawable.bloco);
                } else {
                    objetos[linha][coluna].setImageResource(R.drawable.caixa);
                }
                row[linha].addView(objetos[linha][coluna], 75, 75);
            }
            tabela.addView(row[linha], 450, 675);
        }
        objetos[3][1].setImageResource(0);
        objetos[2][1].setImageResource(0);
        objetos[2][2].setImageResource(0);
        objetos[1][2].setImageResource(0);
        objetos[1][3].setImageResource(0);
        objetos[0][3].setImageResource(0);
        // Colocando os personagens nas posições da matriz
        objetos[3][0].setImageResource(R.drawable.passaro_avance);
        objetos[0][4].setImageResource(R.drawable.porco_malvado);
    }
    private void play() {
        String txtBotao = ((Button) btPlay).getText().toString();
        String txt1 = ((EditText) valor1).getText().toString();
        String txt2 = ((EditText) valor2).getText().toString();
        if(txtBotao.equals("Executar")){
            if(!(txt1.isEmpty())){
                if(!(txt2.isEmpty())){
                    this.repeticoes =(int)(Integer.parseInt(txt1) / Integer.parseInt(txt2));
                    btPlay.setEnabled(false);
                    this.toque1 = MediaPlayer.create(this,R.raw.start);
                    this.toque1.start();
                    //avance();
                }else{
                    MensagemDialog.openMensagem(this,"Opa!","Preencha o segundo campo com um valor.");
                }
            }else{
                MensagemDialog.openMensagem(this,"Opa!","Preencha o primeiro campo com um valor.");
            }
        } else if (txtBotao.equals("Recarregar")) {
            resetar();
        } else if (txtBotao.equals("Próximo")) {
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
     * Método de animação do passaro em movimento
     * */
    private void avance(){
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

    private void caminhar() {
        segundos++;

    }
}
