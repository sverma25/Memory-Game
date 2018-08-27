package com.example.sahil.memorygame;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity implements HintDialog.CommunicationChannel{

    //Initialising the values
    LinearLayout container_1, container_2, container_3, container_4, container_5, container_6,
            container_7, container_8, container_9;
    ImageView purple_image, white_image, blue_image, green_image, yellow_image, orange_image,
            red_image, black_image, pink_image;
    Button submit_Button, reset_Button, hint_Button;

    protected TextView triesText;
    protected int tries = 5;
    protected double seconds = 10;

    Context context;
    GameTemplate gt;
    FunctionsModule fm;
    MyTouchListener touchListener;
    MyDragListener dragListener;

    ArrayList<LinearLayout> containerList = new ArrayList<>();
    ArrayList<ImageView> imageList = new ArrayList<>();

    //Empty Constructor
    public MainActivity() {

    }

    //Called when Activity is created
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gt = getIntent().getParcelableExtra("Game Template Key");

        context = getBaseContext();
        fm = new FunctionsModule(context);
        touchListener = new MyTouchListener();
        dragListener = new MyDragListener(context, touchListener);

        //Adding container names and values
        container_1 = (LinearLayout)findViewById(R.id.container1);
        container_2 = (LinearLayout)findViewById(R.id.container2);
        container_3 = (LinearLayout)findViewById(R.id.container3);
        container_4 = (LinearLayout)findViewById(R.id.container4);
        container_5 = (LinearLayout)findViewById(R.id.container5);
        container_6 = (LinearLayout)findViewById(R.id.container6);
        container_7 = (LinearLayout)findViewById(R.id.container7);
        container_8 = (LinearLayout)findViewById(R.id.container8);
        container_9 = (LinearLayout)findViewById(R.id.container9);

        containerList.add(container_1);
        containerList.add(container_2);
        containerList.add(container_3);
        containerList.add(container_4);
        containerList.add(container_5);
        containerList.add(container_6);
        containerList.add(container_7);
        containerList.add(container_8);
        containerList.add(container_9);

        //Adding image names and values
        purple_image = (ImageView)findViewById(R.id.myimage1);
        white_image = (ImageView)findViewById(R.id.myimage2);
        blue_image = (ImageView)findViewById(R.id.myimage3);
        green_image = (ImageView)findViewById(R.id.myimage4);
        yellow_image = (ImageView)findViewById(R.id.myimage5);
        orange_image = (ImageView)findViewById(R.id.myimage6);
        red_image = (ImageView)findViewById(R.id.myimage7);
        black_image = (ImageView)findViewById(R.id.myimage8);
        pink_image = (ImageView)findViewById(R.id.myimage9);

        imageList.add(purple_image);
        imageList.add(white_image);
        imageList.add(blue_image);
        imageList.add(green_image);
        imageList.add(yellow_image);
        imageList.add(orange_image);
        imageList.add(red_image);
        imageList.add(black_image);
        imageList.add(pink_image);

        //Adding buttons and text field(s)
        submit_Button = (Button)findViewById(R.id.submitButton);
        reset_Button = (Button)findViewById(R.id.resetButton);
        hint_Button = (Button)findViewById(R.id.hintButton);

        triesText = (TextView) findViewById(R.id.attemptsNumberText);
        triesText.setText("" + tries);

        //Adding touch listeners to the images
        findViewById(R.id.myimage1).setOnTouchListener(touchListener);
        findViewById(R.id.myimage2).setOnTouchListener(touchListener);
        findViewById(R.id.myimage3).setOnTouchListener(touchListener);
        findViewById(R.id.myimage4).setOnTouchListener(touchListener);
        findViewById(R.id.myimage5).setOnTouchListener(touchListener);
        findViewById(R.id.myimage6).setOnTouchListener(touchListener);
        findViewById(R.id.myimage7).setOnTouchListener(touchListener);
        findViewById(R.id.myimage8).setOnTouchListener(touchListener);
        findViewById(R.id.myimage9).setOnTouchListener(touchListener);

        //Adding drag listeners to the box layouts
        findViewById(R.id.container1).setOnDragListener(dragListener);
        findViewById(R.id.container2).setOnDragListener(dragListener);
        findViewById(R.id.container3).setOnDragListener(dragListener);
        findViewById(R.id.container4).setOnDragListener(dragListener);
        findViewById(R.id.container5).setOnDragListener(dragListener);
        findViewById(R.id.container6).setOnDragListener(dragListener);
        findViewById(R.id.container7).setOnDragListener(dragListener);
        findViewById(R.id.container8).setOnDragListener(dragListener);
        findViewById(R.id.container9).setOnDragListener(dragListener);

        //Hint Button: Available for only 10 seconds to the user.
        hint_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Bringing up the hint dialog
                FragmentManager manager = getFragmentManager();
                Fragment frag = manager.findFragmentByTag("fragment_hint_dialog");

                if (frag != null) {
                    manager.beginTransaction().remove(frag).commit();
                }

                HintDialog hint_Dialog = new HintDialog();
                hint_Dialog.show(manager, "fragment_hint_dialog");

                Boolean seconds_below_zero = fm.HintTime(seconds, gt, hint_Dialog);

                //If time is up, disable hint button
                if (seconds_below_zero) {
                    hint_Button.setEnabled(false);
                }
            }
        });

        //Submit Button: Submits the user's result.
        submit_Button.setOnClickListener(new View.OnClickListener() {
            Boolean b;

            @Override
            public void onClick(View view) throws NullPointerException {
                b = fm.Submit(containerList, imageList, gt);

                if (!b) {
                    triesText.setText("" + --tries);

                    if (tries == 0) {
                        Toast.makeText(MainActivity.this, "Maybe next time man",
                                Toast.LENGTH_SHORT).show();
                        submit_Button.setEnabled(false);
                    }
                }
            }
        });

        //Reset Button: Resets the grid to the original layout
        reset_Button.setOnClickListener(new View.OnClickListener() {
            //Add functionality that shaking the device triggers reset command
            @Override
            public void onClick(View view) {
                fm.Reset(containerList, imageList);
            }
        });
    }

    /* Receives seconds remaining on hint timer. If hits 0 (-1 due to int values being floored),
        hint button is disabled */
    @Override
    public void setCommunication(double msg) {
        seconds = msg;

        if (seconds <=0) {
            hint_Button.setEnabled(false);
        }
    }}