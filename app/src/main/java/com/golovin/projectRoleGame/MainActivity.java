package com.golovin.projectRoleGame;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public static Player gamer = new Player (); // создание объекта главного героя (ГГ)
    public static Player bot = new Player (); // создание обьекта бота
    public static Shop store = new Shop (); // создание объекта магазина

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bNewGame:
                Intent intent = new Intent(this, funcMenu.class);
                startActivity(intent);
                break;
            case R.id.bExitGame:
                moveTaskToBack(true);
                super.onDestroy();
                System.runFinalizersOnExit(true);
                System.exit(0);
                break;
            case R.id.bLoadGame:
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput("save.txt")));
                    LoadClass.loadGamePlayer(gamer, br);
                    LoadClass.loadGamePlayer(bot, br);
                    Toast.makeText(this, "Загружено", Toast.LENGTH_SHORT).show();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
                Intent intent1 = new Intent(this,  mainFuncMenu.class);
                startActivity(intent1);
            default:
                break;
        }
    }


}
