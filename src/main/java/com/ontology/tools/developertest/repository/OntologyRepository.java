package com.ontology.tools.developertest.repository;

import com.ontology.tools.developertest.model.Ontology;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OntologyRepository extends MongoRepository<Ontology, String> {
}
