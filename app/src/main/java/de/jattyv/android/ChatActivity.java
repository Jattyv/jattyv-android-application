package de.jattyv.android;

import android.app.ListActivity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import de.jattyv.jcapi.client.Chat;
import de.jattyv.jcapi.client.gui.JGui;

public class ChatActivity extends ListActivity implements JGui{

    private String fname = "";

    private static Chat chat;
    private ArrayList<String> messages =new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private EditText inputEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        fname = getIntent().getExtras().getString(ChatSelectionActivity.FRIEND_NAME);
        chat = ChatSelectionActivity.getChat();
        chat.setGui(this);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                messages);
        setListAdapter(adapter);
        inputEdit = (EditText) findViewById(R.id.input_message);
        inputEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(event != null) {
                    String a = "" + event.getKeyCode();
                }
                if (event != null&& (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                    return true;

                }
                return false;
            }
        });
    }

    public void send(View view){
        chat.getHandler().getOutHandler().sendNewMessage(fname, inputEdit.getText().toString());
        inputEdit.setText("");

    }

    @Override
    public void changeWindow(String s) {

    }

    @Override
    public void showError(String s) {

    }

    @Override
    public void addMessage(String fname, String message) {
        if (this.fname.equals(fname)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                            messages.add(message);
                            adapter.notifyDataSetChanged();
                            getListView().smoothScrollToPosition(adapter.getCount());
                    }
                });

        }
    }
}
