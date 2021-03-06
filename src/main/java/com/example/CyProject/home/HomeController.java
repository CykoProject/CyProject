package com.example.CyProject.home;

import com.example.CyProject.PageEntity;
import com.example.CyProject.Utils;
import com.example.CyProject.config.AuthenticationFacade;
import com.example.CyProject.home.model.comment.CommentRepository;
import com.example.CyProject.home.model.home.HomeEntity;
import com.example.CyProject.home.model.diary.DiaryEntity;
import com.example.CyProject.home.model.diary.DiaryRepository;
import com.example.CyProject.home.model.home.HomeRepository;
import com.example.CyProject.home.model.miniroom.MiniroomEntity;
import com.example.CyProject.home.model.miniroom.MiniroomRepository;
import com.example.CyProject.home.model.photo.PhotoEntity;
import com.example.CyProject.home.model.photo.PhotoImgEntity;
import com.example.CyProject.home.model.photo.PhotoImgRepository;
import com.example.CyProject.home.model.photo.PhotoRepository;
import com.example.CyProject.home.model.jukebox.JukeBoxRepository;
import com.example.CyProject.home.model.scrap.BoardListEntity;
import com.example.CyProject.home.model.scrap.BoardListRepository;
import com.example.CyProject.home.model.visit.VisitEntity;
import com.example.CyProject.home.model.profile.ProfileEntity;
import com.example.CyProject.home.model.profile.ProfileRepository;
import com.example.CyProject.home.model.visit.VisitRepository;
import com.example.CyProject.user.model.UserEntity;

import com.example.CyProject.home.model.visitor.VisitorEntity;
import com.example.CyProject.home.model.visitor.VisitorPk;
import com.example.CyProject.home.model.visitor.VisitorRepository;
import com.example.CyProject.home.model.visitor.VisitorService;

import com.example.CyProject.shopping.model.history.purchase.PurchaseHistoryRepository;
import com.example.CyProject.shopping.model.item.ItemCategory;
import com.example.CyProject.user.model.UserRepository;
import com.example.CyProject.user.model.friends.FriendsEntity;
import com.example.CyProject.user.model.friends.FriendsRepository;
import com.example.CyProject.user.model.friends.FriendsService;
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
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.logging.SimpleFormatter;

