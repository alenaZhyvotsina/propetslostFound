package propets.lostfound.filter.security;

import java.io.IOException;
import java.net.URI;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import propets.lostfound.dto.UserRoleDto;

import static propets.lostfound.configuration.Constants.TOKEN_HEADERS;

/**
 * 
 *
 */
@Component
@Order(20)
public class UserValidateFilter implements Filter {

	@Autowired
	RestTemplate restTemplate;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		
		//System.out.println("!+!+!" + checkEndpoint(request.getServletPath(), request.getMethod()));
		/*
		if(checkEndpoint(request.getServletPath(), request.getMethod())) {
			try {
				String token = request.getHeader(TOKEN_HEADERS);
				if(token != null) {
					
					HttpHeaders headers = new HttpHeaders();
					headers.add(TOKEN_HEADERS, token);
					
					RequestEntity<String> requestEntity =  new RequestEntity<>(headers, HttpMethod.GET, 
							new URI("http://localhost:8080/accounting/token"));
					
					ResponseEntity<UserRoleDto> responseEntity = 
							restTemplate.exchange(requestEntity, UserRoleDto.class);
					UserRoleDto userRoleDto = responseEntity.getBody();
					
					if(userRoleDto.isBanned()) {
						response.sendError(403);
						return;
					}
					
					response.setHeader(TOKEN_HEADERS, 
							responseEntity.getHeaders().getFirst(TOKEN_HEADERS));
				
				} else {
					response.sendError(403);
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.sendError(403);
				return;
			}
		}
		*/
		chain.doFilter(request, response);

	}
	
	private boolean checkEndpoint(String path, String method) {
		boolean res = path.matches("/lostfound/.*") && !"GET".equalsIgnoreCase(method);
		return res;
	}
	
}
