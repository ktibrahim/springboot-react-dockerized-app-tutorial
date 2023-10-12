package com.ontology.tools.developertest.model;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Jacksonized
@Document(collection = "ontologies")
public class Ontology {
    @Id
    private String ontologyId;
    private String title;
    private String description;
    private List<String> definitionProperties;
    private List<String> synonymProperties;
}
