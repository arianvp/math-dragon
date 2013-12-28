package org.teaminfty.math_dragon.view.math;

import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IExpr;
import org.teaminfty.math_dragon.exceptions.EmptyChildException;

import android.graphics.Canvas;
import android.graphics.Rect;


public class MathOperationArcSine extends MathObjectSinoid
{
	//String of which to get the TextBounds
    public MathOperationArcSine()
    {
    	tmpStr = "sin" ;
    	arc = 1;
    }

    //returns a arcsin 
	@Override
	public IExpr eval() throws EmptyChildException 
	{
		return F.ArcSin(getChild(0).eval());
	}

	@Override
	public void draw(Canvas canvas) 
	{
		int x = 0;
		
		// Draw the bounding boxes
        drawBoundingBoxes(canvas);
		
        // Get the text size and the bounding box
        final float textSize = findTextSize(level);
        Rect textBounding = getSize(textSize);
        Rect totalBounding = sizeAddPadding(textBounding);

        // Set the text size
        operatorPaint.setTextSize(textSize);
        
        // Set the paint color
        operatorPaint.setColor(getColor());
        
        // Gap between the sin and -1
        final int smallGap = (int) (30 / MathObject.lineWidth);

        // Draw the main operator
        canvas.save();
        canvas.translate((totalBounding.width() - textBounding.width()) / 2, (totalBounding.height() - textBounding.height()) / 2);
        operatorPaint.getTextBounds(tmpStr, 0, tmpStr.length(), bounds);
        canvas.drawText(tmpStr, bounds.left, textBounding.height() - bounds.height() - bounds.top, operatorPaint);
        x += bounds.width();
        
        operatorPaint.getTextBounds(tmpStr2, 0, tmpStr2.length(), bounds2);
        canvas.drawText(tmpStr2, (x + smallGap) - bounds2.left, bounds.height()/2  , exponentPaint);
        x += bounds2.width();

        canvas.restore();
        this.drawChildren(canvas);
	}

}
