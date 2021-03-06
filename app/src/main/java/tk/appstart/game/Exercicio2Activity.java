package tk.appstart.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioGroup;

import tk.appstart.start.BancoControle;
import tk.appstart.start.MensagemDialog;
import tk.appstart.start.R;

/**
 * Created by Adjailson on 03/07/2017.
 */

public class Exercicio2Activity extends AppCompatActivity {

    private RadioGroup grupoPergunta;
    BancoControle controle = new BancoControle(this);

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_exercicio2);
        getSupportActionBar().hide();

        grupoPergunta = (RadioGroup) findViewById(R.id.exercicio2);

    }

    public void btPular(View view){
        startActivity(new Intent(this, Exercicio3Activity.class));
        this.finish();
    }

    public void btEnviar(View view){
        int selecionado = grupoPergunta.getCheckedRadioButtonId();
        switch (selecionado){
            case -1:// não selecionou
                MensagemDialog.openMensagem(this,"Ei!","Você não respondeu.");
                break;
            case R.id.select_4:// resposta certa
                controle.salvarPontuacao(1);
                MensagemDialog.toastMensagem(this,"É isso aí. Você acertou!",2, Gravity.CENTER);
                //próximo exercício:
                startActivity(new Intent(this, Exercicio3Activity.class));
                this.finish();
                break;
            default:// resposta errada
                MensagemDialog.openMensagem(this,"Eita!","Não foi dessa vez.");
                break;
        }
    }

}
