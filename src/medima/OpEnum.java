package medima;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

public enum OpEnum implements Operator {

		OP_ADD('+') {
				@Override
				public BigDecimal op(BigDecimal left, BigDecimal right) {
						return left.add(right);
				}

				@Override
				public int priority() {
						return 0;
				}
		},
		OP_SUB('-') {
				@Override
				public BigDecimal op(BigDecimal left, BigDecimal right) {
						return left.subtract(right);
				}

				@Override
				public int priority() {
						return 0;
				}
		},
		OP_MUL('*') {
				@Override
				public BigDecimal op(BigDecimal left, BigDecimal right) {
						return left.multiply(right);
				}

				@Override
				public int priority() {
						return 1;
				}
		},
		OP_DIV('/') {
				@Override
				public BigDecimal op(BigDecimal left, BigDecimal right) {
						return left.divide(right);
				}

				@Override
				public int priority() {
						return 1;
				}
		},
		OP_MOD('%') {
				@Override
				public BigDecimal op(BigDecimal left, BigDecimal right) {
						return BigDecimal.valueOf(left.doubleValue() % right.doubleValue());
				}

				@Override
				public int priority() {
						return 1;
				}
		};

		private final char opt;

		OpEnum(final char opt) {
				this.opt = opt;
		}

		public char getOpt() {
				return opt;
		}

		public static Optional<OpEnum> fromOpt(char opt) {
				return Arrays.stream(values()).filter(op -> op.getOpt() == opt).findAny();
		}

		public static boolean isOpt(char ch) {
				return fromOpt(ch).isPresent();
		}
}
