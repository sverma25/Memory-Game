package com.example.sahil.memorygame;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Sahil on 25-03-2016.
 */
//Drag listener on grid piece detects whether the image has entered a grid, hovering a grid etc
public class MyDragListener implements View.OnDragListener {
    Context context;
    Drawable enterShape, normalShape;
    ImageView draggedImageView;
    MyTouchListener myTouchListener;

    public MyDragListener(Context context, MyTouchListener myTouchListener) {
        this.context = context;
        enterShape = context.getDrawable(R.drawable.shape_droptarget);
        normalShape = context.getDrawable(R.drawable.shape);
        this.myTouchListener = myTouchListener;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        draggedImageView = myTouchListener.getDraggedImageView();
        //int action = event.getAction();
        switch (event.getAction()) {
            //When drag starts, do nothing;
            case DragEvent.ACTION_DRAG_STARTED:
                // do nothing
                break;

            //When image enters grid piece, change the aspects of grid piece (adds red boundary)
            case DragEvent.ACTION_DRAG_ENTERED:
                v.setBackgroundDrawable(enterShape);
                break;

            //When image exits grid piece, change the aspects of grid piece (original boundary)
            case DragEvent.ACTION_DRAG_EXITED:
                v.setBackgroundDrawable(normalShape);
                break;

                /*When image is dropped in a grid piece,
                    remove that image from it's home grid piece and add it to the new grid piece*/
            case DragEvent.ACTION_DROP:
                ViewGroup draggedImageViewParentLayout = (ViewGroup) draggedImageView.getParent();
                draggedImageViewParentLayout.removeView(draggedImageView);

                LinearLayout layout_container = (LinearLayout) v;
                layout_container.addView(draggedImageView);
                draggedImageView.setVisibility(View.VISIBLE);
                break;

            //When the drag ends, again return the grid piece to the original boundary
            case DragEvent.ACTION_DRAG_ENDED:
                v.setBackgroundDrawable(normalShape);
                break;

            //Do nothing
            default:
                break;
        }

        return true;
    }
}