package com.example.CyProject.message;

import com.example.CyProject.ResultVo;
import com.example.CyProject.config.AuthenticationFacade;
import com.example.CyProject.message.model.*;
import com.example.CyProject.user.model.UserEntity;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ajax/msg")
public class MessageRestController {

    @Autowired private MessageSaveBoxRepository messageSaveBoxRepository;
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

    @GetMapping("/del")
    public ResultVo delMsgDetailProc(int imsg) {
        ResultVo vo = new ResultVo();
        Optional<MessageEntity> dbData = messageRepository.findById(imsg);
        MessageEntity dbEntity = dbData.get();
        int loginUserPk = authenticationFacade.getLoginUserPk();
        int iuser = dbEntity.getIuser().getIuser();
        int receiver = dbEntity.getReceiver().getIuser();
        int resultCnt = 0;
        if(iuser == loginUserPk) {
            resultCnt = messageRepository.delMsg(true, false, imsg);
        } else if(receiver == loginUserPk) {
            resultCnt = messageRepository.delMsg(false, true, imsg);
        }
        vo.setResult(resultCnt);
        return vo;
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
                messageRepository.updRecvRead(item);
            }
        }

        vo.setResult(resultCnt == 0 ? 1 : 0);
        return vo;
    }

    @PostMapping("/check")
    public ResultVo msgCheck(@RequestBody List<Integer> imsg) {
        ResultVo vo = new ResultVo();
        vo.setResult(0);
        int cnt = imsg.size();
        int resultCnt = 0;
        for(Integer item : imsg) {
            resultCnt += messageRepository.updRecvRead(item);
        }
        if(cnt == resultCnt) {
            vo.setResult(1);
        }
        return vo;
    }

    @PostMapping("/savebox")
    public ResultVo msgSaveBoxProc(@RequestBody String obj) {
        ResultVo vo = new ResultVo();
        MessageSaveBoxEntity entity = new MessageSaveBoxEntity();
        JSONObject json = new JSONObject(obj);
        int imsg = Integer.parseInt(json.get("imsg").toString());
        int iuser = Integer.parseInt(json.get("iuser").toString());
        MessageSaveBoxEntity resultEnt = null;

        UserEntity userEntity = new UserEntity();
        userEntity.setIuser(iuser);
        entity.setIuser(userEntity);

        MessageEntity msgEntity = new MessageEntity();
        msgEntity.setImsg(imsg);
        entity.setImsg(msgEntity);

        MessageSaveBoxEntity isMsgSaveBox = messageSaveBoxRepository.selMsgBox(iuser, imsg);

        if(isMsgSaveBox == null) {
            resultEnt = messageSaveBoxRepository.save(entity);
        }
        if(resultEnt != null) {
            vo.setResult(1);
        }
        return vo;
    }
}
