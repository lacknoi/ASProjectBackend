package imp.as.accountservice.dto.response;

import java.util.Date;

import lombok.Data;

@Data
public class ApiResponse {
	private Date timestamp;
	private Object data;
	
	public ApiResponse(Object data) {
        this.timestamp = new Date();
        this.data = data;
    }
}
