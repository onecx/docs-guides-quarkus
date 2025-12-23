package org.tkit.onecx.themes.bff.rs.log;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import org.tkit.quarkus.log.cdi.LogParam;

import gen.org.tkit.onecx.theme.bff.rs.internal.model.ThemeSearchCriteriaDTO;

// tag::logger[]
@ApplicationScoped
public class ThemeLog implements LogParam {
    @Override
    public List<Item> getClasses() {
        return List.of(
                this.item(10, ThemeSearchCriteriaDTO.class,
                        x -> "ThemeSearchCriteriaDTO[ name: " +
                                ((ThemeSearchCriteriaDTO) x).getName()
                                + " ]"));
    }
}
// end::logger[]