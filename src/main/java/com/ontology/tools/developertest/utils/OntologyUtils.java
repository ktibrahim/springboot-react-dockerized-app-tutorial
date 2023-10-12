package com.ontology.tools.developertest.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.ontology.tools.developertest.dto.OntologyDto;
import com.ontology.tools.developertest.model.Ontology;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class OntologyUtils {

    public static List<OntologyDto> getOntologyDtoList(List<Ontology> ontologyList) {
        List<OntologyDto> ontologyDtoList = new ArrayList<>();
        for(Ontology ontology: ontologyList) {
            OntologyDto ontologyDto = new OntologyDto();
            ontologyDto.setOntologyId(ontology.getOntologyId());
            ontologyDto.setTitle(ontology.getTitle());
            ontologyDto.setDescription(ontology.getDescription());
            ontologyDto.setDefinitionProperties(ontology.getDefinitionProperties());
            ontologyDto.setSynonymProperties(ontology.getSynonymProperties());
            ontologyDtoList.add(ontologyDto);
        }
        return ontologyDtoList;
    }

    public static OntologyDto getOntologyDto(Ontology ontology) {
        OntologyDto ontologyDto = new OntologyDto();
        ontologyDto.setOntologyId(ontology.getOntologyId());
        ontologyDto.setTitle(ontology.getTitle());
        ontologyDto.setDescription(ontology.getDescription());
        ontologyDto.setDefinitionProperties(ontology.getDefinitionProperties());
        ontologyDto.setSynonymProperties(ontology.getSynonymProperties());
        return ontologyDto;
    }

    public static Ontology buildOntologyObject(JsonObject configObj) {
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<String>>(){}.getType();
        return Ontology.builder()
                .ontologyId(configObj.get("id").getAsString())
                .description((configObj.get("description").getAsString()))
                .title(configObj.get("title").getAsString())
                .synonymProperties(gson.fromJson(configObj.getAsJsonArray("synonymProperties"), listType))
                .definitionProperties(gson.fromJson(configObj.getAsJsonArray("definitionProperties"), listType))
                .build();
    }

    public static JsonObject callOlsApi(String ontologyId) throws RuntimeException {
        JsonObject jsonObject;
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://www.ebi.ac.uk/ols4/api/ontologies/"+ontologyId;
        try {
            String response = restTemplate.getForObject(url, String.class);
            jsonObject = new Gson().fromJson(response, JsonObject.class);
        } catch (RestClientException e) {
            throw new RuntimeException(e.getMessage());
        }
        return jsonObject;
    }

    public static List<Ontology> buildOntologyObjectList() {
        List<Ontology> ontologyList = new ArrayList<>();
        ontologyList.add(Ontology.builder()
                .ontologyId("id1")
                .description("description1")
                .title("title1")
                .synonymProperties(new ArrayList<>())
                .definitionProperties(new ArrayList<>())
                .build());

        ontologyList.add(Ontology.builder()
                .ontologyId("id2")
                .description("description2")
                .title("title2")
                .synonymProperties(new ArrayList<>())
                .definitionProperties(new ArrayList<>())
                .build());
        return ontologyList;
    }
}
