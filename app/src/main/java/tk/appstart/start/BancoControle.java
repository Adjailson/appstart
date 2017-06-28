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
        this.pontos = Integer.parseInt(getValorColuna(coluna));
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
                valores.put(BancoStart.TIPOS, this.pontos);
                break;
            case 3:
                valores.put(BancoStart.OPERADORES, this.pontos);
                break;
            case 4:
                valores.put(BancoStart.CONDICIONAIS, this.pontos);
                break;
            case 5:
                valores.put(BancoStart.LOOPS, this.pontos);
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
        }else if(coluna == 2){
            campos[0] = banco.TIPOS;
        }else if(coluna == 3){
            campos[0] = banco.OPERADORES;
        }else if(coluna == 4){
            campos[0] = banco.CONDICIONAIS;
        }else if(coluna == 5){
            campos[0] = banco.LOOPS;
        }
        cursor = db.query(banco.TABELA, campos, null, null, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        switch (coluna){
            case 1:
                saida = cursor.getString(cursor.getColumnIndexOrThrow(BancoStart.LOGICA));
                break;
            case 2:
                saida = cursor.getString(cursor.getColumnIndexOrThrow(BancoStart.TIPOS));
                break;
            case 3:
                saida = cursor.getString(cursor.getColumnIndexOrThrow(BancoStart.OPERADORES));
                break;
            case 4:
                saida = cursor.getString(cursor.getColumnIndexOrThrow(BancoStart.CONDICIONAIS));
                break;
            case 5:
                saida = cursor.getString(cursor.getColumnIndexOrThrow(BancoStart.LOOPS));
                break;
        }
        db.close();
        return saida;
    }
    /*
    public String excluiTudo(int id){
        long resultado;
        String condicao = BancoStart.ID + "=" + id;
        db = banco.getReadableDatabase();
        resultado = db.delete(BancoStart.TABELA,condicao,null);
        db.close();
        if(resultado == -1){
            return "Erro!";
        }else {
            return "Sucesso!";
        }
    }
    */
    public String resetarTudo(){
        int valor = Integer.parseInt(getValorColuna(5));
        if(valor >= 3) {
            ContentValues valores;
            String condicao;
            db = banco.getWritableDatabase();
            condicao = BancoStart.ID + "=" + 2017;
            valores = new ContentValues();

            valores.put(BancoStart.LOGICA, 0);
            valores.put(BancoStart.TIPOS, 0);
            valores.put(BancoStart.OPERADORES, 0);
            valores.put(BancoStart.CONDICIONAIS, 0);
            valores.put(BancoStart.LOOPS, 0);

            db.update(BancoStart.TABELA, valores, condicao, null);
            db.close();
            return "Sucesso!\n\nTodos os desafios voltaram ao estado inicial.";
        }else{
            return "Erro!\n\nVocê deve concluir todos os desafios.";
        }
    }
}
