package com.makeid.makeflow.basic.cache;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
*@program makeflow-service
*@description 包装的属性 读时 一定有 且不会一直从数据库或其他服务获取 读扩散
*@author feng_wf
*@create 2023-05-30
*/
public class Lazy<T> implements Supplier<T> {

    private final Supplier<T> supplier;

    private T value;

    public Lazy(Supplier<T> supplier) {
        this.supplier = supplier;
    }


    @Override
    public T get() {
        if(Objects.nonNull(value)) {
            return value;
        }
        this.value = supplier.get();
        return value;
    }

    public static <T> Lazy<T> of(Supplier<T> supplier) {
        return new Lazy<T>(supplier);
    }

    public <S> Lazy<S> map(Function<T,? extends S> function) {
        return new Lazy<>(()->function.apply(this.get()));
    }


}
