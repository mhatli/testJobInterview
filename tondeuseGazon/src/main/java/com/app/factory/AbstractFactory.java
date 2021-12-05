package com.app.factory;

import com.app.shared.VersionOfInterface;

public interface AbstractFactory<T> {

	T create(VersionOfInterface version);

}
