package com.example.CyProject;

import com.example.CyProject.home.HomeCategory;
import com.example.CyProject.home.model.diary.DiaryEntity;
import com.example.CyProject.home.model.home.HomeEntity;
import com.example.CyProject.home.model.home.HomeRepository;
import com.example.CyProject.home.model.visit.VisitEntity;
import com.example.CyProject.user.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Utils {

    @Autowired
    private HomeRepository homeRepository;

    public int getCommentCategory(String category) {
        int result = 0;
        switch (category) {
            case "diary":
                result = HomeCategory.DIARY.getCategory();
                break;
            case "visit":
                result = HomeCategory.VISIT.getCategory();
                break;
            case "photo":
                result = HomeCategory.PHOTOS.getCategory();
        }

        return result;
    }

    public int findHomePk(int iuser) {
        int homePk = 0;
        try {
            homePk = homeRepository.findByIuser(iuser).getIhome();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return homePk;
    }

    public int getParseIntParameter(String param) {
        int idx = 0;
        try {
            idx = Integer.parseInt(param);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return idx;
    }

    // 프로필 용도
    public Map<String, Object> makeStringNewLine(Object entity) {
        Map<String, Object> result = new HashMap<>();

        try {
            for (Field field : entity.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                String nm = field.getName();
                Object value = field.get(entity);
                if ("profile_ctnt".equals(nm)) {
                    value = value.toString().replaceAll("\r\n", "<br>");
                }
                result.put(nm, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


//         =============== 작동 테스트 용 =============
//        for(Object list : objList) {
//            System.out.println(list.toString());
//        }
//         ==========================================

        return result;
    }

    // 사진첩 리스트
    public List<Object> makeStringNewLineForPhoto(List<?> entity) {
        /*
         * 리턴 값 List<Object>
         */
        ArrayList<Object> objList = new ArrayList<>();
        Map<String, Object> result = null;
        Map<String, Object> result2 = null;
        for (Object list : entity) {
            result = new HashMap<>();
            try {
                for (Field field : list.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    String nm = field.getName();
                    Object value = field.get(list);

                    result2 = new HashMap<>();
                    for (Field inField : value.getClass().getDeclaredFields()) {
                        inField.setAccessible(true);
                        String fieldNm = inField.getName();
                        Object fieldValue = inField.get(value);

                        if ("ctnt".equals(fieldNm)) {
                            fieldValue = fieldValue.toString().replaceAll("\r\n", "<br>");
                        }
                        result2.put(fieldNm, fieldValue);
                    }

                    if ("scrap".equals(nm)) {
                        result.put(nm, value);
                    } else {
                        result.put(nm, result2);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(objList);
            objList.add(result);
        }
//         =============== 작동 테스트 용 =============
//        for(Object list : objList) {
//            System.out.println(list.toString());
//        }
//         ==========================================

        return objList;
    }

    public List<Object> makeStringNewLine(List<?> entity) {
        /*
         * 리턴 값 List<Object>
         */
        ArrayList<Object> objList = new ArrayList<>();
        Map<String, Object> result = null;
        for (Object list : entity) {
            result = new HashMap<>();
            try {
                for (Field field : list.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    String nm = field.getName();
                    Object value = field.get(list);
                    if ("ctnt".equals(nm)) {
                        value = value.toString().replaceAll("\r\n", "<br>");
                    }
                    result.put(nm, value);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            objList.add(result);
        }
//         =============== 작동 테스트 용 =============
//        for(Object list : objList) {
//            System.out.println(list.toString());
//        }
//         ==========================================

        return objList;
    }

    public List<Object> makeStringNewLine(Page<?> entity) {
        /*
         * 리턴 값 List<Object>
         */
        ArrayList<Object> objList = new ArrayList<>();
        Map<String, Object> result = null;
        for (Object list : entity) {
            result = new HashMap<>();
            try {
                for (Field field : list.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    String nm = field.getName();
                    Object value = field.get(list);
                    if ("ctnt".equals(nm)) {
                        value = value.toString().replaceAll("\r\n", "<br>");
                    }
                    result.put(nm, value);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            objList.add(result);
        }
//         =============== 작동 테스트 용 =============
//        for(Object list : objList) {
//            System.out.println(list.toString());
//        }
//         ==========================================

        return objList;
    }


}