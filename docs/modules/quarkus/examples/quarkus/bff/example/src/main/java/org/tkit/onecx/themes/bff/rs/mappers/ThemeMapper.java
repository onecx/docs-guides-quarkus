package org.tkit.onecx.themes.bff.rs.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.tkit.quarkus.rs.mappers.OffsetDateTimeMapper;

import gen.org.tkit.onecx.theme.bff.rs.internal.model.ThemePageResultDTO;
import gen.org.tkit.onecx.theme.bff.rs.internal.model.ThemeSearchCriteriaDTO;
import gen.org.tkit.onecx.theme.client.model.ThemePageResult;
import gen.org.tkit.onecx.theme.client.model.ThemeSearchCriteria;
// tag::mapper[]
@Mapper(uses = { OffsetDateTimeMapper.class })
public interface ThemeMapper {

    ThemeSearchCriteria mapSearchCriteria(ThemeSearchCriteriaDTO themeSearchCriteriaDTO);

    @Mapping(target = "themes", source = "stream")
    @Mapping(target = "removeThemesItem", ignore = true)
    ThemePageResultDTO pageResultMapper(ThemePageResult themePageResult);
}
// end::mapper[]
