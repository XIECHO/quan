package util;

import org.jscience.mathematics.number.Complex;
import org.jscience.mathematics.vector.ComplexMatrix;

public class ConjugateTranspose {
	
	public static ComplexMatrix apply(ComplexMatrix cm) {
		int rowNum = cm.getNumberOfRows();
		int columnNum = cm.getNumberOfColumns();
		ComplexMatrix temp = cm;
		ComplexMatrix temp1 = null ;
		Complex[][] m = new Complex[columnNum][rowNum];
		
		for(int i=0;i<rowNum;i++) {
			for(int j=0;j<columnNum;j++) {
				m[j][i]=temp.get(i, j).conjugate();
			}
		}
		
		temp1=ComplexMatrix.valueOf(m);
		return temp1;
	}
	


}
