package medima;

import java.math.BigDecimal;

public interface Operator {

    BigDecimal op(BigDecimal left, BigDecimal right);

    int priority();
}
