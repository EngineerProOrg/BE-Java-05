package com.engineerpro.rest.example.common;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.engineerpro.rest.example.model.App;
import com.engineerpro.rest.example.service.AppService;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthenticationFilter implements Filter {
  private static final String API_HEADER_KEY = "x-api-key";
  public static final String APP_ATTRIBUTE = "authenticatedApp";
  private AppService appService;

  public AuthenticationFilter(AppService appService) {
    this.appService = appService;
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    // Initialization code if needed
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;

    String apiClientKey = request.getHeader(API_HEADER_KEY);
    log.debug(String.format("got apikey from header: {}", apiClientKey));
    if (apiClientKey == null) {
      response.setStatus(HttpStatus.FORBIDDEN.value());
      return;
    }

    App app = appService.getAppByClientKey(apiClientKey);
    if (app == null) {
      response.setStatus(HttpStatus.FORBIDDEN.value());
      return;
    }

    request.setAttribute(APP_ATTRIBUTE, app);

    // Continue with the filter chain
    filterChain.doFilter(request, response);
  }

  @Override
  public void destroy() {
    // Cleanup code if needed
  }
}
