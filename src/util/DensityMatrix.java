package util;


import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class DensityMatrix {
	// 传入的俄式概率值
	public static String apply(double[] values) {
		String str = "";
		for (int j = 0; j < values.length; j++) {
			double i = Math.log(values.length) / Math.log(2);
			String valuesS = Integer.toBinaryString(j);
			int numb = valuesS.length();
			while (numb < i) {
				valuesS = "0" + valuesS;
				numb++;
			}


			// 如果不是第一位
			if (j != 0) {
				// 如果实数大于零并小于1
				if (values[j] > 0 && values[j] < 1) {
					str = str + "+" + String.format("%.4f", values[j]) + "|" + valuesS + ">" + "<" + valuesS + "|";
				} else if (values[j] == 1) {
					str = str + "+" + "|" + valuesS + ">" + "<" + valuesS + "|";
				}
			} else {
				if (values[j] > 0 && values[j] < 1) {
					str = str + String.format("%.4f", values[j]) + "|" + valuesS + ">" + "<" + valuesS + "|";
				} else if (values[j] == 1) {
					str = str + "|" + valuesS + ">" + "<" + valuesS + "|";
				}
			}

		}
		return str;
	}
}
