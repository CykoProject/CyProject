package com.example.CyProject.message.model;

import com.example.CyProject.config.AuthenticationFacade;
import com.example.CyProject.user.model.UserEntity;
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

    public List<MessageEntity> toListEntity(int iuser) {
        List<MessageEntity> list = new ArrayList<>();
        for(Integer item : receiver) {
            MessageEntity entity = new MessageEntity();
            UserEntity iuserEnt = new UserEntity();
            iuserEnt.setIuser(iuser);
            entity.setIuser(iuserEnt);

            UserEntity recEnt = new UserEntity();
            recEnt.setIuser(item);
            entity.setReceiver(recEnt);

            entity.setCtnt(this.ctnt);

            list.add(entity);
        }
        return list;
    }
}
