package com.example.CyProject.home;

import com.example.CyProject.Utils;
import com.example.CyProject.common.MyFileUtils;
import com.example.CyProject.config.AuthenticationFacade;
import com.example.CyProject.home.model.friends.FriendsEntity;
import com.example.CyProject.home.model.home.HomeEntity;
import com.example.CyProject.home.model.diary.DiaryEntity;
import com.example.CyProject.home.model.diary.DiaryRepository;
import com.example.CyProject.home.model.home.HomeRepository;
import com.example.CyProject.home.model.photo.PhotoEntity;
import com.example.CyProject.home.model.photo.PhotoImgEntity;
import com.example.CyProject.home.model.photo.PhotoImgRepository;
import com.example.CyProject.home.model.photo.PhotoRepository;
import com.example.CyProject.home.model.visit.VisitEntity;
import com.example.CyProject.home.model.profile.ProfileEntity;
import com.example.CyProject.home.model.profile.ProfileRepository;
import com.example.CyProject.home.model.visit.VisitRepository;
import com.example.CyProject.user.model.UserEntity;
import com.example.CyProject.user.model.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

@Controller
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController {

    @Autowired private Utils utils;
    @Autowired private DiaryRepository diaryRepository;
    @Autowired private VisitRepository visitRepository;
    @Autowired private AuthenticationFacade authenticationFacade; // 로그인 된 회원정보 가져올 수 있는 메소드 있는 클래스
    @Autowired private PageService pageService;
    @Autowired private UserRepository userRepository;
    @Autowired private HomeService homeService;
    @Autowired private AuthenticationFacade auth;
    @Autowired private PhotoRepository photoRepository;

    @GetMapping
    public String home(HomeEntity entity, Model model) {
        model.addAttribute("loginUserPk", authenticationFacade.getLoginUserPk());
        model.addAttribute("user", userRepository.findByIuser(entity.getIuser()));

        FriendsEntity friendsEntity = new FriendsEntity();
        friendsEntity.setIuser(entity.getIuser());
        friendsEntity.setFuser(auth.getLoginUserPk());

        model.addAttribute("isFriend", homeService.selFriends(friendsEntity));

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
        int category = HomeCategory.VISIT.getCategory();
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


// ======================= 방명록, 다이어리, 주크박스 =====================================================================================


    @GetMapping("/profile")
    public String profile(HomeEntity entity, Model model) {
        int loginUserPk = authenticationFacade.getLoginUserPk();
        model.addAttribute("loginUserPk", loginUserPk);
        model.addAttribute("user", userRepository.findByIuser(entity.getIuser()));
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

    @GetMapping("/photo")
    public String photo(HomeEntity entity, Model model) {
        int loginUserPk = authenticationFacade.getLoginUserPk();
        List<PhotoEntity> list = photoRepository.findByIhostOrderByRdtDesc(entity.getIuser());

        model.addAttribute("loginUserPk", loginUserPk);
        model.addAttribute("list", list);
        return "home/photo/photo";
    }

    @GetMapping("/photo/write")
    public String writePhoto() {
        return "home/photo/write";
    }

    @PostMapping("/photo/write")
    public String writePhotoProc(PhotoEntity entity, MultipartFile imgs) {
        System.out.println("entity : " + entity);

        entity.setIhost(auth.getLoginUserPk());
        PhotoEntity resultEntity = photoRepository.save(entity);
        System.out.println(resultEntity);

        PhotoImgEntity imgEntity = new PhotoImgEntity();
        imgEntity.setIphoto(resultEntity.getIphoto());

        int result = homeService.writePhoto(imgs, imgEntity);

        return "redirect:/home/photo?iuser=" + entity.getIhost();
    }

    @GetMapping("/miniroom")
    public String miniroom(HomeEntity entity, Model model) {
        int loginUserPk = authenticationFacade.getLoginUserPk();
        model.addAttribute("loginUserPk", loginUserPk);

        return "/home/miniroom/miniroom";
    }



}