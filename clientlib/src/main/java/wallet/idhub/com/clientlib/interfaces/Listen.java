package wallet.idhub.com.clientlib.interfaces;

public interface Listen<T> {
	void listen(ResultListener<T> l, ExceptionListener el);
}
