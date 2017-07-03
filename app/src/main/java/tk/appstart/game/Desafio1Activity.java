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

import tk.appstart.start.BancoControle;
import tk.appstart.start.MensagemDialog;
import tk.appstart.start.R;

public class Desafio1Activity extends AppCompatActivity {

    private String txtVariavel;
    private Button btPlay;
    private int passos = 0;
    private Timer tempo;
    private int segundos = 0;
    private TableLayout tabela;
    private TableRow[] row = new TableRow[4];
    private ImageView[][] objetos = new ImageView[4][6];
    private Random random = new Random();
    private MediaPlayer toque1,toque2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_desafio1);
        getSupportActionBar().hide();

        tabela = (TableLayout) findViewById(R.id.table_minimundo);
        gerarMinimundo();
        btPlay = (Button) findViewById(R.id.btDesafio1);

        Integer[] valores = {0,2,3,4};
        String[] nomes = {"var","passos","volta"};

        ArrayAdapter adapter = new ArrayAdapter<Integer>(this,
                R.layout.estilo_numero_spinner, valores);

        ArrayAdapter adapter2 = new ArrayAdapter<String>(this,
                R.layout.estilo_texto_spinner, nomes);

        Spinner valor = (Spinner) findViewById(R.id.spinner_passo1);
        valor.setAdapter(adapter);
        Spinner variavel = (Spinner) findViewById(R.id.spinner_passo2);
        variavel.setAdapter(adapter2);

        valor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView parent,View view, int p, long id) {
                passos = (Integer) parent.getItemAtPosition(p);
            }
            public void onNothingSelected(AdapterView parent){}
        });
        variavel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView parent,View view, int p, long id) {
                txtVariavel = (String) parent.getItemAtPosition(p);
            }
            public void onNothingSelected(AdapterView parent){}
        });
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
                row[linha].addView(objetos[linha][coluna], -2, -2);// 75, 75
            }
            tabela.addView(row[linha], -2, -2);
            //tabela.addView(row[linha], 450, 675);
        }
        // Colocando os personagens nas posições da matriz
        objetos[2][0].setImageResource(R.drawable.passaro_avance);
        objetos[2][4].setImageResource(R.drawable.porco_malvado);
    }
    private void play(){
        String txtButton = ((Button)btPlay).getText().toString();
        if (txtButton.equals("Executar")){
            if(txtVariavel.equals("passos")){
                btPlay.setEnabled(false);
                this.toque1 = MediaPlayer.create(this,R.raw.start);
                this.toque1.start();
                avance();
            }else{
                MensagemDialog.openMensagem(this,"Opa!","A variável '"+ txtVariavel +"' não é reconhecida pelo programa.");
            }
        }else if (txtButton.equals("Recarregar")){
            resetar();
        }else if (txtButton.equals("Próximo")){
            BancoControle controle = new BancoControle(this);
            controle.salvarPontuacao(2);
            startActivity(new Intent(this, Desafio2Activity.class));
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
    /**
     * Método para acionar passos e objetivos conquistados
     * */
    private void caminhar(){
        segundos++;
        if(this.passos > 0){
            if(segundos <= this.passos){
                if(segundos == 4){
                    objetos[2][segundos - 1].setImageResource(0);
                    objetos[2][segundos].setImageResource(R.drawable.passaro_inicial);
                    this.tempo.cancel();
                    this.toque2 = MediaPlayer.create(this,R.raw.vitoria);
                    this.toque2.start();
                    btPlay.setText("Próximo");
                    btPlay.setEnabled(true);
                }else {
                    objetos[2][segundos - 1].setImageResource(0);
                    objetos[2][segundos].setImageResource(R.drawable.passaro_avance);
                }
            }else{
                this.tempo.cancel();
                this.toque2 = MediaPlayer.create(this,R.raw.falha);
                this.toque2.start();
                btPlay.setText("Recarregar");
                btPlay.setEnabled(true);
            }
        }else{
            this.tempo.cancel();
            this.toque2 = MediaPlayer.create(this,R.raw.falha);
            this.toque2.start();
            btPlay.setText("Recarregar");
            btPlay.setEnabled(true);
        }
    }

}