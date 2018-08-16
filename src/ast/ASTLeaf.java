package ast;

import interp.Environment;
import interp.Token;

public abstract class ASTLeaf extends ASTree {

    protected Token token;
    //构造器方法，传入一个token
    public ASTLeaf(Token t) { token = t; }
    //叶节点没有孩子
    public ASTree child(int i) { throw new IndexOutOfBoundsException(); }
    //叶节点孩子为0
    public int numChildren() { return 0; }

    //覆盖toString , 返回 字符串 tokenValue(都被小写过)
    public String toString() { return token.getText(); }

    public Token getToken() { return token; }


    // public Iterator<ASTree> children() { return empty.iterator(); }


    //语义操作
    public abstract Object eval(Environment env) ;

}
