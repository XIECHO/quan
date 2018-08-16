package util;

import org.jscience.mathematics.number.Complex;

public class AmpToBra {
    public static String translate(Complex[] temp) {
        String str = "";
        for (int j = 0; j < temp.length; j++) {
            //求需要几个比特
            double i = Math.log(temp.length) / Math.log(2);
            //转为二进制
            String tempS = Integer.toBinaryString(j);
            //长度
            int numb = tempS.length();
            //如果长度小于几比特，前面补零
            while (numb < i) {
                tempS = "0" + tempS;
                numb++;
            }
            // 如果是实数
            if (temp[j].getImaginary() == 0) {
                // 如果不是第一位

                if (!str.equals("")) {
                    // 如果实数大于零并小于1
                    if (temp[j].getReal() > 0 && temp[j].getReal() < 1) {
                        str = str + "+" + String.format("%.4f", temp[j].getReal()) + "|" + tempS + ">";
                    } else if (temp[j].getReal() == 1) {
                        str = str + "+" + "|" + tempS + ">";
                    } else if (temp[j].getReal() == -1) {
                        str = str + "-" + "|" + tempS + ">";
                    } else if (temp[j].getReal() == 0) {
                    } else {
                        str = str + String.format("%.4f", temp[j].getReal()) + "|" + tempS + ">";
                    }
                } else {
                    if (temp[j].getReal() > 0 && temp[j].getReal() < 1) {
                        str = str + String.format("%.4f", temp[j].getReal()) + "|" + tempS + ">";
                    } else if (temp[j].getReal() == 1) {
                        str = str + "|" + tempS + ">";
                    } else if (temp[j].getReal() == -1) {
                        str = str + "-" + "|" + tempS + ">";
                    } else if (temp[j].getReal() == 0) {
                    } else {
                        str = str + String.format("%.4f", temp[j].getReal()) + "|" + tempS + ">";
                    }
                }
            } else {
                //如果虚数不为0
                //如果实数为0
                //这里可能有问题
                if(temp[j].getReal()==0){
                    if (!str.equals("")) {
                        str = str + "+" + "(" +String.format("%.4f", temp[j].getImaginary()) + "i" + ")" + "|" + tempS + ">";
                    } else {
                        str = str +"(" +String.format("%.4f", temp[j].getImaginary()) + "i" + ")" + "|" + tempS + ">";
                    }
                }else{
                    if (!str.equals("")) {
                        if(temp[j].getImaginary()>0){
                            str = str + "+" + "(" + String.format("%.4f", temp[j].getReal()) +"+"+String.format("%.4f", temp[j].getImaginary())+ "i"  + ")" + "|" + tempS + ">";
                        }else{
                            str = str + "+" + "(" + String.format("%.4f", temp[j].getReal()) + String.format("%.4f", temp[j].getImaginary())+ "i"  + ")" + "|" + tempS + ">";
                        }
                    } else {
                        if(temp[j].getImaginary()>0){
                            str = str + "(" + String.format("%.4f", temp[j].getReal()) +"+"+String.format("%.4f", temp[j].getImaginary())+ "i"  + ")" + "|" + tempS + ">";
                        }else{
                            str = str + "(" + String.format("%.4f", temp[j].getReal()) + String.format("%.4f", temp[j].getImaginary())+ "i"  + ")" + "|" + tempS + ">";
                        }
                    }
                }
            }
        }
        return str;
    }
}
