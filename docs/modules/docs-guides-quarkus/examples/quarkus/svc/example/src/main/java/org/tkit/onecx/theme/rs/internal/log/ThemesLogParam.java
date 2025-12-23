package org.tkit.onecx.theme.rs.internal.log;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import org.tkit.quarkus.log.cdi.LogParam;

import gen.org.tkit.onecx.theme.rs.internal.model.ThemeSearchCriteriaDTO;

@ApplicationScoped
public class ThemesLogParam implements LogParam {

    @Override
    public List<Item> getClasses() {
        return List.of(
                item(10, ThemeSearchCriteriaDTO.class, x -> {
                    ThemeSearchCriteriaDTO d = (ThemeSearchCriteriaDTO) x;
                    return ThemeSearchCriteriaDTO.class.getSimpleName() + "[" + d.getPageNumber() + "," + d.getPageSize() + "]";
                }));
    }
}
