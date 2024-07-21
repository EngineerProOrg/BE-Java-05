package engineer.pro.spring.boot.web.netflix.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Repository;

import engineer.pro.spring.boot.web.netflix.model.Movie;
import engineer.pro.spring.boot.web.netflix.model.User;
import engineer.pro.spring.boot.web.netflix.model.UserFavoriteMovie;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class AppRepositoryImpl implements AppRepository {
  private static AtomicInteger atomicUserId = new AtomicInteger();
  // allUser has user's email as the key
  private static Map<String, User> allUsers = new ConcurrentHashMap<>();
  // allMovies has movieId as the key
  private static Map<Integer, Movie> allMovies = new ConcurrentHashMap<>();

  private static Map<UserFavoriteMovie, Integer> allUserFavoriteMovieMap = new ConcurrentHashMap<>();

  @Override
  public User createNewUser(String email, String password) {
    int id = atomicUserId.getAndIncrement();
    User newUser = User.builder().id(id).email(email).password(password).build();
    User previousUser = allUsers.putIfAbsent(email, newUser);
    if (Objects.isNull(previousUser)) {
      // no exist this email,
      return newUser;
    } else {
      // already has this email in repository
      return null;
    }
  }

  @Override
  public User findUserByUserEmail(String email) {
    return allUsers.get(email);
  }

  @Override
  public Movie addMovie(int id, String title) {
    Movie newMovie = Movie.builder().id(id).title(title).build();
    allMovies.put(id, newMovie);
    return newMovie;
  }

  @Override
  public Movie findMovieByMovieId(int movieId) {
    return allMovies.get(movieId);
  }

  @Override
  public void saveFavoriteMovie(int userId, int movieId) {
    allUserFavoriteMovieMap.put(UserFavoriteMovie.builder().userId(userId).movieId(movieId).build(), 1);
    log.info("allUserFavoriteMovieMap" + allUserFavoriteMovieMap);
  }

  @Override
  public List<Integer> getUserFavoritesMovieIds(int userId) {
    List<Integer> movieIds = new ArrayList<>();
    // brute-force
    for (UserFavoriteMovie userFavoriteMovie : allUserFavoriteMovieMap.keySet()) {
      if (userFavoriteMovie.getUserId() == userId) {
        movieIds.add(userFavoriteMovie.getMovieId());
      }
    }
    return movieIds;
  }
}
