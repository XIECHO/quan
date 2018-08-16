# Quantum-1.0.exe 
Quantum-1.0.exe is an executable file as we can see in the same level
# Source code
You can clone my repository quan and it can run in eclipse, and you may need add j-science libaray
# Qsimulation Grammer
<pre>
program --> statement {;statement}  
statement --> declare | state   
state --> if bool then state fi |  
	  while bool do state od  |  
	  Measure argument => ID |  
	  IDstate |
          Arraystate |
          UOP |
          BOP 
declare --> Quantum Array | Quantum(exp,exp...) Array
bool --> ID := INT
argument --> ID | Array
BOP --> CNOT argument argument
UOP --> (X|Y|Z|S|SDG|T|TDG argument) | U(explist) argument
explist --> exp; exp;exp;
exp --> real | INT | pi |exp(+|-|/|* ) exp | -exp

ID := [a-zA-Z]* (without 'lastcome')
real := ([0-9]+\.[0-9]* | [0-9]* \.[0-9]+)([eE][-+]?[0-9]+)?
INT := [0-9]+
</pre>
The list grammer has been used in the Qsimulation. We will add some useful grammer for example:statement "reset" , and statement "while", and these statements hava been shown in the code.
# Qsimulation statements
Statement  | Description 
:-|:-
Quantum name[size]   | Declare a quantum state
Quantum name\[size\](param...)   | Declare a quantum state with parameters
Identity := name[size] | Assignment
Unary Identity \| name[size] | Apply built-in single qubit gate(H,S...)
U(theta,phi,lambda) Identity \| name[size] | Apply built-in single qubit gate with parameters
CNOT Identity \| name[size],Identity \| name[size] | Apply built-in CNOT gate
Measure Identity \| name[size] =>Identity  | Make measurement
if(Identity := int) then qop fi | Conditionally apply quantum operator

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

## green box area
It is code editor.
## purple box area
It will show quantum circuit.
## yellow box area
It will show the outcome.
## blue box area
It will show the final distribution of statistics.
# Example
There are some examples in the file "example".
## quantum teleportation
<pre>
Quantum a[3];
Alice1 := a[0];
Alice2 := a[1];
Bob1 := a[2];
H Alice1;
H Alice2;
CNOT Alice2 Bob1;
CNOT Alice1 Alice2;
H Alice1;
Measure Alice1 => i;
Measure Alice2 => j;
if i==1 then Z Bob1 fi;
if j==1 then X Bob1 fi
</pre>

## quantum fourier transform
<pre>
Quantum q[3];
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
Whenever you install Quantum-1.0.exe, it is easy to open the software.
