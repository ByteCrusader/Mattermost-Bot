package com.crusader.bt.utils;

import org.springframework.stereotype.Component;
import org.springframework.transaction.NoTransactionException;

@Component
public class MqUtil {

    public static <T extends Throwable> boolean errorPredicate(T exception) {
        return !(exception instanceof NoTransactionException);
    }

}
