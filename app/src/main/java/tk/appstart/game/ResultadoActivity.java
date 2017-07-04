package tk.appstart.game;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import tk.appstart.start.BancoControle;
import tk.appstart.start.R;

/**
 * Created by Adjailson on 03/07/2017.
 */

public class ResultadoActivity extends AppCompatActivity {

    private Timer tempo;
    private int acertos = 0;
    private int contador = 0;

    private TextView msg;
    private TextView porcentagem;
    private ImageView sleepImg;
    private Drawable drawable;
    BancoControle controle = new BancoControle(this);

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_resultado);
        getSupportActionBar().hide();

        msg = (TextView) findViewById(R.id.msg_fedback);
        porcentagem = (TextView) findViewById(R.id.porcentagem);
        sleepImg = (ImageView) findViewById(R.id.sleepImg);

        this.acertos = getPontos(1);

        startResultados();

    }

    private int getPontos(int exercicio){
        return Integer.parseInt(controle.getValorColuna(exercicio));
    }

    public void startResultados(){
        try {
            String porcento = ""+((acertos*100)/6);// Seis exercícios teoricos
            if(porcento.length() > 3){
                porcento = porcento.substring(0,4);
            }
            this.porcentagem.setText(porcento+" Pontos");
            if(((acertos*100)/6) >= 70){
                msg.setText("Excelente!");
                ((TextView) msg).setTextColor(Color.parseColor("#1a8018"));
            }else if((((acertos*100)/6) < 70) && (((acertos*100)/6) >= 50)){
                msg.setText("Foi bom!");
                ((TextView) msg).setTextColor(Color.parseColor("#1a8018"));
            }else{
                msg.setText("Não desista, você é capaz.");
                ((TextView) msg).setTextColor(Color.parseColor("#f93f48"));
            }
        }catch (Exception e){
            this.porcentagem.setText("0 Ponto");
            msg.setText("Não desista, você é capaz.");
            ((TextView) msg).setTextColor(Color.parseColor("#f93f48"));
        }
        animacao();
    }

    private void animacao(){
        tempo = new Timer();
        tempo.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        processar();
                    }
                });
            }
        },100,100);
    }

    public void processar(){
        contador++;
        if(contador <= 10){
            try {
                drawable = Drawable.createFromStream(getAssets().open("imagens/sleep/sleep" + contador + ".png"), null);
                this.sleepImg.setImageDrawable(drawable);
            } catch (Exception e) {
            }
        }else if(contador > 10){
            this.tempo.cancel();
            controle.resetarTudo();
        }
    }

}
