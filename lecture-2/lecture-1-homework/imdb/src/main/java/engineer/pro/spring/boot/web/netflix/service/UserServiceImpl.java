package engineer.pro.spring.boot.web.netflix.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import engineer.pro.spring.boot.web.netflix.dto.FavoriteMovieRequest;
import engineer.pro.spring.boot.web.netflix.dto.GetUserFavoriteMovieResponse;
import engineer.pro.spring.boot.web.netflix.dto.LoginRequest;
import engineer.pro.spring.boot.web.netflix.dto.RegistrationRequest;
import engineer.pro.spring.boot.web.netflix.dto.RegistrationResponse;
import engineer.pro.spring.boot.web.netflix.exception.MovieNotFoundException;
import engineer.pro.spring.boot.web.netflix.model.Movie;
import engineer.pro.spring.boot.web.netflix.model.User;
import engineer.pro.spring.boot.web.netflix.repository.AppRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
  @Autowired
  private AppRepository appRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;

  private String hashPassword(String password) {
    return passwordEncoder.encode(password);
  }

  @Override
  public RegistrationResponse createUser(RegistrationRequest registrationRequest) {
    final String hashedPassword = hashPassword(registrationRequest.getPassword());
    log.info("hashedPassword:" + hashedPassword);
    User createdUser = appRepository.createNewUser(registrationRequest.getEmail(), hashedPassword);
    if (Objects.isNull(createdUser)) {
      return null;
    }
    return new RegistrationResponse(createdUser.getId());
  }

  @Override
  public User findUser(LoginRequest loginRequest) {
    final String hashedPassword = hashPassword(loginRequest.getPassword());
    log.info("hashedPassword:" + hashedPassword);
    User user = appRepository.findUserByUserEmail(loginRequest.getEmail());
    if (!Objects.isNull(user) && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
      return user;
    }
    return null;
  }

  @Override
  public void favoriteMovie(User user, FavoriteMovieRequest favoriteMovieRequest) throws MovieNotFoundException {
    Movie movie = appRepository.findMovieByMovieId(favoriteMovieRequest.getMovieId());
    if (Objects.isNull(movie)) {
      throw new MovieNotFoundException();
    }
    appRepository.saveFavoriteMovie(user.getId(), favoriteMovieRequest.getMovieId());
  }

  @Override
  public GetUserFavoriteMovieResponse getUserFavoriteMovies(User user) {
    List<Integer> movieIds = appRepository.getUserFavoritesMovieIds(user.getId());
    List<Movie> favoriteMovies = movieIds.stream().map(movieId -> appRepository.findMovieByMovieId(movieId)).toList();
    return GetUserFavoriteMovieResponse.builder().favoriteMovies(favoriteMovies).build();
  }

}
