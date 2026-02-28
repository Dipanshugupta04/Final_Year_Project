import java.util.*;

public class Series {
    public static String getNthNumerator(int n) {
        // Starting
        String num = "4";

        // Build numerator by inserting digits
        for (int i = 2; i <= n; i++) {
            num = i - 1 + num; // add digit in front
        }

        return num;
    }

    public static String getNthTerm(int n) {
        String numerator = getNthNumerator(n);

        long denominator = (long) Math.pow(10, n) - 1;

        return numerator + "/" + denominator;
    }

    public static void main(String[] args) {
        int n = 4; // find 4th term
        System.out.println("Term " + n + " = " + getNthTerm(n));
    }
}
