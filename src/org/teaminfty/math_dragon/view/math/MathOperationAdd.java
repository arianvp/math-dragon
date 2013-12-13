package org.teaminfty.math_dragon.view.math;

import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IExpr;
import org.teaminfty.math_dragon.exceptions.EmptyChildException;
import org.teaminfty.math_dragon.exceptions.NotConstantException;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class MathOperationAdd extends MathBinaryOperationLinear
{

    /** Constructor
     * @param defWidth The default maximum width
     * @param defHeight The default maximum height
     */
    public MathOperationAdd(int defWidth, int defHeight)
    { super(defWidth, defHeight); }

    public MathOperationAdd(MathObject A, MathObject B, int defWidth, int defHeight)
    { 
    	super(defWidth, defHeight);
		this.setChild(0, A);
		this.setChild(1, B);
	}
    
    @Override
    public int getPrecedence()
    { return MathObjectPrecedence.ADD; }

	@Override
    public IExpr eval() throws EmptyChildException
    {
        // Check if the children are not empty
        this.checkChildren();
        
        // Return the result
        return F.Plus(getChild(0).eval(), getChild(1).eval());
    }

    @Override
    public double approximate() throws NotConstantException, EmptyChildException
    {
        // Check if the children are not empty
        this.checkChildren();
        
        // Return the result
        return getChild(0).approximate() + getChild(1).approximate();
    }

    @Override
    public void draw(Canvas canvas, int maxWidth, int maxHeight)
    {
        // Get the bounding box
        final Rect operator = getOperatorBoundingBoxes(maxWidth, maxHeight)[0];
        
        /*Paint tmp = new Paint();
        tmp.setColor(Color.GREEN);
        canvas.drawRect(getBoundingBox(maxWidth, maxHeight), tmp);
        tmp.setColor(Color.RED);
        for(int i = 0; i < getChildCount(); ++i)
            canvas.drawRect(getChildBoundingBox(i, maxWidth, maxHeight), tmp);*/
        
        // Draw the operator
        operator.inset(operator.width() / 10, operator.height() / 10);      // Padding
        canvas.save();
        canvas.translate(operator.left, operator.top);
        operatorPaint.setStrokeWidth(operator.width() / 5);
        operatorPaint.setColor(this.getColor());
        canvas.drawLine(0, operator.height() / 2, operator.width(), operator.height() / 2, operatorPaint);
        canvas.drawLine(operator.width() / 2, 0, operator.width() / 2, operator.height(), operatorPaint);
        canvas.restore();
        
        // Draw the children
        drawChildren(canvas, maxWidth, maxHeight);
    }

}