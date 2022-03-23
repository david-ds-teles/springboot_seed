package com.david.ds.teles.springboot.seed.utils.converters;

public interface EntityConverter<T> {
	void convert(T entity);
}
