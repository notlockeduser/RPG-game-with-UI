package com.golovin.projectRoleGame;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static com.golovin.projectRoleGame.Battle.recalculationForce;
import static com.golovin.projectRoleGame.MainActivity.bot;
import static com.golovin.projectRoleGame.MainActivity.gamer;

public class mainFuncMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_func_menu);
        TextView level = (TextView) findViewById(R.id.level);
        TextView money = (TextView) findViewById(R.id.money);
        TextView helpers = (TextView) findViewById(R.id.helpers);
        level.setText("Уровень " + Integer.toString(gamer.getLevel()));
        money.setText("Деньги " + Integer.toString(gamer.getMoney()));
        helpers.setText("Помощнки " + Integer.toString(getNumNowHelpers()) + "/" + Integer.toString(GlobalVar.getNumHelp()));
    }

    private int getNumNowHelpers() {
        int n=0;
        for (int i = 0; i < GlobalVar.getNumHelp(); i++)
            if (gamer.listHelpers.get(i) != null) n++;
        return n;
    }

    public void onClickGoToShop(View v) {
        Intent intent = new Intent(this, Store.class);
        startActivity(intent);
    }

    public void onClickExit(View v) {
        moveTaskToBack(true);
        super.onDestroy();
        System.runFinalizersOnExit(true);
        System.exit(0);
    }


    public String inString(int i) {
        String str = "";
        str += (i + 1) + ". - Тип = " + gamer.listHelpers.get(i).getType() +
                "\nУрон = " + gamer.listHelpers.get(i).getDamage() +
                "\nЗдоровье = " + gamer.listHelpers.get(i).getHealth() +
                "\nСтоймость = " + (int) (gamer.listHelpers.get(i).getCost() / 2) + "\n";
        return str;
    }

    public void onClickStats(View v) {
        String resString = "", Result;
        for (int i = 0; i < GlobalVar.getNumHelp(); i++)
            if (gamer.listHelpers.get(i) != null)
                resString += inString(i);
        AlertDialog.Builder a_builder = new AlertDialog.Builder(mainFuncMenu.this);
        Result = "Ваш герой\n" +
                "\nИмя = " + gamer.getName() +
                "\nТип = " + gamer.getType() +
                "\nУрон = " + gamer.getDamage() +
                "\nЗдоровье = " + gamer.getHealth() +
                "\nУровень = " + gamer.getLevel();
        if (resString != "")
            Result += "" + "\nВаши персонажи\n" + "\n" + resString;

        a_builder.setMessage(Result);

        AlertDialog alert = a_builder.create();
        alert.setTitle("Внимание");
        alert.show();
    }

    public void onClickSave(View v) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput("save.txt", MODE_PRIVATE)));
            SaveClass.saveGamePlayer(gamer, bw);
            SaveClass.saveGamePlayer(bot, bw);
            bw.close();
            Toast.makeText(this, "Игра сохранена", Toast.LENGTH_SHORT).show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void onClickGoToBattle(View v){
        fastBattle(gamer,bot);
    }

    public void fastBattle(Player gamer, Player bot) {
        final Intent intent = new Intent(this, mainFuncMenu.class);
        int allDamagePlayer = gamer.getDamage();
        int allHealthPlayer = gamer.getHealth();
        int allDamageBot = bot.getDamage();
        int allHealthBot = bot.getHealth();
        for (int i = 0; i < GlobalVar.getNumHelp(); i++) {
            if (gamer.listHelpers.get(i) != null) {
                allDamagePlayer += gamer.listHelpers.get(i).getDamage();
                allHealthPlayer += gamer.listHelpers.get(i).getHealth();
            }
            if (bot.listHelpers.get(i) != null) {
                allDamageBot += bot.listHelpers.get(i).getDamage();
                allHealthBot += bot.listHelpers.get(i).getHealth();
            }
        }
        ////
        String Result;
        AlertDialog.Builder a_builder = new AlertDialog.Builder(mainFuncMenu.this);
        Result = "\nОбщий урон игрока = " + allDamagePlayer +
                "\nОбщее здоровье игрока = " + allHealthPlayer +
                "\nОбщий урон бота = " + allDamageBot +
                "\nОбщее здоровье бота = " + allHealthBot;


        while (allHealthPlayer > 0 || allHealthBot > 0) {
            allHealthPlayer -= allDamageBot;
            allHealthBot -= allDamagePlayer;
        }
        if ((allHealthPlayer - allDamageBot) > (allHealthBot - allDamagePlayer)) {
            Result+=("\n\n --- Победа --- \n");
            recalculationForce(gamer);
            recalculationForce(bot);
            gamer.setMoney(gamer.getMoney() + 100);
        } else if ((allHealthPlayer - allDamageBot) == (allHealthBot - allDamagePlayer)) {
            Result+=("\n\n --- Ничья --- \n");
            gamer.setMoney(gamer.getMoney() + 30);
        } else if ((allHealthPlayer - allDamageBot) < (allHealthBot - allDamagePlayer)) {
            Result+=("\n\n --- Проигрыш ---\n");
            gamer.setMoney(gamer.getMoney() + 10);
        }
        Shop.changeHelpersBot(bot);
        a_builder.setMessage(Result)
                .setCancelable(false)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = a_builder.create();
        alert.setTitle("Битва");
        alert.show();

    }

}
