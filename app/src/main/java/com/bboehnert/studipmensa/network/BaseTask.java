package com.bboehnert.studipmensa.network;

public abstract class BaseTask<R> implements CustomCallable<R> {

    public abstract void setUiForLoading();

    public abstract void setDataAfterLoading(R result);

    @Override
    public abstract R call() throws Exception;
}
