package com.golovin.projectRoleGame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Stack;

import static com.golovin.projectRoleGame.MainActivity.bot;
import static com.golovin.projectRoleGame.MainActivity.gamer;

public class BattleMove extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_move);
//        TextView vhe1 = (TextView) findViewById(R.id.vhe1);
//        TextView vhe2 = (TextView) findViewById(R.id.vhe2);
//        TextView vhe3 = (TextView) findViewById(R.id.vhe3);
//        TextView vhe4 = (TextView) findViewById(R.id.vhe4);
//        TextView vhe5 = (TextView) findViewById(R.id.vhe5);
//        TextView vhe6 = (TextView) findViewById(R.id.vhe6);
//        TextView vhe7 = (TextView) findViewById(R.id.vhe7);
//        TextView vhe8 = (TextView) findViewById(R.id.vhe8);
//
//        TextView vda1 = (TextView) findViewById(R.id.vda1);
//        TextView vda2 = (TextView) findViewById(R.id.vda2);
//        TextView vda3 = (TextView) findViewById(R.id.vda3);
//        TextView vda4 = (TextView) findViewById(R.id.vda4);
//        TextView vda5 = (TextView) findViewById(R.id.vda5);
//        TextView vda6 = (TextView) findViewById(R.id.vda6);
//        TextView vda7 = (TextView) findViewById(R.id.vda7);
//        TextView vda8 = (TextView) findViewById(R.id.vda8);
//
//        TextView vty1 = (TextView) findViewById(R.id.vty1);
//        TextView vty2 = (TextView) findViewById(R.id.vty2);
//        TextView vty3 = (TextView) findViewById(R.id.vty3);
//        TextView vty4 = (TextView) findViewById(R.id.vty4);
//        TextView vty5 = (TextView) findViewById(R.id.vty5);
//        TextView vty6 = (TextView) findViewById(R.id.vty6);
//        TextView vty7 = (TextView) findViewById(R.id.vty7);
//        TextView vty8 = (TextView) findViewById(R.id.vty8);
//
//        TextView vso1 = (TextView) findViewById(R.id.vso1);
//        TextView vso2 = (TextView) findViewById(R.id.vso2);
//        TextView vso3 = (TextView) findViewById(R.id.vso3);
//        TextView vso4 = (TextView) findViewById(R.id.vso4);
//        TextView vso5 = (TextView) findViewById(R.id.vso5);
//        TextView vso6 = (TextView) findViewById(R.id.vso6);
//        TextView vso7 = (TextView) findViewById(R.id.vso7);
//        TextView vso8 = (TextView) findViewById(R.id.vso8);

        //TextView console = (TextView) findViewById(R.id.console);

        //try {
        //    simpleBattle(gamer, bot);
     //   } catch (CloneNotSupportedException e) {
      //      e.printStackTrace();
      //  }


    }

    static int value = 0;
    static boolean control = true;
    Button button = new Button(this);
    ArrayList<BattleWarrior> listWarriorsGamer = new ArrayList<>(GlobalVar.getNumHelp() + 1);
    ArrayList<BattleWarrior> listWarriorsBot = new ArrayList<>(GlobalVar.getNumHelp() + 1);
    // создание стеков для проверки жив ли кто то в команде
    Stack<Object> statusAliveTeamGamer = new Stack<>();
    Stack<Object> statusAliveTeamBot = new Stack<>();

    private static void simpleBattle(Player gamer, Player bot) throws CloneNotSupportedException {
        // создание двух массивов для битвы и заполнение копиями их -> герой + помощники
        ArrayList<BattleWarrior> listWarriorsGamer = new ArrayList<>(GlobalVar.getNumHelp() + 1);
        ArrayList<BattleWarrior> listWarriorsBot = new ArrayList<>(GlobalVar.getNumHelp() + 1);
        // создание стеков для проверки жив ли кто то в команде
        Stack<Object> statusAliveTeamGamer = new Stack<>();
        Stack<Object> statusAliveTeamBot = new Stack<>();
        // создание массивов со всеми героями для каждого игрока и заполнение стеков
        preparationToBattle(gamer, listWarriorsGamer, statusAliveTeamGamer);
        preparationToBattle(bot, listWarriorsBot, statusAliveTeamBot);


    }

    private static void preparationToBattle(Player player, ArrayList<BattleWarrior> listWarriors,
                                            Stack<Object> statusAliveTeam) {
        listWarriors.add(player.clone());
        statusAliveTeam.push(true);
        for (int i = 0; i < player.listHelpers.size() && player.listHelpers.get(i) != null; i++) {
            listWarriors.add(player.listHelpers.get(i).clone(true));
            statusAliveTeam.push(true);
        }

    }

    private void gameBattle(ArrayList<BattleWarrior> WarriorsGamer, ArrayList<BattleWarrior> WarriorsBot,
                            Player gamer, Player bot, Stack<Object> statusAliveTeamGamer,
                            Stack<Object> statusAliveTeamBot) throws CloneNotSupportedException {
        int countStep = 1; // счетчик ходов
        while (!statusAliveTeamBot.empty() && !statusAliveTeamGamer.empty()) {
            stepGamer(WarriorsGamer, WarriorsBot, countStep, statusAliveTeamGamer, statusAliveTeamBot); // ход Игрока
            stepBot(WarriorsGamer, WarriorsBot, statusAliveTeamGamer, statusAliveTeamBot); // ход Бота
            countStep++;
        }
        // показывает результат битвы и расчитывает данные для следущей битвы
         resultBattle (gamer,bot,statusAliveTeamGamer,statusAliveTeamBot);
    }

    private static void resultBattle
            (Player gamer,Player bot,Stack<Object> statusAliveTeamGamer,
             Stack<Object> statusAliveTeamBot) throws CloneNotSupportedException {

        if ((!statusAliveTeamGamer.empty ()) && (statusAliveTeamBot.empty ())) {
            System.out.print ("\n --- Победа --- \n");
            // с увеличением уровня увеличивается урон пресонажа и помощниов
            recalculationForce (gamer);
            recalculationForce (bot);
            gamer.setMoney (gamer.getMoney () + 100);
        } else if ((statusAliveTeamGamer.empty ()) && (statusAliveTeamBot.empty ())) {
            System.out.print ("\n --- Ничья ---\n");
            gamer.setMoney (gamer.getMoney () + 30);
        } else if ((statusAliveTeamGamer.empty ()) && (!statusAliveTeamBot.empty ())) {
            System.out.print ("\n --- Проигрыш --- \n");
            gamer.setMoney (gamer.getMoney () + 15);
        }
        Shop.changeHelpersBot (bot);
    }

    private static void recalculationForce(Player Object) {
        Object.setLevel (Object.getLevel () + 1);
        for (int i = 0; i < GlobalVar.getNumHelp (); i++)
            if (Object.listHelpers.get (i) != null) {
                Object.listHelpers.get (i).setDamage ((int) (Object.listHelpers.get (i).getDamage () + (1 + 0.05 * Object.getLevel ())));
                Object.listHelpers.get (i).setHealth ((int) (Object.listHelpers.get (i).getHealth () + (1 + 0.05 * Object.getLevel ())));
                Object.setDamage ((int) (Object.getDamage () + (1 + 0.05 * Object.getLevel ())));
                Object.setHealth ((int) (Object.getHealth () + (1 + 0.05 * Object.getLevel ())));
            }
    }

    public void goToConsole(String s) {
        TextView console = (TextView) findViewById(R.id.console);
        console.setText(s);
    }

    private void stepGamer(ArrayList<BattleWarrior> warriorsGamer, ArrayList<BattleWarrior> warriorsBot,
                           int countStep, Stack<Object> statusAliveTeamGamer, Stack<Object> statusAliveTeamBot) {
        goToConsole("\n*********************\n\t\t Ход " + countStep + "\n*********************");
        for (BattleWarrior warrior : warriorsGamer) { //каждый боец нанесет урон, если он живой
            if (warrior.getStatusAlive() && !statusAliveTeamBot.empty()) {
                while (true) {
                    if (!control)
                        break;
                }
                control = true;
                while (!warriorsBot.get(value).getStatusAlive()) {
                    goToConsole(" Этот войн уже мертв\n Выберете кого вы хотите ударить : ");
                    while (true) {
                        if (!control)
                            break;
                    }
                }
                makeDamage(warrior, warriorsBot.get(value), statusAliveTeamBot, statusAliveTeamGamer);
            }
        }
    }

    private static void stepBot(ArrayList<BattleWarrior> warriorsGamer, ArrayList<BattleWarrior> warriorsBot,
                                Stack<Object> statusAliveTeamGamer, Stack<Object> statusAliveTeamBot) {
        if (!statusAliveTeamBot.empty()) { // если команда Бота не мертва
            for (BattleWarrior warrior : warriorsBot) { //каждый боец нанесет урон, если он живой
                if (warrior.getStatusAlive() && !statusAliveTeamGamer.empty()) {
                    int value = (int) (Math.random() * warriorsGamer.size());
                    while (!warriorsBot.get(value).getStatusAlive()) {
                        value = (int) (Math.random() * warriorsGamer.size());
                    }
                    makeDamage(warrior, warriorsGamer.get(value), statusAliveTeamGamer, statusAliveTeamBot);
                }
            }
        }
    }

    private static void makeDamage(BattleWarrior warrior, BattleWarrior victim,
                                   Stack<Object> statusAliveTeam1, Stack<Object> statusAliveTeam2) {
        // боец наносит урон жертве
        victim.setHealth(victim.getHealth() - warrior.getDamage());
        // жертва отбивается 40% собственным уроном
        warrior.setHealth(warrior.getHealth() - (int) (0.4 * victim.getDamage()));

        // проверка живы ли бойцы после нанесения урона
        if (victim.getHealth() <= 0) {
            victim.setStatusAlive(false);
            victim.setHealth(0);
            statusAliveTeam1.pop();
        }
        if (warrior.getHealth() <= 0) {
            warrior.setStatusAlive(false);
            warrior.setHealth(0);
            statusAliveTeam2.pop();
        }
    }


    public void onClick(View v) {
        try {
            // алгоритм битвы
            gameBattle(listWarriorsGamer, listWarriorsBot, gamer, bot, statusAliveTeamGamer, statusAliveTeamBot);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public void onClickAttack(View v) {
        switch (v.getId()) {
            case R.id.but1:
                value = 0;
                control = false;
                break;
            case R.id.but2:
                value = 1;
                control = false;
                break;
            case R.id.but3:
                value = 4;
                control = false;
                break;
            case R.id.but4:
                value = 3;
                control = false;
                break;
        }
    }
}
