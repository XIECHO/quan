package alogrithm;/* ------------------------------------------------------------------------------
 * Authors:	DK Lee, Derek Dang
 * 
 * Class: 	Driver.java
 * 
 * Purpose:	Runs Grover's Algorithm.
 * 
 * Compile:	Compile in Terminal.
 * 			javac Driver.java
 * 
 * Run:		java Driver
 * 
 * Input:	numQubits - the number of qubits passing in the top wire.
 * 			value	  - the special value to be found.
 * 
 * Output:	The probability of finding that value.
 * 
 * Notes:
 * 		1)	To search for a term, enter in the decimal value. The program
 * 			calculates the probability using decimal values.
 * 		2)	Remember that this is a classical program running a quantum
 * 			program. Running time of this program is O(2^n), but the quantum
 * 			version is O(√(2^n)), which is quadratic speedup.
 * 
 * ------------------------------------------------------------------------------ */
import java.util.*;
public class Driver
{
	public static void main(String[] args) 
	{
		int numQubits = 0, value = 0;
		double j;
		ArrayList<Double> vector = new ArrayList<Double>();
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter number of qubits:");
		numQubits = scan.nextInt();
		System.out.println("Enter the value you're searching for:");
		value = scan.nextInt();
				
		for(double i = 0; i < Math.pow(2, numQubits); i++)
		{
			vector.add(i);
		}
		
		// Begins Grover's Algorithm.
		GroverAlgorithm grover = new GroverAlgorithm(numQubits, value, vector);
		grover.initializeBinary();
		grover.initialize();
		vector = grover.createArray();
		grover.setVector(vector);
		for (j = 0.0; j < (int) Math.sqrt(Math.pow(2, numQubits));j++)
		{
			grover.phaseInversion();
			grover.inversionMean();
		}
		
		// Iterating beyond the √(2^n). Yields worse results.
		
//  		for (j = 0.0; j < (int) Math.sqrt(Math.pow(2, numQubits)) + 1; j++)
//		{
//			alogrithm.phaseInversion();
//			alogrithm.inversionMean();
//		}

		
		scan.close();
		
		// Prints the result.
		System.out.println("Final vector: " + vector);
		double probability = grover.findProbability();
		System.out.println("The probability of finding " + value + " is " + probability + ".");
		System.out.println("Found in " + (int) j + " steps.");
		
	} /* main */
}
