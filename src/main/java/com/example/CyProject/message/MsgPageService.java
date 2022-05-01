package com.example.CyProject.message;

import com.example.CyProject.message.model.MessageEntity;
import com.example.CyProject.message.model.MessageRepository;
import com.example.CyProject.message.model.MessageSaveBoxEntity;
import com.example.CyProject.message.model.MessageSaveBoxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class MsgPageService {

    @Autowired private MessageRepository messageRepository;

    @Autowired private MessageSaveBoxRepository messageSaveBoxRepository;

    Page<MessageEntity> msgInBoxPaging(int receiver, int page, int rowCnt) {
        return messageRepository.getReceiveMsgList(receiver, PageRequest.of(page-1, rowCnt, Sort.by(Sort.Direction.DESC, "rdt")));
    }

    Page<MessageEntity> msgOutBoxPaging(int iuser, int page, int rowCnt) {
        return messageRepository.getSendMsgList(iuser, PageRequest.of(page-1, rowCnt, Sort.by(Sort.Direction.DESC, "rdt")));
    }

    Page<MessageSaveBoxEntity> msgSaveBoxPaging(int iuser, int page, int rowCnt) {
        return messageSaveBoxRepository.selMsgSaveBoxList(iuser, PageRequest.of(page-1, rowCnt));
    }
}
