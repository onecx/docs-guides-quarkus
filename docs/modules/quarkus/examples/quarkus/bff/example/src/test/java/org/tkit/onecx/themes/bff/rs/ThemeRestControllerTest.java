package org.tkit.onecx.themes.bff.rs;

import static io.restassured.RestAssured.given;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import java.util.*;

import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.core.Response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.JsonBody;
import org.mockserver.model.MediaType;
import org.tkit.onecx.themes.bff.rs.controllers.ThemeRestController;
import org.tkit.quarkus.log.cdi.LogService;

import gen.org.tkit.onecx.theme.bff.rs.internal.model.*;
import gen.org.tkit.onecx.theme.client.model.*;
import io.quarkiverse.mockserver.test.InjectMockServerClient;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.keycloak.client.KeycloakTestClient;
// tag::testClassDefinition[]
@QuarkusTest
@LogService
@TestHTTPEndpoint(ThemeRestController.class)
class ThemeRestControllerTest extends AbstractTest {
    // end::testClassDefinition[]

    // tag::keycloakAndMockClient[]
    KeycloakTestClient keycloakClient = new KeycloakTestClient();

    @InjectMockServerClient
    MockServerClient mockServerClient;
    // end::keycloakAndMockClient[]
    // tag::resetExpectations[]
    static final String MOCK_ID = "MOCK";

    @BeforeEach
    void resetExpectation() {
        try {
            mockServerClient.clear(MOCK_ID);
        } catch (Exception ex) {
            //  mockId not existing
        }
    }
    // end::resetExpectations[]

    @Test
    void searchThemeByCriteriaTest() {
        ThemeSearchCriteria criteria = new ThemeSearchCriteria();
        criteria.setPageNumber(1);
        criteria.setName("test");
        criteria.setPageSize(1);

        Theme t1 = new Theme();
        t1.setId("1");
        t1.setName("test");

        ThemePageResult data = new ThemePageResult();
        data.setNumber(1);
        data.setSize(1);
        data.setTotalElements(1L);
        data.setTotalPages(1L);
        data.setStream(List.of(t1));

        ThemeSearchCriteriaDTO searchThemeRequestDTO = new ThemeSearchCriteriaDTO();
        searchThemeRequestDTO.setPageNumber(1);
        searchThemeRequestDTO.setPageSize(1);
        searchThemeRequestDTO.setName("test");

        // create mock rest endpoint
        // tag::exampleMock[]
        mockServerClient.when(request().withPath("/internal/themes/search").withMethod(HttpMethod.POST)
                .withBody(JsonBody.json(criteria)))
                .withId(MOCK_ID)
                .respond(httpRequest -> response().withStatusCode(Response.Status.OK.getStatusCode())
                        .withContentType(MediaType.APPLICATION_JSON)
                        .withBody(JsonBody.json(data)));
        // end::exampleMock[]

        // tag::exampleRequest[]
        var output = given()
                .when()
                .auth().oauth2(keycloakClient.getAccessToken(ADMIN))
                .header(APM_HEADER_PARAM, ADMIN)
                .contentType(APPLICATION_JSON)
                .body(searchThemeRequestDTO)
                .post()
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .contentType(APPLICATION_JSON)
                .extract().as(ThemePageResultDTO.class);
        // end::exampleRequest[]

        // tag::exampleAssertions[]
        Assertions.assertNotNull(output);
        Assertions.assertEquals(data.getSize(), output.getSize());
        Assertions.assertEquals(data.getStream().size(), output.getThemes().size());
        Assertions.assertEquals(data.getStream().get(0).getName(), output.getThemes().get(0).getName());
        // end::exampleAssertions[]
    }

    @Test
    void searchThemeByEmptyCriteriaTest() {

        ProblemDetailResponse problemDetailResponse = new ProblemDetailResponse();

        // create mock rest endpoint
        mockServerClient.when(request().withPath("/internal/themes/search").withMethod(HttpMethod.POST))
                .withId(MOCK_ID)
                .respond(httpRequest -> response().withStatusCode(Response.Status.BAD_REQUEST.getStatusCode())
                        .withContentType(MediaType.APPLICATION_JSON)
                        .withBody(JsonBody.json(problemDetailResponse)));

        var output = given()
                .when()
                .auth().oauth2(keycloakClient.getAccessToken(ADMIN))
                .header(APM_HEADER_PARAM, ADMIN)
                .contentType(APPLICATION_JSON)
                .post()
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode())
                .contentType(APPLICATION_JSON)
                .extract().as(ProblemDetailResponse.class);

        Assertions.assertNotNull(output);
    }
}
