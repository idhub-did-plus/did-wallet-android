package com.idhub.magic.clientlib.interfaces;

import java.util.List;

public interface IncomingListener<T> {
	void income(List<T> ts);
}
