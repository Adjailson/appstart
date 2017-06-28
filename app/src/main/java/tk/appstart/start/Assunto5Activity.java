package tk.appstart.start;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import tk.appstart.game.Desafio10Activity;

public class Assunto5Activity extends AppCompatActivity {

    private ImageView imgLoop;
    private Timer tempoLoop;
    private int segundoLoop = 0;
    private Drawable drawableLoop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assunto5);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imgLoop = (ImageView) findViewById(R.id.exemplo_loop);

        TextView exemplo = (TextView) findViewById(R.id.exemplo_codigo_loop);
        exemplo.setText(Html.fromHtml(new ArquivoHtml(this).openCodigoHtml("exemplo_codigo_python_loop.html")));

        TextView explicacao = (TextView) findViewById(R.id.explicacao_loop);
        explicacao.setText(Html.fromHtml(new ArquivoHtml(this).openCodigoHtml("explicacao_loop.html")));

        startAnimacao();
    }

    /**
     * Método para startar o tempo em segundos e fazer a chamada
     * do segundo método de animação ou troca de imagens.
     * */
    private void startAnimacao(){
        tempoLoop = new Timer();
        tempoLoop.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        animacaoCondicional();
                    }
                });
            }


        },1000,1000);
    }
    /**
     * Método para fazer a troca de imagem de acordo com a quantidade
     * */
    private void animacaoCondicional(){
        segundoLoop++;
        if (segundoLoop == 17){
            this.segundoLoop = 0;
        }else {
            try {
                drawableLoop = Drawable.createFromStream(getAssets().open("imagens/loop/fluxograma_loop" + segundoLoop + ".png"), null);
                this.imgLoop.setImageDrawable(drawableLoop);
            } catch (Exception e) {
            }
        }
    }

    public void exercicioAssunto5(View view){
        startActivity(new Intent(this, Desafio10Activity.class));
    }


}
