package org.teaminfty.math_dragon.view;

import org.teaminfty.math_dragon.R;

import com.espian.showcaseview.OnShowcaseEventListener;
import com.espian.showcaseview.ShowcaseView;
import com.espian.showcaseview.targets.Target;
import com.espian.showcaseview.targets.ViewTarget;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;

public class ShowcaseViewDialog extends Dialog
{
    public ShowcaseViewDialog(Activity ctx, Target target, String title, String msg)
    {
        // Construct an invisible dialog
        super(ctx, R.style.ShowcaseViewDialogTheme);
        
        // Create the ShowcaseView and directly remove is from its parent
        ShowcaseView sv = ShowcaseView.insertShowcaseView(target, ctx, title, msg);
        ((ViewGroup) sv.getParent()).removeView(sv);
        sv.setOnShowcaseEventListener(new ShowcaseEventListener());
        
        // Set the ShowcaseView as content
        setContentView(sv);
        
        // Remember the activity
        activity = ctx;
    }

    public ShowcaseViewDialog(Activity ctx, Target target, int titleId, int msgId)
    {
        // Simply call the other constructor
        this(ctx, target, ctx.getResources().getString(titleId), ctx.getResources().getString(msgId));
    }
    
    /** The activity the dialog is shown for */
    private Activity activity = null;

    /** The DialogFragment the dialog is shown for */
    private DialogFragment dlgFrag = null;
    
    /** Sets the DialogFragment that touch events should be passed to */
    public void setDialogFragment(DialogFragment dlg)
    { dlgFrag = dlg; }
    
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(dlgFrag != null)
        {
            // Translate the touch position
            Point pos = new Point((int) event.getX(), (int) event.getY());
            int[] viewPos = new int[2];
            dlgFrag.getView().getLocationOnScreen(viewPos);
            event.setLocation(pos.x - viewPos[0], pos.y - viewPos[1]);
            
            // Dispatch the event to the DialogFragment
            return dlgFrag.getView().dispatchTouchEvent(event);
        }
        else if(activity != null)
        {
            // Dispatch the event to the Activity
            activity.dispatchTouchEvent(event);
        }
        return false;
    }
    
    /** Translates the given position to or from the coordinate system used by the given DialogFragment
     * @param pos The point to translate
     * @param dlgFrag The DialogFragment that should be translated to or from
     * @param translateFrom Whether the coordinates should be translated from or to the DialogFragment's coordinate system
     * @return The translated point */
    private static Point translateDialogFragmentPos(Point pos, DialogFragment dlgFrag, boolean translateFrom)
    {
        // Get the layout parameters of the dialog and get the content view and the action bar
        WindowManager.LayoutParams params = dlgFrag.getDialog().getWindow().getAttributes();
        View contentView = dlgFrag.getActivity().findViewById(android.R.id.content);
        ActionBar actionBar = dlgFrag.getActivity().getActionBar();

        // Calculate the coordinates of the dialog
        int x = (int) params.horizontalMargin;
        if(params.width != LayoutParams.MATCH_PARENT)
            x += (contentView.getWidth() - params.width) / 2;

        int y = (int) params.verticalMargin;
        if(params.height != LayoutParams.MATCH_PARENT)
            y += (contentView.getHeight() + actionBar.getHeight() - params.height) / 2;
        
        // Offset the point by the coordinates of the window
        if(translateFrom)
            pos.offset(x, y);
        else
            pos.offset(-x, -y);
        
        // Return the result
        return pos;
    }
    
    /** A target for the ShowcaseView that can target a certain view in a DialogFragment */
    public static class DialogFragmentTarget extends ViewTarget
    {
        /** The DialogFragment */
        private DialogFragment dlgFrag;

        public DialogFragmentTarget(View view, DialogFragment dlgFrag)
        {
            // Call the super constructor
            super(view);
            
            // Remember the DialogFragment
            this.dlgFrag = dlgFrag;
        }
        
        @Override
        public Point getPoint()
        {
            // Translate the position of the view to a position in the ShowcaseView
            return translateDialogFragmentPos(super.getPoint(), dlgFrag, true);
        }
    }
    
    /** The current OnShowcaseEventListener */
    private OnShowcaseEventListener onShowcaseEventListener = null;
    
    /** Sets the current OnShowcaseEventListener
     * @param listener The new listener */
    public void setOnShowcaseEventListener(OnShowcaseEventListener listener)
    { onShowcaseEventListener = listener; }
    
    /** Listens for events from the ShowcaseView */
    private class ShowcaseEventListener implements OnShowcaseEventListener
    {

        @Override
        public void onShowcaseViewHide(ShowcaseView showcaseView)
        {
            // Call the OnShowcaseEventListener
            if(onShowcaseEventListener != null)
                onShowcaseEventListener.onShowcaseViewHide(showcaseView);
        }

        @Override
        public void onShowcaseViewDidHide(ShowcaseView showcaseView)
        {
            // Simply dismiss the dialog
            dismiss();

            // Call the OnShowcaseEventListener
            if(onShowcaseEventListener != null)
                onShowcaseEventListener.onShowcaseViewDidHide(showcaseView);
        }

        @Override
        public void onShowcaseViewShow(ShowcaseView showcaseView)
        {
            // Call the OnShowcaseEventListener
            if(onShowcaseEventListener != null)
                onShowcaseEventListener.onShowcaseViewShow(showcaseView);
            }
        
    }
}
