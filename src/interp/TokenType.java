package interp;

public enum TokenType {
    Dot,                            //.
    Comma,                       //,
    SemiColon,                   //;


    Not,                         //!




    LParenthesis,                //(
    RParenthesis,                //)

    Minus,                        //-
    ADD,                            //+
    Multi,                        //*
    Divide,


    Int,
    Real,
    Complex,


    Array,
    Idertifier,


    If,
    Then,
    Fi,

    Alias,

    Show,


    EOF,


    Quantum,
    Measure,
    Gate,
    Reset,

    UnaryOp,
    BinaryOp,

    PI,

}
