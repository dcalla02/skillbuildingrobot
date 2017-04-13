package com.dreslab.www.drethan_newapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dreslab.www.drethan_newapp.Helperfiles.NavigateAdaptor;
import com.dreslab.www.drethan_newapp.Helperfiles.NavigateTools;
import com.dreslab.www.drethan_newapp.mainfragments.AboutAndAdmin;
import com.dreslab.www.drethan_newapp.mainfragments.Connect_bluetooth;
import com.dreslab.www.drethan_newapp.mainfragments.Intro_paragraph;
import com.dreslab.www.drethan_newapp.mainfragments.howToUseBluetooth;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 7/26/2016.
 */
public class welcome_page extends ActionBarActivity {
    DrawerLayout drawerLayout;
    RelativeLayout drawerpane;
    ListView lvNav;
    List<NavigateTools> listNavTools;
    List<Fragment> listFragment;

    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;}
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
                super.onPostCreate(savedInstanceState);
                actionBarDrawerToggle.syncState();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);
        String name = getIntent().getStringExtra("name");


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerpane = (RelativeLayout) findViewById(R.id.drawer_pane);
        lvNav = (ListView) findViewById(R.id.nav_list);

        //set tile of welcome page
       listNavTools = new ArrayList<NavigateTools>();
       listNavTools.add(new NavigateTools("Welcome!", "Click for info about app",R.drawable.home_icon));
       listNavTools.add(new NavigateTools("Bluetooth Help", "Having problem with connecting?",R.drawable.bluetoothhelp));
       listNavTools.add(new NavigateTools("Connect  Bluetooth", "Connect",R.drawable.bluetooth1));
       listNavTools.add(new NavigateTools("About/Admin", "Want to know more?",R.drawable.about));
       listNavTools.add(new NavigateTools("Student Display Test", "test", R.drawable.skillbuildingrobot_plate));



        //populate
        listFragment = new ArrayList<Fragment>();
        listFragment.add(new Intro_paragraph());
        listFragment.add(new howToUseBluetooth());
        listFragment.add(new Connect_bluetooth());
        listFragment.add(new AboutAndAdmin());

        //add items to the right and left scroll
        NavigateAdaptor navigateAdaptor = new NavigateAdaptor(this, R.layout.navigatelist, listNavTools);
        lvNav.setAdapter(navigateAdaptor);



        //load first fragment as default
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, listFragment.get(0)).commit();

        setTitle(listNavTools.get(0).getToolName());
        lvNav.setItemChecked(0, true);
        drawerLayout.closeDrawer(drawerpane);
        lvNav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //replace fragment with the selected fragment
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_content, listFragment.get(position)).commit();

                setTitle(listNavTools.get(position).getToolName());
                lvNav.setItemChecked(position, true);
                drawerLayout.closeDrawer(drawerpane);
            }
        });
        //create listener for drawer activated
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,
                R.string.drawer_opened, R.string.drawer_closed){
            @Override
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                invalidateOptionsMenu();
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

    }
}
