package com.bojie.materialtest.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bojie.materialtest.R;
import com.bojie.materialtest.extras.SortListener;
import com.bojie.materialtest.fragments.BoxOfficeFragment;
import com.bojie.materialtest.fragments.NavigationDrawerFragment;
import com.bojie.materialtest.fragments.SearchFragment;
import com.bojie.materialtest.fragments.UpcomingFragment;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;


public class MainActivity extends ActionBarActivity implements MaterialTabListener,
        View.OnClickListener {

    private Toolbar mToolbar;
    private MaterialTabHost mTabHost;
    private ViewPager mViewPager;
    private MyPagerAdapter mMyPagerAdapter;
    private static final int MOVIES_SEARCH_RESULTS = 0;
    private static final int MOVIES_HITS = 1;
    private static final int MOVIES_UPCOMING = 2;
    private static final String TAG_SORT_NAME = "sortName";
    private static final String TAG_SORT_DATE = "sortDate";
    private static final String TAG_SORT_RATINGS = "sortRatings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setUpNavigationDrawer();

        buildTab();

        buildFAB();

    }

    private void buildTab() {
        mTabHost = (MaterialTabHost) findViewById(R.id.materialTabHost);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        mMyPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mMyPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mTabHost.setSelectedNavigationItem(position);
            }
        });

        // insert all tabs from pagerAdapter data
        for (int i = 0; i < mMyPagerAdapter.getCount(); i++) {
            mTabHost.addTab(
                    mTabHost.newTab()
                            .setIcon(mMyPagerAdapter.getIcon(i))
                            .setTabListener(this)
            );
        }
    }

    private void setUpNavigationDrawer() {
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout),
                mToolbar);
    }

    private void buildFAB() {
        // Float Action Button
        ImageView icon = new ImageView(this); // Create an icon
        icon.setImageResource(R.drawable.ic_action_new);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .setBackgroundDrawable(R.drawable.selector_button_red)
                .build();

        ImageView iconSortName = new ImageView(this);
        iconSortName.setImageResource(R.drawable.ic_action_alphabets);
        ImageView iconSortDate = new ImageView(this);
        iconSortDate.setImageResource(R.drawable.ic_action_calendar);
        ImageView iconSortRatings = new ImageView(this);
        iconSortRatings.setImageResource(R.drawable.ic_action_important);

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        itemBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_sub_button_gray));

        // Build the sub buttons
        SubActionButton buttonSortName = itemBuilder.setContentView(iconSortName).build();
        SubActionButton buttonSortDate = itemBuilder.setContentView(iconSortDate).build();
        SubActionButton buttonSortRatings = itemBuilder.setContentView(iconSortRatings).build();

        buttonSortName.setTag(TAG_SORT_NAME);
        buttonSortDate.setTag(TAG_SORT_DATE);
        buttonSortRatings.setTag(TAG_SORT_RATINGS);

        buttonSortDate.setOnClickListener(this);
        buttonSortName.setOnClickListener(this);
        buttonSortRatings.setOnClickListener(this);

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(buttonSortName)
                .addSubActionView(buttonSortDate)
                .addSubActionView(buttonSortRatings)
                .attachTo(actionButton)
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_using_tab_library, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.navigate) {
            startActivity(new Intent(this, SubActivity.class));
        }
        if (id == R.id.action_tab_using_SlidingTabLayout) {
            startActivity(new Intent(this, ActivityWithSlidingTabLayout.class));
        }
        if (id == R.id.action_tab_vector_activity) {
            startActivity(new Intent(this, VectorTestActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(MaterialTab materialTab) {
        mViewPager.setCurrentItem(materialTab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }

    @Override
    public void onClick(View v) {

        Fragment fragment = (Fragment) mMyPagerAdapter
                .instantiateItem(mViewPager, mViewPager.getCurrentItem());

        if (fragment instanceof SortListener) {
            if (v.getTag().equals(TAG_SORT_NAME)) {
                ((SortListener) fragment).onSortByName();
            }
            if (v.getTag().equals(TAG_SORT_DATE)) {
                ((SortListener) fragment).onSortByDate();
            }
            if (v.getTag().equals(TAG_SORT_RATINGS)) {
                ((SortListener) fragment).onSortByRating();
            }
        }
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        //String[] tabText = getResources().getStringArray(R.array.tabs);
        int icons[] = {R.drawable.ic_action_home, R.drawable.ic_action_articles, R.drawable.ic_action_personal};
        //int icons[] = {R.drawable.vector_android, R.drawable.vector_android, R.drawable.vector_android};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = null;
            switch (i) {
                case MOVIES_SEARCH_RESULTS:
                    fragment = SearchFragment.newInstance("", "");
                    break;
                case MOVIES_HITS:
                    fragment = BoxOfficeFragment.newInstance("", "");
                    break;
                case MOVIES_UPCOMING:
                    fragment = UpcomingFragment.newInstance("", "");
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getStringArray(R.array.tabs)[position];
        }

        private Drawable getIcon(int position) {
            return getResources().getDrawable(icons[position]);
        }
    }
}
