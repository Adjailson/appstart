package tk.appstart.start;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.TextView;

/**
 * Created by Adjailson on 28/06/2017.
 */

public class VariaveisActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.informacoes_variaveis);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView sobreVar = (TextView) findViewById(R.id.mais_sobre_var);
        sobreVar.setText(Html.fromHtml(new ArquivoHtml(this).openCodigoHtml("sobre_variaveis.html")));

    }
}
