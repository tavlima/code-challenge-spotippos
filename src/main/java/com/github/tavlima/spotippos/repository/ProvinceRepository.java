package com.github.tavlima.spotippos.repository;

import com.github.tavlima.spotippos.domain.Property;
import com.github.tavlima.spotippos.domain.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

/**
 * Created by thiago on 8/3/16.
 */
@RepositoryDefinition(domainClass = Province.class, idClass = Long.class)
public interface ProvinceRepository extends JpaRepository<Province, Long> {

    @Query("SELECT p FROM Province p WHERE ((p.x0 <= :x AND :x <= p.x1) AND (p.y0 <= :y AND :y <= p.y1))\n")
    Set<Province> findByCoord(@Param("x") int x, @Param("y") int y);

}
