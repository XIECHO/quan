package util;

public class TenToBinary {

	public static String ttb(int i,int length) {
		double j = Math.ceil(Math.log(length) / Math.log(2));
		String tempS = Integer.toBinaryString(i);
		int numb = tempS.length();
		while (numb < j) {
			tempS = "0" + tempS;
			numb++;
		}
		return "|" + tempS + ">";
	}
}
