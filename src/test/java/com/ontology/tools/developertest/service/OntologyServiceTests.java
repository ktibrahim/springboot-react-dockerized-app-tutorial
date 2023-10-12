package com.ontology.tools.developertest.service;

import com.google.gson.JsonObject;
import com.ontology.tools.developertest.dto.OntologyDto;
import com.ontology.tools.developertest.model.Ontology;
import com.ontology.tools.developertest.repository.OntologyRepository;
import com.ontology.tools.developertest.service.impl.OntologyServiceImpl;
import com.ontology.tools.developertest.utils.OntologyUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ontology.tools.developertest.utils.OntologyUtils.buildOntologyObjectList;
import static com.ontology.tools.developertest.utils.OntologyUtils.callOlsApi;
import static com.ontology.tools.developertest.utils.OntologytestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OntologyServiceTests {

    @Mock
    private OntologyRepository ontologyRepository;

    @InjectMocks
    private OntologyServiceImpl ontologyService;

    @Test
    public void testGetOntologiesList() {
        List<Ontology> sampleOntologies = buildOntologyObjectList();
        when(ontologyRepository.findAll()).thenReturn(sampleOntologies);

        List<Ontology> result = ontologyService.getOntologiesList();
        assertEquals(sampleOntologies, result);
    }

    @Test
    public void testGetOntologyById_OntologyExistsInDatabase() {
        String ontologyId = "efo";
        Ontology ontology = buildOntologyObjectForTest();
        when(ontologyRepository.findById(ontologyId)).thenReturn(Optional.of(ontology));

        Ontology result = ontologyService.getOntologyById(ontologyId);
        assertEquals(ontology, result);
    }

    @Test
    public void testGetOntologyById_OntologyDoesNotExistInDatabase() {
        String ontologyId = "mondo";
        JsonObject json = createOntologyJsonObjectForTesting();
        when(ontologyRepository.findById(ontologyId)).thenReturn(Optional.empty());
        try (MockedStatic<OntologyUtils> ontologyUtilsMocked = Mockito.mockStatic(OntologyUtils.class)) {
            ontologyUtilsMocked.when(() -> callOlsApi(ontologyId))
                    .thenReturn(json);
            assertThat(OntologyUtils.callOlsApi(ontologyId)).isEqualTo(json);
        }
    }

    @Test
    public void testSaveOntology() {
        OntologyDto ontologyDto = getTestOntologyDto();
        Ontology expectedOntology = Ontology.builder()
                .ontologyId(ontologyDto.getOntologyId())
                .description(ontologyDto.getDescription())
                .title(ontologyDto.getTitle())
                .synonymProperties(ontologyDto.getSynonymProperties())
                .definitionProperties(ontologyDto.getDefinitionProperties())
                .build();
        when(ontologyRepository.save(Mockito.any())).thenReturn(expectedOntology);

        Ontology result = ontologyService.saveOntology(ontologyDto);

        assertEquals(expectedOntology, result);
    }
}

