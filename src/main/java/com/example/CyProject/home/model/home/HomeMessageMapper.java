package com.example.CyProject.home.model.home;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HomeMessageMapper {
    int selMessage(HomeMessageEntity entity);
    int updMessage(HomeMessageEntity entity);

    List<HomeMessageEntity> selMessageList(int ihost);
}
