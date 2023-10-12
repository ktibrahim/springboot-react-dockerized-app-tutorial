package com.ontology.tools.developertest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ontology.tools.developertest.dto.OntologyDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.ontology.tools.developertest.utils.OntologytestUtils.getTestOntologyDto;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OntologyControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetOntologiesList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/ontologies"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetOntologyById_OntologyExistsInDatabase() throws Exception {
        String ontologyId = "efo";
        mockMvc.perform(MockMvcRequestBuilders.get("/api/ontologies/{ontologyId}", ontologyId))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetOntologyById_OntologyDoesNotExistInDatabase() throws Exception {
        String ontologyId = "nonexistent";
        mockMvc.perform(MockMvcRequestBuilders.get("/api/ontologies/{ontologyId}", ontologyId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testSaveOntology() throws Exception {
        OntologyDto ontologyDto = getTestOntologyDto();
        String requestJson = new ObjectMapper().writeValueAsString(ontologyDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/ontologies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());
    }
}
