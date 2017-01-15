package de.jattyv.android;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import de.jattyv.jcapi.client.Chat;
import de.jattyv.jcapi.client.gui.JGui;

public class ChatSelectionActivity extends ListActivity implements JGui{

    private static Chat chat;
    private static ArrayList<String> friends =new ArrayList<String>();
    private static ArrayAdapter<String> adapter;

    public static final String FRIEND_NAME = "fname";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_selection);

        this.chat = LoginActivity.getChat();
        chat.setGui(this);

        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                friends);
        setListAdapter(adapter);

    }

    public void addFriend(View view){
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.dialog_add_friend, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptView);
        final EditText input = (EditText) promptView.findViewById(R.id.friendsname);
        alertDialogBuilder.setCancelable(true)
                .setTitle("Add a friends")
                .setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id){
                        addFriend(input.getText().toString());
                        getListView().smoothScrollToPosition(adapter.getCount());
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();


    }

    public void addFriend(String friend){
        friends.add(friend);
        adapter.notifyDataSetChanged();
    }

    public static Chat getChat(){
        return chat;
    }

    @Override
    public void changeWindow(String s) {

    }

    @Override
    public void showError(String s) {

    }

    @Override
    public void addMessage(String s, String s1) {

    }

    @Override
    protected void onListItemClick (ListView l, View v, int position, long id) {
        Intent intent = new Intent(ChatSelectionActivity.this, ChatActivity.class);
        intent.putExtra(FRIEND_NAME, friends.get(position));
        startActivity(intent);
    }
}
