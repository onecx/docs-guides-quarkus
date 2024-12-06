package org.tkit.onecx.themes.bff.rs.controllers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.ClientWebApplicationException;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;
import org.tkit.onecx.themes.bff.rs.mappers.ExceptionMapper;
import org.tkit.onecx.themes.bff.rs.mappers.ThemeMapper;
import org.tkit.quarkus.log.cdi.LogService;

import gen.org.tkit.onecx.theme.bff.rs.internal.ThemesApiService;
import gen.org.tkit.onecx.theme.bff.rs.internal.model.ProblemDetailResponseDTO;
import gen.org.tkit.onecx.theme.bff.rs.internal.model.ThemePageResultDTO;
import gen.org.tkit.onecx.theme.bff.rs.internal.model.ThemeSearchCriteriaDTO;
import gen.org.tkit.onecx.theme.client.api.ThemesInternalApi;
import gen.org.tkit.onecx.theme.client.model.ThemePageResult;

// tag::controllerClass[]
@ApplicationScoped
@Transactional(value = Transactional.TxType.NOT_SUPPORTED)
@LogService
public class ThemeRestController implements ThemesApiService {
// end::controllerClass[]

    // tag::clientAndMapper[]
    @Inject
    @RestClient
    ThemesInternalApi client;

    @Inject
    ThemeMapper mapper;

    @Inject
    ExceptionMapper exceptionMapper;
    // end::clientAndMapper[]

    // tag::exampleEndpoint[]
    @Override
    public Response searchThemes(ThemeSearchCriteriaDTO themeSearchCriteriaDTO) {
        try (Response response = client.searchThemes(mapper.mapSearchCriteria(themeSearchCriteriaDTO))) {
            ThemePageResultDTO themePageResultDTO = mapper
                    .pageResultMapper(response.readEntity(ThemePageResult.class));
            return Response.status(response.getStatus()).entity(themePageResultDTO).build();
        }
    }
    // end::exampleEndpoint[]

    // tag::serverExceptionMapper[]
    @ServerExceptionMapper
    public RestResponse<ProblemDetailResponseDTO> constraint(ConstraintViolationException ex) {
        return exceptionMapper.constraint(ex);
    }

    @ServerExceptionMapper
    public Response restException(ClientWebApplicationException ex) {
        return exceptionMapper.clientException(ex);
    }
    // end::serverExceptionMapper[]
}
