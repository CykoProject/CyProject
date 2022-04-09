package com.example.CyProject;

import com.example.CyProject.home.model.diary.DiaryEntity;
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
    public int getParseIntParameter(String param) {
        int idx = 0;
        try {
            idx = Integer.parseInt(param);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return idx;
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
                    if("ctnt".equals(nm)) {
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
                    if("ctnt".equals(nm)) {
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
