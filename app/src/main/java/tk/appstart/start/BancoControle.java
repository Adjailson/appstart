package tk.appstart.start;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Adjailson on 26/06/2017.
 */

public class BancoControle {

    private SQLiteDatabase db;
    private BancoStart banco;
    private int pontos;

    public BancoControle(Context context){
        banco = new BancoStart(context);
    }

    public long iniciaPontuacao(int id, int logica, int tipo, int oper, int cond, int loop){
        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(BancoStart.ID, id);
        valores.put(BancoStart.LOGICA, logica);
        valores.put(BancoStart.TIPOS, tipo);
        valores.put(BancoStart.OPERADORES, oper);
        valores.put(BancoStart.CONDICIONAIS, cond);
        valores.put(BancoStart.LOOPS, loop);

        resultado = db.insert(BancoStart.TABELA, null, valores);
        db.close();
        return resultado;
    }

    public void salvarPontuacao(int coluna){
        //UPDATE pontos SET coluna=pontos WHERE id=2017;
        ContentValues valores;
        this.pontos = Integer.parseInt(getValorColuna(1));
        this.pontos += 1;

        String condicao;
        db = banco.getWritableDatabase();
        condicao = BancoStart.ID + "=" + 2017;
        valores = new ContentValues();
        switch (coluna){
            case 1:
                valores.put(BancoStart.LOGICA, this.pontos);
                break;
            case 2:
                valores.put(BancoStart.LOGICA, this.pontos+2);
                break;
            case 3:
                valores.put(BancoStart.LOGICA, this.pontos+2);
                break;
            case 4:
                valores.put(BancoStart.LOGICA, this.pontos+2);
                break;
            case 5:
                valores.put(BancoStart.LOGICA, this.pontos+3);
                break;
        }
        db.update(BancoStart.TABELA,valores,condicao,null);
        db.close();
    }

    public String getValorColuna(int coluna){
        db = banco.getReadableDatabase();
        Cursor cursor;
        String saida = "0";
        String[] campos = new String[1];
        if(coluna == 1){
            campos[0] = banco.LOGICA;
        }
        cursor = db.query(banco.TABELA, campos, null, null, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        switch (coluna){
            case 1:
                saida = cursor.getString(cursor.getColumnIndexOrThrow(BancoStart.LOGICA));
                break;
            default:
                break;
        }
        db.close();
        return saida;
    }

    public void resetarTudo(){
        int valor = Integer.parseInt(getValorColuna(1));
        if(valor >= 1) {
            ContentValues valores;
            String condicao;
            db = banco.getWritableDatabase();
            condicao = BancoStart.ID + "=" + 2017;
            valores = new ContentValues();

            valores.put(BancoStart.LOGICA, 0);

            db.update(BancoStart.TABELA, valores, condicao, null);
            db.close();
        }
    }
}
