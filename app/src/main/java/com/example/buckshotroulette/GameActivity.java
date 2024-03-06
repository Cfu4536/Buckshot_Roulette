package com.example.buckshotroulette;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GameActivity extends AppCompatActivity {
    TextView infoA, infoB, phA, phB, gunStatsA, gunStatsB;
    int shidanCount, xudanCount;
    int[] gun;//0空1实弹2空弹
    Button shottingA, shottingB;
    Button youBtnA, youBtnB;
    Button meBtnA, meBtnB;
    ImageButton goodA1, goodA2, goodA3, goodA4, goodA5, goodA6, goodA7, goodA8;
    ImageButton goodB1, goodB2, goodB3, goodB4, goodB5, goodB6, goodB7, goodB8;
    Player playerA = null, playerB = null;
    int turnsWho;
    Random random = new Random();
    MediaPlayer mediaPlayer;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initUI();
        initGame();
        Toast.makeText(this, "游戏开始", Toast.LENGTH_SHORT).show();
        //game
        buttonLinsten();
        //goods
        goodsListener();
        //bgm
        mediaPlayer = MediaPlayer.create(this, R.raw.game);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.seekTo(0);
                mediaPlayer.start();
            }
        });
        mediaPlayer.start();

    }

    private void goodsListener() {
        ImageButton[] imageButtonsA = new ImageButton[]{goodA1, goodA2, goodA3, goodA4, goodA5, goodA6, goodA7, goodA8};
        for (int i = 0; i < 8; i++) {
            int finalI = i;
            imageButtonsA[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //用了什么
                    int good = playerA.getGoods()[finalI];
                    switch (good) {
                        //0空;1放大镜;2饮料;3小刀;4香烟;5手铐
                        case 1:
                            for (int j = 0; j < 8; j++) {
                                if (gun[j] == 1) {
                                    infoA.setText("下一发是：实弹");
                                    break;
                                } else if (gun[j] == 2) {
                                    infoA.setText("下一发是：空弹");
                                    break;
                                }
                            }
                            break;
                        case 2:
                            for (int j = 0; j < 8; j++) {
                                if (j == 7) {
                                    infoA.setText("最后一发子弹不能使用");
                                    break;
                                }
                                if (gun[j] == 1) {
                                    gun[j] = 0;
                                    shidanCount--;
                                    infoA.setText("退了一发实弹");
                                    updateGunStats();
                                    break;
                                } else if (gun[j] == 2) {
                                    gun[j] = 0;
                                    xudanCount--;
                                    infoA.setText("退了一发空弹");
                                    updateGunStats();
                                    break;
                                }
                            }
                            break;
                        case 3:
                            if (playerB.getHurt() == 2) {
                                infoA.setText("不能重复使用！");
                            } else {
                                playerB.setHurt(2);
                                infoA.setText("伤害+1！");
                            }
                            break;
                        case 4:
                            playerA.setHealth(playerA.getHealth() + 1);
                            updateHealth();
                            break;
                        case 5:
                            if (playerB.isLock) {
                                infoA.setText("只能使用一次");
                            } else {
                                playerB.setLock(true);
                                infoB.setText("跳过一回合");
                                infoA.setText("对面跳过一回合");
                            }
                            break;
                    }
                    //清空
                    playerA.motifyGoods(finalI, 0);
                    imageButtonsA[finalI].setImageResource(R.drawable.ic_launcher_foreground);
                    updateGoodsUI();
                    System.out.println(playerA.toString());
                }
            });
        }
        ImageButton[] imageButtonsB = new ImageButton[]{goodB1, goodB2, goodB3, goodB4, goodB5, goodB6, goodB7, goodB8};
        for (int i = 0; i < 8; i++) {
            int finalI = i;
            imageButtonsB[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //用了什么
                    int good = playerB.getGoods()[finalI];
                    switch (good) {
                        //0空;1放大镜;2饮料;3小刀;4香烟;5手铐
                        case 1:
                            for (int j = 0; j < 8; j++) {
                                if (gun[j] == 1) {
                                    infoB.setText("下一发是：实弹");
                                    break;
                                } else if (gun[j] == 2) {
                                    infoB.setText("下一发是：空弹");
                                    break;
                                }
                            }
                            break;
                        case 2:
                            for (int j = 0; j < 8; j++) {
                                if (j == 7) {
                                    infoB.setText("最后一发子弹不能使用");
                                    break;
                                }
                                if (gun[j] == 1) {
                                    gun[j] = 0;
                                    shidanCount--;
                                    infoB.setText("退了一发实弹");
                                    updateGunStats();
                                    break;
                                } else if (gun[j] == 2) {
                                    gun[j] = 0;
                                    xudanCount--;
                                    infoB.setText("退了一发实弹");
                                    updateGunStats();
                                    break;
                                }
                            }
                            break;
                        case 3:
                            if (playerA.getHurt() == 2) {
                                infoB.setText("不能重复使用！");
                            } else {
                                playerA.setHurt(2);
                                infoB.setText("伤害+1！");
                            }
                            break;
                        case 4:
                            playerB.setHealth(playerB.getHealth()+1);
                            updateHealth();
                            break;
                        case 5:
                            if (playerA.isLock) {
                                infoB.setText("只能使用一次");
                            } else {
                                playerA.setLock(true);
                                infoA.setText("跳过一回合");
                                infoB.setText("对面跳过一回合");
                            }
                            break;
                    }
                    //清空
                    playerB.motifyGoods(finalI, 0);
                    imageButtonsB[finalI].setImageResource(R.drawable.ic_launcher_foreground);
                    updateGoodsUI();
                    System.out.println(playerB.toString());
                }
            });
        }
    }

    private void buttonLinsten() {
        shottingA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                youBtnA.setEnabled(true);
                meBtnA.setEnabled(true);
                shottingA.setEnabled(false);
                unableGoodsUse(0);
            }
        });
        shottingB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                youBtnB.setEnabled(true);
                meBtnB.setEnabled(true);
                shottingB.setEnabled(false);
                unableGoodsUse(1);
            }
        });
        youBtnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                youBtnA.setEnabled(false);
                meBtnA.setEnabled(false);
                //todo:shotting result
                for (int i = 0; i < 8; i++) {
                    if (gun[i] == 1) {
                        //shot B
                        gun[i] = 0;
                        shidanCount--;
                        playerB.injured();//受一点伤害
                        infoA.setText("他死了！");
                        infoB.setText("你死了！");
                        turnsChange(true);
                        return;
                    } else if (gun[i] == 2) {
                        //no shot B
                        gun[i] = 0;
                        xudanCount--;
                        infoA.setText("没打中...");
                        infoB.setText("暂时安全！");
                        turnsChange(true);
                        return;
                    }
                }
            }
        });
        youBtnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                youBtnB.setEnabled(false);
                meBtnB.setEnabled(false);
                for (int i = 0; i < 8; i++) {
                    if (gun[i] == 1) {
                        //shot A
                        gun[i] = 0;
                        shidanCount--;
                        playerA.injured();//受一点伤害
                        infoA.setText("你死了！");
                        infoB.setText("他死了！");
                        turnsChange(true);
                        return;
                    } else if (gun[i] == 2) {
                        //no shot A
                        gun[i] = 0;
                        xudanCount--;
                        infoA.setText("暂时安全！");
                        infoB.setText("没打中...");
                        turnsChange(true);
                        return;
                    }
                }
            }
        });
        meBtnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                youBtnA.setEnabled(false);
                meBtnA.setEnabled(false);
                //todo:shotting result
                for (int i = 0; i < 8; i++) {
                    if (gun[i] == 1) {
                        //shot A
                        gun[i] = 0;
                        shidanCount--;
                        playerA.injured();//受一点伤害
                        infoA.setText("你死了！");
                        infoB.setText("他死了！");
                        turnsChange(true);
                        return;
                    } else if (gun[i] == 2) {
                        //no shot A
                        gun[i] = 0;
                        xudanCount--;
                        infoA.setText("暂时安全！");
                        infoB.setText("算他运气...");
                        turnsChange(false);
                        return;
                    }
                }
            }
        });
        meBtnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                youBtnB.setEnabled(false);
                meBtnB.setEnabled(false);
                for (int i = 0; i < 8; i++) {
                    if (gun[i] == 1) {
                        //shot B
                        gun[i] = 0;
                        shidanCount--;
                        playerB.injured();//受一点伤害
                        infoA.setText("他死了！");
                        infoB.setText("你死了！");
                        turnsChange(true);
                        return;
                    } else if (gun[i] == 2) {
                        //no shot B
                        gun[i] = 0;
                        xudanCount--;
                        infoA.setText("算他运气...");
                        infoB.setText("暂时安全！");
                        turnsChange(false);
                        return;
                    }
                }
            }
        });
    }

    private void nextGun() {
        initGame();
        Toast.makeText(this, "下一轮", Toast.LENGTH_SHORT).show();
    }

    private void turnsChange(boolean isChange) {
        //turn change
        if (isChange) {
            if (playerA.isLock || playerB.isLock) {
                playerA.setLock(false);
                playerB.setLock(false);
            } else {
                if (turnsWho == 0) {
                    turnsWho = 1;
                } else {
                    turnsWho = 0;
                }
            }
        }
        //ui change
        if (turnsWho == 0) {
            shottingA.setEnabled(true);
            shottingB.setEnabled(false);
            unableGoodsUse(1);
            ableGoodsUse(0);
        } else {
            shottingA.setEnabled(false);
            shottingB.setEnabled(true);
            unableGoodsUse(0);
            ableGoodsUse(1);
        }
        // update
        updateHealth();
        updateGunStats();
    }

    private void initUI() {
        //tv
        infoA = findViewById(R.id.textViewInfo1);
        infoB = findViewById(R.id.textViewInfo2);
        phA = findViewById(R.id.PH1);
        phB = findViewById(R.id.PH2);
        gunStatsA = findViewById(R.id.gunStatus1);
        gunStatsB = findViewById(R.id.gunStatus2);
        //buttons
        shottingA = findViewById(R.id.shotting1);
        shottingB = findViewById(R.id.shotting2);
        youBtnA = findViewById(R.id.shotYou1);
        youBtnB = findViewById(R.id.shotYou2);
        meBtnA = findViewById(R.id.shotMe1);
        meBtnB = findViewById(R.id.shotMe2);
        //goods
        goodA1 = findViewById(R.id.AimageButton1);
        goodB1 = findViewById(R.id.BimageButton1);
        goodA2 = findViewById(R.id.AimageButton2);
        goodB2 = findViewById(R.id.BimageButton2);
        goodA3 = findViewById(R.id.AimageButton3);
        goodB3 = findViewById(R.id.BimageButton3);
        goodA4 = findViewById(R.id.AimageButton4);
        goodB4 = findViewById(R.id.BimageButton4);

        goodA5 = findViewById(R.id.AimageButton5);
        goodB5 = findViewById(R.id.BimageButton5);
        goodA6 = findViewById(R.id.AimageButton6);
        goodB6 = findViewById(R.id.BimageButton6);
        goodA7 = findViewById(R.id.AimageButton7);
        goodB7 = findViewById(R.id.BimageButton7);
        goodA8 = findViewById(R.id.AimageButton8);
        goodB8 = findViewById(R.id.BimageButton8);

    }

    private void initGame() {

        //players
        if (playerA == null || (playerA.getHealth() <= 0 || playerB.getHealth() <= 0)) {
            int ph = random.nextInt(5) + 2;//2-5
            playerA = new Player(ph);
            playerB = new Player(ph);
        }
        //add goods
        int num = random.nextInt(3) + 1;
        playerA.addGoods(num);
        playerB.addGoods(num);
        //turns
        turnsWho = random.nextInt(2);
        //子弹
        shidanCount = 0;
        xudanCount = 5;
        while (shidanCount - xudanCount >= 3 || xudanCount - shidanCount >= 3) {
            shidanCount = random.nextInt(4) + 1;
            xudanCount = random.nextInt(4) + 1;
        }
        gun = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
        int tshi = shidanCount;
        int txu = xudanCount;
        int index = 0;
        for (int i = 0; i < shidanCount + xudanCount; i++) {
            if (tshi == 0) {
                gun[index] = 2;
                txu--;
            } else if (txu == 0) {
                gun[index] = 1;
                tshi--;
            } else {
                if (random.nextInt(2) == 0) {
                    gun[index] = 1;
                    tshi--;
                } else {
                    gun[index] = 2;
                    txu--;
                }
            }
            index++;
        }
        //UI
        youBtnA.setEnabled(false);
        youBtnB.setEnabled(false);
        meBtnA.setEnabled(false);
        meBtnB.setEnabled(false);
        if (turnsWho == 0) {
            //A先
            shottingA.setEnabled(true);
            shottingB.setEnabled(false);
            unableGoodsUse(1);
            ableGoodsUse(0);
            infoA.setText("轮到你了！");
            infoB.setText("对方先手...");
        } else {
            shottingA.setEnabled(false);
            shottingB.setEnabled(true);
            unableGoodsUse(0);
            ableGoodsUse(1);
            infoA.setText("对方先手...");
            infoB.setText("轮到你了！");
        }
        updateHealth();//更新生命值
        updateGunStats();//更新子弹信息
        updateGoodsUI();//更新物品信息
    }

    private void updateGunStats() {
        gunStatsA.setText(shidanCount + "实" + xudanCount + "空");
        gunStatsB.setText(shidanCount + "实" + xudanCount + "空");
        if (shidanCount == 0 && xudanCount == 0) {
            nextGun();//下一轮
        }
    }

    private void updateHealth() {
        phA.setText("生命值：" + playerA.getHealth());
        phB.setText("生命值：" + playerB.getHealth());
        if (playerA.getHealth() <= 0) {
            getWinner(1);
            initGame();
        }
        if (playerB.getHealth() <= 0) {
            getWinner(0);
            initGame();
        }
    }

    private void updateGoodsUI() {
        int[] goodsA = playerA.getGoods();
        int[] goodsB = playerB.getGoods();
        ImageButton[] imageButtonsA = new ImageButton[]{goodA1, goodA2, goodA3, goodA4, goodA5, goodA6, goodA7, goodA8};
        ImageButton[] imageButtonsB = new ImageButton[]{goodB1, goodB2, goodB3, goodB4, goodB5, goodB6, goodB7, goodB8};
        for (int i = 0; i < 8; i++) {
            //0空;1放大镜;2饮料;3小刀;4香烟;5手铐
            switch (goodsA[i]) {
                case 1:
                    imageButtonsA[i].setImageResource(R.drawable.fangdajing);
                    break;
                case 2:
                    imageButtonsA[i].setImageResource(R.drawable.yilaguan);
                    break;
                case 3:
                    imageButtonsA[i].setImageResource(R.drawable.juzi);
                    break;
                case 4:
                    imageButtonsA[i].setImageResource(R.drawable.xiangyan);
                    break;
                case 5:
                    imageButtonsA[i].setImageResource(R.drawable.shoukao);
                    break;
                default:
                    imageButtonsA[i].setImageResource(R.drawable.ic_launcher_foreground);
            }
        }
        for (int i = 0; i < 8; i++) {
            //0空;1放大镜;2饮料;3小刀;4香烟;5手铐
            switch (goodsB[i]) {
                case 1:
                    imageButtonsB[i].setImageResource(R.drawable.fangdajing);
                    break;
                case 2:
                    imageButtonsB[i].setImageResource(R.drawable.yilaguan);
                    break;
                case 3:
                    imageButtonsB[i].setImageResource(R.drawable.juzi);
                    break;
                case 4:
                    imageButtonsB[i].setImageResource(R.drawable.xiangyan);
                    break;
                case 5:
                    imageButtonsB[i].setImageResource(R.drawable.shoukao);
                    break;
                default:
                    imageButtonsB[i].setImageResource(R.drawable.ic_launcher_foreground);

            }
        }
    }

    private void getWinner(int who) {
        if (who == 0) {
            infoA.setText("你赢了！");
            infoB.setText("你输了！");
            Toast.makeText(this, "A赢了", Toast.LENGTH_SHORT).show();
        } else {
            infoA.setText("你输了！");
            infoB.setText("你赢了！");
            Toast.makeText(this, "B赢了", Toast.LENGTH_SHORT).show();
        }
    }

    private void unableGoodsUse(int who) {
        ImageButton[] goodsAs = new ImageButton[]{goodA1, goodA2, goodA3, goodA4, goodA5, goodA6, goodA7, goodA8};
        ImageButton[] goodsBs = new ImageButton[]{goodB1, goodB2, goodB3, goodB4, goodB5, goodB6, goodB7, goodB8};
        if (who == 0) {
            for (int i = 0; i < 8; i++) {
                goodsAs[i].setEnabled(false);
            }
        } else {
            for (int i = 0; i < 8; i++) {
                goodsBs[i].setEnabled(false);
            }
        }
    }

    private void ableGoodsUse(int who) {
        ImageButton[] goodsAs = new ImageButton[]{goodA1, goodA2, goodA3, goodA4, goodA5, goodA6, goodA7, goodA8};
        ImageButton[] goodsBs = new ImageButton[]{goodB1, goodB2, goodB3, goodB4, goodB5, goodB6, goodB7, goodB8};
        if (who == 0) {
            for (int i = 0; i < 8; i++) {
                goodsAs[i].setEnabled(true);
            }
        } else {
            for (int i = 0; i < 8; i++) {
                goodsBs[i].setEnabled(true);
            }
        }
    }
}