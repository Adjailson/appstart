package tk.appstart.start;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import tk.appstart.game.Desafio10Activity;

public class MainActivity extends AppCompatActivity {

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
        startActivity(new Intent(this, Assunto2Activity.class));
    }
    public void btConteudo3(View view){
        startActivity(new Intent(this, Assunto3Activity.class));
    }
    public void btConteudo4(View view){
        startActivity(new Intent(this, Assunto4Activity.class));
    }
    public void btConteudo5(View view) {
        //startActivity(new Intent(this, Assunto5Activity.class));
        startActivity(new Intent(this, Desafio10Activity.class));
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        // Verificação quando selecionar o item 'Resetar fases':
        if (id == R.id.action_config) {
            // Definição de métodos...
        // Verificação quando selecionar o item 'Referências':
        }else if(id == R.id.action_referencias){
            startActivity(new Intent(this, ReferenciasActivity.class));
        // Verificação quando selecionar o item 'Sobre-nós':
        }else if (id == R.id.action_sobre){
            startActivity(new Intent(this, SobreActivity.class));
        }
        // Retorno do que foi somente selecionado:
        return super.onOptionsItemSelected(item);
    }
}
