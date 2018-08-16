package Quantum;

import org.jscience.mathematics.number.Complex;
import org.jscience.mathematics.vector.ComplexMatrix;
import org.jscience.mathematics.vector.ComplexVector;


//Qubit记录每个qubit的状态概率
public class Qubit {
	Complex[] possibles;

	public Qubit(Complex[] possibles) {
		this.possibles = possibles;
	}

	//得到qubit是几个量子比特
	public int number(){
		return (int)(Math.log(possibles.length)/Math.log(2));
	}
	
	// Qbits-->矩阵
	public ComplexMatrix vector() {
		return ComplexMatrix.valueOf(ComplexVector.valueOf(possibles)).transpose();
	}

	// 矩阵-->Qubit
	public void matrixToQbits(ComplexMatrix temp) {
		ComplexVector vec = temp.vectorization();
		for (int i = 0; i < vec.getDimension(); i++) {
			possibles[i] = vec.get(i);
		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Complex i:possibles) {
			sb.append(i+"  ");
		}
		return sb.toString();
	}
	
	
	public void setPossibles(Complex[] possibles) {
		this.possibles = possibles;
	}
	
	//返回长度
	public int Length() {
		return  possibles.length;
	}

	//返回概率辐
	public Complex[] getPossibles() {
		return possibles;
	}
	
	//返回概率值
	public double[] getPossiblesValue() {
		double[] temp = new double[Length()];
		for(int i=0;i<Length();i++) {
			temp[i]=this.possibles[i].conjugate().times(this.possibles[i]).doubleValue();
		}
		return  temp;
	}
	
	public String possibleValue() {
		double[] temp = getPossiblesValue();
		String tempS="";
		for(int i=0;i<Length();i++) {
			tempS=tempS + String.valueOf(temp[i])+" ";
		}
		return tempS;
	}

	public ComplexMatrix matrix(){
		return vector().times(vector().transpose());
	}

}
