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

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import tk.appstart.start.BancoControle;
import tk.appstart.start.MensagemDialog;
import tk.appstart.start.R;
import tk.appstart.start.ValidaVariavel;


public class Desafio3Activity extends AppCompatActivity {

    private Button btPlay;
    private EditText nova_var;
    private EditText chamada_var;
    ValidaVariavel variavel = new ValidaVariavel();

    private Timer tempo;
    private int segundos = 0;
    private int passos = 0;
    private TableLayout tabela;
    private TableRow[] row = new TableRow[4];
    private ImageView[][] objetos = new ImageView[4][6];
    private Random random = new Random();
    private MediaPlayer toque1,toque2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_desafio3);
        getSupportActionBar().hide();

        tabela = (TableLayout) findViewById(R.id.table_minimundo);
        gerarMinimundo();
        btPlay = (Button) findViewById(R.id.btDesafio3);

        nova_var = (EditText) findViewById(R.id.nome_variavel1);
        chamada_var = (EditText) findViewById(R.id.nome_variavel2);
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
            tabela.addView(row[linha], 450, 675);
        }
        // Colocando os personagens nas posições da matriz
        objetos[2][0].setImageResource(R.drawable.passaro_avance);
        objetos[2][4].setImageResource(R.drawable.porco_malvado);
    }
    private void play(){
        String txtButton = ((Button)btPlay).getText().toString();
        String txt1 = ((EditText)nova_var).getText().toString();
        String txt2 = ((EditText)chamada_var).getText().toString();
        if(txtButton.equals("Executar")){
            if(!(txt1.isEmpty())){
                if(variavel.isVariavel(txt1)){
                    if(!(txt2.isEmpty())){
                        if(txt1.equals(txt2)){
                            btPlay.setEnabled(false);
                            this.toque1 = MediaPlayer.create(this,R.raw.start);
                            this.toque1.start();
                            avance();
                        }else{
                            MensagemDialog.openMensagem(this,"Opa!","A variável passada para a função não foi declarada.");
                        }
                    }else{
                        MensagemDialog.openMensagem(this,"Opa!","Você precisa fazer a chamada da variável declarada na função 'avance()'.");
                    }
                }else{
                    MensagemDialog.openMensagem(this,"Opa!","Você escreveu alguma variável com caractere especial, número no início" +
                            " da variável ou contém símbolos.");
                }
            }else{
                MensagemDialog.openMensagem(this,"Opa!","Você precisa declarar o nome de uma variável para armazenar o valor 4.");
            }
        }else if (txtButton.equals("Recarregar")){
            resetar();
        }else if (txtButton.equals("Finalizar")){
            BancoControle controle = new BancoControle(this);
            controle.salvarPontuacao(2,3);
            this.finish();
        }else{
            MensagemDialog.openMensagem(this,"Atenção","Crie uma variável e faça a chamada da mesma variável" +
                    " na função 'avance().'");
        }
    }

    private void resetar(){
        this.segundos = 0;
        this.tempo.cancel();
        this.tabela.removeAllViews();
        btPlay.setText("Executar");
        gerarMinimundo();
    }

    /**
     * Método de animação do passaro em movimento
     * */
    private void avance(){
        this.passos = 4;
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
        if(segundos <= this.passos){
            objetos[2][segundos-1].setImageResource(0);
            objetos[2][segundos].setImageResource(R.drawable.passaro_avance);
        }else if(segundos == 5){
            objetos[2][segundos-2].setImageResource(0);
            objetos[2][segundos-1].setImageResource(R.drawable.passaro_inicial);
            this.tempo.cancel();
            this.toque2 = MediaPlayer.create(this,R.raw.vitoria);
            this.toque2.start();
            btPlay.setText("Finalizar");
            btPlay.setEnabled(true);
        }
    }
}