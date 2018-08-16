package interp;


import ast.List_Statements;

import java.io.FileReader;


public class Input {

    public static void main(String[] args) throws Exception {
        //FileReader reader2 = new FileReader("test2.txt");


//		Lexer lexer2 = new Lexer("QuantumOperator c 15 (<1,2>,1,1+3);Alias a[0] , Alice;" +
//                "Alias a[1] , Bob1 ;" +
//                "Alias a[2] , Bob2 ;" +
//                "U(PI/2,0.2,0.1) Alice;" + "H Bob1;CNOT Bob1 Bob2;CNOT Alice Bob1;"
//        +"H Alice;" +
//                "Measure;" +
//                "Measure Bob1 ,j;"+
//                "if i then Z Bob2 fi;"+"Gate(1,2) a b");

        // Lexer lexer2 = new Lexer("QuantumOperator c 1 (0.3,0.4);Show" );
//        Lexer lexer2 = new Lexer("QuantumOperator c 2;Show; Alias c[0] , Alice;  Show; " +
//                "Alias c[1] , Bob1 ;Show; H Alice;H Bob1;Show; Measure;show" );

        //
        // Lexer lexer2 = new Lexer("QuantumOperator c 1 ;Show; Alias c[0] , Alice;Show ; Gate(0,<0,-1>,<0,1>,0) Alice;show" );

//		while(!(t=lexer2.nextToken()).getText().equals("EOF")){
//
//			System.out.println(t);
//		}
        try {
            Lexer lexer2 = new Lexer("QuantumOperator a 3;" +
                    "show;" +
                    "Alias a[0] Alice1;" +
                    "show;" +
                    "Alias a[1] Alice2;" +
                    "Alias a[2] Bob;" +
                    "H Alice1;" +
                   // "show;" +
                    "H Alice2;" +
                   // "show;" +
                    "CNOT Alice2 Bob;" +
                    "CNOT Alice1 Alice2;" +
                    "H Alice1;" +
                    "show;" +
                    "Measure axi;"+
                    "show"
                    //"show;" +
//                    "Measure Alice2,j;" +
//                    "show;" +
//                    "if j then Z Bob fi;"+
//                   "show "
            );
            Parser parser = new Parser(lexer2);
            List_Statements st = parser.program();
            //System.out.println(st.toString());
            Environment env = new Environment();
            st.eval(env);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
