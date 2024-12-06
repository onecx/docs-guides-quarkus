package org.tkit.onecx.theme.domain.daos;

import jakarta.enterprise.context.ApplicationScoped;

import org.tkit.onecx.theme.domain.criteria.ThemeSearchCriteria;
import org.tkit.onecx.theme.domain.models.*;
import org.tkit.quarkus.jpa.daos.AbstractDAO;
import org.tkit.quarkus.jpa.daos.Page;
import org.tkit.quarkus.jpa.daos.PageResult;
import org.tkit.quarkus.jpa.exceptions.DAOException;
import org.tkit.quarkus.jpa.models.AbstractTraceableEntity_;
import org.tkit.quarkus.jpa.utils.QueryCriteriaUtil;

@ApplicationScoped
public class ThemeDAO extends AbstractDAO<Theme> {

    public PageResult<Theme> findThemesByCriteria(ThemeSearchCriteria criteria) {
        try {
            var cb = this.getEntityManager().getCriteriaBuilder();
            var cq = cb.createQuery(Theme.class);
            var root = cq.from(Theme.class);

            if (criteria.getName() != null && !criteria.getName().isBlank()) {
                cq.where(cb.like(root.get(Theme_.name), QueryCriteriaUtil.wildcard(criteria.getName())));
            }
            cq.orderBy(cb.desc(root.get(AbstractTraceableEntity_.CREATION_DATE)));

            return createPageQuery(cq, Page.of(criteria.getPageNumber(), criteria.getPageSize())).getPageResult();
        } catch (Exception ex) {
            throw new DAOException(ErrorKeys.ERROR_FIND_THEMES_BY_CRITERIA, ex);
        }
    }

    public enum ErrorKeys {
        ERROR_FIND_THEMES_BY_CRITERIA;
    }
}
