package Quantum;

import gui.model.Operator;
import gui.model.QuantumOperator;

import java.util.LinkedList;
import java.util.List;

public class Circuit {

    //寄存器大小
    private int register;

    //定义每一个操作的集合
    private List<Operator> operators ;

    public int getRegister() {
        return register;
    }


    //初始化寄存器
    public void initializeRegisters(int qubitsNum){
        register = qubitsNum;

        //初始化操作集合
        operators = new LinkedList<>();
        //加入quantum 操作
       operators.add(new QuantumOperator());
    }

    //增加操作
    public void addOperator(Operator operator, int operationStep) {
        if (operationStep == operators.size()) {
            operators.add(operator);
        }
        else {
            operators.add(operationStep, operator);
        }
    }

    public Operator getOperator(int index) {
        return operators.get(index);
    }
    public Operator getLastOperator() {
        return operators.get(operators.size()-1);
    }

    public int getNumberOfOperators() {
        if(operators == null){
            return -1;
        }
        return operators.size();
    }

}
