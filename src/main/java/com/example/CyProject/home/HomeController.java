package com.example.CyProject.home;

import com.example.CyProject.PageEntity;
import com.example.CyProject.Utils;
import com.example.CyProject.config.AuthenticationFacade;
import com.example.CyProject.home.model.comment.CommentRepository;
import com.example.CyProject.home.model.home.HomeEntity;
import com.example.CyProject.home.model.diary.DiaryEntity;
import com.example.CyProject.home.model.diary.DiaryRepository;
import com.example.CyProject.home.model.home.HomeRepository;
import com.example.CyProject.home.model.jukebox.JukeBoxRepository;
import com.example.CyProject.home.model.visit.VisitEntity;
import com.example.CyProject.home.model.profile.ProfileEntity;
import com.example.CyProject.home.model.profile.ProfileRepository;
import com.example.CyProject.home.model.visit.VisitRepository;
import com.example.CyProject.user.model.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController {

    @Autowired private AuthenticationFacade authenticationFacade; // 로그인 된 회원정보 가져올 수 있는 메소드 있는 클래스
    @Autowired private JukeBoxRepository jukeBoxRepository;
    @Autowired private ProfileRepository profileRepository;
    @Autowired private CommentRepository commentRepository;
    @Autowired private VisitRepository visitRepository;
    @Autowired private DiaryRepository diaryRepository;
    @Autowired private HomeRepository homeRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private PageService pageService;
    @Autowired private HomeService homeService;
    @Autowired private Utils utils;

    @GetMapping
    public String home(HomeEntity entity, Model model) {
        int loginUser = authenticationFacade.getLoginUserPk();
        int ihomePk = utils.findHomePk(entity.getIuser());

        model.addAttribute("loginUserPk", authenticationFacade.getLoginUserPk());
        model.addAttribute("data", profileRepository.findTop1ByIhostOrderByRdtDesc(entity.getIuser()));
        model.addAttribute("user", userRepository.findByIuser(entity.getIuser()));
        return "home/home";
    }
// ======================= 방명록, 다이어리, 주크박스 =====================================================================================

    // 다이어리 ============================================================================================
    @GetMapping("/diary")
    public String diary(HomeEntity entity, Model model,
                        @RequestParam(required = false, defaultValue = "1", value = "page") int page,
                        @RequestParam(required = false, defaultValue = "", value = "searchRdt") String rdt) {
        int rowCnt = 10;
        int pageCnt = 10;

        int maxPage = pageService.diaryMaxPage(entity.getIuser(), rowCnt, rdt);
        int loginUserPk = authenticationFacade.getLoginUserPk();
        Page<DiaryEntity> list = pageService.diaryPaging(entity.getIuser(), page, rowCnt, rdt);

        PageEntity pageEntity = new PageEntity.Builder()
                .page(page)
                .pageCnt(pageCnt)
                .maxPage(maxPage)
                .rowCnt(rowCnt)
                .build();

        model.addAttribute("loginUserPk", loginUserPk);
        model.addAttribute("data", utils.makeStringNewLine(list));
        model.addAttribute("pageData", pageEntity);
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
        model.addAttribute("loginUserPk", authenticationFacade.getLoginUserPk());
        return "home/diary/write";
    }
    @PostMapping("/diary/write")
    public String writeDiaryProc(DiaryEntity entity) {
        if(entity.getIhost() != 0) {
            diaryRepository.save(entity);
        }
        return "redirect:/home/diary?iuser=" + entity.getIhost();
    }
    // 다이어리 ============================================================================================

    // 방명록 ============================================================================================================
    @GetMapping("/visit")
    public String visit(Model model, @RequestParam(required = false, defaultValue = "1", value = "page") int page
            , @RequestParam(required = false, defaultValue = "0", value = "iuser") int iuser) {
        // TODO - 동적 페이징
        int rowCnt = 10;
        int pageCnt = 10;
        int maxPage = pageService.visitMaxPage(iuser, rowCnt);
        Page<VisitEntity> list = pageService.visitPaging(iuser, page, rowCnt);

        /*
         * 페이징처리
         */
        PageEntity pageEntity = new PageEntity.Builder()
                .page(page)
                .pageCnt(pageCnt)
                .maxPage(maxPage)
                .rowCnt(rowCnt)
                .build();

        model.addAttribute("loginUserPk", authenticationFacade.getLoginUserPk());
        model.addAttribute("data", utils.makeStringNewLine(list));
        model.addAttribute("pageData", pageEntity);

        return "home/visit/visit";
    }

    @PostMapping("/visit/write")
    public String writeVisitProc(VisitEntity entity) {
        if(entity.getCtnt().trim().length() == 0) {
            return authenticationFacade.loginChk("redirect:/home/visit?iuser=" + entity.getIhost());
        }
        visitRepository.save(entity);
        return authenticationFacade.loginChk("redirect:/home/visit?iuser=" + entity.getIhost());
    }
    // 방명록 ============================================================================================================

    // 주크박스 ============================================================================================================
    @GetMapping("/jukebox")
    public String jukeBox(@RequestParam(value = "iuser") int iuser, Model model) {
        model.addAttribute("folder", "jukebox");
        model.addAttribute("loginUserPk", authenticationFacade.getLoginUserPk());
        model.addAttribute("data", jukeBoxRepository.findAllByIhost(iuser));
        return "home/jukebox/jukebox";
    }

    @GetMapping("/jukebox/repre")
    public String jukeBoxRepreFolder(@RequestParam(value = "iuser") int iuser, Model model) {
        model.addAttribute("folder", "repre");
        model.addAttribute("data", jukeBoxRepository.selRepreList(iuser));

        return "home/jukebox/repre";
    }

    @GetMapping("/audio")
    public String audio() {

        return "home/audio";
    }
    // 주크박스 ============================================================================================================

    // 관리 ============================================================================================================
    @GetMapping("/manage")
    public String manage(@RequestParam int iuser, Model model, HttpSession hs) {
        int loginIuser = authenticationFacade.getLoginUserPk();


        if(iuser != loginIuser) {
            return "redirect:/manage?iuser=" + loginIuser;
        }
        model.addAttribute("data", homeRepository.findByIuser(iuser));
        model.addAttribute("loginUserPk", authenticationFacade.getLoginUserPk());
        model.addAttribute("error", hs.getAttribute("error"));
        hs.removeAttribute("error");
        return "home/manage/manage";
    }

    @PostMapping("/manage/tab")
    public String manageTabProc(HomeEntity entity, HttpSession hs) {
        int ihome = homeRepository.findByIuser(entity.getIuser()).getIhome();
        entity.setIhome(ihome);
        try{
            if(entity.getIuser() == authenticationFacade.getLoginUserPk()) {
                homeRepository.save(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            hs.setAttribute("error", "실패");
        }
        hs.setAttribute("error", "성공");
        return "redirect:/home/manage?iuser=" + entity.getIuser();
    }
    @PostMapping("/manage/scope")
    public String manageScopeProc(HomeEntity entity) {
        homeRepository.updateScope(entity.getScope(), entity.getIuser());

        return "redirect:/home/manage?iuser=" + entity.getIuser();
    }
    // 관리 ============================================================================================================
// ======================= 방명록, 다이어리, 주크박스, 관리 =====================================================================================


    @GetMapping("/profile")
    public String profile(HomeEntity entity, Model model) {
        int loginUser = authenticationFacade.getLoginUserPk();
        model.addAttribute("loginUser", loginUser);
        model.addAttribute("data", profileRepository.findTop1ByIhostOrderByRdtDesc(entity.getIuser()));
        return "home/profile/profile";
    }

    @GetMapping("/profile/write")
    public String writeProfile() {
        return "home/profile/write";
    }

    @PostMapping("/profile/write")
    public String writeProfileProc(MultipartFile profileImg, ProfileEntity entity, Model model) {
        ProfileEntity param = new ProfileEntity();
        param.setIhost(entity.getIhost());
        param.setCtnt(entity.getCtnt());

        int result = homeService.writeProfile(profileImg, entity);
        if (result == 0) {
            model.addAttribute("error", "프로필 등록에 실패하였습니다.");
            return "home/profile/profile";
        }
        return "redirect:/home/profile?iuser=" + entity.getIhost();
    }
}