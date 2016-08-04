package com.github.tavlima.spotippos.repository;

import com.github.tavlima.spotippos.domain.Property;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

/**
 * Created by thiago on 8/3/16.
 */
@RepositoryDefinition(domainClass = Property.class, idClass = Long.class)
public interface PropertyRepository extends CrudRepository<Property, Long> {
}
