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

import tk.appstart.start.MensagemDialog;
import tk.appstart.start.R;

/**
 * Created by Adjailson on 24/05/2017.
 */

public class Desafio2Activity extends AppCompatActivity {

    private int passos_x = 0;
    private int passos_y = 0;
    private String variavelPassos, variavelFuncao;
    private Button btPlay;

    private Timer tempo;
    private int segundos = 0;
    private int passos = 0;
    private TableLayout tabela;
    private TableRow[] row = new TableRow[4];
    private ImageView[][] objetos = new ImageView[4][6];
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_desafio2);
        getSupportActionBar().hide();

        tabela = (TableLayout) findViewById(R.id.table_minimundo);
        gerarMinimundo();
        btPlay = (Button) findViewById(R.id.btDesafio2);

        Integer[] valoresX = {1,3,2};
        Integer[] valoresY = {2,0,1};
        String[] vars1 = {"x","y","x+y"};
        String[] vars2 = {"volta","passos","para"};

        ArrayAdapter adapter1 = new ArrayAdapter<Integer>(this,
                R.layout.estilo_numero_spinner, valoresX);
        ArrayAdapter adapter2 = new ArrayAdapter<Integer>(this,
                R.layout.estilo_numero_spinner, valoresY);

        ArrayAdapter adapter3 = new ArrayAdapter<String>(this,
                R.layout.estilo_texto_spinner, vars1);
        ArrayAdapter adapter4 = new ArrayAdapter<String>(this,
                R.layout.estilo_texto_spinner, vars2);

        Spinner valorX = (Spinner) findViewById(R.id.spinner_passo0);
        valorX.setAdapter(adapter1);
        Spinner valorY = (Spinner) findViewById(R.id.spinner_passo1);
        valorY.setAdapter(adapter2);

        Spinner variavel = (Spinner) findViewById(R.id.spinner_passo2);
        variavel.setAdapter(adapter3);
        Spinner variavel2 = (Spinner) findViewById(R.id.spinner_passo3);
        variavel2.setAdapter(adapter4);

        valorX.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView parent, View view, int p, long id) {
                passos_x = (Integer) parent.getItemAtPosition(p);
            }
            public void onNothingSelected(AdapterView parent){}
        });
        valorY.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView parent, View view, int p, long id) {
                passos_y = (Integer) parent.getItemAtPosition(p);
            }
            public void onNothingSelected(AdapterView parent){}
        });
        variavel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView parent,View view, int p2, long id) {
                variavelPassos = (String) parent.getItemAtPosition(p2);
            }
            public void onNothingSelected(AdapterView parent){}
        });
        variavel2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView parent,View view, int p, long id) {
                variavelFuncao = (String) parent.getItemAtPosition(p);
            }
            public void onNothingSelected(AdapterView parent){}
        });
    }

    private void play(){
        String txtButton = ((Button)btPlay).getText().toString();
        if (variavelPassos.equals("x")){
            this.passos = passos_x;
        }else if (variavelPassos.equals("y")){
            this.passos = passos_y;
        }else if(variavelPassos.equals("x+y")){
            this.passos = (passos_x + passos_y);
        }
        if (txtButton.equals("Executar")){
            if(variavelFuncao.equals("passos")){
                btPlay.setEnabled(false);
                new MediaPlayer().create(getApplicationContext(), R.raw.start).start();
                avance();
            }else{
                MensagemDialog.openMensagem(this,"Opa!","A variável '"+ variavelFuncao +"' não é reconhecida pelo programa.");
            }

        }else if (txtButton.equals("Recarregar")){
            resetar();
        }else if (txtButton.equals("Próximo")){
            startActivity(new Intent(this, Desafio3Activity.class));
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
        objetos[2][0].setImageResource(R.drawable.passaro_avance);
        objetos[2][5].setImageResource(R.drawable.porco_malvado);
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
                if(segundos == 5){
                    objetos[2][segundos - 1].setImageResource(0);
                    objetos[2][segundos].setImageResource(R.drawable.passaro_inicial);
                    this.tempo.cancel();
                    new MediaPlayer().create(getApplicationContext(), R.raw.vitoria).start();
                    btPlay.setText("Próximo");
                    btPlay.setEnabled(true);
                }else {
                    objetos[2][segundos - 1].setImageResource(0);
                    objetos[2][segundos].setImageResource(R.drawable.passaro_avance);
                }
            }else{
                this.tempo.cancel();
                new MediaPlayer().create(getApplicationContext(), R.raw.falha).start();
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
