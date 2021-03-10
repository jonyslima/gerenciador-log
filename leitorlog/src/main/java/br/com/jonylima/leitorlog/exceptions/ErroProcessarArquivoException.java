package br.com.jonylima.leitorlog.exceptions;

public class ErroProcessarArquivoException extends RuntimeException {
	private static final long serialVersionUID = -5988627336848227911L;

	public ErroProcessarArquivoException(String message, Throwable cause) {
		super(message, cause);
	}

	public ErroProcessarArquivoException(String message) {
		super(message);
	}
}
