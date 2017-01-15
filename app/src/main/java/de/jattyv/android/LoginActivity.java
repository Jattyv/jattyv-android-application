package de.jattyv.android;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.jattyv.jcapi.client.gui.JGui;
import de.jattyv.jcapi.client.handler.Handler;
import de.jattyv.jcapi.client.network.Client;
import de.jattyv.jcapi.util.Packer;

import static android.Manifest.permission.READ_CONTACTS;

public class LoginActivity extends AppCompatActivity{


    private Handler handler;
    private Client cl;
    private EditText tUserName;
    private EditText tPassword;
    private String uname;
    private String upassword;

    public LoginActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        handler = new Handler();
        cl  = new Client("192.168.0.103", 36987);
        cl.setHandler(handler);
        handler.setClient(cl);
    }

    public void login(View v){
        getText();
        handler.start(Packer.packLogin(uname, upassword));

    }
    public void regist(View v) {
        getText();
        try {
            handler.start(Packer.packRegistration(uname, upassword));
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void getText(){
        tUserName = (EditText) findViewById(R.id.username);
        tPassword = (EditText) findViewById(R.id.password);
        uname = tUserName.getText().toString();
        upassword = tPassword.getText().toString();
    }

}

