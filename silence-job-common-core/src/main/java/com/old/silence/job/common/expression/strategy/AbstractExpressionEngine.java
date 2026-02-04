package com.old.silence.job.common.expression.strategy;

import java.util.Map;

import com.old.silence.job.common.expression.ExpressionEngine;


public abstract class AbstractExpressionEngine implements ExpressionEngine {
    @Override
    public Object eval(String expression, Object... t) {

        return doEval(expression, (Map<String, Object>) t[0]);
    }

    protected abstract Object doEval(String expression, Map<String, Object> context);
}
