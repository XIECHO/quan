package interp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;


public class Lexer {
	private StringBuffer input;
	private int pos=0;
	char c;
	public static final char EOF = (char)-1;
	ArrayList<Token> tokens = new ArrayList<Token>();
	
	public Lexer(Reader reader) throws IOException{
		BufferedReader br = new BufferedReader(reader);
		StringBuffer text = new StringBuffer();
		String tmp;
		while((tmp = br.readLine())!=null){
			text.append(tmp);
			text.append("\n");
		}
		
		input = text;
		c = input.charAt(pos);
	}
	
	public Lexer(String str){
		input = new StringBuffer();
		input.append(str);
		c = input.charAt(pos);
	}

	public Token nextToken(){
		//System.out.println(c);
		while(c!=EOF){
			if(Character.isDigit(c)){	
				return scanNumericConstant();
			}else if(isOperatorStart(c)){
				return scanOperator();	
			}else if (Character.isJavaIdentifierStart(c)) {	
				return scanIdentifier();
			} else if (Character.isWhitespace(c)) {
				skipWhitespace();continue;
			} 
		}
		return new Token("EOF",TokenType.EOF);
	}
	
	
	private void skipWhitespace() {
		consume();
	}
	private Token scanNumericConstant() {
		StringBuilder buf = new StringBuilder();
		buf.append(c);consume();
		while(Character.isDigit(c)){
			buf.append(c);consume();
		}
		if(c=='.'){
			buf.append(c);consume();
			while(Character.isDigit(c)){
				buf.append(c);consume();
			}
			return new Token(buf.toString(),TokenType.Real);
		}
//		else if(c == '+'){
//			buf.append(c);consume();
//			while(Character.isDigit(c)){
//				buf.append(c);consume();
//			}
//			if (c=='i'){
//				buf.append(c);consume();
//			}
//			return new Token(buf.toString(),TokenType.Complex);
//		}else if(c == '-'){
//			buf.append(c);consume();
//			while(Character.isDigit(c)){
//				buf.append(c);consume();
//			}
//			if (c=='i'){
//				buf.append(c);consume();
//			}
//			return new Token(buf.toString(),TokenType.Complex);
//		}
		else{
			return new Token(buf.toString(),TokenType.Int);
		}
	}
	
	 public void consume() {
	      pos++;
	      if ( pos >= input.length() ) c = EOF;
	      else c = input.charAt(pos);
	 }
	 
	 
	static final char[] opStarts = { ',','.',';','(',')','+','-','!','<','>','/','*'};
	
	private boolean isOperatorStart(char c) {
		for(char o : opStarts){
			if(c == o){
				return true;
			}
		}
		return false;
	}
	
	private Token scanOperator() {
		if(c == ','){
			consume();
			return new Token(",",TokenType.Comma);
		}else if(c == '.'){
			consume();
			return new Token(".",TokenType.Dot);
		} else if(c == ';'){
			consume();
			return new Token(";",TokenType.SemiColon);
		} else if(c == '('){
			consume();
			return new Token("(",TokenType.LParenthesis);
		}else if(c == ')'){
			consume();
			return new Token(")",TokenType.RParenthesis);
		}else if(c == '+'){
			consume();
			return new Token("+",TokenType.ADD);
		}else if(c == '-'){
			consume();
			return new Token("-",TokenType.Minus);
		}else if(c == '*'){
            consume();
            return new Token("*",TokenType.Multi);
        }else if(c == '/'){
            consume();
            return new Token("/",TokenType.Divide);
        }else if (c == '!') {
			consume();
            return new Token("!",TokenType.Not);
		}
		//判断是不是复数
		else if (c == '<') {
            StringBuilder buf = new StringBuilder();
            buf.append(c);consume();
            if(c=='-'){
                buf.append(c);consume();
            }
            while(Character.isDigit(c)){
                buf.append(c);consume();
            }
            if(c=='.'){
                buf.append(c);consume();
                while(Character.isDigit(c)){
                    buf.append(c);consume();
                }
            }
            if(c==','){
                buf.append(c);consume();
            }
            if(c=='-'){
                buf.append(c);consume();
            }
            while(Character.isDigit(c)){
                buf.append(c);consume();
            }
            if(c=='.'){
                buf.append(c);consume();
                while(Character.isDigit(c)){
                    buf.append(c);consume();
                }
            }
            if(c=='>'){
                buf.append(c);consume();
            }
            return new Token(buf.toString(),TokenType.Complex);
        }
		return null;
	}

	
	private Token scanIdentifier() {
		int start = pos;
		
		while (Character.isJavaIdentifierPart(c)) {
			consume();
		}
		String text = input.substring(start, pos).toLowerCase();
		switch(text){

        case "if":
        	return new Token(text,TokenType.If);
        case "then":
        	return new Token(text,TokenType.Then);
        case "fi":
        	return new Token(text,TokenType.Fi);
        case "quantum":
        	return new Token(text,TokenType.Quantum);
        case "measure":
        	return new Token(text,TokenType.Measure);
        case "reset":
        	return new Token(text,TokenType.Reset);
        case "gate":
        	return new Token(text,TokenType.Gate);
        case "cnot":
        	return new Token(text,TokenType.BinaryOp);
        case "u":
        	return new Token(text,TokenType.UnaryOp);
        case "x":
        	return new Token(text,TokenType.UnaryOp);
        case "y":
        	return new Token(text,TokenType.UnaryOp);
        case "z":
        	return new Token(text,TokenType.UnaryOp);
        case "h":
        	return new Token(text,TokenType.UnaryOp);
        case "s":
        	return new Token(text,TokenType.UnaryOp);
        case "sdg":
        	return new Token(text,TokenType.UnaryOp);
        case "t":
        	return new Token(text,TokenType.UnaryOp);
        case "tdg":
        	return new Token(text,TokenType.UnaryOp);

        case "pi":
        	return new Token(text,TokenType.PI);
        case "alias":
            return new Token(text,TokenType.Alias);
         case "show":
            return new Token(text,TokenType.Show);
        default:
            if(c=='['){
                StringBuilder sb =  new StringBuilder();
                sb.append(text);
                sb.append(c);
                consume();
                while(Character.isDigit(c)){
                    sb.append(c);consume();
                }
                if(c==']'){
                    sb.append(c);consume();
                    return new Token(sb.toString(),TokenType.Array);
                }
                return null;
            }
            return new Token(text,TokenType.Idertifier);

		}

	}
}
