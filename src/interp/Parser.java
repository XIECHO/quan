package interp;

import java.util.ArrayList;
import java.util.List;

import ast.*;


//当解析的时候有语法错误，统一处理
//比如一句话以"+"开头该怎么办
public class Parser {
    Lexer input;
    Token lookahead;

    public Parser(Lexer input) {
        this.input = input;
        consume();
    }

    public void consume() {
        lookahead = input.nextToken();
    }

    public List_Statements program() throws Exception {
        List<ASTree> list = new ArrayList<ASTree>();
        ASTList stats2;
        //第一句必须是quantum
        if (lookahead.tokenType == TokenType.Quantum) {
            list.add(declare());
        } else {
            throw new Exception("you must declare firstly!");
        }

        //后面就是普通state语句
        while (lookahead.tokenType == TokenType.SemiColon) {

            match(TokenType.SemiColon);
            stats2 = statement();
            list.add(stats2);


        }
        return new List_Statements(list);
    }


    private void match(TokenType t) {
        if (lookahead.tokenType == t) {
            consume();
        }
    }

    private ASTList declare() {
        List<ASTree> list = new ArrayList<ASTree>();
        //量子的变量名
        ASTLeaf identity;
        //几个量子比特
        ASTLeaf count;
        //参数列表
        List<ASTLeaf> params = new ArrayList<>();


        //匹配Quantum
        match(TokenType.Quantum);

        //匹配变量名
        identity = new Leaf_Idertifier(lookahead);
        list.add(identity);
        match(TokenType.Idertifier);

        //量子比特
        count = new Leaf_Int(lookahead);
        list.add(count);
        match(TokenType.Int);

        //看有没有参数
        if (lookahead.tokenType.equals(TokenType.LParenthesis)) {
            ASTList exp;
            exp = expList();
            list.add(exp);
        }
//        if (lookahead.tokenType.equals(TokenType.LParenthesis)) {
//            match(TokenType.LParenthesis);
//            int i = 0;
//
//
//            //这里可能需要重新写 改为参数列表
//            while (!(lookahead.tokenType.equals(TokenType.RParenthesis))) {
//                switch (lookahead.tokenType) {
//                    case Real:
//                        list.add(new Leaf_Real(lookahead));
//                        match(TokenType.Real);
//                        i++;
//                        break;
//                    case Int:
//                        list.add(new Leaf_Int(lookahead));
//                        match(TokenType.Int);
//                        i++;
//                        break;
//                    case Comma:
//                        match(TokenType.Comma);
//                        break;
//                    case Complex:
//                        list.add(new Leaf_Complex(lookahead));
//                        match(TokenType.Complex);
//                        i++;
//                        break;
//                    default:
//                        break;
//                }
//            }
//            match(TokenType.RParenthesis);

        //判断个数对不对
        //报错；
//        }

        return new List_Quantum(list);
    }

    private ASTList statement() throws Exception {
        List<ASTree> list = new ArrayList<ASTree>();
        ASTList bool;
        ASTList state;
        ASTLeaf argument1;
        ASTLeaf argument2;

        switch (lookahead.tokenType) {
            case Alias:
                match(TokenType.Alias);
                argument1 = argument();
                list.add(argument1);
                match(TokenType.Comma);
                //alias 赋值操作不能赋值给array
                argument2 = new Leaf_Idertifier(lookahead);
                list.add(argument2);
                match(TokenType.Idertifier);
                return new List_Alias(list);
            case Show:
                match(TokenType.Show);
                return new List_Show(list);
            case UnaryOp:
                state = unaryOp();
                return state;
            case BinaryOp:
                state = binaryOp();
                return state;
            case Measure:
                match(TokenType.Measure);
                // || !(lookahead != null)

                if (!(lookahead.tokenType.equals(TokenType.EOF))) {

                    if (!lookahead.tokenType.equals(TokenType.SemiColon)) {

                        argument1 = argument();
                        list.add(argument1);

                        //measurement也不能赋值给array
                        if (!(lookahead.tokenType.equals(TokenType.EOF))) {

                            if (!lookahead.tokenType.equals(TokenType.SemiColon)) {
                                match(TokenType.Comma);
                                argument2 = new Leaf_Idertifier(lookahead);
                                list.add(argument2);
                                match(TokenType.Idertifier);
                            }
                        }
                    }
                }


                return new List_Measure(list);
            //这里reset要重新写
            case Reset:
                match(TokenType.Reset);
                return new List_Reset(list);
            // return new List_Reset(list);
            //自定义门的情况
            case Gate:
                match(TokenType.Gate);
                ASTList exp;
                exp = expList();
                list.add(exp);

                //while语句中不能以semiColon结尾
                while (!(lookahead.tokenType.equals(TokenType.SemiColon)) && !(lookahead.tokenType.equals(TokenType.EOF))) {
                    argument1 = argument();
                    list.add(argument1);
                }
                return new List_Gate(list);
            case If:
                match(TokenType.If);
                bool = bool();
                list.add(bool);
                match(TokenType.Then);
                //这里要重写，执行的内容可能不止一条。。。。
                state = statement();
                list.add(state);
                match(TokenType.Fi);
                return new List_IF(list);
            case EOF:
                throw new Exception("最后的语句不要以；结尾");
            default:
                throw new Exception("不合法的语句");
        }
    }

