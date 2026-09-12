package com.kairosds.tvmazeapi.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

// guarda el detalle de un show en Mongo, para no pedirlo siempre a TVMaze. */
@Document(collection = "shows")
// necesarios para evitar la creacion de gets y sets
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShowDocument {

    @Id
    private Long id;
    private String name;
    private String channel;
    private String summary;
    private List<String> genres;
}
