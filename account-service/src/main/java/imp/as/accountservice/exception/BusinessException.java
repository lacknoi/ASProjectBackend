package imp.as.accountservice.exception;

public class BusinessException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4035453717222791552L;
	
	private String messageCode;
	private Object data;
	
	public BusinessException(String message) {
		super(message);
	}
	
	public BusinessException(Object data) {
		this.data = data;
	}
	
	public BusinessException(String messageCode, Object data) {
		this.data = data;
	}
	
	public Object getData(){
        return this.data;
    }
	
	public String getMessageCode() {
		return messageCode;
	}
}
