package com.apps.lucho.busappwithtabs;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private String[] routesArray =  {"B1","B11","B12","B13","B14","B15","B16","B17","B2","B20","B24","B25","B26","B3",
                                     "B31","B32","B35","B36","B37","B38","B39","B4","B41","B42","B43","B44","B44-SBS",
                                     "B45","B46","B47","B48","B49","B52","B54","B57","B6","B60","B61","B62","B63","B64",
                                     "B65","B67","B68","B69","B7","B70","B74","B8","B82","B83","B84","B9","Bx3","Bx4",
                                     "Bx4A","Bx5","Bx6","Bx7","Bx8","Bx9","Bx1","Bx10","Bx11","Bx12","Bx12-SBS","Bx13",
                                     "Bx15","Bx16","Bx17","Bx18","Bx19","Bx2","Bx20","Bx21","Bx22","Bx24","Bx26","Bx27",
                                     "Bx28","Bx29","Bx30","Bx31","Bx32","Bx33","Bx34","Bx35","Bx36","Bx38","Bx39","Bx40",
                                     "Bx41","Bx41-SBS","Bx42","Bx46","Bx55","M1","M2","M3","M4","M5","M7","M8","M9","M10",
                                     "M100","M101","M102","M103","M104","M106","M11","M116","M12","M14A","M14D","M15",
                                     "M15-SBS","M20","M21","M22","M23","M31","M34-SBS","M34A-SBS","M35","M42","M50","M57",
                                     "M60","M60-SBS","M66","M72","M79","M86","M96","M98","S1115","Q1","Q2","Q3","Q4","Q5",
                                     "Q12","Q13","Q15","Q15A","Q16","Q17","Q20A","Q20B","Q24","Q26","Q27","Q28","Q30","Q31",
                                     "Q32","Q36","Q42","Q43","Q44","Q46","Q48","Q54","Q55","Q56","Q58","Q59","Q76","Q77",
                                     "Q83","Q84","Q85","Q88","S40","S42","S44","S46","S48","S51","S52","S53","S54","S55",
                                     "S56","S57","S59","S61","S62","S66","S74","S76","S78","S79","S79-SBS","S81","S84","S86",
                                     "S89","S90","S91","S92","S93","S94","S96","S98","X1","X10B","X10","X11","X12","X14",
                                     "X15","X17","X17A","X17J","X19","X2","X21","X22","X22A","X27","X28","X3","X30","X31",
                                     "X37","X38","X4","X42","X5","X63","X64","X68","X7","X8","X9"};


    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private AutoCompleteTextView actv;
    private String busEntered;
    private TabLayout tabLayout;

    private SharedPreferences pref;
    //private String spinnerArray[] = new String[3];
    private String spinnerArray[] = {"B1","B11","B12"};

    //private boolean isSpinnerTouched = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); // hide keyboard on startup
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);  // prevent activity to move up

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


//        SharedPreferences prefsGet = this.getSharedPreferences("data", MODE_PRIVATE);
//        spinnerArray = prefsGet.getString("recents","").split(",");


        ArrayAdapter<String> adapterSp = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapterSp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) findViewById(R.id.spinner);
        sItems.setAdapter(adapterSp);

//        sItems.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                isSpinnerTouched = true;
//                return false;
//            }
//        });

        int initialPos = sItems.getSelectedItemPosition();
        sItems.setSelection(initialPos, false);

        sItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                //if (!isSpinnerTouched) return;
                actv.setText(parentView.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, routesArray);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.my_dropdown_item1, routesArray);
        actv.setThreshold(1);
        actv.setAdapter(adapter);

    }

    public void btnClicked(View view){

        busEntered = actv.getText().toString();

        if (!isValidRoute(busEntered)) {
            Toast.makeText(getApplicationContext(), "Not a valid bus route.", Toast.LENGTH_LONG).show();
            return;
        }

        for (int i = 0; i < spinnerArray.length-1; i++) {
            spinnerArray[i+1] = spinnerArray[i];
        }
        spinnerArray[0] = busEntered;


        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        // Create the adapter that will return a fragment for each of the two
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setVisibility(View.VISIBLE);
        tabLayout.setupWithViewPager(mViewPager);

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Tab1NorthBus t1 = new Tab1NorthBus();
                    Log.d(TAG, "TAB1 Frag INIT");
                    Bundle args1 = new Bundle();
                    args1.putString("busCode", busEntered);
                    t1.setArguments(args1);
                    return t1;
                case 1:
                    Tab2SouthBus t2 = new Tab2SouthBus();
                    Log.d(TAG, "TAB2 Frag INIT");
                    Bundle args2 = new Bundle();
                    args2.putString("busCode", busEntered);
                    t2.setArguments(args2);
                    return t2;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "";
                    //return "NORTH";
                case 1:
                    return "";
                    //return "SOUTH"
            }
            return null;
        }

    }

    // method to check for valid bus input
    private boolean isValidRoute(String bus){
        for (int i = 0; i < routesArray.length; i++) {
            if (bus.equals(routesArray[i])) return true;
        }
        return false;
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
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < spinnerArray.length; i++) {
//            sb.append(spinnerArray[i]).append(",");
//        }
//
//        pref = getSharedPreferences("data", MODE_PRIVATE);
//        SharedPreferences.Editor editor = pref.edit();
//        editor.putString("recents", sb.toString());
//        editor.apply();

        // Writing data to SharedPreferences
        //PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("data", sb.toString()).apply();

    }
}