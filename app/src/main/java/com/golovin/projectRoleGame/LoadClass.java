package com.golovin.projectRoleGame;

import java.io.BufferedReader;
import java.io.IOException;

public class LoadClass {
    public static void loadGamePlayer(Player Object, BufferedReader br) throws IOException {
        Object.setMoney(Integer.parseInt(br.readLine()));
        Object.setLevel(Integer.parseInt(br.readLine()));
        Object.setName(br.readLine());
        Object.setType(br.readLine());
        Object.setDamage(Integer.parseInt(br.readLine()));
        Object.setHealth(Integer.parseInt(br.readLine()));

        int n = Integer.parseInt(br.readLine());
        for (int i = 0; i < n; i++) {
            String type = br.readLine();
            int damage = Integer.parseInt(br.readLine());
            int health = Integer.parseInt(br.readLine());
            int cost = Integer.parseInt(br.readLine());
            Warrior temp = new Warrior(type, damage, health, cost);
            Object.listHelpers.set(i, temp.clone());
        }
    }
}
