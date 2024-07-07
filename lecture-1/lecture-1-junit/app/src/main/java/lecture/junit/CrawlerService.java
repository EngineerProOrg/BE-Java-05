package lecture.junit;

import lecture.junit.exception.InvalidStatusCodeException;
import lecture.junit.scrapper.Scrapper;

public class CrawlerService {
  private final Scrapper scrapper;

  public CrawlerService(Scrapper scrapper) {
    this.scrapper = scrapper;
  }

  public int showStatusCodeOrRaiseException(String url) throws InvalidStatusCodeException {
    int statusCode = scrapper.crawlStatusCode(url);
    if (statusCode == 200) {
      System.out.println("Happy status code");
      return statusCode;
    }
    throw new InvalidStatusCodeException();
  }
}
