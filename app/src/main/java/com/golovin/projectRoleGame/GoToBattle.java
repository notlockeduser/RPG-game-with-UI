package com.golovin.projectRoleGame;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import static com.golovin.projectRoleGame.Battle.recalculationForce;
import static com.golovin.projectRoleGame.MainActivity.bot;
import static com.golovin.projectRoleGame.MainActivity.gamer;

public class GoToBattle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_to_battle);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bGoToBattle:
                Intent intent = new Intent(this, BattleMove.class);
                startActivity(intent);
                break;
            case R.id.bGoToSimpleBattle:
                fastBattle(gamer,bot);
                break;
        }
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
        AlertDialog.Builder a_builder = new AlertDialog.Builder(GoToBattle.this);
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
                        startActivity(intent);
                    }
                });
        AlertDialog alert = a_builder.create();
        alert.setTitle("Битва");
        alert.show();

    }
}
