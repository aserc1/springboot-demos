package com.example.dateservice.service.calculate;

/**
 * 表达式有限机状态转换路径
 *
 * @author jgblm
 * @date 2021/11/13
 */
public enum ExpStateRoute {
    FLAG {
        @Override
        public boolean match(char ch, char preCh) {
            return preCh == '(' && (ch == '+' || ch == '-');
        }
    },
    NUMBER {
        @Override
        public boolean match(char ch, char preCh) {
            return ch >= '0' && ch <= '9';
        }
    },
    DOT {
        @Override
        public boolean match(char ch, char preCh) {
            return ch == '.';
        }
    },
    LEFT_BRACKET {
        @Override
        public boolean match(char ch, char preCh) {
            return ch == '(';
        }
    },
    RIGHT_BRACKET {
        @Override
        public boolean match(char ch, char preCh) {
            return ch == ')';
        }
    },
    OPERATOR {
        @Override
        public boolean match(char ch, char preCh) {
            return ch == '+' || ch == '-' || ch == '*' || ch == '/';
        }
    },
    OTHER {
        @Override
        public boolean match(char ch, char preCh) {
            return false;
        }
    };

    public abstract boolean match(char ch, char preCh);

    public static ExpStateRoute getRoute(char ch, char preCh) {
        for (ExpStateRoute route : ExpStateRoute.values()) {
            if (route.match(ch, preCh)) {
                return route;
            }
        }
        return OTHER;
    }
}
