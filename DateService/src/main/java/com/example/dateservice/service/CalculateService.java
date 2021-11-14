package com.example.dateservice.service;

import com.example.dateservice.dto.CalculatorResponse;
import com.example.dateservice.dto.Result;
import com.example.dateservice.exception.ExpressionFormatException;
import com.example.dateservice.service.calculate.ExpressionChecker;
import com.example.dateservice.service.calculate.Operator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

@Service
@Slf4j
public class CalculateService {
    private static final Set<Character> ALLOWED_NUMBERS = new HashSet<>(Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'));

    /**
     * 根据表达式计算结果
     * 如果表达式有错，抛出{@link ExpressionFormatException}
     *
     * @param expression 表达式
     * @return {@link Result}<{@link CalculatorResponse}>
     */
    public Result<CalculatorResponse> calculate(String expression) {
        String expAfterTrim = expression.trim();
        if (!ExpressionChecker.check(expAfterTrim)) {
            throw new ExpressionFormatException(expAfterTrim);
        }
        double result = compute(expAfterTrim);
        CalculatorResponse response = new CalculatorResponse();
        response.setResult(result);
        return Result.success(response);
    }

    private double compute(String expression) {
        Stack<Operator> operatorStack = new Stack<>();
        Stack<Double> numberStack = new Stack<>();
        StringBuilder currentNum = new StringBuilder(); //当前数字
        char[] expressionChars = expression.toCharArray();
        char preChar = '(';
        for (char cur : expressionChars) {
            if (isNumber(preChar,cur)) {
                currentNum.append(cur);
                preChar = cur;
                continue;
            }
            preChar = cur;
            Operator curOpt = Operator.getOperator(cur);
            if (curOpt != Operator.OTHER) {
                // 操作数入栈
                if (currentNum.length() > 0) {
                    numberStack.push(Double.parseDouble(currentNum.toString()));
                    currentNum.delete(0, currentNum.length());
                }

                if (operatorStack.empty() || curOpt == Operator.LEFT_BRACKET) {
                    operatorStack.push(curOpt);
                } else if (curOpt == Operator.RIGHT_BRACKET) {
                    subComputeWithBracket(operatorStack, numberStack);
                } else {
                    compareAndCalc(curOpt, operatorStack, numberStack);
                }
            } else {
                throw new ExpressionFormatException(expression);
            }
        }

        if (currentNum.length() > 0) {
            numberStack.push(Double.parseDouble(currentNum.toString()));
        }
        subComputeWithoutBracket(operatorStack, numberStack);
        return numberStack.pop();
    }

    private boolean isNumber(char preChar,char curChar){
        // 当前一个字符是'('时，+-判定为正负符号
        if(preChar == '(' && (curChar == '+' || curChar == '-')){
            return true;
        }
        return ALLOWED_NUMBERS.contains(curChar);
    }

    private void subComputeWithBracket(Stack<Operator> operatorStack, Stack<Double> numberStack) {
        if (operatorStack.peek() == Operator.LEFT_BRACKET) {
            operatorStack.pop();
        } else {
            Double num1 = numberStack.pop();
            Double num2 = numberStack.pop();
            Operator opt = operatorStack.pop();
            Double result = opt.compute(num1, num2);
            numberStack.push(result);
            subComputeWithBracket(operatorStack, numberStack);
        }
    }

    private void subComputeWithoutBracket(Stack<Operator> operatorStack, Stack<Double> numberStack) {
        Double num1 = numberStack.pop();
        Double num2 = numberStack.pop();
        Operator opt = operatorStack.pop();
        Double result = opt.compute(num1, num2);
        numberStack.push(result);
        if (!operatorStack.empty()) {
            subComputeWithoutBracket(operatorStack, numberStack);
        }
    }

    private void compareAndCalc(Operator curOpt, Stack<Operator> operatorStack, Stack<Double> numberStack) {
        Operator stackTopOpt = operatorStack.peek();

        // 当前运算符级别比较高，先计算当前运算符
        if (curOpt.compareWith(stackTopOpt) > 0) {
            operatorStack.push(curOpt);
        } else {
            // 当前运算符级别低，先计算栈中级别高的运算符
            Operator topOpt = operatorStack.pop();
            Double num1 = numberStack.pop();
            Double num2 = numberStack.pop();
            numberStack.push(topOpt.compute(num1, num2));

            if (operatorStack.empty()) {
                operatorStack.push(curOpt);
            } else {
                compareAndCalc(curOpt, operatorStack, numberStack);
            }
        }
    }

}
