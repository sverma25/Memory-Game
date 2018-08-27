package com.example.sahil.memorygame;

import java.util.ArrayList;

/**
 * Created by Sahil on 20-02-2016.
 */
public class GameData {

    ArrayList<GameTemplate> dataset = new ArrayList<GameTemplate>();
    //Color int combinations
    int purple_num = 1, white_num = 2, blue_num = 3, green_num = 4, yellow_num = 5, orange_num = 6, red_num = 7, black_num = 8, pink_num = 9;
    //Box number int combinations
    int box_1 = 1, box_2 = 2, box_3 = 3, box_4 = 4, box_5 = 5, box_6= 6, box_7 = 7, box_8 = 8, box_9 = 9;

    public GameData() {
        ArrayList color_num = new ArrayList();
        color_num.add(orange_num);
        color_num.add(purple_num);
        color_num.add(white_num);
        color_num.add(blue_num);
        color_num.add(yellow_num);
        color_num.add(red_num);
        color_num.add(pink_num);
        color_num.add(black_num);
        color_num.add(green_num);

        ArrayList box_num = new ArrayList();
        box_num.add(box_1);
        box_num.add(box_2);
        box_num.add(box_2);
        box_num.add(box_3);
        box_num.add(box_5);
        box_num.add(box_6);
        box_num.add(box_7);
        box_num.add(box_8);
        box_num.add(box_8);

        ArrayList pos_num = new ArrayList();
        pos_num.add(1);
        pos_num.add(1);
        pos_num.add(2);
        pos_num.add(1);
        pos_num.add(1);
        pos_num.add(1);
        pos_num.add(1);
        pos_num.add(1);
        pos_num.add(2);

        GameTemplate temp1 = new GameTemplate(color_num, box_num, pos_num);

        dataset.add(temp1);
    }

    public GameTemplate receive_template(int pos) {
        return dataset.get(pos-1);
    }
}
