package com.idhub.magic.clientlib.interfaces;

public interface Listen<T> {
	void listen(ResultListener<T> l, ExceptionListener el);
}
