package com.example.tust.tmdbmovieviewer.Activity;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.widget.TabHost;
import android.widget.TextView;


import com.example.tust.tmdbmovieviewer.R;



public class MainActivity extends TabActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tabHost = getTabHost();

        // Inbox Tab
        TabHost.TabSpec inboxSpec = tabHost.newTabSpec("Popular");
        // Tab Icon
        inboxSpec.setIndicator("Popular");
        Intent inboxIntent = new Intent(this, PopularActivity.class);
        // Tab Content
        inboxSpec.setContent(inboxIntent);

        // Outbox Tab
        TabHost.TabSpec outboxSpec = tabHost.newTabSpec("Top Rated");
        outboxSpec.setIndicator("Top Rated");
        Intent outboxIntent = new Intent(this, TopRatedActivity.class);
        outboxSpec.setContent(outboxIntent);

        // Profile Tab
        TabHost.TabSpec profileSpec = tabHost.newTabSpec("Upcoming");
        profileSpec.setIndicator("Upcoming");
        Intent profileIntent = new Intent(this, UpcomingActivity.class);
        profileSpec.setContent(profileIntent);

        // Adding all TabSpec to TabHost
        tabHost.addTab(inboxSpec); // Adding Inbox tab
        tabHost.addTab(outboxSpec); // Adding Outbox tab
        tabHost.addTab(profileSpec); // Adding Profile tab

    }
}
