package engineer.pro.spring.boot.web.netflix.controller;

import engineer.pro.spring.boot.web.netflix.model.User;
import jakarta.servlet.http.HttpServletRequest;

public abstract class AbstractController {
  static final String SESSION_ATTRIBUTE_USER = "user";

  User getUserFromSession(HttpServletRequest request) {
    return (User) request.getSession().getAttribute(SESSION_ATTRIBUTE_USER);
  }
}
