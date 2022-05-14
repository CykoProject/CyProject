package com.example.CyProject.home;

import com.example.CyProject.ResultVo;
import com.example.CyProject.Utils;
import com.example.CyProject.config.AuthenticationFacade;
import com.example.CyProject.home.model.comment.CommentEntity;
import com.example.CyProject.home.model.comment.CommentRepository;
import com.example.CyProject.home.model.diary.DiaryEntity;
import com.example.CyProject.home.model.diary.DiaryRepository;
import com.example.CyProject.home.model.home.HomeEntity;
import com.example.CyProject.home.model.home.HomeRepository;
import com.example.CyProject.home.model.jukebox.JukeBoxDto;
import com.example.CyProject.home.model.jukebox.JukeBoxEntity;
import com.example.CyProject.home.model.jukebox.JukeBoxRepository;
import com.example.CyProject.home.model.report.ReportEntity;
import com.example.CyProject.home.model.report.ReportRepository;
import com.example.CyProject.home.model.visit.VisitDto;
import com.example.CyProject.home.model.visit.VisitEntity;
import com.example.CyProject.home.model.visit.VisitRepository;
import com.example.CyProject.user.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ajax/home")
public class HomeRestController {

    @Autowired private HomeRepository homeRepository;
    @Autowired private DiaryRepository diaryRepository;
    @Autowired private VisitRepository visitRepository;
    @Autowired private ReportRepository reportRepository;
    @Autowired private CommentRepository commentRepository;
    @Autowired private JukeBoxRepository jukeBoxRepository;
    @Autowired private AuthenticationFacade authenticationFacade;
    @Autowired private Utils utils;

    @GetMapping
    public HomeEntity home(HomeEntity entity) {
        return homeRepository.findByIuser(entity.getIuser());
    }

    @GetMapping("/cmt/{cg}")
    public List<CommentEntity> selCommentList(@PathVariable String cg, CommentEntity param) {
        int ihost = param.getIhost().getIuser();
        int iboard = param.getIboard();
        int icategory = utils.getCommentCategory(cg);
        int parent = 0;

        UserEntity entity = new UserEntity();
        entity.setIuser(ihost);

        return commentRepository.findAllByIhostAndIboardAndCategoryAndParent(entity, iboard, icategory, parent);
    }

    @GetMapping("/reply/{cg}")
    public List<CommentEntity> selReplyList(@PathVariable String cg, CommentEntity param) {
        int icmt = param.getParent();
        int ihost = param.getIhost().getIuser();
        int icategory = utils.getCommentCategory(cg);

        UserEntity entity = new UserEntity();
        entity.setIuser(ihost);

        System.out.println(param);

        return commentRepository.selReplyList(icmt, entity, icategory);
    }

    @PostMapping("/cmt/{category}")
    public ResultVo insComment(@PathVariable String category) {
        ResultVo vo = new ResultVo();
        System.out.println(category);
        System.out.println(utils.getCommentCategory(category));
        return vo;
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

    @PostMapping("/diary/report")
    public ResultVo reportDiary(@RequestBody ReportEntity reportEntity) {
        ResultVo vo = new ResultVo();
        vo.setResult(0);

        int icategory = HomeCategory.DIARY.getCategory();
        int loginUserPk = authenticationFacade.getLoginUserPk();
        if(loginUserPk == 0) {
            vo.setResult(0);
            return vo;
        }
        reportEntity.setIcategory(icategory);
        reportEntity.setReporter(loginUserPk);

        try {
            reportRepository.save(reportEntity);
            vo.setResult(1);
        } catch (Exception e) {
            e.printStackTrace();
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

    @GetMapping("/visit/mod")
    public VisitEntity visitMod(@RequestParam int ivisit) {
        VisitEntity entity = visitRepository.findByIvisit(ivisit);
        return entity;
    }
    @PostMapping("/visit/mod")
    public ResultVo visitModProc(@RequestBody VisitEntity entity) {
        ResultVo vo = new ResultVo();
        String preCtnt = visitRepository.findByIvisit(entity.getIvisit()).getCtnt();
        VisitEntity result = visitRepository.save(entity);
        if(!preCtnt.equals(result.getCtnt())) {
            vo.setResult(1);
        }
        return vo;
    }

    @GetMapping("/visit/secret")
    public ResultVo onVisitSecret(VisitDto dto) {
        VisitEntity entity = visitRepository.getById(dto.getIvisit());
        if(entity.isSecret()) {
            entity.setSecret(false);
        } else {
            entity.setSecret(true);
        }
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

    @PostMapping("/jukebox/repre")
    public ResultVo updRepreStatus(@RequestBody JukeBoxDto dto) {
        ResultVo vo = new ResultVo();
        int cnt = 0;
        for(JukeBoxEntity item : dto.getJukeBoxList()) {
            cnt += jukeBoxRepository.updRepreStatus(item.isRepre(), item.getIhost(), item.getIjukebox());
        }

        vo.setResult(cnt == dto.getJukeBoxList().size() ? 1 : 0);

        return vo;
    }

    @GetMapping("/repre/audio")
    public List<JukeBoxEntity> getRepreMusicList(int iuser) {
        List<JukeBoxEntity> list = jukeBoxRepository.selRepreList(iuser);
        return list;
    }
}
