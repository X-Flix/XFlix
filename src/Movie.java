import javax.swing.ImageIcon;

/**
 * This class is the framework that contains the basic information stored for a
 * movie in the the library that is available for rental.
 *
 */
public class Movie {

  protected String title;
  protected String year;
  protected String genre;
  protected String director;
  protected String cast1;
  protected String cast2;
  protected String cast3;
  protected String tag1;
  protected String tag2;
  protected String tag3;
  protected String picFileName;
  protected String synopsis;
  protected ImageIcon moviePoster;

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
    this.picFileName = picFileName;
    this.synopsis = synopsis;
    moviePoster = new ImageIcon(picFileName);
  }

}
