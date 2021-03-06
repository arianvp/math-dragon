package org.teaminfty.math_dragon.view.math;

/** A class that holds constants for the precedence levels of the {@link Expression}s */
public abstract class Precedence
{
    /** The highest precedence */
    public static final int HIGHEST = 0;
    
    /** The precedence functions have */
    public static final int FUNCTION = HIGHEST;
    
    /** The precedence that integrals have */
    public static final int INTEGRAL = 1;

    /** The precedence power operations have */
    public static final int POWER = 2;

    /** The precedence multiply operations have */
    public static final int MULTIPLY = 3;
    
    /** The precedence of the negate operator */
    public static final int NEGATE = 4;

    /** The precedence add operations have */
    public static final int ADD = 5;
}
