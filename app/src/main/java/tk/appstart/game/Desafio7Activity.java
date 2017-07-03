package tk.appstart.game;

import android.content.Intent;
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

import tk.appstart.start.BancoControle;
import tk.appstart.start.MensagemDialog;
import tk.appstart.start.R;
import tk.appstart.start.ValidaVariavel;

/**
 * Created by Adjailson on 17/06/2017.
 */

public class Desafio7Activity extends AppCompatActivity {

    private Button btPlay;
    private EditText txt_var, valor_var;
    private Spinner condicao;
    private int opSpinner = 0;
    private int valorPasso;
    ValidaVariavel variavel = new ValidaVariavel();
    private MediaPlayer toque1,toque2;

    private Timer tempo;
    private int segundos = 0;
    private int passos = 0;
    private TableLayout tabela;
    private TableRow[] row = new TableRow[4];
    private ImageView[][] objetos = new ImageView[4][6];
    private Random random = new Random();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_desafio7);
        getSupportActionBar().hide();

        tabela = (TableLayout) findViewById(R.id.table_minimundo);
        gerarMinimundo();
        btPlay = (Button) findViewById(R.id.btDesafio7);
        txt_var = (EditText) findViewById(R.id.var_passos);
        valor_var = (EditText) findViewById(R.id.valor_passos);

        String[] operacoes = {"!="," <",">="};

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.estilo_spinner_operadores, operacoes);

        condicao = (Spinner) findViewById(R.id.opera_condicao);
        condicao.setAdapter(adapter);
        // Eventos spinner:
        condicao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView parent, View view, int p, long id) {
                opSpinner = p;
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
                if(!(linha == 2) && coluna != 5) { //Definindo um caminho sem blocos e caixas
                    if (valor == 1) {
                        objetos[linha][coluna].setImageResource(R.drawable.bloco);
                    } else {
                        objetos[linha][coluna].setImageResource(R.drawable.caixa);
                    }
                }
                row[linha].addView(objetos[linha][coluna], -2, -2);
            }
            tabela.addView(row[linha], -2, -2);
        }
        // Colocando os personagens nas posições da matriz
        objetos[2][0].setImageResource(R.drawable.passaro_avance);
        objetos[0][5].setImageResource(R.drawable.porco_malvado);
    }
    private void play(){
        String txtBotao = ((Button)btPlay).getText().toString();
        String txtVar = ((EditText)txt_var).getText().toString();
        String valorVar = ((EditText)valor_var).getText().toString();
        if (txtBotao.equals("Executar")) {
            if(!(txtVar.isEmpty() || valorVar.isEmpty())){
                this.valorPasso = Integer.parseInt(valorVar);
                 if(variavel.isVariavel(txtVar)){
                    if(txtVar.equals("passos")){
                        btPlay.setEnabled(false);
                        this.toque1 = MediaPlayer.create(this,R.raw.start);
                        this.toque1.start();
                        avance(8);
                    }else{
                        MensagemDialog.openMensagem(this,"Opa!","A variável 'passos' não foi declarada.");
                    }
                 }else{
                   MensagemDialog.openMensagem(this,"Opa!","Você declarou à variável errada.");
                 }
            }else{
                MensagemDialog.openMensagem(this, "Opa!","Você deve escrever uma variável e atribuir um valor.");
            }
        } else if (txtBotao.equals("Recarregar")) {
            resetar();
        } else if (txtBotao.equals("Próximo")) {
            BancoControle controle = new BancoControle(this);
            controle.salvarPontuacao(4);
            startActivity(new Intent(this, Desafio8Activity.class));
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
        if(this.valorPasso == 5){// 1ª condição possível
            if(opSpinner == 2){
                if(segundos <= 5){
                    objetos[2][segundos - 1].setImageResource(0);
                    objetos[2][segundos].setImageResource(R.drawable.passaro_avance);
                }else if(segundos == 6){
                    objetos[2][segundos-1].setImageResource(R.drawable.passaro_acima);
                }else if(segundos == 7){
                    objetos[2][segundos-2].setImageResource(0);
                    objetos[1][segundos-2].setImageResource(R.drawable.passaro_acima);
                }else if(segundos == 8){
                    objetos[1][segundos-3].setImageResource(0);
                    objetos[0][segundos-3].setImageResource(R.drawable.passaro_inicial);
                    this.tempo.cancel();
                    this.toque1 = MediaPlayer.create(this,R.raw.vitoria);
                    this.toque1.start();
                    btPlay.setEnabled(true);
                    btPlay.setText("Próximo");
                }
            }else{
                if(segundos <= 5){
                    objetos[2][segundos - 1].setImageResource(0);
                    objetos[2][segundos].setImageResource(R.drawable.passaro_avance);
                }else if(segundos == 6){
                    objetos[2][segundos-1].setImageResource(R.drawable.passaro_inicial);
                }else if(segundos == 7){
                    objetos[2][segundos-2].setImageResource(0);
                    objetos[3][segundos-2].setImageResource(R.drawable.passaro_inicial);
                }else if(segundos == 8){
                    this.tempo.cancel();
                    this.toque2 = MediaPlayer.create(this,R.raw.batida);
                    this.toque2.start();
                    this.toque1 = MediaPlayer.create(this,R.raw.falha);
                    this.toque1.start();
                    btPlay.setEnabled(true);
                    btPlay.setText("Recarregar");
                }
            }
        }else if(this.valorPasso > 5){ // 2ª condição possível
            if(segundos <= 5){
                objetos[2][segundos - 1].setImageResource(0);
                objetos[2][segundos].setImageResource(R.drawable.passaro_avance);
            }else if(segundos == 6){
                this.tempo.cancel();
                this.toque2 = MediaPlayer.create(this,R.raw.batida);
                this.toque2.start();
                this.toque1 = MediaPlayer.create(this,R.raw.falha);
                this.toque1.start();
                btPlay.setEnabled(true);
                btPlay.setText("Recarregar");
            }
        }else if(this.valorPasso < 5){ // 3ª condição possível
            if(opSpinner == 0 || opSpinner == 1){
                if(segundos <= this.valorPasso){
                    objetos[2][segundos - 1].setImageResource(0);
                    objetos[2][segundos].setImageResource(R.drawable.passaro_avance);
                }else if(segundos == this.valorPasso+1){
                    objetos[2][this.valorPasso].setImageResource(R.drawable.passaro_acima);
                }else if(segundos == this.valorPasso+2){
                    this.tempo.cancel();
                    this.toque2 = MediaPlayer.create(this,R.raw.batida);
                    this.toque2.start();
                    this.toque1 = MediaPlayer.create(this,R.raw.falha);
                    this.toque1.start();
                    btPlay.setEnabled(true);
                    btPlay.setText("Recarregar");
                }
            }else{
                if(segundos <= this.valorPasso){
                    objetos[2][segundos - 1].setImageResource(0);
                    objetos[2][segundos].setImageResource(R.drawable.passaro_avance);
                }else if(segundos == this.valorPasso+1){
                    objetos[2][this.valorPasso].setImageResource(R.drawable.passaro_inicial);
                }else if(segundos == this.valorPasso+2){
                    this.tempo.cancel();
                    this.toque2 = MediaPlayer.create(this,R.raw.batida);
                    this.toque2.start();
                    this.toque1 = MediaPlayer.create(this,R.raw.falha);
                    this.toque1.start();
                    btPlay.setEnabled(true);
                    btPlay.setText("Recarregar");
                }
            }
        }
    }
}
