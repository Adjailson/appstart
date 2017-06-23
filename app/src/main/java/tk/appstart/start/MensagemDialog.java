package tk.appstart.start;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Adjailson on 10/06/2017.
 */

public class MensagemDialog {

    private static AlertDialog alerta = null;

    /**
     * Método para abrir uma simples AlertDialog, mas personalizada:
     * */
    public static void openMensagem(Context context, String titulo, String msn){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titulo);
        builder.setMessage(msn);
        builder.setPositiveButton("Ok", null);
        builder.setCancelable(false);
        alerta = builder.create();
        alerta.show();
        getButtonPersonalizado(alerta);
    }
    /**
     * Método retorna uma mensagem do tipo Toast personalizada:
     * */
    public static void toastMensagem(Context context, String msg, int segundo, int posicao){
        final Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(posicao, 0, 0);
        toast.getView().setBackgroundResource(R.drawable.estilo_barra);
        toast.getView().setPadding(20,0,20,0);
        toast.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, (segundo*1000));
    }
    /**
     * Método para abrir layout dentro AlertDialog:
     * */
    public static void openLayoutDialog(Context context, String titulo, int layout){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE);
        View alertLayout = inflater.inflate(layout, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Estilo_Web_Site);
        builder.setView(alertLayout);
        builder.setCustomTitle(getTituloPersonalizado(context,titulo));
        builder.setPositiveButton("Voltar",null);
        alerta = builder.create();
        alerta.show();
        getButtonPersonalizado(alerta);
    }
    /**
     * Método retorna um Button personalizado:
     * */
    private static Button getButtonPersonalizado(AlertDialog dialog){
        Button objetoBotao = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        objetoBotao.setBackgroundResource(R.drawable.estilo_botao);
        objetoBotao.setAllCaps(false);
        objetoBotao.setTextColor(Color.WHITE);
        return objetoBotao;
    }
    /**
     * Método retorna um TextView personalizada:
     * */
    private static TextView getTituloPersonalizado(Context context,String titulo){
        TextView objetoTitulo = new TextView(context);
        objetoTitulo.setText(titulo);
        objetoTitulo.setTextColor(Color.WHITE);
        objetoTitulo.setTextSize(16);
        objetoTitulo.setPadding(10,10,5,10);
        objetoTitulo.setBackgroundResource(R.drawable.estilo_barra);
        return objetoTitulo;
    }
}
