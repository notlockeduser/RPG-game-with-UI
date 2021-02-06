package com.golovin.projectRoleGame;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import static com.golovin.projectRoleGame.MainActivity.bot;
import static com.golovin.projectRoleGame.MainActivity.gamer;

public class funcMenu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_func_menu);
        // получаем объект RadioGroup
        RadioGroup radGrp = (RadioGroup) findViewById(R.id.radios);
        // обработка переключения состояния переключателя
        radGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int id) {
                switch (id) {
                    case R.id.radioWarrior:
                        chooseHero(gamer, 1);
                        break;
                    case R.id.radioArcher:
                        chooseHero(gamer, 2);
                        break;
                    case R.id.radioMag:
                        chooseHero(gamer, 3);
                        break;
                }
            }
        });
    }


    private static void settingsBot(Player bot) {
        bot.setName("Bot");
        chooseHero(bot, (int) (Math.random() * 2 + 1));
    }

    private static void chooseHero(Player Object, int n) {
        switch (n) {
            case 1:
                Object.setType("Рыцарь");
                Object.setDamage((int) (Math.random() * 35 + 30));
                Object.setHealth((int) (Math.random() * 30 + 100));
                break;
            case 2:
                Object.setType("Лучник");
                Object.setDamage((int) (Math.random() * 10 + 45));
                Object.setHealth((int) (Math.random() * 25 + 80));
                break;
            case 3:
                Object.setType("Маг");
                Object.setDamage((int) (Math.random() * 15 + 50));
                Object.setHealth((int) (Math.random() * 20 + 60));
                break;
        }
    }


    public void onButtonClick(View v) {
        EditText name = (EditText) findViewById(R.id.nameGamer);
        AlertDialog.Builder a_builder = new AlertDialog.Builder(funcMenu.this);
        if (!(name.getText().toString().length() > 0) || (name.getText().toString().equals(" ") ||
        (gamer.getType() == null))){
            a_builder.setMessage("Введите коректно данные");
            AlertDialog alert = a_builder.create();
            alert.setTitle("Внимание");
            alert.show();
        } else {
            gamer.setName(name.getText().toString());
            final Intent intent = new Intent(this, mainFuncMenu.class);

            a_builder.setMessage("Ваш герой\n" +
                    "\nИмя = " + gamer.getName() +
                    "\nТип = " + gamer.getType() +
                    "\nУрон = " + gamer.getDamage() +
                    "\nЗдоровье = " + gamer.getHealth())
                    .setCancelable(false)
                    .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            settingsBot(bot);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = a_builder.create();
            alert.setTitle("Подтверждение");
            alert.show();
        }
    }
}
