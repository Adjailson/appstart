package tk.appstart.start;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import tk.appstart.game.Desafio1Activity;

public class Assunto2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assunto2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Exemplo de código explicando diferenças em tipo de dados
        TextView exemplo1 = (TextView) findViewById(R.id.exemplo_codigo1);
        exemplo1.setText(Html.fromHtml(new ArquivoHtml(this).openCodigoHtml("exemplo_codigo1.html")));

        TextView exemplo2 = (TextView) findViewById(R.id.exemplo_codigo2);
        exemplo2.setText(Html.fromHtml(new ArquivoHtml(this).openCodigoHtml("exemplo_codigo2.html")));

    }
    public void exercicioAssunto2(View view){
        startActivity(new Intent(this, Desafio1Activity.class));
    }

}
