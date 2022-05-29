package com.example.CyProject.home.model.jukebox;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class JukeBoxDto {
    private List<JukeBoxEntity> jukeBoxList;
}
