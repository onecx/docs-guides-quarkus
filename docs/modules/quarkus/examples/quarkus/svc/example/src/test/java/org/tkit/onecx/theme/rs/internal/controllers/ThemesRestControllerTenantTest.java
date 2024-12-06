package org.tkit.onecx.theme.rs.internal.controllers;

import static io.restassured.RestAssured.given;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.Response.Status.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.tkit.quarkus.security.test.SecurityTestUtils.getKeycloakClientToken;

import org.junit.jupiter.api.Test;
import org.tkit.onecx.theme.test.AbstractTest;
import org.tkit.quarkus.security.test.GenerateKeycloakClient;
import org.tkit.quarkus.test.WithDBData;

import gen.org.tkit.onecx.theme.rs.internal.model.*;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@TestHTTPEndpoint(ThemesRestController.class)
@WithDBData(value = "data/testdata-internal.xml", deleteBeforeInsert = true, deleteAfterTest = true, rinseAndRepeat = true)
@GenerateKeycloakClient(clientName = "testClient", scopes = { "ocx-th:all", "ocx-th:read", "ocx-th:write", "ocx-th:delete" })
class ThemesRestControllerTenantTest extends AbstractTest {

    @Test
    void searchThemesTest() {
        var criteria = new ThemeSearchCriteriaDTO();

        var data = given()
                .auth().oauth2(getKeycloakClientToken("testClient"))
                .contentType(APPLICATION_JSON)
                .header(APM_HEADER_PARAM, createToken("org1"))
                .body(criteria)
                .post()
                .then()
                .statusCode(OK.getStatusCode())
                .contentType(APPLICATION_JSON)
                .extract()
                .as(ThemePageResultDTO.class);

        assertThat(data).isNotNull();
        assertThat(data.getTotalElements()).isEqualTo(2);
        assertThat(data.getStream()).isNotNull().hasSize(2);

        criteria.setName(" ");
        data = given()
                .auth().oauth2(getKeycloakClientToken("testClient"))
                .contentType(APPLICATION_JSON)
                .header(APM_HEADER_PARAM, createToken("org1"))
                .body(criteria)
                .post()
                .then()
                .statusCode(OK.getStatusCode())
                .contentType(APPLICATION_JSON)
                .extract()
                .as(ThemePageResultDTO.class);

        assertThat(data).isNotNull();
        assertThat(data.getTotalElements()).isEqualTo(2);
        assertThat(data.getStream()).isNotNull().hasSize(2);

        criteria.setName("cg");
        data = given()
                .auth().oauth2(getKeycloakClientToken("testClient"))
                .contentType(APPLICATION_JSON)
                .header(APM_HEADER_PARAM, createToken("org1"))
                .body(criteria)
                .post()
                .then()
                .statusCode(OK.getStatusCode())
                .contentType(APPLICATION_JSON)
                .extract()
                .as(ThemePageResultDTO.class);

        assertThat(data).isNotNull();
        assertThat(data.getTotalElements()).isEqualTo(1);
        assertThat(data.getStream()).isNotNull().hasSize(1);

    }
}
