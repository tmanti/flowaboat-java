import me.tmanti.flowaboat.errors.web.InvalidRequestCount;
import me.tmanti.flowaboat.utils.ApiResponse;
import me.tmanti.flowaboat.utils.WebApi;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
import java.time.Clock;
import java.time.Instant;

public class WebApiTests {
    private WebApi webApi;

//    public static String readFileAsString(String fileName) throws IOException {
//        return new String(Files.readAllBytes(Paths.get(fileName)));
//    }

    @Before
    public void setUp() {
        webApi = new WebApi("https://www.cscenter.ca", 4);
    }

    @After
    public void tearDown() {
        webApi.setClock(Clock.system(Clock.systemUTC().getZone()));
        webApi = null;
    }

    @Test
    public void getTest() throws IOException, InvalidRequestCount {
        ApiResponse response = webApi.get("robots.txt");
//        String expected = readFileAsString("tests/robots.txt");
        String expected = "User-Agent: *\n\r" +
                "Disallow: ";
        String actual = response.getContent();

        String lines="//Newline//";

        actual = actual.replaceAll("\n", lines).replaceAll("\r", lines);
        expected = expected.replaceAll("\n", lines).replaceAll("\r", lines);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void usableTrue() {
        final boolean expected = true;

        final boolean actual = webApi.usable();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void usableFalse() throws IOException, InvalidRequestCount {
        final boolean expected = false;

        webApi.get("robots.txt");
        webApi.get("robots.txt");
        webApi.get("robots.txt");
        webApi.get("robots.txt");
        final boolean actual = webApi.usable();

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = InvalidRequestCount.class)
    public void tooMeanyRequests() throws IOException, InvalidRequestCount {
        webApi.get("robots.txt");
        webApi.get("robots.txt");
        webApi.get("robots.txt");
        webApi.get("robots.txt");
        webApi.get("robots.txt");
    }

    @Test
    public void requestClear() throws IOException, InvalidRequestCount {
        Clock clockBefore = Clock.fixed(Instant.parse("2020-01-01T12:00:00.00Z"), Clock.systemUTC().getZone());
        Clock clockAfter = Clock.fixed(Instant.parse("2020-01-01T12:01:04.00Z"), Clock.systemUTC().getZone());

        webApi.setClock(clockBefore);
        webApi.get("robots.txt");
        webApi.get("robots.txt");
        webApi.get("robots.txt");

        webApi.setClock(clockAfter);
        webApi.get("robots.txt");
        webApi.get("robots.txt");
    }
}
