package util;

public class BinaryToTen {
    public static double btt(char[] num) {
        double integ = 0;
        for(int i=0;i<num.length;i++) {
            int temp = num[i] - '0';
            integ = integ + temp * Math.pow(2, num.length-i-1);
        }
        return integ;
    }
}
