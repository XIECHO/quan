package interp;

public class Token {

    public final String tokenValue;
    public final TokenType tokenType;
    

    //constructor
    public Token (String _tokenValue, TokenType _tokenType) {
        //assignements
        tokenValue = _tokenValue;
        tokenType = _tokenType;

    }
    
    public String getText(){
    	return tokenValue;
    }

    @Override
    public String toString() {
        return "(\""+tokenValue+"\",\""+tokenType.name()+"\")";
    }
}
