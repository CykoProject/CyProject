package com.example.CyProject.home;

import com.example.CyProject.ResultVo;
import com.example.CyProject.Utils;
import com.example.CyProject.common.MyFileUtils;
import com.example.CyProject.config.AuthenticationFacade;
import com.example.CyProject.home.model.diary.DiaryEntity;
import com.example.CyProject.home.model.diary.DiaryRepository;
import com.example.CyProject.home.model.home.HomeEntity;
import com.example.CyProject.home.model.home.HomeMessageEntity;
import com.example.CyProject.home.model.home.HomeMessageRepository;
import com.example.CyProject.home.model.home.HomeRepository;
import com.example.CyProject.home.model.photo.PhotoEntity;
import com.example.CyProject.home.model.photo.PhotoImgEntity;
import com.example.CyProject.home.model.photo.PhotoImgRepository;
import com.example.CyProject.home.model.photo.PhotoRepository;
import com.example.CyProject.home.model.visit.VisitDto;
import com.example.CyProject.home.model.visit.VisitEntity;
import com.example.CyProject.home.model.visit.VisitRepository;
import com.example.CyProject.user.model.UserEntity;
import com.example.CyProject.user.model.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/ajax/home")
public class HomeRestController {

    @Autowired private HomeRepository homeRepository;
    @Autowired private DiaryRepository diaryRepository;
    @Autowired private VisitRepository visitRepository;
    @Autowired private AuthenticationFacade authenticationFacade;
    @Autowired private Utils utils;
    @Autowired private HomeService homeService;
    @Autowired private MyFileUtils myFileUtils;
    @Autowired private HomeMessageRepository homeMessageRepository;
    @Autowired private PhotoImgRepository photoImgRepository;
    @Autowired private UserRepository userRepository;

    @GetMapping
    public HomeEntity home(HomeEntity entity) {
        return homeRepository.findByIuser(entity.getIuser());
    }

    @PutMapping("/nm/mod")
    public ResultVo modHomeNm(@RequestBody HomeEntity entity) {
        // TODO : home 테이블 `home_nm` 추가
        ResultVo vo = new ResultVo();
        vo.setResult(0);

        homeRepository.updHomeNm(entity.getHome_nm(), entity.getIhome());
        vo.setResult(1);

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
        System.out.println(preCtnt);
        System.out.println(result);
        if(!preCtnt.equals(result.getCtnt())) {
            vo.setResult(1);
        }
        return vo;
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
        // TODO : home_message 테이블 `rdt` 추가

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
