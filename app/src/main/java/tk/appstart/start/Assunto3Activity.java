package tk.appstart.start;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
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
        exemplo1.setText(Html.fromHtml("<font color='#006dbd'>10</font> / <font color='#006dbd'>4</font><br>\n" +
                "<font color='#0000CD'> SAIDA>>> 2.5</font><br><br>\n" +
                "<font color='#006dbd'>10</font> // <font color='#006dbd'>4</font><br>\n" +
                "<font color='#0000CD'> SAIDA>>> 2</font><br><br>\n" +
                "<font color='#006dbd'>10</font> * <font color='#006dbd'>2</font><br>\n" +
                "<font color='#0000CD'> SAIDA>>> 20</font><br><br>\n" +
                "<font color='#006dbd'>10</font> ** <font color='#006dbd'>2</font><br>\n" +
                "<font color='#0000CD'> SAIDA>>> 100</font><br>"));

        // Exemplo Operadores lógicos

        TextView exemplo2 = (TextView) findViewById(R.id.exemplo_operadores_logi);
        exemplo2.setText(Html.fromHtml("<font color='#006dbd'>10</font> >= <font color='#006dbd'>4</font><br>\n" +
                "<font color='#0000CD'> SAIDA>>> True</font><br><br>\n" +
                "<font color='#006dbd'>10</font> != <font color='#006dbd'>10</font><br>\n" +
                "<font color='#0000CD'> SAIDA>>> False</font><br><br>\n" +
                "(<font color='#006dbd'>2</font> + <font color='#006dbd'>2</font> == <font color='#006dbd'>4</font>)\n" +
                "<font color='#ff0f00'>or</font>\n" +
                "(<font color='#006dbd'>2</font> + <font color='#006dbd'>5</font> == <font color='#006dbd'>4</font>)<br>\n" +
                "<font color='#0000CD'> SAIDA>>> True</font><br><br>\n" +
                "(<font color='#006dbd'>2</font> + <font color='#006dbd'>2</font> == <font color='#006dbd'>4</font>)\n" +
                "<font color='#ff0f00'>and</font>\n" +
                "(<font color='#006dbd'>2</font> + <font color='#006dbd'>5</font> == <font color='#006dbd'>4</font>)<br>\n" +
                "<font color='#0000CD'> SAIDA>>> False</font><br>"));

    }

    public void exercicioAssunto3(View view){
        startActivity(new Intent(this, Desafio4Activity.class));
        this.finish();
    }

    // Método para alguns testes de colori letras ou palavras
    private SpannableString palavraReservada(String texto, String palavra, String HexCor) {
        int inicioPalavra = texto.indexOf(palavra);
        int fimPalavra = inicioPalavra + palavra.length();
        SpannableString spanTexto = new SpannableString(texto);
        if (inicioPalavra > -1) {
            spanTexto.setSpan(new ForegroundColorSpan(Color.parseColor(HexCor)), inicioPalavra, fimPalavra, 0);
        }
        return spanTexto;
    }

}
