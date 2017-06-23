package tk.appstart.start;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class ReferenciasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referencias);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void linkCode(View view){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://code.org")));
    }
    public void linkCodecademy(View view){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.codecademy.com/")));
    }
    public void linkLightbot(View view){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://lightbot.com/")));
    }
    public void linkScratch(View view){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://scratch.mit.edu/")));
    }
    public void linkRobocode(View view){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://robocode.sourceforge.net/")));
    }
    public void linkKhan(View view){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://pt.khanacademy.org/computing")));
    }

}
