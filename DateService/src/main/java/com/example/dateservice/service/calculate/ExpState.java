package com.example.dateservice.service.calculate;

/**
 * 表达式有限机状态
 *
 * @author jgblm
 * @date 2021/11/13
 */
public enum ExpState {
    FLAG{
        @Override
        public boolean match(char ch) {
            return ch=='+'||ch=='-';
        }
    },
    DIGIT{
        @Override
        public boolean match(char ch) {
            return ch>='0'&&ch<='9';
        }
    },
    DOT_DIGIT,
    OPERATOR,
    LEFT_BRACKET{
        @Override
        public boolean match(char ch) {
            return ch=='(';
        }
    },
    RIGHT_BRACKET,
    OTHER;

    public boolean match(char ch){
        return false;
    };

    /**
     * 获取表达式有限机状态
     *
     * @param ch 字符
     * @return {@link ExpState}
     */
    public static ExpState getExpressionState(char ch){
        for (ExpState state : ExpState.values()) {
            if(state.match(ch)){
                return state;
            }
        }
        return OTHER;
    }

    public static boolean isEndState(ExpState state){
        return state==DIGIT||state==DOT_DIGIT||state==RIGHT_BRACKET;
    }
}
