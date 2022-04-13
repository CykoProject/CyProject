package com.example.CyProject.home;

import com.example.CyProject.ResultVo;
import com.example.CyProject.Utils;
import com.example.CyProject.security.AuthenticationFacade;
import com.example.CyProject.home.model.diary.DiaryEntity;
import com.example.CyProject.home.model.diary.DiaryRepository;
import com.example.CyProject.home.model.home.HomeEntity;
import com.example.CyProject.home.model.home.HomeRepository;
import com.example.CyProject.home.model.visit.VisitDto;
import com.example.CyProject.home.model.visit.VisitEntity;
import com.example.CyProject.home.model.visit.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ajax/home")
public class HomeRestController {

    @Autowired private HomeRepository homeRepository;
    @Autowired private DiaryRepository diaryRepository;
    @Autowired private VisitRepository visitRepository;
    @Autowired private AuthenticationFacade authenticationFacade;
    @Autowired private Utils utils;

    @GetMapping
    public HomeEntity home(HomeEntity entity) {
        return homeRepository.findByIuser(entity.getIuser());
    }

    @DeleteMapping("/diary/del")
    public ResultVo delDiary(DiaryEntity entity) {
        ResultVo vo = new ResultVo();
        vo.setResult(0);
        DiaryEntity dbData = diaryRepository.getById(entity.getIdiary());
        if(dbData.getIhost() == authenticationFacade.getLoginUserPk()) {
            diaryRepository.deleteById(entity.getIdiary());
            vo.setResult(1);
        }
        return vo;
    }

    @GetMapping("/visit")
    public List<Object> visitList(VisitEntity entity) {
        List<VisitEntity> list = visitRepository.findByIhostOrderByRdtDesc(entity.getIhost());
        for(VisitEntity item : list) {
            item.getIuser().setUpw(null);
        }
        List<Object> obj = utils.makeStringNewLine(list);

        return obj;
    }

    @GetMapping("/visit/secret")
    public ResultVo onVisitSecret(VisitDto dto) {
        VisitEntity entity = visitRepository.getById(dto.getIvisit());
        entity.setSecret(true);
        VisitEntity status = visitRepository.save(entity);
        ResultVo vo = new ResultVo();
        vo.setResult(entity.isSecret() == status.isSecret() ? 1 : 0);

        return vo;
    }

    @DeleteMapping("/visit/del")
    public ResultVo delVisit(VisitDto dto) {
        ResultVo vo = new ResultVo();
        vo.setResult(0);
        int userPk = authenticationFacade.getLoginUserPk();
        if(userPk == dto.getIuser().getIuser() || userPk == dto.getIhost()) {
            visitRepository.deleteById(dto.getIvisit());
            vo.setResult(1);
        }
        return vo;
    }
}
