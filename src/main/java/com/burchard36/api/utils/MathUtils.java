package com.burchard36.api.utils;

/**
 * THIS CLASS WAS PROVIDED BY ANOTHER DEVELOPER NOT ME!
 *
 * These math methods are faster to work with than typical Math classes, as they work
 * with raw bits
 *
 * Users GitHub who provided these methods: https://github.com/frostalf
 *
 * @author frostalf
 * @since 2.1.5
 */
public class MathUtils {

    /**
     * Efficiently multiplies 2 numbers via bit flipping
     * @param n1 A {@link Integer}
     * @param n2 A {@link Integer}
     * @return The result {@link Integer} of {@param n1} * {@param n2}
     * @since 2.1.5
     */
    public static int multiply(int n1, int n2) {
        int a = n1, b = n2, result=0;
        while (b != 0) // Iterate the loop till b==0
        {
            if ((b & 01) != 0) // Logical ANDing of the value of b with 01
            {
                result = result + a; // Update the result with the new value of a.
            }
            a <<= 1;              // Left shifting the value contained in 'a' by 1.
            b >>= 1;             // Right shifting the value contained in 'b' by 1.
        }
        return result;
    }

    /**
     * Efficiently adds two numbers via bit flipping
     * @param n1 A {@link Integer}
     * @param n2 A {@link Integer}
     * @return The result {@link Integer} of {@param n1} + {@param n2}
     * @since 2.1.5
     */
    public static int add(int n1, int n2) {
        //noinspection UnnecessaryLocalVariable
        int x = n1, y = n2;
        int xor, and, temp;
        and = x & y;
        xor = x ^ y;

        while (and != 0) {
            and <<= 1;
            temp = xor ^ and;
            and &= xor;
            xor = temp;
        }
        return xor;
    }

    /**
     * Bit-flips a square root of an {@link Short}
     * @param num A {@link Short} to calculate square root of
     * @return The square root of {@param num}
     * @since 2.1.5
     */
    public static short squareRoot(short num) {
        short res = 0;
        short bit = 1 << 14; // The second-to-top bit is set: 1L<<30 for long

        // "bit" starts at the highest power of four <= the argument.
        while (bit > num)
            bit >>= 2;

        while (bit != 0) {
            if (num >= res + bit) {
                num -= res + bit;
                res = (short) ((res >> 1) + bit);
            }
            else
                res >>= 1;
            bit >>= 2;
        }
        return res;
    }

    /**
     * Efficiently calculates if a provided {@link Integer} is odd
     * @param n A {@link Integer} to check if odd
     * @return A {@link Boolean}, true if number is odd, false if not
     * @since 2.1.5
     */
    public static boolean isOdd(int n) {
        return (n&1)==0;
    }

    /**
     * Calculated the {@param a} when its exponent is {@param b}
     * @param a A {@link Integer}, this is the base number
     * @param b A {@link Integer}, this is the exponent
     * @return A {@link Long}, this is the result of this statement
     * @since 2.1.5
     */
    public static long calcExponent(int a,int b) {
        // initialize ans with 1
        long ans = 1;
        while(b>0) {
            // check if last bit 1
            if((b&1)==1){
                ans = (ans * a);
            }

            // update value of a by a*a
            a = multiply(a, a);

            // right shift b by 1
            b = b>>1;
        }
        return ans;
    }
}