package tk.appstart.start;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Adjailson on 20/06/2017.
 */

public class ArquivoHtml {

    private static AssetManager enderecoAssets = null;
    private Context context;
    private String codigo = "";

    public ArquivoHtml(Context context){
        this.context = context;
    }

    public String openCodigoHtml(String arquivo){
        enderecoAssets = context.getResources().getAssets();
        try {
            InputStream buscaArquivo = enderecoAssets.open("html/"+arquivo+"");
            InputStreamReader leituraArquivo = new InputStreamReader(buscaArquivo);
            BufferedReader modoReader = new BufferedReader(leituraArquivo);
            String temporaria;
            while((temporaria = modoReader.readLine()) != null){
                setCodigo(temporaria);
            }
            buscaArquivo.close();
        } catch (Exception e){}
        return getCodigo();
    }

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo += codigo;
    }
}
