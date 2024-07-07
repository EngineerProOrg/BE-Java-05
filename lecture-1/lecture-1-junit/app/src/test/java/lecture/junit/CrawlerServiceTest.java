package lecture.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import lecture.junit.exception.InvalidStatusCodeException;
import lecture.junit.scrapper.Scrapper;

class CrawlerServiceTest {
    private static CrawlerService crawlerService;
    private static Scrapper scrapper;

    @BeforeAll
    public static void beforeAll() {
        scrapper = mock(Scrapper.class);
        crawlerService = new CrawlerService(scrapper);
    }

    @Test
    void testShowStatusCode_ScrapperReturn200_VerifyScrapperCallAndNoError() throws InvalidStatusCodeException {
        // Arrange
        String url = "https://engineepro-java-spring.class";
        when(scrapper.crawlStatusCode(url)).thenReturn(200);
        // Act
        int statusCode = crawlerService.showStatusCodeOrRaiseException(url);
        // Assert
        assertEquals(300, statusCode);
        verify(scrapper).crawlStatusCode("https://engineepro-java-spring.class");
    }

    @Test
    void testShowStatusCode_ScrapperReturn500_VerifyScrapperCallAndException() {
        // Arrange
        String url = "https://engineepro-java-spring-500.class";
        when(scrapper.crawlStatusCode(url)).thenReturn(500);
        // Act
        assertThrows(InvalidStatusCodeException.class, () -> crawlerService.showStatusCodeOrRaiseException(url));
        // Assert
        verify(scrapper).crawlStatusCode("https://engineepro-java-spring-500.class");
    }

}
