package com.ontology.tools.developertest.service.impl;

import com.google.gson.JsonObject;
import com.ontology.tools.developertest.dto.OntologyDto;
import com.ontology.tools.developertest.model.Ontology;
import com.ontology.tools.developertest.repository.OntologyRepository;
import com.ontology.tools.developertest.service.OntologyService;
import com.ontology.tools.developertest.utils.OntologyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.ontology.tools.developertest.utils.OntologyUtils.buildOntologyObject;
import static com.ontology.tools.developertest.utils.OntologyUtils.callOlsApi;

@Service
public class OntologyServiceImpl implements OntologyService {

    @Autowired
    OntologyRepository ontologyRepository;

    @Override
    public List<Ontology> getOntologiesList() {
        return ontologyRepository.findAll();
    }

    @Override
    public Ontology getOntologyById(String ontologyId) throws RuntimeException {
        Optional<Ontology> ontology = ontologyRepository.findById(ontologyId);
        if(ontology.isEmpty()) {
            JsonObject json = callOlsApi(ontologyId);
            if(json.get("status").getAsString().equals("LOADED")) {
                JsonObject configObj = (JsonObject) json.get("config");
                ontology = Optional.of(buildOntologyObject(configObj));
                ontologyRepository.save(ontology.get());
            } else {
                throw new RuntimeException(json.getAsString());
            }

        }
        return ontology.get();
    }

    @Override
    public Ontology saveOntology(OntologyDto ontologyDto) {
        Ontology ontology = Ontology.builder()
                .ontologyId(ontologyDto.getOntologyId())
                .description(ontologyDto.getDescription())
                .title(ontologyDto.getTitle())
                .synonymProperties(ontologyDto.getSynonymProperties())
                .definitionProperties(ontologyDto.getDefinitionProperties())
                .build();
        return ontologyRepository.save(ontology);
    }
}
