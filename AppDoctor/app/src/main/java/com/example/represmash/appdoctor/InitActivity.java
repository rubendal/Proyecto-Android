package com.example.represmash.appdoctor;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class InitActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public RegistroFragment rFragment;
    public MenuFragment mFragment;
    public AlertaFragment aFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setTitle(R.string.app_name);

        getFragmentManager().beginTransaction().replace(R.id.contenido, new BienvenidaFragment()).commit();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            if (getFragmentManager().getBackStackEntryCount() == 0){
                salirAlerta();
            } else {
                getFragmentManager().popBackStack();
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            rFragment = new RegistroFragment();
            getFragmentManager().beginTransaction().replace(R.id.contenido,rFragment).addToBackStack("-").commit();
        } else if (id == R.id.nav_gallery) {
            getFragmentManager().beginTransaction().replace(R.id.contenido, new ListaPacientesFragment()).addToBackStack("--").commit();
            //rFragment = null;
        } else if (id == R.id.salir){
            salirAlerta();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void registrarPaciente2(View view){
        rFragment.registrarPaciente2(view);
    }

    public void mandarAlerta2(View view){
        mFragment.mandarAlerta2(view);
    }

    public void llamar2(View view){
        mFragment.llamar2(view);
    }

    public void enviar2 (View view) {
        aFragment.enviar2(view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!isServiceRunning(Servicio.class)) {
            startService(new Intent(this, Servicio.class));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //stopService(new Intent(this, Servicio.class));

    }

    private void salirAlerta(){
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.salir))
                .setMessage(R.string.salida)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
