package de.jattyv.android;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.LinkedList;

import de.jattyv.android.cell.FGAdapter;
import de.jattyv.jcapi.client.Chat;
import de.jattyv.jcapi.client.gui.JGui;
import de.jattyv.jcapi.client.gui.cell.FG;

public class ChatSelectionActivity extends ListActivity implements JGui{

    private static Chat chat;
    private static LinkedList<FG> fg =new LinkedList<>();
    private static FGAdapter adapter;

    public static final String FG_ID = "id";
    public static final String FG_TYPE = "type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_selection);

        this.chat = LoginActivity.getChat();
        chat.setGui(this);

        adapter=new FGAdapter(this,
                android.R.layout.simple_list_item_1,
                fg);
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

    public void addGroup(View view){
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.dialog_add_friend, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptView);
        final EditText input = (EditText) promptView.findViewById(R.id.friendsname);
        alertDialogBuilder.setCancelable(true)
                .setTitle("Add a Group")
                .setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id){
                        createGroup(input.getText().toString());
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();


    }

    public void addFriend(String friend){
        fg.add(new FG(friend, FG.FG_TYPE_FRIEND, friend));
        adapter.notifyDataSetChanged();
    }

    public void createGroup(String gname){
        chat.getHandler().getOutHandler().createGroup(gname);
    }

    @Override
    public void addGroup(String gname, String gid) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fg.add(new FG(gname, FG.FG_TYPE_GROUP, gid));
                adapter.notifyDataSetChanged();
            }
        });
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
    public boolean alert(String s, String s1) {
        return false;
    }

    @Override
    public void addMessage(String s, String s1) {

    }

    @Override
    public void addGroupMessage(String s, String s1) {

    }

    @Override
    protected void onListItemClick (ListView l, View v, int position, long id) {
        Intent intent = new Intent(ChatSelectionActivity.this, ChatActivity.class);
        intent.putExtra(FG_ID, fg.get(position).getId());
        intent.putExtra(FG_TYPE, fg.get(position).getType());
        startActivity(intent);
    }
}
