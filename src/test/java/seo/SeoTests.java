package seo;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import utils.SSLHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static helpers.AssertionMessages.WRONG_NUMBER_OF_ELEMENTS;
import static helpers.AssertionMessages.WRONG_VALUE_OF_ELEMENT;
import static org.junit.jupiter.api.Assertions.*;

@Tag("SEO_REGRESSION")
@Execution(ExecutionMode.CONCURRENT)
public class SeoTests {

    private static final String META_TEST_DATA_CSV = "/MetaTestData.csv";
    private static final String SERVICE_URL = "https://yourservice.com";

    @DisplayName("Check for correct meta titles and descriptions")
    @ParameterizedTest(name = "{0} Should have correct meta and description")
    @CsvFileSource(resources = META_TEST_DATA_CSV, numLinesToSkip = 1)
    public void checkIfPagesHaveCorrectMetaTitlesAndDescriptions(String url, final String metaTitle, String metaDescription) throws IOException {

        Document document = SSLHelper.getConnection(url).get();
        String metaTitleWebsite = document.select("head title").text();
        String metaDescWebsite = document.select("meta[name='description']").attr("content");

        assertAll(
                () -> assertEquals(
                        metaTitle,
                        metaTitleWebsite,
                        WRONG_VALUE_OF_ELEMENT
                ),
                () -> assertEquals(
                        metaDescription,
                        metaDescWebsite,
                        WRONG_VALUE_OF_ELEMENT
                )
        );
    }

    @DisplayName("Sitemap should contain links")
    @ParameterizedTest
    @ValueSource(strings = {
            "/sitemap_offers.xml",
            "/sitemap_articles.xml",
            "/sitemap_product.xml",
            "/sitemap_articlescat.xml",
            "/sitemap_static.xml",
            "/sitemap_banks.xml"
    })
    public void sitemapsShouldContainLinks(String sitemap) throws IOException {

        Document document = SSLHelper.getConnection(SERVICE_URL.concat(sitemap)).get();
        Elements urls = document.select("url");
        assertTrue(urls.size() > 0, WRONG_NUMBER_OF_ELEMENTS);
    }

    @DisplayName("Checked if links are returning 200")
    @ParameterizedTest(name = "Check for service: {0}")
    @ValueSource(strings = {
            SERVICE_URL
    })
    public void testLinkStatus(String url) throws IOException {

        Document doc = SSLHelper.getConnection(url).get();
        Elements links = doc.select("a[href]");

        List<String> failedLinks = new ArrayList<>();

        for (Element link : links) {
            String href = link.attr("abs:href");
            int status = getResponseStatus(href);

            if (status != 200) {
                failedLinks.add(href + " returned status code: " + status);
            }
        }

        assertTrue(failedLinks.isEmpty(), "Failed links:\n" + String.join("\n", failedLinks));
    }

    private int getResponseStatus(String urlString) {

        try {
            return SSLHelper.getConnection(urlString).execute().statusCode();

        } catch (HttpStatusException e) {

            return e.getStatusCode();
        } catch (IOException e) {

            return -1;
        }
    }
}