@Controller
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController {

    @Autowired private PurchaseHistoryRepository purchaseHistoryRepository;
    @Autowired private AuthenticationFacade authenticationFacade; // ????????? ??? ???????????? ????????? ??? ?????? ????????? ?????? ?????????
    @Autowired private VisitRepository visitRepository;
    @Autowired private JukeBoxRepository jukeBoxRepository;
    @Autowired private ProfileRepository profileRepository;
    @Autowired private VisitorService visitorService;
    @Autowired private CommentRepository commentRepository;
    @Autowired private DiaryRepository diaryRepository;
    @Autowired private HomeRepository homeRepository;
    @Autowired private PageService pageService;
    @Autowired private UserRepository userRepository;
    @Autowired private HomeService homeService;
    @Autowired private Utils utils;
    @Autowired private AuthenticationFacade auth;
    @Autowired private PhotoRepository photoRepository;
    @Autowired private MiniroomRepository miniroomRepository;
    @Autowired private PhotoImgRepository photoImgRepository;
    @Autowired private BoardListRepository boardListRepository;

    @Autowired private FriendsRepository friendsRepository;
    @Autowired private FriendsService friendsService;

    @GetMapping
    public String home(HomeEntity entity, Model model) {
        int homeScope = authenticationFacade.isHomeVisitScope(entity.getIuser());
        if(homeScope < 2) {
            return authenticationFacade.returnPath(entity.getIuser(), model);
        }
        int loginUser = authenticationFacade.getLoginUserPk();
        int ihomePk = utils.findHomePk(entity.getIuser());


        // todo jpa primary key ?????????
        int success = visitorService.saveVisitor(loginUser, ihomePk);
        model.addAttribute("loginUserPk", authenticationFacade.getLoginUserPk());
        model.addAttribute("user", userRepository.findByIuser(entity.getIuser()));

        UserEntity user = userRepository.findByIuser(entity.getIuser());
//        List<PhotoEntity> news = photoRepository.findTop4ByIhostAndRdtBetweenOrderByRdtDesc(user, LocalDateTime.now().minusWeeks(1), LocalDateTime.now());
        List<BoardListEntity> news = boardListRepository.newPhotos(user, LocalDateTime.now().minusWeeks(1), LocalDateTime.now());
        model.addAttribute("news", news);

        if (miniroomRepository.countByIhostAndRepre(entity.getIuser(), true) > 0) {
            model.addAttribute("isRoom", 1);
            model.addAttribute("myRoom", miniroomRepository.findByIhostAndRepre(entity.getIuser(), true));
        } else {
            model.addAttribute("isRoom", 0);
        }

        FriendsEntity friendsEntity = new FriendsEntity();
        friendsEntity.setIuser(entity.getIuser());
        friendsEntity.setFuser(userRepository.findByIuser(auth.getLoginUserPk()));
        if (authenticationFacade.getLoginUserPk() != 0) {
            model.addAttribute("isFriend", homeService.selFriends(friendsEntity));
        }

        return "home/home";
    }

    @GetMapping("/friend")
    public String addFriend(int iuser, String nickname, Model model) {
        int loginUserPk = auth.getLoginUserPk();
        boolean status = false;

        UserEntity user = new UserEntity();
        if(loginUserPk > 0) {
            FriendsEntity friend = new FriendsEntity();
            user.setIuser(iuser);
            friend.setIuser(loginUserPk);
            friend.setFuser(user);
            friend.setNickname(nickname);
            friend.setStatus(0);

            if(friendsService.isFriend(loginUserPk, iuser)) {
                friendsRepository.save(friend);
            }

            user = userRepository.findByIuser(iuser);
            user.setUpw(null);
            user.setProvider(null);
            user.setEmail(null);
            user.setRole(null);

            status = true;
        }


        model.addAttribute("status", status);
        model.addAttribute("user", user);
        return "home/error";
    }
// ======================= ?????????, ????????????, ???????????? =====================================================================================

    // ???????????? ============================================================================================
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

        int fontCategory = ItemCategory.FONT.getCategory();

        model.addAttribute("font", purchaseHistoryRepository.findAllByIcategoryInHisotry(fontCategory, loginUserPk));
        model.addAttribute("loginUserPk", loginUserPk);
        model.addAttribute("data", utils.makeStringNewLine(list));
        model.addAttribute("pageData", pageEntity);
        return "home/diary/diary";
    }

    @GetMapping("/diary/write")
    public String writeDiary(int iuser, String idiary, Model model) {
        int idx = utils.getParseIntParameter(idiary);
        if(idx != 0) {
            System.out.println("mod");
            model.addAttribute("modData", diaryRepository.findById(idx));
        }
        if(authenticationFacade.getLoginUserPk() != iuser) {
            return "redirect:/home/diary?iuser="+iuser;
        }

        int fontCategory = ItemCategory.FONT.getCategory();
        model.addAttribute("font", purchaseHistoryRepository.findAllByIcategoryInHisotry(fontCategory, authenticationFacade.getLoginUserPk()));
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
    // ???????????? ============================================================================================

    // ????????? ============================================================================================================
    @GetMapping("/visit")
    public String visit(Model model, @RequestParam(required = false, defaultValue = "1", value = "page") int page
            , @RequestParam(required = false, defaultValue = "0", value = "iuser") int iuser) {
        int rowCnt = 10;
        int pageCnt = 10;
        int maxPage = pageService.visitMaxPage(iuser, rowCnt);
        Page<VisitEntity> list = pageService.visitPaging(iuser, page, rowCnt);

        /*
         * ???????????????
         */
        PageEntity pageEntity = new PageEntity.Builder()
                .page(page)
                .pageCnt(pageCnt)
                .maxPage(maxPage)
                .rowCnt(rowCnt)
                .build();

        /*
         * ???????????????
         */
        int loginUserPk = authenticationFacade.getLoginUserPk();
        int minimeCategory = ItemCategory.MINIME.getCategory();
        int fontCategory = ItemCategory.FONT.getCategory();

        model.addAttribute("font", purchaseHistoryRepository.findAllByIcategoryInHisotry(fontCategory, loginUserPk));
        model.addAttribute("minime", purchaseHistoryRepository.findAllByIcategoryInHisotry(minimeCategory, loginUserPk));
        model.addAttribute("loginUserPk", authenticationFacade.getLoginUserPk());
        model.addAttribute("data", utils.makeStringNewLine(list));
        model.addAttribute("pageData", pageEntity);

        return "home/visit/visit";
    }

    @GetMapping("/visit/mod")
    public String modVisit(int iuser, int writer, int iboard, Model model) {
        int loginUserPk = authenticationFacade.getLoginUserPk();
        if(writer != loginUserPk) {
            return "redirect:/";
        }
        model.addAttribute("modData", visitRepository.findByIvisit(iboard));
        model.addAttribute("loginUserPk", loginUserPk);
        return "home/visit/write";
    }

    @PostMapping("/visit/write")
    public String writeVisitProc(VisitEntity entity) {
        if(entity.getCtnt().trim().length() == 0) {
            return authenticationFacade.loginChk("redirect:/home/visit?iuser=" + entity.getIhost());
        }
        visitRepository.save(entity);
        return authenticationFacade.loginChk("redirect:/home/visit?iuser=" + entity.getIhost());
    }
    // ????????? ============================================================================================================

    // ???????????? ============================================================================================================
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
        model.addAttribute("loginUserPk", authenticationFacade.getLoginUserPk());
        return "home/jukebox/repre";
    }

    @GetMapping("/audio")
    public String audio() {

        return "home/audio";
    }
    // ???????????? ============================================================================================================

    // ?????? ============================================================================================================
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
        HomeEntity home = homeRepository.findByIuser(entity.getIuser());
        int ihome = home.getIhome();
        entity.setIhome(ihome);
        entity.setHome_nm(home.getHome_nm());
        try{
            if(entity.getIuser() == authenticationFacade.getLoginUserPk()) {
                homeRepository.save(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            hs.setAttribute("error", "??????");
        }
        hs.setAttribute("error", "??????");
        return "redirect:/home/manage?iuser=" + entity.getIuser();
    }
    @PostMapping("/manage/scope")
    public String manageScopeProc(HomeEntity entity) {
        homeRepository.updateScope(entity.getScope(), entity.getIuser());

        return "redirect:/home/manage?iuser=" + entity.getIuser();
    }
    // ?????? ============================================================================================================
// ======================= ?????????, ????????????, ????????????, ?????? =====================================================================================


    @GetMapping("/profile")
    public String profile(HomeEntity entity, Model model) {
        int loginUserPk = authenticationFacade.getLoginUserPk();
        model.addAttribute("loginUserPk", loginUserPk);

        UserEntity list = userRepository.findByIuser(entity.getIuser());

//        model.addAttribute("user", userRepository.findByIuser(entity.getIuser()));
        if (list.getProfile_ctnt() == null) {
            model.addAttribute("user", list);
        } else {
            model.addAttribute("user", utils.makeStringNewLine(list));
        }
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
            model.addAttribute("error", "????????? ????????? ?????????????????????.");
            return "home/profile/profile";
        }
        return "redirect:/home/profile?iuser=" + entity.getIhost();
    }

    @GetMapping("/profile/history")
    public String profileHistory(@RequestParam int ihost, Model model) {
        List<ProfileEntity> entity = profileRepository.findByIhostOrderByRdtDesc(ihost);
        List<Object> list = utils.makeStringNewLine(entity);
        model.addAttribute("data", list);
        return "home/profile/history";
    }

    @GetMapping("/photo")
    public String photo(HomeEntity entity, Model model) {
        int loginUserPk = authenticationFacade.getLoginUserPk();

        List<BoardListEntity> scrapList = boardListRepository.findAllByIuserOrderByIphotoDesc(userRepository.findByIuser(entity.getIuser()));
        System.out.println("sl : " + scrapList);
//        model.addAttribute("list", utils.makeStringNewLine(scrapList));
        System.out.println("line : " + utils.makeStringNewLineForPhoto(scrapList));
        model.addAttribute("list", utils.makeStringNewLineForPhoto(scrapList));

//        List<PhotoEntity> list = photoRepository.findByIhostOrderByRdtDesc(userRepository.findByIuser(entity.getIuser()));
//        model.addAttribute("list", list);

        model.addAttribute("loginUserPk", loginUserPk);

        FriendsEntity friendsEntity = new FriendsEntity();
        friendsEntity.setIuser(entity.getIuser());
        friendsEntity.setFuser(userRepository.findByIuser(auth.getLoginUserPk()));

        if (authenticationFacade.getLoginUserPk() != 0) {
            model.addAttribute("isFriend", homeService.selFriends(friendsEntity));
        }

        return "home/photo/photo";
    }

    @GetMapping("/photo/write")
    public String writePhoto(String iphoto, int iuser, Model model) {
        int parseIphoto = utils.getParseIntParameter(iphoto);

        if (parseIphoto != 0) {
            System.out.println("mod");
            model.addAttribute("data", photoRepository.findById(parseIphoto));
            model.addAttribute("fileData", photoImgRepository.findAllByIphoto(parseIphoto));
        }

        if (iuser != authenticationFacade.getLoginUserPk()) {
            return "redirect:/home/diary?iuser=" + iuser;
        }

        model.addAttribute("loginUserPk", authenticationFacade.getLoginUserPk());

        return "home/photo/write";
    }

    @PostMapping("/photo/write")
    public String writePhotoProc(PhotoEntity entity, MultipartFile imgs) {
        if (entity.getIphoto() != 0) {
            // ??????
            if (!imgs.isEmpty()) {
                // ????????? ?????? O
                photoImgRepository.deleteAllByIphoto(entity.getIphoto());

                PhotoImgEntity imgEntity = new PhotoImgEntity();
                imgEntity.setIphoto(entity.getIphoto());
                homeService.writePhoto(imgs, imgEntity);
            }
            photoRepository.save(entity);

        } else {
            // ?????? ??????
            entity.setIhost(userRepository.findByIuser(auth.getLoginUserPk()));
            PhotoEntity resultEntity = photoRepository.save(entity);

            PhotoImgEntity imgEntity = new PhotoImgEntity();
            imgEntity.setIphoto(resultEntity.getIphoto());

            homeService.writePhoto(imgs, imgEntity);

            PhotoEntity photoEntity = photoRepository.findByIphoto(entity.getIphoto());

            BoardListEntity scrapEntity = new BoardListEntity();
            scrapEntity.setIuser(entity.getIhost());
            scrapEntity.setIphoto(photoEntity);
            scrapEntity.setScrap(false);
            boardListRepository.save(scrapEntity);
        }

        return "redirect:/home/photo?iuser=" + entity.getIhost().getIuser();
    }

    @GetMapping("/miniroom")
    public String miniroom(HomeEntity entity, Model model) {
        int loginUserPk = authenticationFacade.getLoginUserPk();
        model.addAttribute("loginUserPk", loginUserPk);
        model.addAttribute("miniroomList", miniroomRepository.findAllByIhost(entity.getIuser()));

        if (miniroomRepository.countByIhostAndRepre(entity.getIuser(), true) > 0) {
            model.addAttribute("isRoom", 1);
            model.addAttribute("myRoom", miniroomRepository.findByIhostAndRepre(entity.getIuser(), true));
        } else {
            model.addAttribute("isRoom", 0);
        }

        return "/home/miniroom/miniroom";
    }

    @PostMapping("/miniroom")
    public String miniroomProc(HomeEntity entity, @RequestParam(value = "myroom") int myroom) {
        System.out.println("myroom : " + myroom);

        if (miniroomRepository.countByIhostAndRepre(entity.getIuser(), true) > 0) {
            MiniroomEntity prevRoom = miniroomRepository.findByIhostAndRepre(entity.getIuser(), true);
            prevRoom.setRepre(false);
            System.out.println("prevRoom : " + prevRoom);
            miniroomRepository.save(prevRoom);
        }

        MiniroomEntity currentRoom = miniroomRepository.findByItemIdInMyroom(entity.getIuser(), myroom);
        System.out.println("currentRoom : " + currentRoom);
        currentRoom.setRepre(true);
        System.out.println("changeRoom : " + currentRoom);
        miniroomRepository.save(currentRoom);

        return "redirect:/home/miniroom?iuser=" + entity.getIuser();
    }


}