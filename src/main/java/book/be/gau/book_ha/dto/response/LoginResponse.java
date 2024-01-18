package book.be.gau.book_ha.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    @JsonProperty("customer_staff_email")
    private String customer_staff_email;
    @JsonProperty("login_password")
    private String login_password;
}