    //bool
    private List_Bool bool() {
        List<ASTree> list = new ArrayList<ASTree>();
        ASTLeaf left;

        if (lookahead.tokenType.equals(TokenType.Not)) {
            //Operator 为！
            left = new Leaf_Operator(lookahead);
            match(TokenType.Not);
            list.add(left);
        }

        list.add(new Leaf_Idertifier(lookahead));
        match(TokenType.Idertifier);

        return new List_Bool(list);
    }

    private ASTLeaf argument() {
        ASTLeaf argument;
        switch (lookahead.tokenType) {
            case Idertifier:
                argument = new Leaf_Idertifier(lookahead);
                match(TokenType.Idertifier);
                return argument;
            case Array:
                argument = new Leaf_Array(lookahead);
                match(TokenType.Array);
                return argument;
            default:
                return null;
        }
    }

    //双量子比特
    private ASTList binaryOp() {
        ASTLeaf bOperator;
        List<ASTree> list = new ArrayList<ASTree>();
        ASTLeaf argument1;
        ASTLeaf argument2;

        bOperator = new Leaf_BOperator(lookahead);
        list.add(bOperator);
        match(TokenType.BinaryOp);
        //第一个变量
        argument1 = argument();
        //第二个变量
        argument2 = argument();
        list.add(argument1);
        list.add(argument2);
        return new List_BinaryOP(list);
    }

    //单量子比特
    private ASTList unaryOp() {
        ASTLeaf uOperator;
        ASTLeaf argument;
        ASTList exp;
        List<ASTree> list = new ArrayList<ASTree>();


        switch (lookahead.tokenValue) {
            //通用门的情况
            case "u":
                match(TokenType.UnaryOp);
                //参数列表
                //match(TokenType.LParenthesis);
                exp = expList();
                list.add(exp);
                //match(TokenType.RParenthesis);
                argument = argument();
                list.add(argument);
                return new List_UOP(list);
            default:
                uOperator = new Leaf_UOperator(lookahead);
                list.add(uOperator);
                match(TokenType.UnaryOp);

                argument = argument();
                list.add(argument);
                return new List_UnaryOP(list);
        }
    }

    private List_ExpList expList() {
        List<ASTree> list = new ArrayList<ASTree>();
        //临时参数
        ASTList exp;

        //参数列表匹配左括号

        match(TokenType.LParenthesis);
        //用来记录参数的个数
        int i = 0;
        //如果不是右括号，则遍历
        while (!(lookahead.tokenType.equals(TokenType.RParenthesis))) {
            //参数
            exp = exp();
            list.add(exp);
            i++;
        }
        //匹配右括号
        match(TokenType.RParenthesis);
        return new List_ExpList(list);
    }

    //
    private ASTList exp() {
        List<ASTree> list = new ArrayList<ASTree>();
        ASTList factor1;
        ASTList exp;
        ASTLeaf operator;


        //判断它是正数，还是负数
        factor1 = factor();
        list.add(factor1);


        //判断有没有加减乘除
        switch (lookahead.tokenType) {

            //如果右边是右括号，说明没参数了
            case RParenthesis:
                //match(TokenType.RParenthesis);
                break;
            case Comma:
                match(TokenType.Comma);
                break;
            case ADD:
                operator = new Leaf_Operator(lookahead);
                list.add(operator);
                match(TokenType.ADD);

                exp = exp();
                list.add(exp);
                return new List_Expression(list);

            case Minus:
                System.out.println("mmm");
                operator = new Leaf_Operator(lookahead);
                match(TokenType.Minus);
                list.add(operator);

                exp = exp();
                list.add(exp);
                return new List_Expression(list);
            case Multi:
                operator = new Leaf_Operator(lookahead);
                list.add(operator);
                match(TokenType.Multi);

                exp = exp();
                list.add(exp);
                return new List_Expression(list);
            case Divide:
                operator = new Leaf_Operator(lookahead);
                list.add(operator);
                match(TokenType.Divide);

                exp = exp();
                list.add(exp);
                return new List_Expression(list);
            default:
                break;
        }

        return new List_Expression(list);
    }

    //判断是不是负数
    private ASTList factor() {
        List<ASTree> list = new ArrayList<ASTree>();
        ASTLeaf minus;
        ASTLeaf primary;
        if (lookahead.tokenType == TokenType.Minus) {
            minus = new Leaf_Operator(lookahead);
            match(TokenType.Minus);
            list.add(minus);
//            primary = primary();
//            list.add(primary);
//            return new List_Factor(list);
        }
        primary = primary();
        list.add(primary);

        return new List_Factor(list);

    }

    //数的类型
    private ASTLeaf primary() {
        ASTLeaf element;
        switch (lookahead.tokenType) {
            //实数
            case Real:
                element = new Leaf_Real(lookahead);
                match(TokenType.Real);
                return element;
            //整数
            case Int:
                element = new Leaf_Int(lookahead);
                match(TokenType.Int);
                return element;
            //PI
            case PI:

                element = new Leaf_PI(lookahead);
                match(TokenType.PI);
                return element;
            //实数
            case Complex:
                element = new Leaf_Complex(lookahead);
                match(TokenType.Complex);

                return element;
            default:
                break;
        }
        return null;
    }


}
