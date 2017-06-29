package de.fau.amos.virtualledger.server.savings;


public class SavingsException extends Exception {

	private static final long serialVersionUID = 1L;

	public SavingsException() {
    }

    public SavingsException(String s) {
        super(s);
    }

    public SavingsException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public SavingsException(Throwable throwable) {
        super(throwable);
    }

    public SavingsException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
