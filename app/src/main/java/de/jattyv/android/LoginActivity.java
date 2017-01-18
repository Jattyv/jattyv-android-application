package de.jattyv.android;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Properties;

import de.jattyv.android.data.ConfigFileHandler;
import de.jattyv.jcapi.client.Chat;
import de.jattyv.jcapi.client.gui.JGui;
import de.jattyv.jcapi.data.jfc.JattyvFileController;
import de.jattyv.jcapi.data.jfc.data.Settings;
import de.jattyv.jsapi.ChatServer;

public class LoginActivity extends AppCompatActivity implements JGui{


    private static Chat chat;
    private EditText tUserName;
    private EditText tPassword;
    private String uname;
    private String upassword;
    public static Context context;

    private final static int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 36;

    public LoginActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        if (ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }
        context = getApplicationContext();
        Settings  settings = new ConfigFileHandler().readSettings(JattyvFileController.J_PROP_FILE);

        if(settings != null && settings.isIpAvailable() && settings.isPortAvailable()) {
            ChatServer server = new ChatServer(settings);
            server.start();
            chat = new Chat(settings);
        }else{
            ChatServer server = new ChatServer(36987);
            server.start();
            chat = new Chat("127.0.0.1", 36987);
        }
        chat.setGui(this);
    }

    public void login(View v){
        getText();
        chat.getHandler().getOutHandler().sendLogin(uname,upassword);

    }
    public void regist(View v) {
        getText();
        chat.getHandler().getOutHandler().sendRegist(uname,upassword);

    }

    public void getText(){
        tUserName = (EditText) findViewById(R.id.username);
        tPassword = (EditText) findViewById(R.id.password);
        uname = tUserName.getText().toString();
        upassword = tPassword.getText().toString();
    }

    public static Chat getChat(){
        return chat;
    }

    @Override
    public void changeWindow(String s) {
        if(s.equals(JGui.CHAT_WINDOW)){
            Intent intent = new Intent(LoginActivity.this, ChatSelectionActivity.class);
            startActivity(intent);
        }
    }

    public void openSettings(View view){
        Intent intent = new Intent(LoginActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void showError(String s) {
        this.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void addMessage(String s, String s1) {

    }
}

