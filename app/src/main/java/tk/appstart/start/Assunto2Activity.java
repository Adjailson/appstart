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
        TextView exemplo1 = (TextView) findViewById(R.id.exemplo_tipoDados);
        exemplo1.setText(Html.fromHtml("valor = <font color='#006dbd'>10</font><br>\n" +
                "texto = <font color='#008000'>\"10\"</font><br>\n" +
                "<font color='#a61a98'>print</font>(valor + texto)<br><br>\n" +
                "<font color='#0000CD'> SAIDA>>> Erro! o tipo 'int' não pode ser somado com 'String'.</font>"));

    }
    public void exercicioAssunto2(View view){
        startActivity(new Intent(this, Desafio1Activity.class));
        this.finish();
    }

}
