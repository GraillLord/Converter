package com.example.neo.convertisseur;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    FragmentPagerAdapter adapterViewPager;
    public static String centre;
    public static String centre_val;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        setupUI(findViewById(R.id.snd_layout));
        LinearLayout layout = (LinearLayout) findViewById(R.id.snd_layout);
        TextView clayout = (TextView) findViewById(R.id.centlayout);
        Button cbutton = (Button) findViewById(R.id.centbutton);
        cbutton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_backarr, 0, 0, 0);
        ViewPager vpPager = (ViewPager) findViewById(R.id.vPager);
        PagerTabStrip pagerTabStrip = (PagerTabStrip) findViewById(R.id.pager_header);
        pagerTabStrip.setDrawFullUnderline(true);
        pagerTabStrip.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        pagerTabStrip.setTextColor(Color.WHITE);
        pagerTabStrip.setTabIndicatorColor(Color.WHITE);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        if(getIntent().getExtras() != null)
            centre = getIntent().getExtras().getString("centre");
        switch(centre) {
            case "Corse" :
                layout.setBackgroundResource(R.drawable.corse);
                clayout.setText("Corse");
                centre_val = "757";
                break;
            case "Guadeloupe" :
                layout.setBackgroundResource(R.drawable.guadeloupe);
                clayout.setText("Guadeloupe");
                centre_val = "761";
                break;
            case "Guyane" :
                layout.setBackgroundResource(R.drawable.guyane);
                clayout.setText("Guyane");
                centre_val = "764";
                break;
            case "Martinique" :
                layout.setBackgroundResource(R.drawable.martinique);
                clayout.setText("Martinique");
                centre_val = "762";
                break;
            case "R\u00e9union" :
                layout.setBackgroundResource(R.drawable.reunion);
                clayout.setText("R\u00e9union");
                centre_val = "763";
                break;
        }

        cbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 2;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0: //fragment_a -> page 1
                    return FragmentA.newInstance(1, "PDS->PRM");
                case 1: //fragment_b -> page 2
                    return FragmentB.newInstance(2, "PRM->PDS");
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch(position) {
                case 0: //titre de page 1
                    return "PDS->PRM";
                case 1: //titre de page 2
                    return "PRM->PDS";
                default:
                    return null;
            }
        }
    }

    public void setupUI(View view) {
        int i;
        //set up touch listener pour élément qui ne sont pas des boites à texte
        if(!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Utility.hideSoftKeyboard(SecondActivity.this);
                    return false;
                }
            });
        }

        //si un layout container, alors iterate sur les enfants de manière recusrive
        if(view instanceof ViewGroup) {
            for(i=0; i<((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    public SecondActivity() {
        super();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}