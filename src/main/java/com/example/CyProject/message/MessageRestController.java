package com.example.CyProject.message;

import com.example.CyProject.ResultVo;
import com.example.CyProject.config.AuthenticationFacade;
import com.example.CyProject.message.model.MessageEntity;
import com.example.CyProject.message.model.MessageRepository;
import com.example.CyProject.message.model.MessageSave;
import com.example.CyProject.user.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ajax/msg")
public class MessageRestController {

    @Autowired private AuthenticationFacade authenticationFacade;
    @Autowired private MessageRepository messageRepository;

    @PostMapping("/write")
    public int msgWriteProc(@RequestBody MessageSave save) {
        List<MessageEntity> list = save.toListEntity(authenticationFacade.getLoginUserPk());

        int cnt = 0;
        int result = 0;
        for(MessageEntity item : list) {
            MessageEntity me = messageRepository.save(item);
            if(me != null) { cnt++; }
        }
        if(cnt == list.size()) { ++result; }
        return result;
    }

    @PostMapping("/del")
    public ResultVo msgDelProc(@RequestBody List<Integer> imsg) {
        ResultVo vo = new ResultVo();

        int loginUserPk = authenticationFacade.getLoginUserPk();
        int resultCnt = imsg.size();
        for(Integer item : imsg) {
            Optional<MessageEntity> opt = messageRepository.findById(item);
            MessageEntity msgEntity = opt.get();

            int iuser = msgEntity.getIuser().getIuser();
            int receiver = msgEntity.getReceiver().getIuser();

            if(iuser == loginUserPk) {
                resultCnt = messageRepository.delMsg(true, false, item) == 1 ? --resultCnt : ++resultCnt;
            } else if(receiver == loginUserPk) {
                resultCnt = messageRepository.delMsg(false, true, item) == 1 ? --resultCnt : ++resultCnt;
            }
        }

        vo.setResult(resultCnt == 0 ? 1 : 0);

        return vo;
    }
}
