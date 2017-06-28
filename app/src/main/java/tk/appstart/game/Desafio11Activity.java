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
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import tk.appstart.start.BancoControle;
import tk.appstart.start.MensagemDialog;
import tk.appstart.start.R;
import tk.appstart.start.ValidaVariavel;

/**
 * Created by Adjailson on 17/06/2017.
 */

public class Desafio11Activity extends AppCompatActivity {

    private Button btPlay;
    private EditText valor1, valor2;
    private boolean opc1 = false;
    private boolean opc2 = false;
    private double real = 0.0;
    private int inteiro = 0;
    ValidaVariavel var = new ValidaVariavel();

    private Timer tempo;
    private int segundos = 0;
    private int repeticoes = 0;
    private TableLayout tabela;
    private TableRow[] row = new TableRow[4];
    private ImageView[][] objetos = new ImageView[4][6];
    private Random random = new Random();
    private MediaPlayer toque1, toque2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_desafio11);
        getSupportActionBar().hide();

        tabela = (TableLayout) findViewById(R.id.table_minimundo);
        gerarMinimundo();
        btPlay = (Button) findViewById(R.id.btDesafio11);
        valor1 = (EditText) findViewById(R.id.valor1);
        valor2 = (EditText) findViewById(R.id.valor2);

        TextView simbolo = (TextView) findViewById(R.id.operador_especial);
        simbolo.setText(" <= ");

        String[] vars1 = {"False","True"};
        String[] vars2 = {"(3<3)","(2<3)"};

        ArrayAdapter adapter1 = new ArrayAdapter<String>(this,
                R.layout.estilo_spinner_parametro, vars1);
        ArrayAdapter adapter2 = new ArrayAdapter<String>(this,
                R.layout.estilo_spinner_operadores, vars2);

        Spinner sp1 = (Spinner) findViewById(R.id.opera_1);
        sp1.setAdapter(adapter1);
        Spinner sp2 = (Spinner) findViewById(R.id.opera_2);
        sp2.setAdapter(adapter2);

        // Eventos spinner:
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView parent, View view, int p, long id) {
                if(p == 1){
                    opc1 = true;
                }else{
                    opc1 = false;
                }
            }
            public void onNothingSelected(AdapterView parent){}
        });
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView parent, View view, int p, long id) {
                if(p == 1){
                    opc2 = true;
                }else{
                    opc2 = false;
                }
            }
            public void onNothingSelected(AdapterView parent){}
        });

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
                    if(var.isNumero(txt2)){
                        this.repeticoes = Integer.parseInt(txt1);
                        try {// tratamento de divisão por zero em java
                            this.real = (Double.parseDouble(txt1) / Double.parseDouble(txt2));
                        }catch (Exception e){
                            this.real = 1.0d;
                        }
                        try {// tratamento de divisão por zero em java
                            this.inteiro = (int) (Integer.parseInt(txt1) / Integer.parseInt(txt2));
                        }catch (Exception e){
                            this.inteiro = 1;
                        }
                        btPlay.setEnabled(false);
                        this.toque1 = MediaPlayer.create(this,R.raw.start);
                        this.toque1.start();
                        avance();
                    }else{
                        MensagemDialog.openMensagem(this,"Opa!","Você digitou algo errado no segundo campo, ou não está usando um valor inteiro.");
                    }

                }else{
                    MensagemDialog.openMensagem(this,"Opa!","Preencha o segundo campo com um valor.");
                }
            }else{
                MensagemDialog.openMensagem(this,"Opa!","Preencha o primeiro campo com um valor.");
            }
        } else if (txtBotao.equals("Recarregar")) {
            resetar();
        } else if (txtBotao.equals("Próximo")) {
            BancoControle controle = new BancoControle(this);
            controle.salvarPontuacao(5);
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
        if(repeticoes > 0){
            if((real == 3.0) && (inteiro == 3)) {
                // faz 7 passos
                passos(segundos, 7);
            }else if((real > 2.0) && (inteiro == 2)){
                passos(segundos,7);
            }else if((real > 3.0) && (inteiro >= 3)){// caso de perda
                // faz mais de 7 passos
                passos(segundos,8);

            // casos de perdas
            }else if((inteiro < 2) && (inteiro > 0)){
                if((real == 1.0) && (inteiro == 1)){
                    passos(segundos,3);
                }else{
                    passos(segundos,5);
                }
            }else if((real < 1.0) && (inteiro == 0)){
                // faz 3 passos
                passos(segundos,3);
            }else if((real == 2.0) && (inteiro == 2)){
                passos(segundos,5);
            }
        }else {
            this.tempo.cancel();
            this.toque2 = MediaPlayer.create(this,R.raw.falha);
            this.toque2.start();
            this.btPlay.setText("Recarregar");
            this.btPlay.setEnabled(true);
        }
    }

    private void passos(int seg, int passo){
        if(seg == 1) {
            objetos[3][0].setImageResource(0);
            objetos[3][1].setImageResource(R.drawable.passaro_avance);
        }else if(seg == 2){
            objetos[3][1].setImageResource(R.drawable.passaro_acima);
        }else if(seg == 3){
            objetos[3][1].setImageResource(0);
            objetos[2][1].setImageResource(R.drawable.passaro_acima);
        }else if(seg == 4){
            objetos[2][1].setImageResource(R.drawable.passaro_avance);
        }else if(seg == 5){
            objetos[2][1].setImageResource(0);
            objetos[2][2].setImageResource(R.drawable.passaro_avance);
        }else if(seg == 6){
            objetos[2][2].setImageResource(R.drawable.passaro_acima);
            if(passo == 3){
                this.tempo.cancel();
                this.toque2 = MediaPlayer.create(this,R.raw.falha);
                this.toque2.start();
                this.btPlay.setText("Recarregar");
                this.btPlay.setEnabled(true);
            }
        }else if(seg == 7){
            objetos[2][2].setImageResource(0);
            objetos[1][2].setImageResource(R.drawable.passaro_acima);
        }else if(seg == 8){
            objetos[1][2].setImageResource(R.drawable.passaro_avance);
        }else if(seg == 9){
            objetos[1][2].setImageResource(0);
            objetos[1][3].setImageResource(R.drawable.passaro_avance);
        }else if(seg == 10){
            objetos[1][3].setImageResource(R.drawable.passaro_acima);
            if(passo == 5){
                this.tempo.cancel();
                this.toque2 = MediaPlayer.create(this,R.raw.falha);
                this.toque2.start();
                this.btPlay.setText("Recarregar");
                this.btPlay.setEnabled(true);
            }
        }else if(seg == 11){
            objetos[1][3].setImageResource(0);
            objetos[0][3].setImageResource(R.drawable.passaro_acima);
        }else if(seg == 12){
            objetos[0][3].setImageResource(R.drawable.passaro_avance);
        }else if(seg == 13){
            objetos[0][3].setImageResource(0);
            objetos[0][4].setImageResource(R.drawable.passaro_avance);
        }else if(seg == 14){
            this.tempo.cancel();
            if(passo == 7){
                objetos[0][4].setImageResource(R.drawable.passaro_inicial);
                this.toque2 = MediaPlayer.create(this,R.raw.vitoria);
                this.toque2.start();
                this.btPlay.setText("Próximo");
                this.btPlay.setEnabled(true);
            }else if(passo >= 8){
                objetos[0][4].setImageResource(R.drawable.passaro_avance);
                this.toque2 = MediaPlayer.create(this,R.raw.batida);
                this.toque2.start();
                this.toque1 = MediaPlayer.create(this,R.raw.falha);
                this.toque1.start();
                MensagemDialog.openMensagem(this,"Eita!","Você foi além do objetivo.");
                this.btPlay.setText("Recarregar");
                this.btPlay.setEnabled(true);
            }
        }
    }
}
