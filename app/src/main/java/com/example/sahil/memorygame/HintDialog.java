package com.example.sahil.memorygame;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Sahil on 12-02-2016.
 */

public class HintDialog extends DialogFragment {

    //Initialising the values
    int color1, color2, color3, color4, color5, color6,color7, color8, color9;
    ImageView image1, image2, image3, image4, image5, image6, image7, image8, image9;
    LinearLayout container_1, container_2, container_3, container_4, container_5, container_6,
            container_7, container_8, container_9;

    ArrayList<Integer> colorList = new ArrayList<Integer>();
    ArrayList<LinearLayout> containerList = new ArrayList<LinearLayout>();
    ArrayList<ImageView> imageList = new ArrayList<ImageView>();

    int i = 5;
    double seconds, savedseconds;
    Boolean running, wasRunning = false;
    final Handler handler  = new Handler();
    //Runnable timer;
    View view;
    GameTemplate gt;
    CommunicationChannel mCommChListner = null;

    TextView timeView;
    Button ok_Button;

    // Empty constructor required for DialogFragment
    public HintDialog() {

    }

    //Called when fragment is created
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_hint, container, false);
        getDialog().setTitle("Hint");

        //Adding variable values
        ok_Button = (Button) view.findViewById(R.id.okButton);
        timeView = (TextView) view.findViewById(R.id.time_view);

        //Called from Activity to share the level the user is on and seconds left on hint timer
        gt = getArguments().getParcelable("Game Template");
        seconds = getArguments().getDouble("Seconds");

        //Adding color names and values
        color1 = R.drawable.purple_logo;
        color2 = R.drawable.white_logo;
        color3 = R.drawable.blue_logo;
        color4 = R.drawable.green_logo;
        color5 = R.drawable.yellow_logo;
        color6 = R.drawable.orange_logo;
        color7 = R.drawable.red_logo;
        color8 = R.drawable.black_logo;
        color9 = R.drawable.pink_logo;

        colorList.add(color1);
        colorList.add(color2);
        colorList.add(color3);
        colorList.add(color4);
        colorList.add(color5);
        colorList.add(color6);
        colorList.add(color7);
        colorList.add(color8);
        colorList.add(color9);

        //Adding image names and values
        image1 = new ImageView(view.getContext());
        image2 = new ImageView(view.getContext());
        image3 = new ImageView(view.getContext());
        image4 = new ImageView(view.getContext());
        image5 = new ImageView(view.getContext());
        image6 = new ImageView(view.getContext());
        image7 = new ImageView(view.getContext());
        image8 = new ImageView(view.getContext());
        image9 = new ImageView(view.getContext());

        imageList.add(image1);
        imageList.add(image2);
        imageList.add(image3);
        imageList.add(image4);
        imageList.add(image5);
        imageList.add(image6);
        imageList.add(image7);
        imageList.add(image8);
        imageList.add(image9);

        //Adding container names and values
        container_1 = (LinearLayout) view.findViewById(R.id.container1);
        container_2 = (LinearLayout) view.findViewById(R.id.container2);
        container_3 = (LinearLayout) view.findViewById(R.id.container3);
        container_4 = (LinearLayout) view.findViewById(R.id.container4);
        container_5 = (LinearLayout) view.findViewById(R.id.container5);
        container_6 = (LinearLayout) view.findViewById(R.id.container6);
        container_7 = (LinearLayout) view.findViewById(R.id.container7);
        container_8 = (LinearLayout) view.findViewById(R.id.container8);
        container_9 = (LinearLayout) view.findViewById(R.id.container9);

        containerList.add(container_1);
        containerList.add(container_2);
        containerList.add(container_3);
        containerList.add(container_4);
        containerList.add(container_5);
        containerList.add(container_6);
        containerList.add(container_7);
        containerList.add(container_8);
        containerList.add(container_9);

        LinearLayout container_layout;
        int color, size = imageList.size();
        ImageView image;

        //This is the algorithm that arranges the solution based on the game template methods
        for (int i = 0; i<size; i++) {
            //Calls the box number, its position and the next image from the hint's grid
            container_layout = containerList.get(gt.returnBoxNumbersAt(i+1)-1);
            image = imageList.get(i);
            color = colorList.get(gt.returnColorAt(i+1)-1);

            /*If the parent is not null, it removes it's children, which is never as I have
                set no child. Cautionary method*/
            if(image.getParent()!=null) {
                ((ViewGroup)image.getParent()).removeView(image);
                //Toast.makeText(getActivity(), ""+i, Toast.LENGTH_SHORT).show();
            }

            //Gets the color from the solution set and sets it to the grid at the correct position
            image.setImageResource(color);
            image.setId(i);
            container_layout.addView(image);

            //Sets the appropriate size to each image
            final float scaler = getResources().getDisplayMetrics().density;
            image.getLayoutParams().height = (int) (30 * scaler);
            image.getLayoutParams().width = (int) (30 * scaler);
        }

        running = true;

        /*Calls this when fragment is closed and called, without deleting the parent
            activity and sets the original values of the timer states and seconds left*/
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getDouble("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }

        //runTimer();
        handler.post(timer);

        //When fragment is closed mid-way, a good luck message is shown and activity is dismissed
        ok_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do stuff for signInButtonClick
                Toast.makeText(getActivity(), "Good luck brah", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        return view;
    }

    //Called when the fragment is going to be paused/stopped
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putDouble("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    //During dismissal, the app saves the seconds value at the point it is dismissed
    //Need to convert int to double for seconds
    @Override
    public void dismiss() {
        super.dismiss();
        //Toast.makeText(getActivity(), "Dismissed!", Toast.LENGTH_SHORT).show();
        //stopRepeatingTask();
        sendMessage(seconds);
    }

    //When the activity is started (again), set the values if the timer was already running
    @Override
    public void onStart() {
        super.onStart();
        startRepeatingTask();
        if (wasRunning) {
            //startRepeatingTask();
            running = true;
            seconds = savedseconds;
            //startRepeatingTask();
            //Toast.makeText(getActivity(), "Started: " + seconds, Toast.LENGTH_SHORT).show();
        }
    }

    /*When the activity is paused, seconds value is saved and wasRunning becomes true/false
        depending on running*/
    @Override
    public void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
        savedseconds = seconds;
        stopRepeatingTask();
        //Toast.makeText(getActivity(), "Paused: " + savedseconds, Toast.LENGTH_SHORT).show();
        dismiss();
    }

    /*Runs the timer continuously in the background and updates the seconds when the running
        value is true*/
    public Runnable timer = new Runnable() {
        //final TextView timeView = (TextView) view.findViewById(R.id.time_view);

        @Override
        public void run() {
            double secs = seconds%60;
            String time = "Time: " + String.format("%.2f", secs) + "s";
            timeView.setText(time);
            Toast.makeText(view.getContext(), "Testing " + seconds, Toast.LENGTH_SHORT).show();

            if (running) {
                seconds = seconds - 0.1;
                i++;
            }

            //When timer hits 0, close dialog automatically.
            if  (seconds <= 0.0) {
                handler.removeCallbacks(this);
                Toast.makeText(view.getContext(), "Yo *thumbs up*", Toast.LENGTH_SHORT).show();
                dismiss();
            }

            handler.postDelayed(timer, 100);
        }
    };

    void startRepeatingTask()
    {
        timer.run();
    }

    void stopRepeatingTask()
    {
        handler.removeCallbacks(timer);
    }

    /*Runs the timer continuously in the background and updates the seconds when the running
        value is true*/
    private void runTimer() {
        //final TextView timeView = (TextView) view.findViewById(R.id.time_view);

        /*handler.post(new Runnable() {
            @Override
            public void run() {
                double secs = seconds%60;
                String time = "Time: " + String.format("%.2f", secs) + "s";
                timeView.setText(time);

                if (running) {
                    seconds = seconds - 0.1;
                    i++;
                }

                handler.postDelayed(this, 100);

                //When timer hits 0, close dialog automatically.
                if  (seconds <= 0.0) {
                    handler.removeCallbacks(this);
                    Toast.makeText(view.getContext(), "Yo *thumbs up*", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }
        });*/
    }

    //Create an interface which will help us to communicate with fragments by help of Activity
    interface CommunicationChannel {
        public void setCommunication(double msg);
    }

    //Attaches the communication channel to the activity.... I think
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCommChListner = (CommunicationChannel)activity;
    }

    //Send Message to the activity
    public void sendMessage(double sec) {
        mCommChListner.setCommunication(sec);
    }

}
