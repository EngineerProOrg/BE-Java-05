package engineer.pro.spring.boot.web.netflix.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class User {
  private int id;
  private String email;
  private String password;
}
