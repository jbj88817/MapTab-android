package com.bojie.materialtest.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.bojie.materialtest.R;
import com.bojie.materialtest.fragments.MyFragment;
import com.bojie.materialtest.views.SlidingTabLayout;


public class ActivityWithSlidingTabLayout extends ActionBarActivity {

    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_using_sliding_tab_layout);

        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.tabs);
        mSlidingTabLayout.setCustomTabView(R.layout.custom_tab_view, R.id.tabText);
        mSlidingTabLayout.setDistributeEvenly(true);
        // colors for tab
        mSlidingTabLayout.setBackgroundColor(getResources().getColor(R.color.primary));
        mSlidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.accent));

        mSlidingTabLayout.setViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        if (id == R.id.action_tab_using_library) {
            startActivity(new Intent(this, MainActivity.class));
        }
        if (id == R.id.action_tab_vector_activity) {
            startActivity(new Intent(this, VectorTestActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        String[] tabText = getResources().getStringArray(R.array.tabs);
        int icons[] = {R.drawable.ic_action_home, R.drawable.ic_action_articles, R.drawable.ic_action_personal};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            MyFragment myFragment = MyFragment.getInstance(i);
            return myFragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Drawable drawable = getResources().getDrawable(icons[position]);
            Log.d("!!width", drawable.getIntrinsicWidth() + "");
            drawable.setBounds(150, 0, 280, drawable.getIntrinsicHeight());
            ImageSpan imageSpan = new ImageSpan(drawable);
            SpannableString spannableString = new SpannableString(" ");
            spannableString.setSpan(imageSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableString;
        }
    }
}
