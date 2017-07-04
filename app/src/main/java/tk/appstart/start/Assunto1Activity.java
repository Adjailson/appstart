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

import tk.appstart.game.Exercicio1Activity;


public class Assunto1Activity extends AppCompatActivity {

    private ImageView imgSoma;
    private ImageView imgVariavel;

    private Timer tempo;
    private int segundoSoma = 0;
    private int segundoVariavel = 0;
    private Drawable drawable1;
    private Drawable drawable2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assunto1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imgSoma = (ImageView) findViewById(R.id.fluxograma_soma);
        imgVariavel = (ImageView) findViewById(R.id.animacao_variavel);

        TextView txtCodigo1 = (TextView) findViewById(R.id.codigo_soma);
        txtCodigo1.setText(Html.fromHtml(new ArquivoHtml(this).openCodigoHtml("exemplo_codigo_python.html")));

        TextView txtCodigo2 = (TextView) findViewById(R.id.codigo_subtracao);
        txtCodigo2.setText(Html.fromHtml(new ArquivoHtml(this).openCodigoHtml("exemplo_informacao_subtracao.html")));

        startAnimacao();
    }

    /**
     * Método para startar o tempo em segundos e fazer a chamada
     * do segundo método de animação ou troca de imagens.
     * */
    private void startAnimacao(){
        tempo = new Timer();
        tempo.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        animacaoSoma();
                        animacaoVariavel();
                    }
                });
            }


        },1000,1000);
    }
    /**
     * Método para fazer a troca de imagem de acordo com a quantidade
     * */
    private void animacaoSoma(){
        segundoSoma++;
        if (segundoSoma == 12){
            this.segundoSoma = 0;
        }else {
            try {
                drawable1 = Drawable.createFromStream(getAssets().open("imagens/soma/fluxo_soma" + segundoSoma + ".png"), null);
                this.imgSoma.setImageDrawable(drawable1);
            } catch (Exception e) {
            }
        }
    }
    /**
     * Método para fazer a troca de imagem de acordo com a quantidade
     * */
    private void animacaoVariavel(){
        segundoVariavel++;
        if (segundoVariavel == 5){
            this.segundoVariavel = 0;
        }else {
            try {
                drawable2 = Drawable.createFromStream(getAssets().open("imagens/variavel/passo" + segundoVariavel + ".png"), null);
                this.imgVariavel.setImageDrawable(drawable2);
            } catch (Exception e) {
            }
        }
    }

    public void btSobreVariaveis(View view){
        startActivity(new Intent(this,VariaveisActivity.class));
    }

    public void exercicioAssunto1(View view){
        startActivity(new Intent(this, Exercicio1Activity.class));
    }
}
