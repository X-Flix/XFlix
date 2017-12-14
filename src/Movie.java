import javax.swing.*;

/**
 * This class is the framework that contains the basic information stored for a
 * movie in the the library that is available for rental.
 *
 */
public class Movie {

  String title;
  String year;
  String genre;
  protected String director;
  String cast1;
  String cast2;
  String cast3;
  String tag1;
  String tag2;
  String tag3;
  String picFileName;
  String synopsis;
  ImageIcon moviePoster;

  public Movie(String title, String year, String genre, String director,
      String cast1, String cast2, String cast3, String tag1, String tag2,
      String tag3, String picFileName, String synopsis) {
    this.title = title;
    this.year = year;
    this.genre = genre;
    this.director = director;
    this.cast1 = cast1;
    this.cast2 = cast2;
    this.cast3 = cast3;
    this.tag1 = tag1;
    this.tag2 = tag2;
    this.tag3 = tag3;
    this.picFileName = "MoviePics/"+picFileName;
    this.synopsis = synopsis;
    moviePoster = new ImageIcon(this.picFileName);
  }

  @Override
  public String toString() {
      return title + "|" + year + "|" + genre + "|" + director + "|" + cast1 + "|" + 
                  cast2 + "|" + cast3 + "|" + tag1 + "|" + tag2 + "|" + tag3 + "|" + 
              picFileName + "|" + synopsis + "\n";
  }
}
