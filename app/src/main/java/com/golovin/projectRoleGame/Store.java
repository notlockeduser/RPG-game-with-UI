package com.golovin.projectRoleGame;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import static com.golovin.projectRoleGame.MainActivity.gamer;

public class Store extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bGoToBattle:
                // если последнего помощника нет то продолжить покупку
                if (((gamer.listHelpers.get(GlobalVar.getNumHelp() - 1)) == null)) {
                    Intent intent1 = new Intent(this, Buy.class);
                    startActivity(intent1);
                } else {
                    AlertDialog.Builder a_builder = new AlertDialog.Builder(Store.this);
                    a_builder.setMessage("Лист помощников полон")
                            .setCancelable(false)
                            .setNegativeButton("Хорошо", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = a_builder.create();
                    alert.setTitle("Предупреждение");
                    alert.show();
                }

                break;
            case R.id.bGoToSimpleBattle:
                if (gamer.listHelpers.get(0) != null) {
                    Intent intent2 = new Intent(this, Sale.class);
                    startActivity(intent2);
                } else {
                    AlertDialog.Builder a_builder = new AlertDialog.Builder(Store.this);
                    a_builder.setMessage("У вас нет помощников")
                            .setCancelable(false)
                            .setNegativeButton("Хорошо", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = a_builder.create();
                    alert.setTitle("Предупреждение");
                    alert.show();
                }
                break;
            default:
                break;
        }
    }
}
