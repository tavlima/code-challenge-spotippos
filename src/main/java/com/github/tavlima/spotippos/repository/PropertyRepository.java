package com.github.tavlima.spotippos.repository;

import com.github.tavlima.spotippos.domain.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by thiago on 8/3/16.
 */
@RepositoryDefinition(domainClass = Property.class, idClass = Long.class)
public interface PropertyRepository extends JpaRepository<Property, Long> {

    @Query("SELECT p FROM Property p WHERE ((:x0 <= p.x AND p.x <= :x1) AND (:y0 <= p.y AND p.y <= :y1))")
    List<Property> findAllInRegion(@Param("x0") int x0, @Param("y0") int y0, @Param("x1") int x1, @Param("y1") int y1);

}
