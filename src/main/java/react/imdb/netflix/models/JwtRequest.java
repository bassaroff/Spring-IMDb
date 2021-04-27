package react.imdb.netflix.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequest implements Serializable {
    private static final long serialVersionUID = 123456789L;
    private String email;
    private String password;
    private String fullName;
    private String newPassword;

    JwtRequest(String email, String password){
        this.email = email;
        this.password = password;
    }

    public JwtRequest(String email, String fullName, String newPassword) {
        this.email = email;
        this.fullName = fullName;
        this.newPassword = newPassword;
    }
}