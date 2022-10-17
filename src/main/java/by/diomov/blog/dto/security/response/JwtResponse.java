package by.diomov.blog.dto.security.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtResponse {
	private String token;
	private String id;
	private String username;
	private String email;
	private List<String> roles;
}