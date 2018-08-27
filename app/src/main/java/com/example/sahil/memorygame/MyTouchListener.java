package com.example.sahil.memorygame;

import android.content.ClipData;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Sahil on 25-03-2016.
 */
//Touch listener on images detects if the image has been touched
public class MyTouchListener implements View.OnTouchListener {

    public ImageView draggedImageView;

    /*public MyTouchListener(ImageView draggedImageView) {
        this.draggedImageView = draggedImageView;
    }*/

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            //Creates a duplicate copy of the image that has been touched
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(data, shadowBuilder, view, 0);
            view.setVisibility(View.INVISIBLE);
            draggedImageView = (ImageView) view;
                /*ViewGroup owner = (ViewGroup) view.getParent();
                owner.removeView(view);*/
            return true;
        } else {
            return false;
        }
    }

    public ImageView getDraggedImageView() {
        return draggedImageView;
    }
}
