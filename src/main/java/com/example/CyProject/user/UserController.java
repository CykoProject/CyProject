

package com.example.CyProject.user;

import com.example.CyProject.ResultVo;
import com.example.CyProject.config.AuthenticationFacade;
import com.example.CyProject.home.model.home.HomeEntity;
import com.example.CyProject.home.model.home.HomeRepository;
import com.example.CyProject.shopping.model.history.purchase.PurchaseHistoryRepository;
import com.example.CyProject.user.model.UserDto;
import com.example.CyProject.user.model.UserEntity;
import com.example.CyProject.user.model.UserRepository;
import com.example.CyProject.user.model.friends.FriendsRepository;
import com.example.CyProject.user.model.points.PointHistoryEntity;
import com.example.CyProject.user.model.points.PointHistoryRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/user")
public class UserController {


    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private UserRepository userRepository;
    @Autowired private HomeRepository homeRepository;
    @Autowired private PointHistoryRepository pointHistoryRepository;
    @Autowired private PurchaseHistoryRepository purchaseHistoryRepository;
    @Autowired private AuthenticationFacade auth;
    @Autowired private UserService service;


    @GetMapping("/join")
    public String join() {
        return "user/join";
    }

    @PostMapping("/join")
    public String joinProc(UserEntity ent) {
        ent.setUpw(passwordEncoder.encode(ent.getUpw()));
        userRepository.save(ent);
        HomeEntity entity = new HomeEntity();
        int iuser = ent.getIuser();
        entity.setIuser(iuser);
        entity.setHome_nm(ent.getNm() + "??? ????????????");
        if(iuser != 0){
            homeRepository.save(entity);
        }
        return "redirect:/";
    }

    @GetMapping("/idChk/{email}")
    @ResponseBody
    public ResultVo idChk(@PathVariable String email){
        ResultVo result = new ResultVo();
        UserEntity entity = new UserEntity();
        entity.setEmail(email);
        result.setResult(userRepository.findByEmail(entity.getEmail()) == null ? 0 : 1);
        return result;
    }

    @GetMapping("/phoneChk/{cellphone}")
    @ResponseBody
    public ResultVo phoneChk(@PathVariable String cellphone){
        ResultVo result = new ResultVo();
        UserEntity entity = new UserEntity();
        entity.setCellphone(cellphone);
        result.setResult(userRepository.findByCellphone(entity.getCellphone()) == null ? 0 :1);
        return result;
    }

    @GetMapping("/find_email")
    public void find_email(){}

    @GetMapping("/find_email/{cellphone}")
    @ResponseBody
    public ResultVo find_email(@PathVariable String cellphone){
        UserEntity entity = new UserEntity();
        ResultVo resultVo = new ResultVo();
        entity.setCellphone(cellphone);
        UserEntity userEmail = userRepository.findByCellphone(entity.getCellphone());
        resultVo.setResultString(userEmail != null ? userEmail.getEmail() : null);
        return resultVo;
    }

    @GetMapping("/find_email_result")
    public void find_email_result(){}

    @GetMapping("/find_upw")
    public String find_upw(){
        return "/user/find_upw";
    }

//    @ResponseBody
//    @PostMapping("/find_upw")
//    public ResultVo ajaxFindUpw(@RequestBody String email) {
//        ResultVo vo = new ResultVo();
//        vo.setResultString(email);
//
//        return vo;
//    }

    @ResponseBody
    @PostMapping("/find_upw")
    public ResultVo changeUpw(@RequestBody UserEntity entity) {
        ResultVo vo = new ResultVo();
        String secureUpw = passwordEncoder.encode(entity.getUpw());
        int status = userRepository.updUserUpw(secureUpw, entity.getEmail());
        vo.setResult(status);
        return vo;
    }

    @GetMapping("/mypage")
    public String mypage(Model model) {
        if(auth.getLoginUserPk() == 0 || auth.getLoginUser() ==  null) {
            return "redirect:/";
        }
        model.addAttribute("loginUser", auth.getLoginUser());
        return "user/mypage";
    }

    @GetMapping("/charge")
    public String charge(Model model) {
        if(auth.getLoginUserPk() != 0) {
            int hasPoint = userRepository.findByIuser(auth.getLoginUserPk()).getPoint();
            List<PointHistoryEntity> history = pointHistoryRepository.findByIuserOrderByRdtDesc(auth.getLoginUserPk());
            model.addAttribute("dotori", hasPoint);
            model.addAttribute("history", history);
            return "/user/charge";
        }
        return "redirect:/";
    }

    @PostMapping("/charge")
    public void charge(@RequestParam int money, LocalDateTime rdt) {
        int hasPoint = userRepository.findByIuser(auth.getLoginUserPk()).getPoint();
        int dotori = (money / 100) + hasPoint;
        userRepository.updDotori(dotori, auth.getLoginUserPk());

        PointHistoryEntity entity = new PointHistoryEntity();
        entity.setIuser(auth.getLoginUserPk());
        entity.setChanged_point(money / 100);
        entity.setReason("????????? ??????");
        entity.setRdt(rdt);
        pointHistoryRepository.save(entity);
    }

    @GetMapping("/shopHistory")
    public String shopHistory(Model model) {
        model.addAttribute("purchase", purchaseHistoryRepository.purchaseItemList(auth.getLoginUser()));
        return "user/shopHistory";
    }

    @GetMapping("/change_upw")
    public String change_upw(Model model) {
        model.addAttribute("loginUser", auth.getLoginUser());
        return "user/change_upw";
    }

    @PostMapping("/change_upw")
    public String change_upw(String newUpw) {
        String secureUpw = passwordEncoder.encode(newUpw);
        Optional<UserEntity> user = userRepository.findById(auth.getLoginUserPk());
        user.ifPresent(selectUser -> {
            selectUser.setUpw(secureUpw);
            userRepository.save(selectUser);
        });
        return "redirect:/";
    }

    @GetMapping("/pwChk/{oldUpw}")
    @ResponseBody
    public ResultVo pwChk(@PathVariable String oldUpw) {
        ResultVo vo = new ResultVo();
        String upw = auth.getLoginUser().getUpw();
        vo.setResult(passwordEncoder.matches(oldUpw, upw) ? 1 : 0);
        return vo;
    }
}
