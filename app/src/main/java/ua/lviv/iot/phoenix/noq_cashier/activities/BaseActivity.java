package ua.lviv.iot.phoenix.noq_cashier.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import ua.lviv.iot.phoenix.noq_cashier.R;
import ua.lviv.iot.phoenix.noq_cashier.fragments.NewOrdersFragment;
import ua.lviv.iot.phoenix.noq_cashier.fragments.AcceptedOrdersFragment;
import ua.lviv.iot.phoenix.noq_cashier.fragments.OrderFragment;
import ua.lviv.iot.phoenix.noq_cashier.fragments.OrderFragmentToAcceptedOrderFragment;

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private Fragment fragment;
    private DrawerLayout drawerLayout;
    private String pointer;
    private Toolbar toolbar;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        fragment = new NewOrdersFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.base, fragment).commit();
        pointer = "NewOrdersFragment";


        toolbar = findViewById(R.id.toolbar);
        try {
            setSupportActionBar(toolbar);
        } catch (Exception e) {
            e.printStackTrace();
        }
        toolbar.setTitle("Нові Замовлення");

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        System.out.println("!!!!!!pointer = "+ pointer);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            closeDrawer();
        } else {
            switch (pointer) {
                case "NewOrdersFragment": {
                    break;
                }
                case "OrderFragment": {
                    goBackToNewOrdersFragment();
                    break;
                }
                case "AcceptedOrderFragment": {
                    goBackToNewOrdersFragment();
                    break;
                }
                case "OrderFragmentFromNewOrderFragment": {
                    goBackToNewOrdersFragment();
                    break;
                }
                case "OrderFragmentFromAcceptedOrderFragment": {
                    goBackToAcceptedOrderFragment();
                    break;
                }
                default:{
                    super.onBackPressed();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.base, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {


        int id = menuItem.getItemId();
        Fragment fragment = null;


        if (id == R.id.nav_new_orders) {

            fragment = new NewOrdersFragment();
            toolbar.setTitle("Нові Замовлення");

        } else if (id == R.id.nav_accepted_orders) {

            fragment = new AcceptedOrdersFragment();
            toolbar.setTitle("Підтверджені Замовлення");

        } else if (id == R.id.nav_exit) {
            if (mAuth == null)
                return false;
            mAuth.signOut();
            finish();
            startActivity(new Intent(this, SignInActivity.class));


        } else {

            System.out.println("default");
        }

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(this.fragment.getId(), fragment)
                    .commit();
            this.fragment = fragment;
        }



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    public void goToOrderFragmentFromNewOrderFragment(View view) {
        setFragment(new OrderFragment());
        pointer = "OrderFragmentFromNewOrderFragment";
        toolbar.setTitle("Замовлення");
    }

    public void goToOrderFragmentFromAcceptedOrderFragment(View view) {
        setFragment(new OrderFragmentToAcceptedOrderFragment());
        pointer = "OrderFragmentFromAcceptedOrderFragment";
        toolbar.setTitle("Замовлення");
    }

    public void goBackToNewOrdersFragment() {
        setFragment(new NewOrdersFragment());
        pointer = "NewOrdersFragment";
        toolbar.setTitle("Нові Замовлення");
    }

    public void goToNewOrdersFragment(View view) {
        setFragment(new NewOrdersFragment());
        pointer = "NewOrdersFragment";
        toolbar.setTitle("Нові Замовлення");
    }

    public void goToAcceptedOrderFragment(View view) {
        setFragment(new AcceptedOrdersFragment());
        pointer = "AcceptedOrderFragment";
        toolbar.setTitle("Підтверджені Замовлення");
    }

    public void goBackToAcceptedOrderFragment() {
        setFragment(new AcceptedOrdersFragment());
        pointer = "AcceptedOrderFragment";
        toolbar.setTitle("Підтверджені Замовлення");
    }


    private void setFragment(Fragment f) {
        Bundle args = fragment.getArguments();
        fragment = f;
        fragment.setArguments(args);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.base, fragment)
                .commit();
    }

}
