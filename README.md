
# Source code
You can clone my repository quan and it can run in intellij idea, you may need add j-science libaray
# Qsimulation Grammer
<pre>
Program     --------> Declare { ; Statement }
Declare     --------> Quantum Identifier INT [Explist]
Statement  --------> Operator | Measurement | Show | Alias | IFStatement | Reset
Operator    --------> UnaryOp | BinaryOp | GateOp | UOp
UnaryOp     --------> X|Y|Z|S|SDG|T|TDG Argument
BinaryOp    --------> CNOT Argument Argument
GateOp      --------> Gate[Explist] Argument
UOp         --------> U[Explist] Argument
Measurement--------> Measure [ Argument Identifier ]
Show        --------> Show
Alias       --------> Alias Argument, Argument
IFStatement---------> IF Bool Then Statement { ; Statement } FI
Reset       ---------> Reset
Bool       ----------> [!] Identifier
Argument   ---------> Identifier | Array
Explist    ---------> (Expression {, Expression})
Expression ---------> Factor [+|-|*|/ Expression]
Factor      ---------> [-] INT | Complex | Real

INT := [0-9]+
Real := [0-9]+\.[0-9]*
Complex := \< \-? Real \, \-? Real \>
Identifier := [a-zA-Z]*
Array := [a-zA-Z]*\[INT\]

</pre>
The list grammer has been used in the Qsimulation. We will add some useful grammer in the future.
# QSimulation statements
Statement  | Description 
:-|:-

Quantum Identifier INT	| 声明名为Identifier，含有INT位的量子寄存器
Quantum Identifier INT Explist	| 声明名为Identifier，含有INT位的量子寄存器，并根据Explist初始化量子寄存器
X|Y|Z|S|SDG|T|TDG Argument	| 内置单量子比特门操作
U[Explist] Argument	| 单量子参数量子门操作
CNOT Argument Argument	| 内置双量子比特门操作
Gate[Explist] Argument	| 自定义量子门操作
Measure 	| 量子状态的整体测量
Measure [Argument Identifier ]	| 量子状态的子状态的测量
Show	| 展示量子寄存器的当前状态
Reset	| 重置量子寄存器
Alias Argument, Argument	| 给一个Argument取别名为第二个Argument
IF Bool Then Statement { ; Statement } FI	| 经典判断语句，根据测量结果，判断是否执行相对应操作


# Qsimulation Structure
<img src="/images/QSimulation.PNG">
# How to use
<img src="/images/echo.PNG">
As you can see, the above picture is screenshot of Qsimulation.    

## black box area
The black box area offers some buttons, which represent different functions.         

### initialization
- button 'Init'：initialization without parameters
- button 'ass': initialization with possibility ampitudes

### Unary Gates 
<pre>built-gate(without parameters)
  X        Y         Z        S     SDT          T           TDG 
|0 1|   |0  -i|   |1   0|   |1 0|  |1  0|  |1    0     |   |1     0     |
|1 0|   |i   0|   |0  -1|   |0 i|  |- -i|  |0 exp(iπ/4)|   |0 exp(-iπ/4)|

built-gate(with parameters) U(x,y,z)
            |exp(−i(φ+λ)/2)cos(θ/2)  -exp(−i(φ-λ)/2)sin(θ/2)|
U(θ,φ,λ) =  |                                               |
            |exp(i(φ-λ)/2)sin(θ/2)   exp(i(φ+λ)/2)cos(θ/2)  |
and the global phase is exp(i(φ+λ)/2)/cos(θ/2)
</pre>
### Binary Gate
<pre> Now, we only present a binary gate: CNOT, and we will offer some other universal matrix, such as CNOT-S.
        |1 0 0 0|
        |0 1 0 0|
cnot =  |0 0 0 1|
        |0 0 1 0| 
Although we don't present some other binary gate, we can constuct these.For example,
        |1 0 0 0|
        |0 0 1 0|
swap =  |0 1 0 0| 
        |0 0 0 1| 
swap[q1,q2] = cnot[q1,q2]cnot[q2,q1]cnot[q1,q2].  
</pre>
### Measurements
We only support 0-1 measure now, and we will offer some other measurements
### Classical control
input: number of executions 

It means we can test the program some times and Qsimulation will offer the final distribution of statistics.
## red box area
Qsimulation presents seven functions : import , save , restart, run, circuit, cirnext ,cirback

- import : you can import the program to the editor
- save : you can save the program 
- restart : you can restart the program 
- run : you can run the program
- circuit : you can generate the quantum circuit
- cirNext : you can debug the program
- cirBack : you can debug the program

# Example
There are some examples in the file "example".
## quantum teleportation
<pre>
Quantum a 3;
Alias a[0] Alice1;
Alias a[1] Alice2;
Alias a[2] Bob;
H Alice1;
H Alice2;
CNOT Alice2 Bob;
CNOT Alice1 Alice2;
H Alice1;
Measure Alice1,i;
Measure Alice2, j;
if i then Z Bob fi;
if j then X Bob fi

</pre>

## quantum fourier transform
<pre>
Quantum q 3;
X q[2];
H q[0];
T q[1];
CNOT q[1] q[0];
TDG q[0];
CNOT q[1] q[0];
T q[0];
U(0,0,PI/8) q[2];
CNOT q[2] q[0];
U(0,0,-PI/8) q[0];
CNOT q[2] q[0];
U(0,0,PI/8) q[0];
H q[1];
T q[2];
CNOT q[2] q[1];
TDG q[1];
CNOT q[2] q[1];
T q[1];
H q[2];
CNOT q[0] q[2];
CNOT q[2] q[0];
CNOT q[0] q[2]
</pre>

