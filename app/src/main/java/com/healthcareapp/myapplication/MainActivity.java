package com.healthcareapp.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.healthcareapp.myapplication.Fragments.BooksFragment;
import com.healthcareapp.myapplication.Fragments.GamesFragment;
import com.healthcareapp.myapplication.Fragments.MoviesFragment;
import com.healthcareapp.myapplication.Fragments.SeriesFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager2 viewPager = findViewById(R.id.view_pager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());

        viewPagerAdapter.addFragment(new GamesFragment(), "games");
        viewPagerAdapter.addFragment(new BooksFragment(), "books");
        viewPagerAdapter.addFragment(new SeriesFragment(),"series");
        viewPagerAdapter.addFragment(new MoviesFragment(),"movies");

        viewPager.setAdapter(viewPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager,(tab, position) -> {
            tab.setText(viewPagerAdapter.getPageTitle(position));
        }).attach();



        FloatingActionButton addButton = findViewById(R.id.addItem);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myInt = new Intent(getApplicationContext(), AddActivity.class);
                startActivity(myInt);
            }
        });

    }

    static class ViewPagerAdapter extends FragmentStateAdapter {

        private ArrayList<Fragment> fragments;
        private ArrayList <String> titles;


        ViewPagerAdapter (FragmentManager fm, Lifecycle lifecycle){
            super(fm, lifecycle);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragments.get(position);
        }
        @Override
        public int getItemCount(){
            return fragments.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);
        }
        public String getPageTitle (int position){
            return titles.get(position);

        }
    }





}