package com.kairosds.tvmazeapi.dto.external;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

// necesarios para no escribir gets y sets
@Getter
@Setter
public class TvMazeShowDto {
    private Long id;
    private String name;
    private List<String> genres;
    private String summary;
    private TvMazeChannelDto network;
    private TvMazeChannelDto webChannel;

    public String getChannelName() {
        // si es TV, toma el nombre de "network"
        if (network != null) {
            return network.getName();
        }
        // si es canal web, toma el nombre de "webChannel"
        if(webChannel != null) {
            return webChannel.getName();
        }
        // por si ninguno viene
        return null;
    }
}
