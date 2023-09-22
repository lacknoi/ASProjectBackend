package imp.as.accountservice.service;

import imp.as.accountservice.dto.MobileRequest;
import imp.as.accountservice.dto.MobileResponse;

public interface MobileService {
	public MobileResponse registerMobile(MobileRequest mobileRequest);
}
