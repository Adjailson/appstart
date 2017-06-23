package tk.appstart.game;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import tk.appstart.start.MensagemDialog;
import tk.appstart.start.R;
import tk.appstart.start.ValidaVariavel;

/**
 * Created by Adjailson on 15/06/2017.
 */

public class Desafio6Activity extends AppCompatActivity {
    private Button btPlay;
    private boolean acao;
    private Spinner sp1;
    private EditText valor;
    ValidaVariavel variavel = new ValidaVariavel();

    private Timer tempo;
    private int segundos = 0;
    private int passos = 0;
    private TableLayout tabela;
    private TableRow[] row = new TableRow[4];
    private ImageView[][] objetos = new ImageView[4][6];
    private Random random = new Random();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_desafio6);
        getSupportActionBar().hide();

        tabela = (TableLayout) findViewById(R.id.table_minimundo);
        gerarMinimundo();
        btPlay = (Button) findViewById(R.id.btDesafio6);
        valor = (EditText) findViewById(R.id.txt_valor);

        String[] vars1 = {"(2**3 == 8)","(2 == 5//2)","(2>3)and(2<3)"};

        ArrayAdapter adapter1 = new ArrayAdapter<String>(this,
                R.layout.estilo_spinner_operadores, vars1);

        sp1 = (Spinner) findViewById(R.id.opera_1);
        sp1.setAdapter(adapter1);

        // Eventos spinner:
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView parent, View view, int p, long id) {
                if(p == 1){
                    acao = true;
                }else{
                    acao = false;
                }
            }
            public void onNothingSelected(AdapterView parent){}
        });
    }
    private void play(){
        String txtBotao = ((Button)btPlay).getText().toString();
        String txtValor1 = ((EditText)valor).getText().toString();
        if(!txtValor1.isEmpty()) {
            if (txtBotao.equals("Executar")) {
                if (variavel.isNumero(txtValor1)) {
                    btPlay.setEnabled(false);
                    new MediaPlayer().create(getApplicationContext(), R.raw.start).start();
                    avance(8);
                } else {
                    MensagemDialog.openMensagem(this, "Opa!", "Os valores da variável estão incorretos.");
                }
            } else if (txtBotao.equals("Recarregar")) {
                resetar();
            } else if (txtBotao.equals("Finalizar")) {
                this.finish();
            }
        }else{
            MensagemDialog.openMensagem(this, "Opa!", "Defina um valor no campo abaixo.");
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
                if(!(linha == 2) && coluna != 5) { //Definindo um caminho sem blocos e caixas
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
        objetos[2][0].setImageResource(R.drawable.passaro_avance);
        objetos[0][5].setImageResource(R.drawable.porco_malvado);
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

    private void caminhar(){
        segundos++;
        if(segundos <= 5){
            objetos[2][segundos-1].setImageResource(0);
            objetos[2][segundos].setImageResource(R.drawable.passaro_avance);
        }else if(segundos == 6){
            if(acao){
                objetos[2][5].setImageResource(R.drawable.passaro_acima);
            }else {
                objetos[2][5].setImageResource(R.drawable.passaro_avance);
            }
        }else if(segundos > 6){
            if(acao == true) {
                int v = Integer.parseInt(((EditText) valor).getText().toString());
                if (segundos == 7) {
                    if ((5 - v) >= 1) {
                        objetos[2][5].setImageResource(0);
                        objetos[1][5].setImageResource(R.drawable.passaro_acima);
                    } else {
                        this.tempo.cancel();
                        new MediaPlayer().create(getApplicationContext(), R.raw.falha).start();
                        btPlay.setText("Recarregar");
                        btPlay.setEnabled(true);
                    }
                } else if (segundos == 8) {
                    if ((5 - v) >= 1 && (5 - v) <= 2) {
                        objetos[1][5].setImageResource(0);
                        objetos[0][5].setImageResource(R.drawable.passaro_inicial);
                        this.tempo.cancel();
                        new MediaPlayer().create(getApplicationContext(), R.raw.vitoria).start();
                        btPlay.setText("Finalizar");
                        btPlay.setEnabled(true);
                    } else {
                        this.tempo.cancel();
                        new MediaPlayer().create(getApplicationContext(), R.raw.falha).start();
                        btPlay.setText("Recarregar");
                        btPlay.setEnabled(true);
                    }
                }
            }else{
                this.tempo.cancel();
                int v = Integer.parseInt(((EditText) valor).getText().toString());
                if((5 - v) >= 1){
                    new MediaPlayer().create(getApplicationContext(), R.raw.batida).start();
                }else{
                    new MediaPlayer().create(getApplicationContext(), R.raw.falha).start();
                }
                btPlay.setText("Recarregar");
                btPlay.setEnabled(true);
            }
        }else{
            this.tempo.cancel();
            new MediaPlayer().create(getApplicationContext(), R.raw.falha).start();
            btPlay.setText("Recarregar");
            btPlay.setEnabled(true);
        }

    }
}