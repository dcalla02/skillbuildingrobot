package com.dreslab.www.drethan_newapp.Helperfiles;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreslab.www.drethan_newapp.R;

import java.util.List;

/**
 * Created by Michael on 7/26/2016.
 */
public class NavigateAdaptor extends ArrayAdapter<NavigateTools> {
    Context context;
    int reLayout;
    List<NavigateTools> listToolNav;


    public NavigateAdaptor(Context context, int reLayout, List<NavigateTools> listToolNav) {
        super(context, reLayout, listToolNav);
        this.context = context;
        this.listToolNav = listToolNav;
        this.reLayout = reLayout;
    }
    //to modify content....

    @SuppressLint("Viewholder")@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, reLayout, null);
        TextView title = (TextView) v.findViewById(R.id.tool_title);

        TextView description = (TextView) v.findViewById(R.id.tool_description);
        ImageView navIcon = (ImageView)v.findViewById(R.id.tool_image);
        NavigateTools navigateTools = listToolNav.get(position);
        title.setText(navigateTools.getToolName());
        description.setText(navigateTools.getDescription());
        navIcon.setImageResource(navigateTools.getResIcon());

        return v;
    }
}
