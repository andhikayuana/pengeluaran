package id.yuana.pengeluaran.activites;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


import id.yuana.pengeluaran.R;
import id.yuana.pengeluaran.fragments.BulananFragment;
import id.yuana.pengeluaran.fragments.PengaturanFragment;
import id.yuana.pengeluaran.fragments.RekapKeuanganFragment;
import id.yuana.pengeluaran.fragments.RencanaBelanjaFragment;
import id.yuana.pengeluaran.fragments.TambahAkunFragment;
import id.yuana.pengeluaran.fragments.TambahKategoriFragment;
import id.yuana.pengeluaran.fragments.TransferFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        gotoBulananFragment();

    }

    private void gotoBulananFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frContainer, BulananFragment.newInstance())
                .commit();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.nav_semua_catatan:

                gotoBulananFragment();

                break;

            case R.id.nav_rencana_belanja:

                fragment = RencanaBelanjaFragment.newInstance();

                break;

            case R.id.nav_rekap_keuangan:

                fragment = RekapKeuanganFragment.newInstance();

                break;

            case R.id.nav_tambah_kategori:

                fragment = TambahKategoriFragment.newInstance();

                break;

            case R.id.nav_transfer:

                fragment = TransferFragment.newInstance();

                break;

            case R.id.nav_tambah_akun:

                fragment = TambahAkunFragment.newInstance();

                break;

            case R.id.nav_pengaturan:

                fragment = PengaturanFragment.newInstance();

                break;
        }

        if (fragment != null) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frContainer, fragment)
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
