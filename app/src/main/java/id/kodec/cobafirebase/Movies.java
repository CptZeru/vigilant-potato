package id.kodec.cobafirebase;

public class Movies {
    String movieId;
    String movieName;
    String movieRating;

    public Movies(){

    }

    public Movies(String movieId, String movieName, String movieRating) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.movieRating = movieRating;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getMovieRating() {
        return movieRating;
    }
}
