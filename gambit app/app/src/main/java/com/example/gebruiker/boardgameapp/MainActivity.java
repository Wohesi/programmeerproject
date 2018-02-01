/**
 * Name: Wout Singerling
 * https://github.com/Wohesi/programmeerproject
 * Student number: 11136324
 */


package com.example.gebruiker.boardgameapp;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    // firebase instance
    private FirebaseAuth auth;
    private FirebaseUser currentUser;

    // Setting variables for the toolbar
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.ic_search_white_24dp,
            R.drawable.ic_date_range_white_24dp,
            R.drawable.ic_person_white_24dp
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize the firebase
        auth = FirebaseAuth.getInstance();

        // setting up the tabs and toolbar
        toolbar = findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
        setSupportActionBar(toolbar);

        // setup the actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        // set up the tabs
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }


    // setting icons to the tabs
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    // setting the tabs into the tabbar
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SearchFragment(), "Search");
        adapter.addFragment(new EventsFragment(), "Events");
        adapter.addFragment(new LoginFragment(), "User");
        viewPager.setAdapter(adapter);
    }


    // set viewPagerAdapter for fragments
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }


        @Override
        public Fragment getItem(int position) {
            if (position == 0 ) {
                return new BlankFragment();
            }
                return fragmentList.get(position);
        }

        // count the amount of fragments
        @Override
        public int getCount() {
            return fragmentList.size();
        }

        // method to add fragments to the tabbar
        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }

    // firebase connection
    @Override
    public void onStart() {
        super.onStart();
        // heck if user is signed in (non-null) and update UI accordingly.
        currentUser = auth.getCurrentUser();

    }

}
