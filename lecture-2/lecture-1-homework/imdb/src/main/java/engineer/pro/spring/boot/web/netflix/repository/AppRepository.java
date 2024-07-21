package engineer.pro.spring.boot.web.netflix.repository;

import java.util.List;

import engineer.pro.spring.boot.web.netflix.model.Movie;
import engineer.pro.spring.boot.web.netflix.model.User;

public interface AppRepository {
  User createNewUser(String email, String password);

  User findUserByUserEmail(String email);

  Movie addMovie(int id, String title);

  Movie findMovieByMovieId(int movieId);

  void saveFavoriteMovie(int userId, int movieId);

  List<Integer> getUserFavoritesMovieIds(int userId);
}
