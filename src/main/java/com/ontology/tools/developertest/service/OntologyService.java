package com.ontology.tools.developertest.service;

import com.ontology.tools.developertest.dto.OntologyDto;
import com.ontology.tools.developertest.model.Ontology;

import java.util.List;

public interface OntologyService {
    Ontology getOntologyById(String ontologyId) throws Exception;
    Ontology saveOntology(OntologyDto ontologyDto);
    public List<Ontology> getOntologiesList();

}
