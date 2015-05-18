package com.bojie.materialtest.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bojie.materialtest.pojo.Information;
import com.bojie.materialtest.R;
import com.bojie.materialtest.adapters.RecycleAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment {

     /*
    STEPS TO HANDLE THE RECYCLER CLICK
    1 Create a class that EXTENDS RecylcerView.OnItemTouchListener
    2 Create an interface inside that class that supports click and long click and indicates the View that was clicked and the position where it was clicked
    3 Create a GestureDetector to detect ACTION_UP single tap and Long Press events
    4 Return true from the singleTap to indicate your GestureDetector has consumed the event.
    5 Find the childView containing the coordinates specified by the MotionEvent and if the childView is not null and the listener is not null either, fire a long click event
    6 Use the onInterceptTouchEvent of your RecyclerView to check if the childView is not null, the listener is not null and the gesture detector consumed the touch event
    7 if above condition holds true, fire the click event
    8 return false from the onInterceptedTouchEvent to give a chance to the childViews of the RecyclerView to process touch events if any.
    9 Add the onItemTouchListener object for our RecyclerView that uses our class created in step 1
     */

    public static final String PREF_FILE_NAME = "testpref";

    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstancestate;
    private View containerView;
    private RecyclerView mRecyclerView;
    private RecycleAdapter mAdapter;


    public NavigationDrawerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);
        mAdapter = new RecycleAdapter(getActivity(), getData());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int postion) {
                Toast.makeText(getActivity(), "onClick " + postion, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int positon) {
                Toast.makeText(getActivity(), "onLongClick " + positon, Toast.LENGTH_SHORT).show();
            }
        }));
        return layout;
    }

    public static List<Information> getData() {
        List<Information> data = new ArrayList<>();
        int[] icons = {R.drawable.ic_number1, R.drawable.ic_number2, R.drawable.ic_number3, R.drawable.ic_number4};
        String[] titles = {"Bojie", "Ziyang", "Kyle", "Larry"};
        for (int i = 0; i < titles.length && i < icons.length; i++) {
            Information current = new Information();
            current.iconId = icons[i % icons.length];
            current.title = titles[i % titles.length];
            data.add(current);
        }
        return data;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
        if (savedInstanceState != null) {
            mFromSavedInstancestate = true;
        }
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {

        containerView = getActivity().findViewById(fragmentId);

        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer + "");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if (slideOffset < 0.6) {
                    toolbar.setAlpha(1 - slideOffset);
                }

            }
        };

        if (!mUserLearnedDrawer && !mFromSavedInstancestate) {
            mDrawerLayout.openDrawer(containerView);
        }

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public static String readPreferences(Context context, String preferenceName, String defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return preferences.getString(preferenceName, defaultValue);
    }

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector mGestureDetector;
        private ClickListener mClickListener;

        public RecyclerTouchListener(Context context, RecyclerView recyclerView, final ClickListener clickListener) {
            Log.d("Bojie", "constructor invoked ");
            mClickListener = clickListener;
            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    Log.d("Bojie", "onSingleTapUp " + e);
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, mRecyclerView.getChildAdapterPosition(child));
                    }
                    Log.d("Bojie", "onLongPress " + e);
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && mClickListener != null && mGestureDetector.onTouchEvent(e)) {
                mClickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            Log.d("Bojie", "onTouchEvent " + e);
        }
    }

    public static interface ClickListener {
        public void onClick(View view, int postion);

        public void onLongClick(View view, int positon);
    }
}
