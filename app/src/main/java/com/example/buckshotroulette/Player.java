package com.example.buckshotroulette;

import androidx.annotation.NonNull;

import java.util.Random;

/*
 * 0空;1放大镜;2饮料;3小刀;4香烟;5手铐
 * */
public class Player {
    int health;
    int[] goods;
    int hurt = 1;
    boolean isLock = false;
    Random random = new Random();

    public Player(int health, int[] goods) {
        this.health = health;
        this.goods = goods;
    }

    public boolean isLock() {
        return isLock;
    }

    public void setLock(boolean lock) {
        isLock = lock;
    }


    public Player(int health) {
        this.health = health;
        goods = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
    }

    public int getHealth() {
        return health;
    }

    public int getHurt() {
        return hurt;
    }

    public void setHurt(int hurt) {
        this.hurt = hurt;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int[] getGoods() {
        return goods;
    }

    public void setGoods(int[] goods) {
        this.goods = goods;
    }

    @NonNull
    @Override
    public String toString() {
        String s = "PH:" + health + ";" + "GOODS:" + goods[0] + "-" + goods[1] + "-" + goods[2] + "-" + goods[3] + "-" + goods[4] + "-" + goods[5] + "-" + goods[6] + "-" + goods[7];
        return s;
    }

    public void motifyGoods(int index, int num) {
        goods[index] = num;
    }

    public void injured() {
        if (hurt == 1) {
            health -= 1;
        } else {
            health -= 2;
            hurt = 1;
        }

    }

    public void addGoods(int num) {
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < 8; j++) {
                if (goods[j] == 0) {
                    goods[j] = random.nextInt(5) + 1;
                    break;
                }
            }
        }
    }
}
