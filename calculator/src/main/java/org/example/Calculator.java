package org.example;

import java.math.BigInteger;

public class Calculator {

    public BigInteger add(BigInteger a, BigInteger b) {
        return a.add(b);
    }

    public BigInteger subtract(BigInteger a, BigInteger b) {
        return a.subtract(b);
    }

    public BigInteger multiply(BigInteger a, BigInteger b) {
        return a.multiply(b);
    }

    public BigInteger divide(BigInteger a, BigInteger b) {
        return a.divide(b);
    }
}
