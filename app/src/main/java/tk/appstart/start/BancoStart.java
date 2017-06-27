package tk.appstart.start;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Adjailson on 26/06/2017.
 */

public class BancoStart extends SQLiteOpenHelper {

    protected static final String NOME_BANCO = "banco.db";
    protected static final String TABELA = "pontos";
    protected static final String ID = "id";
    protected static final String LOGICA = "logica";
    protected static final String TIPOS = "tipos";
    protected static final String OPERADORES = "operadores";
    protected static final String CONDICIONAIS = "condicionais";
    protected static final String LOOPS = "loops";
    protected static final int VERSAO = 1;

    public BancoStart(Context context){
        super(context,NOME_BANCO,null,VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+TABELA+"("
                + ID + " integer primary key,"
                + LOGICA + " integer,"
                + TIPOS + " integer,"
                + OPERADORES + " integer,"
                + CONDICIONAIS + " integer,"
                + LOOPS + " integer"
                +")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("CREATE TABLE IF NOT EXISTS" + TABELA);
        onCreate(db);
    }
}
