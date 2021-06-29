
package medima;

import java.math.BigDecimal;
import java.util.Stack;

@SuppressWarnings("all")
public class Calculator {

		public static String calculate(String input) {
				try {
						String str = doCalculate(input);
						return str;
				} catch (Exception e) {
						return "输入错误";
				}
		}

		private static String doCalculate(String input) {
				Stack<BigDecimal> numStack = new Stack<>();
				Stack<OpEnum> operStack = new Stack<>();
				int index = 0;
				char ch = ' ';
				String keep = "";
				if ('-' == input.charAt(index)) {
						index++;
						while (!OpEnum.isOpt((input.charAt(index)))) {
								keep += input.charAt(index++);
						}
						numStack.push(BigDecimal.valueOf(-Double.parseDouble(keep)));
						keep = "";
				}
				while (true) {
						ch = input.charAt(index);
						if (OpEnum.isOpt(ch)) {
								OpEnum opEnum = OpEnum.fromOpt(ch).get();
								if (!operStack.isEmpty()) {
										if (opEnum.priority() <= operStack.peek().priority()) {
												BigDecimal right = numStack.pop(), left = numStack.pop();
												numStack.push(operStack.pop().op(left, right));
												operStack.push(opEnum);
										} else {
												operStack.push(opEnum);
										}
								} else {
										operStack.push(opEnum);
								}
						} else {
								keep += ch;
								if (index == input.length() - 1) {
										numStack.push(BigDecimal.valueOf(Double.parseDouble(keep)));
								} else {
										if (OpEnum.isOpt(input.charAt(index + 1))) {
												numStack.push(BigDecimal.valueOf(Double.parseDouble(keep)));
												keep = "";
										}
								}
						}
						if (++index >= input.length()) {
								break;
						}
				}

				while (true) {
						if (operStack.isEmpty()) {
								break;
						}
						BigDecimal right = numStack.pop(), left = numStack.pop();
						numStack.push(operStack.pop().op(left, right));
				}
				return String.valueOf(numStack.pop());
		}
}
