package logic.login;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class LoginToAccount {
    public Map<String, String> loginToAccount(String BASE_URI, String USERNAME, String PASSWORD, String HID, String DOMAIN) {
        RestAssured.reset();

        // Step 1: Authenticate and get cookies
        Response authResponse = given()
                .baseUri(BASE_URI)
                .auth()
                .preemptive()
                .basic(USERNAME, PASSWORD)  // Replace with actual credentials
                .when()
                .get(HID)
                .then()
                .statusCode(202)
                .extract()
                .response();

        String userCardCookie = authResponse.getCookie("UserCard"); // Adjust based on actual cookie name
        String accessToken = authResponse.getCookie("AccessToken"); // Adjust based on actual cookie name

        assertNotNull(userCardCookie, "UserCard cookie should not be null");
        assertNotNull(accessToken, "AccessToken cookie should not be null");


        Response domainResponse = given()
                .baseUri(BASE_URI)
                .cookie("UserCard", userCardCookie) // Adjust based on actual cookie name
                .header("X-HAPI-RToken", accessToken) // Adjust based on actual cookie name
                .when()
                .get("/api/auth/obtain/domain/" + DOMAIN + ".cookie?lowerdomain=true")
                .then()
                .statusCode(202)
                .extract()
                .response();

        String generatedUserCard = domainResponse.getCookie("UserCard"); // Adjust based on actual cookie name
        String generatedAccessToken = domainResponse.getCookie("AccessToken"); // Adjust based on actual cookie name

        assertNotNull(generatedUserCard, "Generated UserCard cookie should not be null");
        assertNotNull(generatedAccessToken, "Generated AccessToken cookie should not be null");

        // Output the results for verification
        System.out.println("Domain Response: " + domainResponse.asString());
        System.out.println("Generated UserCard: " + generatedUserCard);
        System.out.println("Generated AccessToken: " + generatedAccessToken);

        Map<String, String> credentials = new HashMap<>();
        credentials.put("Generated UserCard", generatedUserCard);
        credentials.put("Generated AccessToken", generatedAccessToken);

        return credentials;    }

    }
