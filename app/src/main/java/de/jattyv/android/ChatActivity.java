package de.jattyv.android;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.ArrayList;

import de.jattyv.jcapi.client.Chat;
import de.jattyv.jcapi.client.gui.JGui;
import de.jattyv.jcapi.client.gui.cell.FG;

public class ChatActivity extends ListActivity implements JGui{

    private String id = "";
    private int type = 0;

    private static Chat chat;
    private ArrayList<String> messages =new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private EditText inputEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        id = getIntent().getExtras().getString(ChatSelectionActivity.FG_ID);
        type = getIntent().getExtras().getInt(ChatSelectionActivity.FG_TYPE);
        chat = ChatSelectionActivity.getChat();
        chat.setGui(this);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                messages);
        setListAdapter(adapter);
        inputEdit = (EditText) findViewById(R.id.input_message);
        if(type == FG.FG_TYPE_FRIEND) {
            for (String message : chat.getHandler().getMessages(id)) {
                addMessage(id, message);
            }
        }else if(type == FG.FG_TYPE_GROUP){
            for (String message : chat.getHandler().getGroupMessages(id)) {
                addMessage(id, message);
            }
        }
    }

    public void send(View view){
        if(type == FG.FG_TYPE_FRIEND) {
            chat.getHandler().getOutHandler().sendNewMessage(id, inputEdit.getText().toString());
            inputEdit.setText("");
        }else if(type == FG.FG_TYPE_GROUP){
            chat.getHandler().getOutHandler().sendNewGroupMessage(id, inputEdit.getText().toString());
            inputEdit.setText("");
        }

    }

    @Override
    public void addFriend(String s) {

    }

    @Override
    public void addGroup(String s, String s1) {

    }

    @Override
    public void changeWindow(String s) {

    }

    @Override
    public void showError(String s) {

    }

    @Override
    public boolean alert(String s, String s1) {
        return false;
    }

    @Override
    public void addMessage(String fname, String message) {
        if (this.id.equals(fname)) {
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

    @Override
    public void addGroupMessage(String s, String s1) {

    }
}
