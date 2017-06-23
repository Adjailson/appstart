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

import tk.appstart.start.MensagemDialog;
import tk.appstart.start.R;
import tk.appstart.start.ValidaVariavel;

/**
 * Created by Adjailson on 17/06/2017.
 */

public class Desafio8Activity extends AppCompatActivity {

    private Button btPlay;
    private Spinner obstaculo;
    private int opSpinner = 0;
    private EditText campo1,campo2,campo3,campo4;
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
        setContentView(R.layout.layout_desafio8);
        getSupportActionBar().hide();

        tabela = (TableLayout) findViewById(R.id.table_minimundo);
        gerarMinimundo();
        btPlay = (Button) findViewById(R.id.btDesafio8);
        campo1 = (EditText) findViewById(R.id.campo1);
        campo2 = (EditText) findViewById(R.id.campo2);
        campo3 = (EditText) findViewById(R.id.campo3);
        campo4 = (EditText) findViewById(R.id.campo4);

        String[] operacoes = {"False","True"};

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
                if(!(linha == 3) && coluna != 2) { //Definindo um caminho sem blocos e caixas
                    if (valor == 1) {
                        objetos[linha][coluna].setImageResource(R.drawable.bloco);
                    } else {
                        objetos[linha][coluna].setImageResource(R.drawable.caixa);
                    }
                }
                row[linha].addView(objetos[linha][coluna], 75, 75);
            }
            tabela.addView(row[linha], 450, 675);
        }
        // Colocando os personagens nas posições da matriz
        objetos[3][0].setImageResource(R.drawable.passaro_avance);
        objetos[0][2].setImageResource(R.drawable.porco_malvado);
    }
    private void play() {
        String txtBotao = ((Button) btPlay).getText().toString();
        String txtCampo1 = ((EditText) campo1).getText().toString();
        String txtCampo2 = ((EditText) campo2).getText().toString();
        String txtCampo3 = ((EditText) campo3).getText().toString();
        String txtCampo4 = ((EditText) campo4).getText().toString();
        if(txtBotao.equals("Executar")){
            if(!(txtCampo1.isEmpty())){
                if(variavel.isVariavel(txtCampo1)){
                    if(txtCampo1.equals("passos")){
                        if(!(txtCampo2.isEmpty() || txtCampo3.isEmpty() || txtCampo4.isEmpty())){
                            if(variavel.isVariavel(txtCampo2) && variavel.isVariavel(txtCampo3) &&
                                    variavel.isVariavel(txtCampo4)){
                                if(txtCampo2.equals("passos") && txtCampo3.equals("passos") && txtCampo4.equals("passos")){
                                    btPlay.setEnabled(false);
                                    this.toque1 = MediaPlayer.create(this,R.raw.start);
                                    this.toque1.start();
                                    avance(6);
                                }else{
                                    MensagemDialog.openMensagem(this,"Opa!","Existe variáveis não declaradas.");
                                }
                            }else{
                                MensagemDialog.openMensagem(this,"Opa!","Alguma das variáveis foi declarada de forma errada.");
                            }
                        }else{
                            MensagemDialog.openMensagem(this,"Opa!","Ainda existe parte do código a ser completado.");
                        }
                    }else{
                        MensagemDialog.openMensagem(this,"Opa!","A variável '"+txtCampo1+"' não é reconhecida pelo programa.");
                    }
                }else{
                    MensagemDialog.openMensagem(this,"Opa!","Você declarou uma variável errada ou não escreveu ainda.");
                }
            }else{
                MensagemDialog.openMensagem(this,"Opa!","Preencha o nome da variável na função 'avance()'.");
            }
        } else if (txtBotao.equals("Recarregar")) {
            resetar();
        } else if (txtBotao.equals("Próximo")) {
            startActivity(new Intent(this, Desafio9Activity.class));
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
        if(opSpinner == 1){ // Caso de ocorrer 1
            if(segundos <= 2){
                objetos[3][segundos - 1].setImageResource(0);
                objetos[3][segundos].setImageResource(R.drawable.passaro_avance);
            }else if(segundos == 3){
                objetos[3][segundos-1].setImageResource(R.drawable.passaro_acima);
            }else if(segundos == 4) {
                objetos[segundos-1][2].setImageResource(0);
                objetos[segundos-2][2].setImageResource(R.drawable.passaro_acima);
            }else if(segundos == 5){
                objetos[segundos-3][2].setImageResource(0);
                objetos[segundos-4][2].setImageResource(R.drawable.passaro_acima);
            }else if(segundos == 6){
                objetos[segundos-5][2].setImageResource(0);
                objetos[segundos-6][2].setImageResource(R.drawable.passaro_inicial);
                this.tempo.cancel();
                this.toque1 = MediaPlayer.create(this,R.raw.vitoria);
                this.toque1.start();
                btPlay.setEnabled(true);
                btPlay.setText("Próximo");
            }

        }else if(opSpinner == 0){ // Caso de ocorrer 2
            if(segundos <= 2){
                objetos[3][segundos - 1].setImageResource(0);
                objetos[3][segundos].setImageResource(R.drawable.passaro_avance);
            }else if(segundos == 3){
                objetos[3][segundos-1].setImageResource(R.drawable.passaro_inicial);
            }else if(segundos == 4) {
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
