package com.ikhiloyaimokhai.dependentworkmanagerperiodictask.view.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.EmployeeApp;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.R;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //start workers.
        EmployeeApp.get().startWorkers();

        NavController navController = Navigation.findNavController(MainActivity.this, R.id.navHostFragment);

        NavigationUI.setupActionBarWithNavController(MainActivity.this, navController);
        appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(MainActivity.this, R.id.navHostFragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration);
    }


}


