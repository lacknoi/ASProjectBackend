package common;

import java.io.Serializable;

import lombok.Data;

@Data
public class CreateAccountTopicRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 12345L;
	private String accountNo;
	private String userName;
}
