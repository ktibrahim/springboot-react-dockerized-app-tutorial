package com.ontology.tools.developertest.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ontology.tools.developertest.dto.OntologyDto;
import com.ontology.tools.developertest.model.Ontology;

import java.util.ArrayList;

public class OntologytestUtils {

    public static Ontology buildOntologyObjectForTest() {
        return Ontology.builder()
                .ontologyId("id")
                .description("description")
                .title("title")
                .synonymProperties(new ArrayList<>())
                .definitionProperties(new ArrayList<>())
                .build();
    }

    public static JsonObject createOntologyJsonObjectForTesting() {
        String jsonString = "{\n" +
                "    \"ontologyId\": \"go\",\n" +
                "    \"title\": \"Gene Ontology\",\n" +
                "    \"description\": \"The Gene Ontology (GO) provides a framework and set of concepts for describing the functions of gene products from all organisms.\",\n" +
                "    \"definitionProperties\": [],\n" +
                "    \"synonymProperties\": []\n" +
                "}";
        JsonObject jsonObject = new Gson().fromJson(jsonString, JsonObject.class);
        return jsonObject;
    }

    public static OntologyDto getTestOntologyDto() {
        OntologyDto ontologyDto = new OntologyDto();
        ontologyDto.setOntologyId("id");
        ontologyDto.setTitle("title");
        ontologyDto.setDescription("description");
        ontologyDto.setDefinitionProperties(new ArrayList<>());
        ontologyDto.setSynonymProperties(new ArrayList<>());
        return ontologyDto;
    }
}
