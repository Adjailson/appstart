package tk.appstart.start;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import tk.appstart.game.Desafio4Activity;

public class Assunto3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assunto3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         * Setando o valores do símbolo < e <= como exemplo da tabela de operadores
         * lógicos, isso por que esses dois símbolos não são ceitos como tipo String
         * no arquivo string.xml.
         * */
        TextView menor_que = (TextView) findViewById(R.id.menor_que);
        TextView menor_igual = (TextView) findViewById(R.id.menor_igual);

        menor_que.setText("< Menor que");
        menor_igual.setText("<= Menor igual");

        // Exemplo Operadores aritmetricos
        TextView exemplo1 = (TextView) findViewById(R.id.exemplo_operadores_arit);
        exemplo1.setText(Html.fromHtml(new ArquivoHtml(this).openCodigoHtml("exemplo_operador_aritmetrico.html")));

        // Exemplo Operadores lógicos
        TextView exemplo2 = (TextView) findViewById(R.id.exemplo_operadores_logi);
        exemplo2.setText(Html.fromHtml(new ArquivoHtml(this).openCodigoHtml("exemplo_operador_logico.html")));

    }

    public void exercicioAssunto3(View view){
        startActivity(new Intent(this, Desafio4Activity.class));
    }

}
