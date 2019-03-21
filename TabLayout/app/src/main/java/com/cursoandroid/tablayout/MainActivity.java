package com.cursoandroid.tablayout;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.widget.TableLayout;

import com.cursoandroid.tablayout.adapters.FragmentAdpater;

public class MainActivity extends AppCompatActivity {

    private TabLayout mtabLayout;
    private ViewPager mviewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mtabLayout = findViewById(R.id.tabLayout);
        mviewPager = findViewById(R.id.view_pager);

        mviewPager.setAdapter(new FragmentAdpater(getSupportFragmentManager(),getResources().getStringArray(R.array.title_tab)));

        mtabLayout.setupWithViewPager(mviewPager);

    }
}
