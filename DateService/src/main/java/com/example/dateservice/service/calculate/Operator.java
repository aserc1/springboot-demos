package com.example.dateservice.service.calculate;

/**
 * 运算操作符
 *
 * @author jgblm
 * @date 2021/11/12
 */
public enum Operator {
    PLUS('+', 1) {
        @Override
        public double compute(double num1, double num2) {
            return num1 + num2;
        }

    },
    MINUS('-', 1) {
        @Override
        public double compute(double num1, double num2) {
            return num2 - num1;
        }
    },
    MULTIPLY('*', 2) {
        @Override
        public double compute(double num1, double num2) {
            return num1 * num2;
        }
    },
    DIVIDE('/', 2) {
        @Override
        public double compute(double num1, double num2) {
            return num2 / num1;
        }
    },
    RIGHT_BRACKET(')', 0) {
        @Override
        public double compute(double num1, double num2) {
            throw new UnsupportedOperationException();
        }
    },
    LEFT_BRACKET('(', 0) {
        @Override
        public double compute(double num1, double num2) {
            throw new UnsupportedOperationException();
        }
    },
    OTHER(' ', 0) {
        @Override
        public double compute(double num1, double num2) {
            return 0;
        }
    };
    private char value;
    private int priority;

    Operator(char value, int priority) {
        this.value = value;
        this.priority = priority;
    }

    public abstract double compute(double num1, double num2);

    public static Operator getOperator(char value) {
        for (Operator operator : Operator.values()) {
            if (operator.value == value) {
                return operator;
            }
        }
        return OTHER;
    }

    public int compareWith(Operator opt){
        return this.priority-opt.priority;
    }
}
