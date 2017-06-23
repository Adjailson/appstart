package tk.appstart.start;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import static tk.appstart.start.MensagemDialog.toastMensagem;


public class Assunto1Activity extends AppCompatActivity {

    private AlertDialog alerta = null;
    private int contagemAcertos = 0;
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

    /**
     * A ação do botão deve abrir um layout xml dentro de uma AlertDialog
     * */
    public void btSobreVariaveis(View view){
        MensagemDialog.openLayoutDialog(this, "Como declarar variáveis ?", R.layout.informacoes_variaveis);
    }

    /**
     * Método de ação do botão de exercício do assunto 'Lógica de Programação'
     * fazemos a chamada do método 'dialogPergunta'. O 'exercicioAssunto1' vem
     * também da propriedade onClick do xml.
     * */
    public void exercicioAssunto1(View view){
        dialogPergunta("6 - Qual das alternativas abaixo corresponde a um algoritmo?", new CharSequence[]{"" +
                "O 5 recebe 10;","O valor de 'a' é igual a 'c' mais dois;","" +
                "Coloque mais ou menos um valor;","O valor 10 vai recebe 0."},1);
        dialogPergunta("5 - Marque a alternativa correta",
                new CharSequence[]{"Um algoritmo não é importante para a Computação, pois ele serve" +
                        " apenas de orientação para declaramos variáveis\n",
                        "Uma variável e um algoritmo são a mesmas coisas, pois ambas armazenam valores\n",
                        "Uma receita de bolo é um algoritmo, assim como pode ser uma variável que recebe " +
                                "o valor de 6 ou qualquer outro valor\n","Um procedimento para a troca de uma" +
                        " lâmpada não é um algoritmo"},2);
        dialogPergunta("4 - Qual a função de uma variável?", new CharSequence[]{
                "Para escrever um código bonito",
                "Para armazenar valores na memória do computador",
                "Para armazenar valores que nunca serão alterados",
                "Para identificar quantas linhas o código terá"},1);
        dialogPergunta("3 - Qual a forma correta de declarar uma Variável?", new CharSequence[]{"" +
                "Escrevemos o nome da variável ou uma letra para representá-la e atribuímos um valor usando o sinal de igual\n",
                "Escrevemos o símbolo de igual e atribuímos um valor\n",
                "Podemos utilizar qualquer símbolo ao invés de nomes ou letras para declarar variáveis\n",
                "É necessário escrever antes do valor que será atribuído um número que vai identificar a" +
                        " sua ordem de atribuição"},0);
        dialogPergunta("2 - O que faz o código x=3, y=6, soma=x+y?",
                new CharSequence[]{"Atribui o valor 3 à variável x, o valor 6 à variável y, mostra " +
                "o resultado da soma de x+y\n","Exibe o resultado 3+6\n",
                "Atribui o valor 3 à variável x, o valor 6 à variável y, faz a soma x+y e exibe o resultado\n" +
                "","Atribui o valor 3 à variável x, o valor 6 à variável y, faz a soma de x+y"},3);
        dialogPergunta("1 - O que é um Algoritmo?", new CharSequence[]{"Linguagem de Programação","" +
                "Descrição passo a passo finita para a realização de uma tarefa",
        "Aplicativo","Descrição passo a passo infinita para a realização de uma tarefa"},1);
    }
    /**
     * O método 'dialogPergunta' recebe 3 @param, são eles:
     * @param pergunta do tipo String para descrever uma pergunta a ser apresentada
     *                 dentro do AlertDialog do Android.
     * @param alternativas do tipo array para adicionar as possíveis alternativas
     *                     corretas e incorretas para serem marcadas.
     * @param correta do tipo inteiro, nele passamos o ponteiro da alternativa
     *                correta que esta no array de 'alternativas' para que possamos
     *                fazer verificação de validação.
     * */
    private void dialogPergunta(String pergunta, CharSequence[] alternativas, final int correta) {
        final CharSequence[] charSequences = alternativas;
        // Usado para armazenar os valores que foram selecionados
        final int[] resposta = new int[1];

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Estilo_Web_Site);
        builder.setTitle(pergunta);
        builder.setCancelable(false);
        // Usaremos 'setSingleChoiceItems' para pode selecionar apenas uma resposta:
        builder.setSingleChoiceItems(charSequences, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int valor) {
                resposta[0] = valor;
            }
        });

        builder.setPositiveButton("Verificar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                if(resposta[0] == correta){
                    mensagem("Você acertou!");
                    contagemAcertos++;
                }else {
                    mensagem("Não foi dessa vez!");
                }
            }
        });
        TextView stylo_titlo = new TextView(this);
        stylo_titlo.setText(pergunta);
        stylo_titlo.setTextColor(Color.WHITE);
        stylo_titlo.setTextSize(16);
        stylo_titlo.setPadding(10,10,5,10);
        stylo_titlo.setBackgroundResource(R.drawable.estilo_barra);
        builder.setCustomTitle(stylo_titlo);
        alerta = builder.create();
        alerta.show();
        Button btPersonalizado = alerta.getButton(DialogInterface.BUTTON_POSITIVE);
        btPersonalizado.setBackgroundResource(R.drawable.estilo_botao);
        btPersonalizado.setTextColor(Color.WHITE);
        btPersonalizado.setPadding(15,0,15,0);
        btPersonalizado.setAllCaps(false);

    }

    public void mensagem(String texto){
        toastMensagem(this,texto,1, Gravity.CENTER);
    }


}
