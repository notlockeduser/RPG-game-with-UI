package com.golovin.projectRoleGame;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SaveClass {



    public static void saveGamePlayer(Player Object, BufferedWriter bw) throws IOException {
        bw.write(Object.getMoney() + "\n");
        bw.write(Object.getLevel() + "\n");
        bw.write(Object.getName() + "\n");
        bw.write(Object.getType() + "\n");
        bw.write(Object.getDamage() + "\n");
        bw.write(Object.getHealth() + "\n");

        int n = 0;
        while (Object.listHelpers.get(n) != null) {
            if (n + 1 == GlobalVar.getNumHelp()) {
                n++;
                break;
            } else n++;
        }

        bw.write(n + "\n");
        for (int i = 0; i < n; i++) {
            bw.write(Object.listHelpers.get(i).getType() + "\n");
            bw.write(Object.listHelpers.get(i).getDamage() + "\n");
            bw.write(Object.listHelpers.get(i).getHealth() + "\n");
            bw.write(Object.listHelpers.get(i).getCost() + "\n");

        }
    }
}
