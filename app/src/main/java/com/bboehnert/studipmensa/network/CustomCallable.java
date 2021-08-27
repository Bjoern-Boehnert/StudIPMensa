package com.bboehnert.studipmensa.network;

import java.util.concurrent.Callable;

interface CustomCallable<R> extends Callable<R> {
    void setDataAfterLoading(R result);
    void setUiForLoading();
}