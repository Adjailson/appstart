package tk.appstart.start;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    BancoControle controle = new BancoControle(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * Propriedade para barra de menu de cada Layout da aplicação
         * ele é utilizado no novo padão de layout da @package layout
         * */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        operacaoDB();
    }

    private void operacaoDB(){
        long resultado;
        resultado = controle.iniciaPontuacao(2017,0,0,0,0,0);
        if(resultado != -1) {
            MensagemDialog.toastMensagem(this, "Carregando banco...", 8, Gravity.CENTER);
        }
    }

    private int getPontos(int exercicio){
        return Integer.parseInt(controle.getValorColuna(exercicio));
    }

    /**
     * Método de para chamada de uma nova janela ou Layout
     * dentro da aplicação, o método é uma chamada
     * direta do recurso 'onClick' do xml.
     * */

    public void btConteudo1(View view){
        startActivity(new Intent(this, Assunto1Activity.class));
    }
    public void btConteudo2(View view){
        if(getPontos(1) >= 4) {
            startActivity(new Intent(this, Assunto2Activity.class));
        }else{
            MensagemDialog.toastMensagem(this,"Sua pontuação ainda é pouca!",4,Gravity.CENTER);
        }
    }
    public void btConteudo3(View view){
        if(getPontos(2) >= 3) {
            startActivity(new Intent(this, Assunto3Activity.class));
        }else{
            MensagemDialog.toastMensagem(this,"Sua pontuação ainda é pouca!",4,Gravity.CENTER);
        }
    }
    public void btConteudo4(View view){
        if(getPontos(3) >= 3) {
            startActivity(new Intent(this, Assunto4Activity.class));
        }else{
            MensagemDialog.toastMensagem(this,"Sua pontuação ainda é pouca!",4,Gravity.CENTER);
        }
    }
    public void btConteudo5(View view) {
        if(getPontos(4) >= 3) {
            startActivity(new Intent(this, Assunto5Activity.class));
        }else{
            MensagemDialog.toastMensagem(this,"Sua pontuação ainda é pouca!",4,Gravity.CENTER);
        }
    }
    /**
     * Os métodos abaixo são adição de opção para um menu superior
     * a direita no primeiro layout, eles se utiliza do arquivo .xml
     * da @package menu. O 'onCreateOptionsMenu' faz a chamada do
     * modelo do arquivo .xml na @package menu, o 'onOptionsItemSelected'
     * verifica o id de cada elemento dentro do arquivo .xml que esta contido
     * na @package menu e faz verificação de select pegando o 'id' via xml.
     * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_referencias){
            startActivity(new Intent(this, ReferenciasActivity.class));
        }else if (id == R.id.action_sobre){
            startActivity(new Intent(this, SobreActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
