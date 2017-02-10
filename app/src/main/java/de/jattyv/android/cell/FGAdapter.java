package de.jattyv.android.cell;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.LinkedList;

import de.jattyv.android.R;
import de.jattyv.jcapi.client.gui.cell.FG;

/**
 * Created by ddia on 09.02.17.
 */

public class FGAdapter extends ArrayAdapter<FG> {

    Context context;
    int layoutResourceId;


    public FGAdapter(Context context, int resource, LinkedList<FG> fg) {
        super(context, resource, fg);
        this.context = context;
        this.layoutResourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        FG row = getItem(position);

        if(convertView == null) {
            if (row.getType() == FG.FG_TYPE_FRIEND) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.fg_item_friend, null);
            } else if (row.getType() == FG.FG_TYPE_GROUP) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.fg_item_group, null);
            }
            TextView title = (TextView) convertView.findViewById(R.id.title);
            if (title != null) {
                title.setText(row.getTitle());
            }
        }
        return convertView;
    }

}
