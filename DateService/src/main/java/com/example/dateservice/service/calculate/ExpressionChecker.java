package com.example.dateservice.service.calculate;

import java.util.HashMap;
import java.util.Map;

/**
 * 表达式检查程序
 *
 * @author jgblm
 * @date 2021/11/13
 */
public class ExpressionChecker {
    private static Map<ExpState, Map<ExpStateRoute, ExpState>> STATE_TRANS_MAP = new HashMap<>();

    static {
        Map<ExpStateRoute, ExpState> flagTransfer = new HashMap<>();
        flagTransfer.put(ExpStateRoute.NUMBER, ExpState.DIGIT);
        STATE_TRANS_MAP.put(ExpState.FLAG, flagTransfer);

        Map<ExpStateRoute, ExpState> digitTransfer = new HashMap<>();
        digitTransfer.put(ExpStateRoute.NUMBER, ExpState.DIGIT);
        digitTransfer.put(ExpStateRoute.DOT, ExpState.DOT_DIGIT);
        digitTransfer.put(ExpStateRoute.OPERATOR, ExpState.OPERATOR);
        digitTransfer.put(ExpStateRoute.RIGHT_BRACKET, ExpState.RIGHT_BRACKET);
        STATE_TRANS_MAP.put(ExpState.DIGIT, digitTransfer);

        Map<ExpStateRoute, ExpState> dotTransfer = new HashMap<>();
        dotTransfer.put(ExpStateRoute.NUMBER, ExpState.DOT_DIGIT);
        dotTransfer.put(ExpStateRoute.OPERATOR, ExpState.OPERATOR);
        dotTransfer.put(ExpStateRoute.RIGHT_BRACKET, ExpState.RIGHT_BRACKET);
        STATE_TRANS_MAP.put(ExpState.DOT_DIGIT, dotTransfer);

        Map<ExpStateRoute, ExpState> optTransfer = new HashMap<>();
        optTransfer.put(ExpStateRoute.NUMBER, ExpState.DOT_DIGIT);
        optTransfer.put(ExpStateRoute.LEFT_BRACKET, ExpState.LEFT_BRACKET);
        STATE_TRANS_MAP.put(ExpState.OPERATOR, optTransfer);

        Map<ExpStateRoute, ExpState> lbTransfer = new HashMap<>();
        lbTransfer.put(ExpStateRoute.FLAG, ExpState.FLAG);
        lbTransfer.put(ExpStateRoute.NUMBER, ExpState.DOT_DIGIT);
        lbTransfer.put(ExpStateRoute.LEFT_BRACKET, ExpState.LEFT_BRACKET);
        STATE_TRANS_MAP.put(ExpState.LEFT_BRACKET, lbTransfer);

        Map<ExpStateRoute, ExpState> rbTransfer = new HashMap<>();
        rbTransfer.put(ExpStateRoute.OPERATOR, ExpState.OPERATOR);
        rbTransfer.put(ExpStateRoute.RIGHT_BRACKET, ExpState.RIGHT_BRACKET);
        STATE_TRANS_MAP.put(ExpState.RIGHT_BRACKET, rbTransfer);
    }

    public static boolean check(String expression) {
        if (expression.isEmpty()) {
            return false;
        }

        ExpState state = ExpState.getExpressionState(expression.charAt(0));
        if (state == ExpState.OTHER) {
            return false;
        }

        int bracketCount = 0; // 还未闭合的括号的个数
        if (state == ExpState.LEFT_BRACKET) {
            bracketCount++;
        }

        for (int i = 1; i < expression.length(); i++) {
            ExpStateRoute route = ExpStateRoute.getRoute(expression.charAt(i), expression.charAt(i - 1));
            if (route == ExpStateRoute.OTHER) {
                return false;
            }
            if (route == ExpStateRoute.LEFT_BRACKET) {
                bracketCount++;
            }
            if (route == ExpStateRoute.RIGHT_BRACKET) {
                bracketCount--;
            }
            state = STATE_TRANS_MAP.get(state).getOrDefault(route, ExpState.OTHER);
            if (state == ExpState.OTHER) {
                return false;
            }
        }
        return bracketCount == 0 && ExpState.isEndState(state);
    }
}
