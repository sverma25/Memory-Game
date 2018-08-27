package com.example.sahil.memorygame;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Sahil on 17-03-2016.
 */
public class FunctionsModule{

    private Context context;

    public FunctionsModule(Context context) {
        this.context = context;
    }

    //Creates Random game for Random Activity
    public GameTemplate RandomGame(int gameNum) {
        ArrayList<Integer> color_num = new ArrayList<>();
        ArrayList<Integer> box_num = new ArrayList<>();
        ArrayList<Integer> pos_num = new ArrayList<>();

        ArrayList<Integer> colorSet = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
        ArrayList<Integer> boxesSet = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));

        int rand_solution, rand_box, curr_box_pos = 0;
        int solutionSetSize = colorSet.size(), boxesSetSize = boxesSet.size();

        //For creating a random color set
        for (int i = 0; i<solutionSetSize; i++) {
            //Randomly select a value between 0 and (size of value set)
            rand_solution = (int) Math.round((Math.random() * (colorSet.size()-1)));

            //Add that position from value set to actual set
            color_num.add(colorSet.get(rand_solution));

            //Delete that position's value from value set and repeat
            colorSet.remove(rand_solution);
        }

        //For creating a random box set
        for (int i = 0; i < boxesSetSize; i++) {
            //First it chooses a box and decides if it is used
            //If it chooses said piece it adds it and next decides whether the piece should be added again
            //If yes, it adds it again and then repeats till box_num is complete

            //Probability of choosing a box once is 60% and second time is 40%
            rand_box = (int) Math.round((Math.random()*9)+1);
            if (rand_box <= 6) {
                box_num.add(boxesSet.get(i));
                rand_box = (int) Math.round((Math.random()*9)+1);
                if (rand_box <=4){
                    box_num.add(boxesSet.get(i));
                }
            }

            //If 9 boxes have been chosen, break
            if (box_num.size() == boxesSetSize) {
                break;
            }

            //If loop finishes before 9 boxes are chosen, repeat this part of the algorithm
            if (i == (boxesSetSize-1) && box_num.size() < boxesSetSize) {
                i = 0;
                box_num.clear();
            }
        }

        for (int i = 0; i<boxesSetSize; i++) {
            box_num.get(i);

            if (curr_box_pos == box_num.get(i)) {
                pos_num.add(pos_num.get(i-1) + 1);
            } else {
                curr_box_pos = box_num.get(i);
                pos_num.add(1);
            }
        }

        return new GameTemplate(color_num, box_num, pos_num);
    }

    public boolean HintTime(double seconds, GameTemplate gt, HintDialog hintDialog) {
        /* Sending the seconds left (if any) back if the hint is called again. Otherwise,
        the usual (10) seconds is sent */
        Bundle args = new Bundle();
        args.putParcelable("Game Template", gt);
        args.putDouble("Seconds", seconds);
        hintDialog.setArguments(args);

        /*Toast.makeText(getBaseContext(), "Hint Button: " + seconds, Toast.LENGTH_SHORT).show();*/

        //If time is up, disable hint button
        return (seconds <= 0);
    }

    public boolean Submit(ArrayList<LinearLayout> containerList, ArrayList<ImageView> imageList, GameTemplate gt) {
        View v;
        Boolean b = false;
        int size = containerList.size();

        /* Try catch statements surrounded to make sure that null exceptions are caught.
                    For example, if the first grid box has an empty cell in result but solution has
                    a filled grid layout then a null error is shown, and caught
                */
        try {
            //Go through each grid piece and check whether the solution matches or not
            for (int i=0; i<size; i++) {
                //Format is v1 = container_1.getChildAt(0) and so on
                v = containerList.get( gt.returnBoxNumbersAt(i+1)-1 )
                        .getChildAt( gt.returnPosNumbersAt(i+1)-1 );

                //Format is b1 = v1.equals(color_image);
                b = v.equals( imageList.get(gt.returnColorAt(i+1)-1) );

                        /* If b is false (grid box in view does not equal the desired solution
                            color) then the loop is broken
                        */
                if (!b) {
                    break;
                }
            }

            /* If code was broken earlier, there is obviously an error, thus, the error is
                shown. If not, a victory message is shown.
            */
            if (!b) {
                Toast.makeText(context, "lol no", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "victory mate", Toast.LENGTH_LONG).show();
            }

            return b;

            /* If a null error, as mentioned above, is thrown by the code, another error is
                shown to the user.
            */
        } catch (NullPointerException e) {
            Toast.makeText(context, "hells nah", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void Reset(ArrayList<LinearLayout> containerList, ArrayList<ImageView> imageList) {
        LinearLayout container;
        int num, size = containerList.size();

        //Go through each grid pieces and remove each child one by one via inner loop
        for (int i = 0; i<size; i++) {
            container = containerList.get(i);
            num = container.getChildCount();

            for (int j = 0; j<num; j++) {
                if (num == 0) {
                    break;
                }
                container.removeView(container.getChildAt(0));
            }
        }

        //Add the original layout to each container
        for (int i=0; i<size; i++) {
            container = containerList.get(i);
            container.addView(imageList.get(i));
        }

        //Reset complete message
        Toast.makeText(context, "Reseted gameboard ya", Toast.LENGTH_SHORT).show();
    }
}
