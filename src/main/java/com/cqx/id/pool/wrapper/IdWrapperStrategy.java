package com.cqx.id.pool.wrapper;

public interface IdWrapperStrategy<T> {

    T wrap(long id);
}
