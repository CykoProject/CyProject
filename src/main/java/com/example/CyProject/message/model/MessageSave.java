package com.example.CyProject.message.model;

import com.example.CyProject.config.AuthenticationFacade;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class MessageSave {
    private List<Integer> receiver;
    private String ctnt;
    private int iuser;

    public int toEntity(int iuser) {
        for(Integer item : receiver) {
            MessageEntity entity = new MessageEntity();
            entity.setReceiver(item);
            entity.setCtnt(this.ctnt);
            entity.setIuser(iuser);
        }
        return 1;
    }
}
