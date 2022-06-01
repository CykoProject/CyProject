package com.example.CyProject.home.model.scrap;

import java.io.Serializable;

public class BoardListId implements Serializable {
    private int iuser;
    private int iphoto;

    public BoardListId(){}
    public BoardListId(int iuser, int iphoto){
        super();
        this.iuser = iuser;
        this.iphoto = iphoto;
    }
}
