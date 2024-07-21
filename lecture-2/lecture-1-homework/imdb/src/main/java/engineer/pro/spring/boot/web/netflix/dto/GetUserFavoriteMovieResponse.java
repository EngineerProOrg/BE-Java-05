package engineer.pro.spring.boot.web.netflix.dto;

import java.util.List;

import engineer.pro.spring.boot.web.netflix.model.Movie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GetUserFavoriteMovieResponse {
  List<Movie> favoriteMovies;
}