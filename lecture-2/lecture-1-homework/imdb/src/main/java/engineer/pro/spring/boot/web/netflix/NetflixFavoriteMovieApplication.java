package engineer.pro.spring.boot.web.netflix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import engineer.pro.spring.boot.web.netflix.dto.FavoriteMovieRequest;
import engineer.pro.spring.boot.web.netflix.dto.GetUserFavoriteMovieResponse;
import engineer.pro.spring.boot.web.netflix.dto.LoginRequest;
import engineer.pro.spring.boot.web.netflix.dto.RegistrationRequest;
import engineer.pro.spring.boot.web.netflix.exception.MovieNotFoundException;
import engineer.pro.spring.boot.web.netflix.model.User;
import engineer.pro.spring.boot.web.netflix.repository.AppRepository;
import engineer.pro.spring.boot.web.netflix.service.UserService;

@SpringBootApplication
public class NetflixFavoriteMovieApplication {

	public static void main(String[] args) throws MovieNotFoundException {
		ApplicationContext context = SpringApplication.run(NetflixFavoriteMovieApplication.class, args);

		String email = "example@engineerpro.com";
		String password = "password";
		// get created bean
		UserService userService = context.getBean(UserService.class);

		AppRepository appRepository = context.getBean(AppRepository.class);
		// use bean to call functions
		// init db
		appRepository.addMovie(1, "Spider man");
		appRepository.addMovie(2, "Superman");
		appRepository.addMovie(3, "Wonder woman");
		// call service
		userService
				.createUser(RegistrationRequest.builder().email(email).password(password).build());
		User user = userService.findUser(LoginRequest.builder().email(email).password(password).build());
		userService.favoriteMovie(user, FavoriteMovieRequest.builder().movieId(1).build());
		userService.favoriteMovie(user, FavoriteMovieRequest.builder().movieId(2).build());
		GetUserFavoriteMovieResponse favoriteResponse = userService.getUserFavoriteMovies(user);
		System.out.println("favoriteResponse=" + favoriteResponse);
	}

}
