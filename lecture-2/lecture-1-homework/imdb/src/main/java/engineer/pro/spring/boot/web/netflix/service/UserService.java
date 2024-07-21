package engineer.pro.spring.boot.web.netflix.service;

import engineer.pro.spring.boot.web.netflix.dto.GetUserFavoriteMovieResponse;
import engineer.pro.spring.boot.web.netflix.dto.LoginRequest;
import engineer.pro.spring.boot.web.netflix.dto.FavoriteMovieRequest;
import engineer.pro.spring.boot.web.netflix.dto.RegistrationRequest;
import engineer.pro.spring.boot.web.netflix.dto.RegistrationResponse;
import engineer.pro.spring.boot.web.netflix.exception.MovieNotFoundException;
import engineer.pro.spring.boot.web.netflix.model.User;

public interface UserService {
  RegistrationResponse createUser(RegistrationRequest registrationRequest);

  User findUser(LoginRequest loginRequest);

  void favoriteMovie(User user, FavoriteMovieRequest favoriteMovieRequest) throws MovieNotFoundException;

  GetUserFavoriteMovieResponse getUserFavoriteMovies(User user);
}
