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

public class Desafio9Activity extends AppCompatActivity {

    private Button btPlay;
    private Spinner obstaculo;
    private int opSpinner = 0;
    private int valorPassos = 0;
    private EditText numero_passos,variavel_funcao;
    ValidaVariavel variavel = new ValidaVariavel();

    private Timer tempo;
    private int segundos = 0;
    private int passos = 0;
    private TableLayout tabela;
    private TableRow[] row = new TableRow[4];
    private ImageView[][] objetos = new ImageView[4][6];
    private Random random = new Random();
    private MediaPlayer toque1,toque2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_desafio9);
        getSupportActionBar().hide();

        tabela = (TableLayout) findViewById(R.id.table_minimundo);
        gerarMinimundo();
        btPlay = (Button) findViewById(R.id.btDesafio8);
        numero_passos = (EditText) findViewById(R.id.numero_passos);
        variavel_funcao = (EditText) findViewById(R.id.var_passos);

        String[] operacoes = {"True","False"};

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.estilo_spinner_parametro, operacoes);

        obstaculo = (Spinner) findViewById(R.id.var_obstaculo);
        obstaculo.setAdapter(adapter);
        // Eventos spinner:
        obstaculo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                if(!(linha == 2)) { //Definindo um caminho sem blocos e caixas
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
        objetos[3][5].setImageResource(R.drawable.porco_malvado);
    }
    private void play() {
        String txtBotao = ((Button) btPlay).getText().toString();
        String txtPassos = ((EditText) numero_passos).getText().toString();
        String txtVar = ((EditText) variavel_funcao).getText().toString();
        if(txtBotao.equals("Executar")){
            if(!txtPassos.isEmpty()){
                this.valorPassos = Integer.parseInt(txtPassos);
                if(variavel.isNumero(txtPassos)){
                    if(!txtVar.isEmpty()){
                        if(variavel.isVariavel(txtVar) && txtVar.equals("passos")){
                            btPlay.setEnabled(false);
                            this.toque1 = MediaPlayer.create(this,R.raw.start);
                            this.toque1.start();
                            avance(7);
                        }else{
                            MensagemDialog.openMensagem(this,"Opa!","Variável inválida ou não existe no programa.");
                        }
                    }else{
                        MensagemDialog.openMensagem(this,"Opa!","Existe trechos de códigos à ser descrito.");
                    }
                }else{
                    MensagemDialog.openMensagem(this,"Opa!","Digite um valor número do tipo inteiro.");
                }
            }else{
                MensagemDialog.openMensagem(this,"Opa!","Declare um valor para a variável 'passos'.");
            }
        } else if (txtBotao.equals("Recarregar")) {
            resetar();
        } else if (txtBotao.equals("Finalizar")) {
            BancoControle controle = new BancoControle(this);
            controle.salvarPontuacao(4);
            startActivity(new Intent(this,ResultadoActivity.class));
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

    private void caminhar() {
        segundos++;
        if(this.valorPassos == 5){ // condição possível
            if(opSpinner == 0){
                if(segundos == 1){
                    objetos[2][segundos-1].setImageResource(R.drawable.passaro_acima);
                }else if(segundos == 2){
                    this.tempo.cancel();
                    this.toque2 = MediaPlayer.create(this,R.raw.batida);
                    this.toque2.start();
                    this.toque1 = MediaPlayer.create(this,R.raw.falha);
                    this.toque1.start();
                    btPlay.setEnabled(true);
                    btPlay.setText("Recarregar");
                }
            }else if(opSpinner == 1){
                if(segundos <= 5){
                    objetos[2][segundos-1].setImageResource(0);
                    objetos[2][segundos].setImageResource(R.drawable.passaro_avance);
                }else if(segundos == 6){
                    objetos[2][segundos-1].setImageResource(R.drawable.passaro_inicial);
                }else if(segundos == 7){
                    objetos[2][segundos-2].setImageResource(0);
                    objetos[3][segundos-2].setImageResource(R.drawable.passaro_inicial);
                    this.tempo.cancel();
                    this.toque1 = MediaPlayer.create(this,R.raw.vitoria);
                    this.toque1.start();
                    btPlay.setEnabled(true);
                    btPlay.setText("Finalizar");
                }
            }
        }else if(this.valorPassos > 5){ // condição possível
            if(opSpinner == 0){
                if(segundos == 1){
                    objetos[2][segundos-1].setImageResource(R.drawable.passaro_acima);
                }else if(segundos == 2){
                    this.tempo.cancel();
                    this.toque2 = MediaPlayer.create(this,R.raw.batida);
                    this.toque2.start();
                    this.toque1 = MediaPlayer.create(this,R.raw.falha);
                    this.toque1.start();
                    btPlay.setEnabled(true);
                    btPlay.setText("Recarregar");
                }
            }else if(opSpinner == 1){
                if(segundos <= 5){
                    objetos[2][segundos-1].setImageResource(0);
                    objetos[2][segundos].setImageResource(R.drawable.passaro_avance);
                }else if(segundos == 6){
                    objetos[2][segundos-1].setImageResource(R.drawable.passaro_avance);
                }else if(segundos == 7){
                    this.tempo.cancel();
                    this.toque2 = MediaPlayer.create(this,R.raw.batida);
                    this.toque2.start();
                    this.toque1 = MediaPlayer.create(this,R.raw.falha);
                    this.toque1.start();
                    btPlay.setEnabled(true);
                    btPlay.setText("Recarregar");
                }
            }
        }else if(this.valorPassos < 5){ // condição possível
            if(opSpinner == 0 || opSpinner == 1){
                if(segundos == 1){
                    objetos[2][segundos-1].setImageResource(R.drawable.passaro_acima);
                }else if(segundos == 2){
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
