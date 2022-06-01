package com.example.CyProject.home;

import com.example.CyProject.ResultVo;
import com.example.CyProject.Utils;
import com.example.CyProject.common.MyFileUtils;
import com.example.CyProject.config.AuthenticationFacade;
import com.example.CyProject.home.model.comment.CommentEntity;
import com.example.CyProject.home.model.comment.CommentRepository;
import com.example.CyProject.home.model.diary.DiaryEntity;
import com.example.CyProject.home.model.diary.DiaryRepository;
import com.example.CyProject.home.model.home.HomeEntity;
import com.example.CyProject.home.model.home.HomeMessageEntity;
import com.example.CyProject.home.model.home.HomeMessageRepository;
import com.example.CyProject.home.model.home.HomeRepository;
import com.example.CyProject.home.model.jukebox.JukeBoxDto;
import com.example.CyProject.home.model.jukebox.JukeBoxEntity;
import com.example.CyProject.home.model.jukebox.JukeBoxRepository;
import com.example.CyProject.home.model.report.ReportEntity;
import com.example.CyProject.home.model.report.ReportRepository;
import com.example.CyProject.home.model.photo.PhotoEntity;
import com.example.CyProject.home.model.photo.PhotoImgEntity;
import com.example.CyProject.home.model.photo.PhotoImgRepository;
import com.example.CyProject.home.model.photo.PhotoRepository;
import com.example.CyProject.home.model.scrap.BoardListEntity;
import com.example.CyProject.home.model.scrap.BoardListRepository;
import com.example.CyProject.home.model.visit.VisitDto;
import com.example.CyProject.home.model.visit.VisitEntity;
import com.example.CyProject.home.model.visit.VisitRepository;
import com.example.CyProject.shopping.model.history.purchase.PurchaseHistoryEntity;
import com.example.CyProject.shopping.model.history.purchase.PurchaseHistoryRepository;
import com.example.CyProject.shopping.model.item.ItemCategory;
import com.example.CyProject.user.model.UserEntity;
import com.example.CyProject.user.model.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.*;

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
    @Autowired private PurchaseHistoryRepository purchaseHistoryRepository;
    @Autowired private Utils utils;
    @Autowired private HomeService homeService;
    @Autowired private MyFileUtils myFileUtils;
    @Autowired private HomeMessageRepository homeMessageRepository;
    @Autowired private PhotoImgRepository photoImgRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private PhotoRepository photoRepository;
    @Autowired private BoardListRepository boardListRepository;

    @GetMapping
    public HomeEntity home(HomeEntity entity) {
        return homeRepository.findByIuser(entity.getIuser());
    }

    @PutMapping("/nm/mod")
    public ResultVo modHomeNm(@RequestBody HomeEntity entity) {
        ResultVo vo = new ResultVo();
        vo.setResult(0);

        homeRepository.updHomeNm(entity.getHome_nm(), entity.getIhome());
        vo.setResult(1);

        return vo;
    }

    @GetMapping("/{cg}/cmt/cnt/{iboard}")
    public ResultVo getCommentCnt(@PathVariable String cg, @PathVariable int iboard) {
        ResultVo vo = new ResultVo();
        vo.setResult(commentRepository.selCommentWithOutReplyCnt(iboard, utils.getCommentCategory(cg)));

        return vo;
    }

    @GetMapping("/{cg}/cmt/{iboard}")
    public List<CommentEntity> getCommentList(@PathVariable String cg, @PathVariable int iboard, Pageable pageable) {
        return commentRepository.selCommentWithOutReply(iboard, utils.getCommentCategory(cg), pageable);
    }

    @GetMapping("/font")
    public List<PurchaseHistoryEntity> getUserFontList(int iuser) {
        int loginUserPk = authenticationFacade.getLoginUserPk();
        if(iuser != loginUserPk) {
            return null;
        }
        return purchaseHistoryRepository.findAllByIcategoryInHisotry(ItemCategory.FONT.getCategory(), authenticationFacade.getLoginUserPk());
    }

    @PostMapping("/{cg}/cmt/write")
    public ResultVo insComment(@PathVariable String cg, @RequestBody CommentEntity commentEntity) {
        commentEntity.setCategory(utils.getCommentCategory(cg));
        ResultVo vo = new ResultVo();
        vo.setResult(commentRepository.save(commentEntity) != null ? 1 : 0);

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
        VisitEntity result = visitRepository.save(entity);
        if(result != null) {
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

        System.out.println(dto);

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

    @PostMapping("/upload/image")
    public JSONObject uploadImage(@RequestParam Map<String, Object> paramMap, MultipartRequest request, PhotoImgEntity imgEntity, HttpServletResponse response, Model model) {

        response.setContentType("application/json");

        System.out.println("entity : " + imgEntity);
//        System.out.println("img : " + imgs[0]);

        MultipartFile uploadImage = request.getFile("upload");
        System.out.println(uploadImage);

        if (uploadImage == null) {
            System.out.println("image null");
            return null;
        }

//        entity.setIhost(auth.getLoginUserPk());
//        photoRepository.save(entity);

        String target = "photos/temp/";

        String saveFileName = myFileUtils.transferTo(uploadImage, target);

        JSONObject json = new JSONObject();

        json.put("uploaded", 1);
        json.put("url", "photos/temp/" + saveFileName);
        json.put("fileName", saveFileName);

        model.addAttribute("test", json);

//        Map<String, Object> data = new HashMap<String, Object>();
//
//        data.put("uploaded", 1);
//        data.put("url", saveFileName);
//        data.put("fileName", saveFileName);
//
        ObjectMapper mapper = new ObjectMapper();
//        try {
//            mapper.writeValue(response.getWriter(), json);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        System.out.println("map : " + json);
        return json;
    }

    @GetMapping("/photo")
    public List<PhotoImgEntity> getPhotoList(@RequestParam int iphoto) {
        List<PhotoImgEntity> photoList = photoImgRepository.findAllByIphoto(iphoto);
        return photoList;
    }

    @DeleteMapping("/photo/del")
    public ResultVo delPhoto(PhotoEntity entity) {
        ResultVo vo = new ResultVo();
        vo.setResult(0);

//        PhotoEntity delEntity = photoRepository.getById(entity.getIhost().getIuser());
        UserEntity userEntity = userRepository.getById(entity.getIhost().getIuser());
        PhotoEntity delEntity = photoRepository.getById(entity.getIphoto());

        if (authenticationFacade.getLoginUserPk() == userEntity.getIuser()) {
            boardListRepository.deleteByIphotoAndIuser(delEntity, entity.getIhost());

            photoRepository.deleteById(entity.getIphoto());
            photoImgRepository.deleteAllByIphoto(entity.getIphoto());

            vo.setResult(1);
        }

        return vo;
    }

    @PostMapping("/photo/scrap")
    public ResultVo scrapPhoto(@RequestParam int iuser, @RequestParam int iphoto) {
        ResultVo vo = new ResultVo();
        vo.setResult(0);

        BoardListEntity entity = new BoardListEntity();
        UserEntity user = userRepository.findByIuser(iuser);
        PhotoEntity photo = photoRepository.findByIphoto(iphoto);

        if (iuser == photo.getIhost().getIuser()) {
            return vo;
        }

        entity.setIuser(user);
        entity.setIphoto(photo);
        entity.setScrap(true);

        boardListRepository.save(entity);
        photoRepository.plusScrap(iphoto);

        vo.setResult(1);

        return vo;
    }

    @GetMapping("profile")
    public UserEntity getProfile(@RequestParam int iuser) {
        return userRepository.findByIuser(iuser);
    }

    @GetMapping("/friendComment")
    public List<HomeMessageEntity> friendComment(@RequestParam int iuser) {
        List<HomeMessageEntity> list = homeService.selMessageList(iuser);
        return list;
    }

    @PostMapping("/friendComment/write")
    public ResultVo writeFriendComment(@RequestBody HomeMessageEntity entity) {
        entity.setWriter(authenticationFacade.getLoginUserPk());

        System.out.println("success!! : " + entity);

        ResultVo vo = new ResultVo();
        vo.setResult(0);

        if (homeService.selMessage(entity) == 0) {
            homeMessageRepository.save(entity);
            vo.setResult(1);
        } else {
            int updMessage = homeService.updMessage(entity);
            vo.setResult(updMessage);
        }

        return vo;
    }

    @DeleteMapping("/friendComment/del")
    public ResultVo delFriendComment(HomeMessageEntity entity) {
        ResultVo vo = new ResultVo();
        vo.setResult(0);

        HomeMessageEntity data = homeMessageRepository.getById(entity.getImsg());
        int loginUser = authenticationFacade.getLoginUserPk();

        if (data.getIhost() == loginUser || data.getWriter() == loginUser) {
            homeMessageRepository.deleteById(entity.getImsg());
            vo.setResult(1);
        }
        return vo;
    }
}
