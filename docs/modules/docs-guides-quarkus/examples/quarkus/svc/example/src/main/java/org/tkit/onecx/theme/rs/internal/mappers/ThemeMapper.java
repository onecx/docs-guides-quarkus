package org.tkit.onecx.theme.rs.internal.mappers;

import java.util.List;
import java.util.stream.Stream;

import jakarta.inject.Inject;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.tkit.onecx.theme.domain.criteria.ThemeSearchCriteria;
import org.tkit.onecx.theme.domain.models.Theme;
import org.tkit.quarkus.jpa.daos.PageResult;
import org.tkit.quarkus.rs.mappers.OffsetDateTimeMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gen.org.tkit.onecx.theme.rs.internal.model.*;

@Mapper(uses = { OffsetDateTimeMapper.class })
public abstract class ThemeMapper {

    @Inject
    ObjectMapper mapper;

    public abstract ThemeSearchCriteria map(ThemeSearchCriteriaDTO dto);

    @Mapping(target = "removeStreamItem", ignore = true)
    public abstract ThemePageResultDTO mapPage(PageResult<Theme> page);

    public abstract List<ThemeDTO> map(Stream<Theme> entity);

    @Mapping(target = "properties", qualifiedByName = "propertiesJson")
    public abstract ThemeDTO map(Theme theme);

    @Named("properties")
    public String mapToString(Object properties) {

        if (properties == null) {
            return null;
        }

        try {
            return mapper.writeValueAsString(properties);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    @Named("propertiesJson")
    public Object stringToObject(String jsonVar) {

        if (jsonVar == null || jsonVar.isEmpty()) {
            return null;
        }

        try {
            return mapper.readTree(jsonVar);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

}
