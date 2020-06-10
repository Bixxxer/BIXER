package biz.bixer.bixer;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        drawer.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.tvBackground));
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    private static long back_pressed;
    @Override
    public void onBackPressed() {
        Fragment fragment = null;
        Class fragmentClass = null;
        if (back_pressed + 500 > System.currentTimeMillis()) {
            new AlertDialog.Builder(this)
                    .setTitle("Really exit?")
                    .setMessage("Are you sure you want to exit?")
                    .setNegativeButton(android.R.string.no,null)
                    .setPositiveButton(android.R.string.yes,new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface arg0,int arg1)
                        {
                            MainActivity.super.onBackPressed();
                        }
                    }).create().show();
        }
        back_pressed = System.currentTimeMillis();
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment fragment = null;
        int id = item.getItemId();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;
        Class fragmentClass = null;
        int id = item.getItemId();
       if (id == R.id.nav_analytics) {
            fragmentClass = SecondFragment.class;
        }
        else if(id == R.id.nav_checker) {
            fragmentClass=Bitcoin_checker.class;
        }
       else if (id == R.id.nav_about) {
            fragmentClass= FourthFragment.class;
        }
         else if (id == R.id.nav_help) {
            fragmentClass= SixthFragment.class;
        }
        else if(id==R.id.nav_analytic)
        {
            fragmentClass= StatisticsFragment.class;
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();

        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
        item.setChecked(true);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}