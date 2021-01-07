package propets.lostfound.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.SERVICE_UNAVAILABLE)
public class TaggingServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8996824271169746788L;

}
