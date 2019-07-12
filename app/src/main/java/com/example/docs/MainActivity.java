package com.example.docs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.docs.Fragments.Bin_fragment;
import com.example.docs.Fragments.Recent_fragment;
import com.example.docs.Fragments.Starred_fragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        floatingActionButton = findViewById(R.id.floating_button);
        navigationView = findViewById(R.id.navigation_view);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,
                toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        if (savedInstanceState==null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                    new Recent_fragment()).commit();
            navigationView.setCheckedItem(R.id.recent);
        }

        navigationView.setNavigationItemSelectedListener(this);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Editor.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId())
        {
            case R.id.recent :
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                        new Recent_fragment()).commit();
                break;
            case R.id.starred :
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                        new Starred_fragment()).commit();
                break;
            case R.id.bin :
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                        new Bin_fragment()).commit();
                break;
            case R.id.notification :
                Toast.makeText(this,"Notifications",Toast.LENGTH_SHORT).show();
                break;
            case R.id.shared_with_me :
                Toast.makeText(this,"Shared with me",Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings :
                Toast.makeText(this,"Settings",Toast.LENGTH_SHORT).show();
                break;
            case R.id.help :
                Toast.makeText(this,"Help",Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
