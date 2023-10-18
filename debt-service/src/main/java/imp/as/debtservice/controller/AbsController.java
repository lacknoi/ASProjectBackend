package imp.as.debtservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import imp.as.debtservice.dto.response.ApiResponse;

@Component
public class AbsController {
	public ResponseEntity<ApiResponse> responseOK(Object data){
        ApiResponse apiResponse = new ApiResponse(data);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
