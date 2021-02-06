package com.golovin.projectRoleGame;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static com.golovin.projectRoleGame.MainActivity.gamer;
import static com.golovin.projectRoleGame.MainActivity.store;

public class Buy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        TextView text4 = (TextView) findViewById(R.id.money);
        text4.setText("Деньги - " + Integer.toString(gamer.getMoney()));
        TextView good = (TextView) findViewById(R.id.good1);
        for (int i = 0; i < GlobalVar.getNumShop(); i++)
            resString += inString(store, i);
        good.setText(resString);
    }


    public String inString(Shop store, int i) {
        String str = "";
        str += (i + 1) + ". - Тип = " + store.listGoods.get(i).getType() +
                "\nУрон = " + store.listGoods.get(i).getDamage() +
                "\nЗдоровье = " + store.listGoods.get(i).getHealth() +
                "\nСтоймость = " + store.listGoods.get(i).getCost() + "\n";
        return str;
    }

    String resString = "";

    private void buyHelper(Shop store, int n) {
        AlertDialog.Builder a_builder = new AlertDialog.Builder(Buy.this);
        if (((gamer.listHelpers.get(GlobalVar.getNumHelp() - 1)) == null)) {
            if ((gamer.getMoney() - store.listGoods.get(n - 1).getCost()) >= 0) {
                for (int i = 0, k = 0; k == 0; i++)
                    if (gamer.listHelpers.get(i) == null) {
                        gamer.listHelpers.set(i, store.listGoods.get(n - 1).clone());
                        k = 1;
                    }
                gamer.setMoney(gamer.getMoney() - store.listGoods.get(n - 1).getCost());
                Toast.makeText(this, "Помощник куплен", Toast.LENGTH_SHORT).show();
            } else {
                a_builder.setMessage("У вас не хватает денег");
                AlertDialog alert = a_builder.create();
                alert.setTitle("Подтверждение");
                alert.show();
            }

        } else {
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
    }

    public void onClick(View v) {
        resString = "";
        int n;
        TextView good = (TextView) findViewById(R.id.good1);
        TextView money = (TextView) findViewById(R.id.money);
        switch (v.getId()) {
            case R.id.bSale1:
                n = 1;
                buyHelper(store, n);
                break;
            case R.id.bSale2:
                n = 2;
                buyHelper(store, n);
                break;
            case R.id.bSale3:
                n = 3;
                buyHelper(store, n);
                break;
        }
        for (int i = 0; i < GlobalVar.getNumShop(); i++)
            resString += inString(store, i);
        good.setText(resString);
        money.setText("Деньги -  " + gamer.getMoney());
    }
}
