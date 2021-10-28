# MyCanculator
# 计算器的设计思路

> 首先知道计算器的功能包括加减乘除，因此我想到的是将要计算的表达式当做一个字符串处理（这里的字符串只包括数字和+ - * / % 运算，不包括括号）因此计算的时候是根据输入的字符串按四则运算优先顺序计算的。

****

### 四则运算的基本思路

​	四则运算我采用的是`BigDecimal`来计算的，因为`BigDecimal`可以避免计算精度的丢失以及计算`double`类型计算不了的数字都可以用`BigDecimal`来代替 ,并且定义了两个栈`numStack`和`operStack`分别存储操作数和操作符。

​	有加减乘除就有计算的优先顺序，即在一个式子中，总是乘除先运算完之后再运算加减，而对于用户输入待计算的式子是作为字符串处理的。因此这里就存在一个问题，如何保证你读入的时候是把一个数字完整读入的呢，比如<u>1.234+5</u>只需要判断当前字符的下一个是否是操作符，如果是，读完当前字符之后转成`double`，并压入`numStack`,如果不是，存到一个局部字符变量等待下一次的操作符到来。

​	操作符的处理是读到一个操作符时就判断它和`operStack`栈顶元素的优先级比较，如果它的优先级高（✖️或➗），压入栈，否则，从`numStack`弹出两个值，`operStack`弹出操作符，并且将运算之后的值压入`numStack`。当字符串处理完之后，对两个栈中的结果进行运算，从`numStack`弹出第一个值给left，第二个值给right，`operStack`弹出操作符对left和right运算。这里举个例子，比如<u>1+2*3-8/2</u>，

​	根据遍历的流程可以看出每次运算都是将当前`operStack`栈顶元素和当前遍历操作符比较，若是同一优先级或者优先级低的话就直接作运算，否则，操作符进栈。




****



### 类的作用

+ 接口`Operator`：两个抽象方法`BigDecimal op(BigDecimal left, BigDecimal right)`、`int priority()`，分别代表运算方法和优先级。

+ 枚举类`OpEnum`：有五个实例，`OP_AND`,`OP_SUB`,`OP_MUL`,`OP_DIV`,`op_MOD`,实现了`Operator`接口，所以都对两个抽象方法进行实现，并且加入了`public static Optional<OpEnum> fromOpt(char opt)`和`public static boolean isOpt(char ch)`，第一个返回一个`Optional`容器对象，他包含有get方法可以返回一个操作符，第二个是判断ch是否是一个操作符。使用枚举的主要原因是后面如果想加入功能便于修改。
+ 计算器类`Calculator`：两个方法，`private static String doCalculate(String input)`以字符串的形式返回结果,`public static String calculate(String input)`第一个方法有可能抛出异常，所以这个方法里面加入捕获异常，别返回"输入错误"。
+ 窗口类`CalculatorFrame`：绘制计算器的图形界面并且美化以及对计算结果的保存和查看。

<mark>以上就是本次期末大作业的设计🥰<mark>

