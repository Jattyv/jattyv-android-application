package de.jattyv.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import de.jattyv.android.data.ConfigFileHandler;
import de.jattyv.jcapi.data.jfc.JattyvFileController;
import de.jattyv.jcapi.data.jfc.data.Settings;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void save(View view){
        EditText ip = (EditText) findViewById(R.id.etip);
        EditText port = (EditText) findViewById(R.id.etport);
        Settings config = new Settings();
        config.setIp(ip.getText().toString());
        config.setPort(Integer.parseInt(port.getText().toString()));
        new ConfigFileHandler().writeSettings(JattyvFileController.J_PROP_FILE, config);
    }
}
