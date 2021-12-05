package com.app.shared;

import java.util.concurrent.atomic.AtomicReference;

public abstract class AbstractSingleton<T> {

	private final AtomicReference<T> atomicReference = new AtomicReference<T>();

	public T getInstance() {
		T ret = atomicReference.get();
		if (ret == null) {
			synchronized (this) {
				if (ret == null) {
					ret = newObj();
					atomicReference.set(ret);
				}
			}
		}
		return ret;
	}

	protected abstract T newObj();
}