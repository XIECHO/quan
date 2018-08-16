package ast;

import interp.Environment;
import org.jscience.mathematics.number.Complex;

import java.util.List;


public class List_Expression extends ASTList {

    public List_Expression(List<ASTree> list) {
        super(list);
    }

    //这里感觉不止是三个
    public ASTree left() {
        return child(0);
    }

    public ASTree operator() {
        return numChildren() > 2 ? child(1) : null;
    }

    public ASTree right() {
        return numChildren() > 2 ? child(2) : null;
    }

    public String toString() {
        return super.toString();
    }

    //不管怎样，这里都会变成复数
    public Object eval(Environment env) {
        if (operator() == (null)) {
            //这边也要变成复数形式
          //  System.out.println(".....");
           // return left().eval(env);
            if(left().eval(env) instanceof Double){
                return Complex.valueOf((double)(left().eval(env)),0);
            }else{
                return left().eval(env);
            }

        } else {
            Object left = left().eval(env);
            Object op = operator().eval(env);
            Object right = right().eval(env);
//            if (!(left instanceof Complex && right instanceof Complex)) {
//                System.out.println(left);
//                System.out.println(right);
//                return computerNumber((Double) left, (String) op, (Double) right);
//            } else {
                return computerNumber(left, (String) op, right);
            //}
        }
    }

    //返回复数，统一处理
    //当两个数字都不是复数
    //除数不能为0
    //报错
//    private Complex computerNumber(Double left, String op, Double right) {
//        double a = left.doubleValue();
//        double b = right.doubleValue();
//        double value ;
//
//        if (op.equals("+")) {
//
//            return Complex.valueOf(a + b,0);
//        } else if (op.equals("-")) {
//            return Complex.valueOf(a - b,0);
//        } else if (op.equals("/")) {
//            return Complex.valueOf(a / b,0);
//        } else {
//            return Complex.valueOf(a * b,0);
//        }
//    }

    //其中至少有一个为复数
    private Complex computerNumber(Object left, String op, Object right) {
        Complex l = null, r = null;
        if (!(left instanceof Complex)) {
            l = Complex.valueOf((double) left, 0);
        } else {
            l = (Complex) left;
        }

        if (!(right instanceof Complex)) {
            r = Complex.valueOf((double) right, 0);
        } else {
            r = (Complex) right;
        }
        if (op.equals("+")) {
            return l.plus(r);
        } else if (op.equals("-")) {
            return l.minus(r);
        } else if (op.equals("/")) {
            return l.divide(r);
        } else{
            return l.times(r);
        }
    }
}
