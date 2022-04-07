package com.example.CyProject.home;

import com.example.CyProject.Utils;
import com.example.CyProject.config.AuthenticationFacade;
import com.example.CyProject.home.model.home.HomeEntity;
import com.example.CyProject.home.model.diary.DiaryEntity;
import com.example.CyProject.home.model.diary.DiaryRepository;
import com.example.CyProject.home.model.visit.VisitEntity;
import com.example.CyProject.home.model.visit.VisitRepository;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.dynamic.DynamicType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@Controller
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController {

    @Autowired private Utils utils;
    @Autowired private DiaryRepository diaryRepository;
    @Autowired private VisitRepository visitRepository;
    @Autowired private AuthenticationFacade authenticationFacade; // 로그인 된 회원정보 가져올 수 있는 메소드 있는 클래스
    @Autowired private HomeService homeService;

    @GetMapping
    public String home() {
        return "home/index";
    }

    @GetMapping("/diary")
    public String diary(HomeEntity entity, Model model) {
        int loginUserPk = authenticationFacade.getLoginUserPk();
        model.addAttribute("loginUser", loginUserPk);
        List<DiaryEntity> diaryList = diaryRepository.findByIhostOrderByRdtDesc(entity.getIuser());
        List<Object> list = utils.makeStringNewLine(diaryList);

        model.addAttribute("data", list);
        return "home/diary/diary";
    }

    @GetMapping("/diary/write")
    public String writeDiary(int iuser, String idiary, Model model) {
        int idx = utils.getParseIntParameter(idiary);
        if(idx != 0) {
            model.addAttribute("modData", diaryRepository.findById(idx));
        }
        if(authenticationFacade.getLoginUserPk() != iuser) {
            return "redirect:/home/diary?iuser="+iuser;
        }
        model.addAttribute("loginUser", authenticationFacade.getLoginUserPk());
        return "home/diary/write";
    }
    @PostMapping("/diary/write")
    public String writeDiaryProc(DiaryEntity entity) {
        if(entity.getIhost() != 0) {
            diaryRepository.save(entity);
        }
        return "redirect:/home/diary?iuser=" + entity.getIhost();
    }

    @GetMapping("/visit")
    public String visit(int iuser, Model model) {
        model.addAttribute("loginUserPk", authenticationFacade.getLoginUserPk());
        return "home/visit/visit";
    }

    @GetMapping("/visit/write")
    public String writeVisit(Model model, VisitEntity entity, String tab) {
        if(tab != null) {
            model.addAttribute("modData", visitRepository.findById(entity.getIvisit()));
        }
        model.addAttribute("loginUserPk", authenticationFacade.getLoginUserPk());
        return "home/visit/write";
    }

    @PostMapping("/visit/write")
    public String writeVisitProc(VisitEntity entity) {
        visitRepository.save(entity);
        return authenticationFacade.loginChk("redirect:/home/visit?iuser=" + entity.getIhost());
    }

    @GetMapping("/test")
    public String list(@RequestParam(required = false, defaultValue = "0", value = "page") int page, @RequestParam(required = false, defaultValue = "0", value = "iuser") int iuser) {
        System.out.println("TEST 성공");
        System.out.println(iuser);
        Page<VisitEntity> listPage = homeService.visitPaging(iuser, page);
        for(VisitEntity list : listPage) {
            System.out.println(list);
        }
        return "home/index";
    }
}