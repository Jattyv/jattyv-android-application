package de.jattyv.android;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import de.jattyv.jcapi.client.Chat;
import de.jattyv.jcapi.client.gui.JGui;

public class ChatActivity extends ListActivity implements JGui{

    private String fname = "";

    private static Chat chat;
    private ArrayList<String> messages =new ArrayList<>();
    private ArrayAdapter<String> adapter;

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
            try {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                            messages.add(message);
                            adapter.notifyDataSetChanged();
                            getListView().smoothScrollToPosition(adapter.getCount());
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
