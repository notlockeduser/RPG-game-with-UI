package com.golovin.projectRoleGame;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static com.golovin.projectRoleGame.MainActivity.gamer;

public class Sale extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);
        TextView text4 = (TextView) findViewById(R.id.money);
        text4.setText("Деньги - " + Integer.toString(gamer.getMoney()));
        TextView good = (TextView) findViewById(R.id.good1);
        for (int i = 0; i < GlobalVar.getNumHelp(); i++)
            if (gamer.listHelpers.get(i) != null)
                resString += inString(i);
        good.setText(resString);
    }

    String resString = "";

    public String inString(int i) {
        String str = "";
        str += (i + 1) + ". - Тип = " + gamer.listHelpers.get(i).getType() +
                "\nУрон = " + gamer.listHelpers.get(i).getDamage() +
                "\nЗдоровье = " + gamer.listHelpers.get(i).getHealth() +
                "\nСтоймость = " + (int) (gamer.listHelpers.get(i).getCost() / 2) + "\n";
        return str;
    }

    private void saleHelper(int n) {
        AlertDialog.Builder a_builder = new AlertDialog.Builder(Sale.this);
        if (gamer.listHelpers.get(0) != null) {
            gamer.setMoney(gamer.getMoney() + (int) (gamer.listHelpers.get(n - 1).getCost() / 2)); // добавление денег за продажу персонажа
            gamer.listHelpers.set(n - 1, null); // удаление помощника
            for (int i = 0; i < GlobalVar.getNumHelp() - 1; i++)
                if (gamer.listHelpers.get(i) == null && gamer.listHelpers.get(i + 1) != null) {
                    gamer.listHelpers.set(i, gamer.listHelpers.get(i + 1).clone());
                    gamer.listHelpers.set(i + 1, null);
                }
            Toast.makeText(this, "Помощник продан", Toast.LENGTH_SHORT).show();
        } else {
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
    }

    public void onClick(View v) {
        resString = "";
        int n;
        TextView good = (TextView) findViewById(R.id.good1);
        TextView money = (TextView) findViewById(R.id.money);
        switch (v.getId()) {
            case R.id.bSale1:
                n = 1;
                if (gamer.listHelpers.get(n - 1) != null)
                    saleHelper(n);
                break;
            case R.id.bSale2:
                n = 2;
                if (gamer.listHelpers.get(n - 1) != null)
                    saleHelper(n);
                break;
            case R.id.bSale3:
                n = 3;
                if (gamer.listHelpers.get(n - 1) != null)
                    saleHelper(n);
                break;
        }
        for (int i = 0; i < GlobalVar.getNumHelp(); i++)
            if (gamer.listHelpers.get(i) != null)
                resString += inString(i);
        if (resString != "") {
            good.setText(resString);
        } else {
            good.setText("У вас больше нет помощников");
        }
        money.setText("Деньги -  " + gamer.getMoney());
    }
}
