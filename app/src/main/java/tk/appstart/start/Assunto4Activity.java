package tk.appstart.start;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import tk.appstart.game.Desafio7Activity;

public class Assunto4Activity extends AppCompatActivity {

    private ImageView imgCondicional;
    private Timer tempoCondicional;
    private int segundoCodicional = 0;
    private Drawable drawableCondicional;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assunto4);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imgCondicional = (ImageView) findViewById(R.id.fluxograma_decisao);

        TextView txtCodigo = (TextView) findViewById(R.id.codigo_if_else);
        txtCodigo.setText(Html.fromHtml(new ArquivoHtml(this).openCodigoHtml("exemplo_codigo_condicional.html")));

        TextView exemplo = (TextView) findViewById(R.id.exemplo_codigo_elif);
        exemplo.setText(Html.fromHtml(new ArquivoHtml(this).openCodigoHtml("exemplo_codigo_elif.html")));

        startAnimacao();

    }

    /**
     * Método para startar o tempo em segundos e fazer a chamada
     * do segundo método de animação ou troca de imagens.
     * */
    private void startAnimacao(){
        tempoCondicional = new Timer();
        tempoCondicional.scheduleAtFixedRate(new TimerTask() {
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
        segundoCodicional++;
        if (segundoCodicional == 18){
            this.segundoCodicional = 0;
        }else {
            try {
                drawableCondicional = Drawable.createFromStream(getAssets().open("imagens/condicional/fluxograma_decisao" + segundoCodicional + ".png"), null);
                this.imgCondicional.setImageDrawable(drawableCondicional);
            } catch (Exception e) {
            }
        }
    }

    public void exercicioAssunto4(View view){
        startActivity(new Intent(this, Desafio7Activity.class));
    }

}
