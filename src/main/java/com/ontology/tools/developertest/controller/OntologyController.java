package com.ontology.tools.developertest.controller;

import com.ontology.tools.developertest.dto.OntologyDto;
import com.ontology.tools.developertest.model.Ontology;
import com.ontology.tools.developertest.service.OntologyService;
import com.ontology.tools.developertest.service.impl.OntologyServiceImpl;
import jakarta.el.PropertyNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.ontology.tools.developertest.utils.OntologyUtils.getOntologyDto;
import static com.ontology.tools.developertest.utils.OntologyUtils.getOntologyDtoList;

@RestController
@RequestMapping("/api")
public class OntologyController {

    @Autowired
    OntologyService ontologyService;
    private static final Logger logger = LoggerFactory.getLogger(OntologyServiceImpl.class);

    @GetMapping(path = "/ontologies")
    public ResponseEntity<List<OntologyDto>> getOntologiesList() {
        List<Ontology> ontology = ontologyService.getOntologiesList();
        return ResponseEntity.ok(getOntologyDtoList(ontology));
    }

    @GetMapping("/ontologies/{ontologyId}")
    public ResponseEntity<OntologyDto> getOntologyById(@PathVariable String ontologyId) {
        Ontology ontology = null;
        OntologyDto ontologyDto = null;
        try {
            ontology = ontologyService.getOntologyById(ontologyId);
            ontologyDto = getOntologyDto(ontology);
        } catch (Exception e) {
            logger.warn("Ontology not found in OLS");
            logger.error(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Ontology not found"
            );
        }
        return ResponseEntity.ok().body(ontologyDto);
    }

    @PostMapping(path = "/ontologies")
    public ResponseEntity<OntologyDto> saveOntology(@Valid @RequestBody OntologyDto ontologyDto) {
        Ontology ontology = ontologyService.saveOntology(ontologyDto);
        return ResponseEntity.ok(getOntologyDto(ontology));
    }

}
