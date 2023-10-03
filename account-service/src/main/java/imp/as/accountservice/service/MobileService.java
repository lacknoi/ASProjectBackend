package imp.as.accountservice.service;

import java.util.List;

import imp.as.accountservice.dto.MobileRequest;
import imp.as.accountservice.dto.MobileResponse;

public interface MobileService {
	public MobileResponse registerMobile(MobileRequest mobileRequest);
	public List<MobileResponse> getMobileActiveByAccountNo(String accountNo);
}
