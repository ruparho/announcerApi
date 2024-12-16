package src;

import io.restassured.response.Response;
import logic.login.LoginToAccount;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.testng.TestRunner.PriorityWeight.dependsOnMethods;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AnnouncerApiTests {


    private static final String BASE_URI = "https://misapps.telmex.com";
    private static final String USERNAME = "qatest-2519-04042019-ne2ruxa-new-yc@ychekulaeva.hostopia.com";
    private static final String PASSWORD = "Temp1234%";
    private static final String HID = "/api/auth/login/portaluser.cookie?hostopianid=2519&_=1702377114799";
    private static final String DOMAIN = "qatest-2519-04042019-ne2ruxa.com";
    private static final String EMAILID = "331962";
    private static final String EXISTED_LIST_ID = "150458";
    private static String email;
    private static String contactId;
    private static String listID;


    String symbols = "TheMoreTheMerrier";
    String randomString = RandomStringUtils.random(6, symbols);

    LoginToAccount loginToAccount = new LoginToAccount();
    Map<String, String> credentials = loginToAccount.loginToAccount(BASE_URI, USERNAME, PASSWORD, HID, DOMAIN);

//    @BeforeClass
//    public void setUp() {
//        LoginToAccount loginToAccount = new LoginToAccount();
//        credentials = loginToAccount.loginToAccount(BASE_URI, USERNAME, PASSWORD, HID, DOMAIN);
//    }


    /*************Autoresponder******************/
    @Test
    @Order(1)
    // Get detailed statistics for an Auto-responder
    public void test01_get_statisticsForAutoresponder() {

        Response getResponse = given()
                .baseUri(BASE_URI)
                .cookie("UserCard", credentials.get("Generated UserCard")) // Adjust based on actual cookie name
                .header("X-HAPI-RToken", credentials.get("Generated AccessToken")) // Adjust based on actual cookie name
                .when()
                .get("/api/db/announcerpro/3.0/autoresponders/get.json?EmailId=331962")
                .then()
                .log().all()  // Logs the full response
                .statusCode(200)
                .extract()
                .response();
        verifySuccess(getResponse);

    }

    @Test
    @Order(2)
    // Retrieve a saved auto-responder email
    public void test02_get_savedAutoresponder() {
        Response getResponse = given()
                .baseUri(BASE_URI)
                .cookie("UserCard", credentials.get("Generated UserCard")) // Adjust based on actual cookie name
                .header("X-HAPI-RToken", credentials.get("Generated AccessToken")) // Adjust based on actual cookie name
                .when()
                .get("/api/db/announcerpro/3.0/Autoresponder/Get.json?EmailId=" + EMAILID)
                .then()
                .log().all()  // Logs the full response
                .statusCode(200)
                .extract()
                .response();
        verifySuccess(getResponse);

    }

    @Test
    @Order(3)
    // Find list of List Ids for a given Auto-responder
    public void test03_get_listIDsForAutoresponder() {
        Response getResponse = given()
                .baseUri(BASE_URI)
                .cookie("UserCard", credentials.get("Generated UserCard")) // Adjust based on actual cookie name
                .header("X-HAPI-RToken", credentials.get("Generated AccessToken")) // Adjust based on actual cookie name
                .when()
                .get("/api/db/announcerpro/3.0/Autoresponder/lists/?EmailId=" + EMAILID)
                .then()
                .log().all()  // Logs the full response
                .statusCode(200)
                .extract()
                .response();
        verifySuccess(getResponse);

    }


    /*************Autoresponders******************/
    @Test
    @Order(4)
    // Return a list of saved Auto-responders
    // GET /api/db/announcerpro/3.0/Autoresponders/Get.json?timeDiff={timeDiff}&Page={Page}&PageSize={PageSize}&sortBy={sortBy}
    public void test04_get_listOfSavedAutoresponders() {
        Response getResponse = given()
                .baseUri(BASE_URI)
                .cookie("UserCard", credentials.get("Generated UserCard")) // Adjust based on actual cookie name
                .header("X-HAPI-RToken", credentials.get("Generated AccessToken")) // Adjust based on actual cookie name
                .when()
                .get("/api/db/announcerpro/3.0/Autoresponders/Get.json?timeDiff=2024-01-17")
                .then()
                .log().all()  // Logs the full response
                .statusCode(200)
                .extract()
                .response();
        verifySuccess(getResponse);

    }

    @Test
    @Order(5)
    // Get list of running auto-responders along with quick statistics.
    // /api/db/announcerpro/3.0/Autoresponders/Results.json?timeDiff={timeDiff}&Page={Page}&PageSize={PageSize}&sortBy={sortBy}
    public void test05_get_listOfSavedAutoresponders() {
        Response getResponse = given()
                .baseUri(BASE_URI)
                .cookie("UserCard", credentials.get("Generated UserCard")) // Adjust based on actual cookie name
                .header("X-HAPI-RToken", credentials.get("Generated AccessToken")) // Adjust based on actual cookie name
                .when()
                .get("/api/db/announcerpro/3.0/Autoresponders/Results.json")
                .then()
                .log().all()  // Logs the full response
                .statusCode(200)
                .extract()
                .response();

        verifySuccess(getResponse);

    }

    /*************Contact******************/
    private void addContact() {


        email = "test" + randomString + "@example.com";

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("Email", email);
        requestBody.put("FirstName", "John");
        requestBody.put("LastName", "Doe");
        requestBody.put("Phone", "123-456-7890");
        requestBody.put("Address", "123 Main St");
        requestBody.put("Address2", "Suite 100");
        requestBody.put("City", "Anytown");
        requestBody.put("StateProv", "CA");
        requestBody.put("ZipPostalCode", "12345");
        requestBody.put("Country", "USA");
        requestBody.put("Company", "TestCompany");
        requestBody.put("Notes", "This is a test contact");
        requestBody.put("ListIds", listID);
        requestBody.put("OptIn", "1");


        Response postResponse = given()
                .baseUri(BASE_URI)
                .cookie("UserCard", credentials.get("Generated UserCard"))
                .header("X-HAPI-RToken", credentials.get("Generated AccessToken"))
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/db/announcerpro/3.0/contact/add.json")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        // Extract ContactId from the response and store it
        contactId = postResponse.jsonPath().getString("ContactId");

        // Log the values for debugging
        System.out.println("Contact ID: " + contactId);
        System.out.println("Email: " + email);

        // Add assertion to ensure the contactId is not null
        assertNotNull(contactId, "ContactId should not be null after adding contact");
    }


    @Test
    @Order(6)
    // Add contact
    // POST api/db/announcerpro/3.0/Contact/Add.json
    public void test06_post_addContact() {
        addContact();
        assertNotNull(contactId, "ContactId should not be null");
    }

    @Test
    @Order(7)
    // Retrieve contact information     FYI DEPENDS ON test06_post_addContact()
    // GET /api/db/announcerpro/3.0/Contact/Get.json?ContactId={ContactId}&Email={Email}
    public void test07_get_contactInformation() {

        // Ensure contactId and email are not null before using them
        assertNotNull(contactId, "ContactId should not be null");
        assertNotNull(email, "Email should not be null");


        Response getResponse = given()
                .baseUri(BASE_URI)
                .cookie("UserCard", credentials.get("Generated UserCard")) // Adjust based on actual cookie name
                .header("X-HAPI-RToken", credentials.get("Generated AccessToken")) // Adjust based on actual cookie name
                .when()
                .get("/api/db/announcerpro/3.0/Contact/Get.json?ContactId=" + contactId + "&Email=" + email)
                .then()
                .log().all()  // Logs the full response
                .statusCode(200)
                .extract()
                .response();

        verifySuccess(getResponse);

    }

    @Test
    @Order(8)
    // Edit contact info          FYI DEPENDS ON test06_post_addContact()
    // EDIT api/db/announcerpro/3.0/Contact/Edit.json
    public void test08_put_editContact() {

        // Ensure contactId and email are not null before using them
        assertNotNull(contactId, "ContactId should not be null");
        assertNotNull(email, "Email should not be null");

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("Email", email);
        requestBody.put("ContactId", contactId);
        requestBody.put("FirstName", "Changed");
        requestBody.put("LastName", "Changed");
        requestBody.put("Phone", "Changed");
        requestBody.put("Address", "Changed");
        requestBody.put("Address2", "Changed");
        requestBody.put("City", "Changed");
        requestBody.put("StateProv", "Changed");
        requestBody.put("ZipPostalCode", "Changed");
        requestBody.put("Country", "Changed");
        requestBody.put("Company", "Changed");
        requestBody.put("Notes", "Changed");


        Response putResponse = given()
                .baseUri(BASE_URI)
                .cookie("UserCard", credentials.get("Generated UserCard")) // Adjust based on actual cookie name
                .header("X-HAPI-RToken", credentials.get("Generated AccessToken")) // Adjust based on actual cookie name
                .body(requestBody)
                .when()
                .put("api/db/announcerpro/3.0/Contact/Edit.json")
                .then()
                .log().all()  // Logs the full response
                .statusCode(200)
                .extract()
                .response();

        verifySuccess(putResponse);

        test07_get_contactInformation();

    }
    @Test
    @Order(9)
    // Send opt in email to certain contact.             FYI DEPENDS ON test06_post_addContact()
    public void test09_post_sendOptIn() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("ContactId", contactId);
        requestBody.put("From", "test@test.com");


        Response postResponse = given()
                .baseUri(BASE_URI)
                .cookie("UserCard", credentials.get("Generated UserCard")) // Adjust based on actual cookie name
                .header("X-HAPI-RToken", credentials.get("Generated AccessToken")) // Adjust based on actual cookie name
                .body(requestBody)
                .when()
                .post("/api/db/announcerpro/3.0/contact/optin.json")
                .then()
                .log().all()  // Logs the full response
                .statusCode(200)
                .extract()
                .response();

        verifySuccess(postResponse);

    }


    @Test
    @Order(10)
    // Get contact amount
    public void test10_get_contactAmount() {
        Response getResponse = given()
                .baseUri(BASE_URI)
                .cookie("UserCard", credentials.get("Generated UserCard")) // Adjust based on actual cookie name
                .header("X-HAPI-RToken", credentials.get("Generated AccessToken")) // Adjust based on actual cookie name
                .when()
                .get("/api/db/announcerpro/3.0/Contact/ContactsCount.json")
                .then()
                .log().all()  // Logs the full response
                .statusCode(200)
                .extract()
                .response();

        verifySuccess(getResponse);

    }

    @Test
    @Order(11)
    // Get contact confirmed
    public void test11_get_contactConfirmed() {
        Response getResponse = given()
                .baseUri(BASE_URI)
                .cookie("UserCard", credentials.get("Generated UserCard")) // Adjust based on actual cookie name
                .header("X-HAPI-RToken", credentials.get("Generated AccessToken")) // Adjust based on actual cookie name
                .body(contactId)
                .when()
                .get("/api/db/announcerpro/3.0/Contact/IsConfirmed.json")
                .then()
                .log().all()  // Logs the full response
                .statusCode(200)
                .extract()
                .response();

        verifySuccess(getResponse);

    }


    @Test
    @Order(30)
    // Delete contact
    public void test12_delete_contact() {
        // Ensure contactId and email are not null before using them
        assertNotNull(contactId, "ContactId should not be null");
        assertNotNull(email, "Email should not be null");

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("Email", email);
        requestBody.put("ContactId", contactId);

        Response deleteResponse = given()
                .baseUri(BASE_URI)
                .cookie("UserCard", credentials.get("Generated UserCard")) // Adjust based on actual cookie name
                .header("X-HAPI-RToken", credentials.get("Generated AccessToken")) // Adjust based on actual cookie name
                .body(requestBody)
                .when()
                .delete("/api/db/announcerpro/3.0/Contact/Delete.json")
                .then()
                .log().all()  // Logs the full response
                .statusCode(200)
                .extract()
                .response();

        verifySuccess(deleteResponse);

    }

    /*************Defaultemail******************/
    @Test
    @Order(13)
    // Retrieve default email name. This name is always unique.
    public void test13_get_defaultEmailName() {

        Response getResponse = given()
                .baseUri(BASE_URI)
                .cookie("UserCard", credentials.get("Generated UserCard")) // Adjust based on actual cookie name
                .header("X-HAPI-RToken", credentials.get("Generated AccessToken")) // Adjust based on actual cookie name
                .when()
                .get("/api/db/announcerpro/3.0/Defaultemail/Name.json")
                .then()
                .log().all()  // Logs the full response
                .statusCode(200)
                .extract()
                .response();
        verifySuccess(getResponse);

    }

    /*************Email******************/

    @Test
    @Order(14)
    // Returns List Ids for a given Email
    public void test14_get_listsForEmail() {

        Response getResponse = given()
                .baseUri(BASE_URI)
                .cookie("UserCard", credentials.get("Generated UserCard")) // Adjust based on actual cookie name
                .header("X-HAPI-RToken", credentials.get("Generated AccessToken")) // Adjust based on actual cookie name
                .when()
                .get("/api/db/announcerpro/3.0/Email/Lists.json?EmailId=" + EMAILID)
                .then()
                .log().all()  // Logs the full response
                .statusCode(200)
                .extract()
                .response();
        verifySuccess(getResponse);

    }

    @Test
    @Order(15)
    // Get detailed statistics for an Email. {Type} is the type of statistic[Bounces, Unsubscribes, Opens, Clicks, All]
    public void test15_get_detailedStatisticsForEmail() {

        Response getResponse = given()
                .baseUri(BASE_URI)
                .cookie("UserCard", credentials.get("Generated UserCard")) // Adjust based on actual cookie name
                .header("X-HAPI-RToken", credentials.get("Generated AccessToken")) // Adjust based on actual cookie name
                .when()
                .get("/api/db/announcerpro/3.0/Email/Results.json?EmailId=" + EMAILID + "&Type=Opens&timeDiff=-2")
                .then()
                .log().all()  // Logs the full response
                .statusCode(200)
                .extract()
                .response();
        verifySuccess(getResponse);
    }

    @Test
    @Order(16)
    // Get detailed statistics for an Email. {Type} is the type of statistic[Bounces, Unsubscribes, Opens, Clicks, All]
    public void test16_get_shortStatisticsForEmail() {

        Response getResponse = given()
                .baseUri(BASE_URI)
                .cookie("UserCard", credentials.get("Generated UserCard")) // Adjust based on actual cookie name
                .header("X-HAPI-RToken", credentials.get("Generated AccessToken")) // Adjust based on actual cookie name
                .when()
                .get("/api/db/announcerpro/3.0/Email/Result.json?EmailId=" + EMAILID + "&Type=Opens&timeDiff=-2")
                .then()
                .log().all()  // Logs the full response
                .statusCode(200)
                .extract()
                .response();
        verifySuccess(getResponse);
    }

    @Test
    @Order(17)
    // Retrieve the entire contents of an email.
    public void test17_get_entireContentOfEmail() {

        Response getResponse = given()
                .baseUri(BASE_URI)
                .cookie("UserCard", credentials.get("Generated UserCard")) // Adjust based on actual cookie name
                .header("X-HAPI-RToken", credentials.get("Generated AccessToken")) // Adjust based on actual cookie name
                .when()
                .get("/api/db/announcerpro/3.0/Email/Get.json?EmailId=" + EMAILID + "&timeDiff=-2")
                .then()
                .log().all()  // Logs the full response
                .statusCode(200)
                .extract()
                .response();
        verifySuccess(getResponse);
    }

    @Test
    @Order(18)
    // Retrieve top recipients list if is exist or empty data
    public void test18_get_topRecipientsList() {

        Response getResponse = given()
                .baseUri(BASE_URI)
                .cookie("UserCard", credentials.get("Generated UserCard")) // Adjust based on actual cookie name
                .header("X-HAPI-RToken", credentials.get("Generated AccessToken")) // Adjust based on actual cookie name
                .when()
                .get("/api/db/announcerpro/3.0/Email/TopRecipients.json?EmailId=" + EMAILID)
                .then()
                .log().all()  // Logs the full response
                .statusCode(200)
                .extract()
                .response();
        verifySuccess(getResponse);
    }

    @Test
    @Order(19)
    // Create an email in db
    public void test18_post_createEmail() {

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("timeDiff", 300);  // Example time difference in seconds
        requestBody.put("designId", 490377);  // Example design ID
        requestBody.put("title", "Test Email Title");  // Example email title
        requestBody.put("from_address", "sender@example.com");  // Example sender's email address


        Response getResponse = given()
                .baseUri(BASE_URI)
                .cookie("UserCard", credentials.get("Generated UserCard")) // Adjust based on actual cookie name
                .header("X-HAPI-RToken", credentials.get("Generated AccessToken")) // Adjust based on actual cookie name
                .body(requestBody)
                .when()
                .post("/api/db/announcerpro/3.0/Email/Create.json")
                .then()
                .log().all()  // Logs the full response
                .statusCode(200)
                .extract()
                .response();
        verifySuccess(getResponse);
    }
    /*************ListSection******************/

    private void addList() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("ListName", randomString);

        Response postResponse = given()
                .baseUri(BASE_URI)
                .cookie("UserCard", credentials.get("Generated UserCard")) // Adjust based on actual cookie name
                .header("X-HAPI-RToken", credentials.get("Generated AccessToken")) // Adjust based on actual cookie name
                .body(requestBody)
                .when()
                .post("/api/db/announcerpro/3.0/list/list.json")
                .then()
                .log().all()  // Logs the full response
                .statusCode(200)
                .extract()
                .response();
        verifySuccess(postResponse);

        System.out.println("Response Body: " + postResponse.getBody().asString());

        listID = postResponse.jsonPath().getString("list.ListId");

        System.out.println("ListId = " + listID);
    }

    @Test
    @Order(1)
    // Create a new contact list
    public void test24_post_createNewList() {
        addList();
        assertNotNull(listID, "ListId should not be null");
    }




    @Test
    @Order(25)
    // Get list of contacts in a list
    public void test26_get_listOfContactsInList() {

        System.out.println("ListId: " + listID);

        Response getResponse = given()
                .baseUri(BASE_URI)
                .cookie("UserCard", credentials.get("Generated UserCard")) // Adjust based on actual cookie name
                .header("X-HAPI-RToken", credentials.get("Generated AccessToken")) // Adjust based on actual cookie name
                .when()
                .get("/api/db/announcerpro/3.0/list/contacts.json?ListId=" + listID)
                .then()
                .log().all()  // Logs the full response
                .statusCode(200)
                .extract()
                .response();
        verifySuccess(getResponse);
    }

    @Test
    @Order(25)
    // Get List info
    public void test25_get_list() {

        System.out.println("ListId: " + listID);

        Response getResponse = given()
                .baseUri(BASE_URI)
                .cookie("UserCard", credentials.get("Generated UserCard")) // Adjust based on actual cookie name
                .header("X-HAPI-RToken", credentials.get("Generated AccessToken")) // Adjust based on actual cookie name
                .when()
                .get("/api/db/announcerpro/3.0/list/list.json?ListId=" + listID)
                .then()
                .log().all()  // Logs the full response
                .statusCode(200)
                .extract()
                .response();
        verifySuccess(getResponse);
    }

    // TODO write test for the deleting contact from the list

    @Test
    @Order(25)
    // Merge two lists into a new list
    public void test25_put_mergeList() {

        // Ensure the list IDs are valid and print them for debugging
        System.out.println("Source List ID 1: " + EXISTED_LIST_ID);
        System.out.println("Source List ID 2: " + listID);

        String sourceListIds = String.valueOf(EXISTED_LIST_ID) + "," + String.valueOf(listID);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("SourceListIds", sourceListIds); // Comma-separated list of source list IDs as a string
        requestBody.put("TargetListId", String.valueOf(listID)); // Target list ID as a string


        Response putResponse = given()
                .baseUri(BASE_URI)
                .cookie("UserCard", credentials.get("Generated UserCard")) // Adjust based on actual cookie name
                .header("X-HAPI-RToken", credentials.get("Generated AccessToken")) // Adjust based on actual token name
                .contentType("application/json")
                .body(requestBody)
                .when()
                .put("/api/db/announcerpro/3.0/list/merge.json")
                .then()
                .log().all()  // Logs the full response
                .statusCode(200) // Expected status code
                .extract()
                .response();
        verifySuccess(putResponse);

    }

    @Test
    @Order(28)
    // Delete List
    public void test28_delete_list() {
        addList();

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("ListId", listID);

        Response postResponse = given()
                .baseUri(BASE_URI)
                .cookie("UserCard", credentials.get("Generated UserCard")) // Adjust based on actual cookie name
                .header("X-HAPI-RToken", credentials.get("Generated AccessToken")) // Adjust based on actual cookie name
                .body(requestBody)
                .when()
                .delete("/api/db/announcerpro/3.0/list/list.json")
                .then()
                .log().all()  // Logs the full response
                .statusCode(200)
                .extract()
                .response();
        verifySuccess(postResponse);


    }

    public void verifySuccess(Response response) {
        int success = response.jsonPath().getInt("Success");
        assertEquals(success, 1, "Expected Success to be 1, but it was " + success);
    }
}
