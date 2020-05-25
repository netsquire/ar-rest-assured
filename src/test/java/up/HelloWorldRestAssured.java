package up;

import com.jayway.restassured.http.ContentType;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

public class HelloWorldRestAssured {

    @Test
    public void makeSureThatGoogleIsUp() {
        given()
                .when().get("http://www.google.com")
                .then().statusCode(200);
    }

    @Test
    public void test_NumberOfCircuitsFor2017Season_ShouldBe20() {
        given().
                when().
                get("http://ergast.com/api/f1/2017/circuits.json").
                then().
                assertThat().
                body("MRData.CircuitTable.Circuits.circuitId", hasSize(20));
    }

    @Test
    public void test_ResponseHeaderData_ShouldBeCorrect() {
        given().
                when().
                get("http://ergast.com/api/f1/2017/circuits.json").
                then().
                assertThat().
                statusCode(200).
                and().
                contentType(ContentType.JSON).
                and().
                header("Content-Length", equalTo("4551"));
    }

    @Test
    public void test_Md5CheckSumForTest_ShouldBe098f6bcd4621d373cade4e832627b4f6() {

        String originalText = "test";
        String expectedMd5CheckSum = "098f6bcd4621d373cade4e832627b4f6";

        given().
                param("text", originalText).
                when().
                get("http://md5.jsontest.com").
                then().
                assertThat().
                body("md5", equalTo(expectedMd5CheckSum));
    }

    @Test
    public void test_NumberOfCircuits_ShouldBe20_Parameterized() {

        String season = "2017";
        int numberOfRaces = 20;

        given().
                pathParam("raceSeason",season).
                when().
                get("http://ergast.com/api/f1/{raceSeason}/circuits.json").
                then().
                assertThat().
                body("MRData.CircuitTable.Circuits.circuitId", hasSize(numberOfRaces));
    }
}
