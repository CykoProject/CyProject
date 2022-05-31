package com.example.CyProject.main;

import com.example.CyProject.home.model.diary.DiaryRepository;
import com.example.CyProject.home.model.photo.PhotoInterface;
import com.example.CyProject.home.model.photo.PhotoRepository;
import com.example.CyProject.home.model.profile.ProfileRepository;
import com.example.CyProject.main.model.SearchEntity;
import com.example.CyProject.main.model.top.TopVo;
import com.example.CyProject.user.model.UserEntity;
import com.example.CyProject.user.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MainService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    DiaryRepository diaryRepository;
    @Autowired
    PhotoRepository photoRepository;
    @Autowired
    ProfileRepository profileRepository;

    public List<UserEntity> searchUsers(String search, Pageable pageable) {
        return userRepository.findByNmContains(search, pageable);
    }

    public int searchUsersCount(String search) {
        return userRepository.findByNmContains(search).size();
    }

    public SearchEntity search(String search) {

        SearchEntity entity = new SearchEntity();

        entity.setProfile(profileRepository.searchProfile(search));
        entity.setPhoto(photoRepository.searchPhoto(search));
        entity.setDiary(diaryRepository.searchDiary(search));

        return entity;
    }

    public List<TopVo> photoTop() {

        List<TopVo> topList = new ArrayList<>();
        List<PhotoInterface> list = photoRepository.countPhotoByUser();

        for (int i = 0; i < list.size(); i++) {
            TopVo vo = new TopVo();
            vo.setCnt(photoRepository.countPhotoByUser().get(i).getCnt());

            topList.add(vo);
        }
        return topList;
    }

    public List<TopVo> diaryTop() {
        List<TopVo> topList = new ArrayList<>();
        List<PhotoInterface> list = diaryRepository.countDiaryByUser();

        for (int i = 0; i < list.size(); i++) {
            TopVo vo = new TopVo();
            vo.setCnt(diaryRepository.countDiaryByUser().get(i).getCnt());

            topList.add(vo);
        }
        return topList;
    }


    public List<TopVo> sumTop() {
        Map<Integer, Integer> topMap = new HashMap<>();
        List<TopVo> topList = new ArrayList<>();
        List<PhotoInterface> list = diaryRepository.countDiaryByUser();
        for(PhotoInterface item : list) {
            topMap.put(item.getIuser(), item.getCnt());
        }
        System.out.println(topMap);
        /*
            1, 2
            3, 2
         */
        List<PhotoInterface> list2 = photoRepository.countPhotoByUser();
        for(PhotoInterface item : list2) {
            topMap.put(item.getIuser(), item.getCnt() + topMap.getOrDefault(item.getIuser(), 0));
        }
        System.out.println(topMap);
        /*
            1, 2
            3, 2
         */

        for(Integer key : topMap.keySet()) {
            TopVo vo = new TopVo();
            vo.setIuser(key);
            vo.setCnt(topMap.get(key));
            vo.setImg(userRepository.findByIuser(key).getProfile_img());
            vo.setEmail(userRepository.findByIuser(key).getEmail());
            vo.setNm(userRepository.findByIuser(key).getNm());
            topList.add(vo);
        }
        TopVo temp = null;
        for (int i = 0; i < topList.size(); i++) {
            for (int j=i+1; j < topList.size(); j++) {
                if (topList.get(i).getCnt() < topList.get(j).getCnt()) {
                    temp = topList.get(i);
                    topList.remove(i);
                    topList.add(temp);
                }
            }
        }
        System.out.println(topList);

//        List<Map.Entry<Integer, Integer>> listEntry = new ArrayList<Map.Entry<Integer, Integer>>(topMap.entrySet());
//        Collections.sort(listEntry, new Comparator<Map.Entry<Integer, Integer>>() {
//            @Override
//            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
//                return o2.getValue().compareTo(o1.getValue());
//            }
//        });
//
//        List<TopVo> result = new ArrayList<>();
//        for(Map.Entry<Integer, Integer> entry : listEntry) {
//            for(TopVo item : topList) {
//                if(entry.getKey() == item.getIuser()) {
//                    result.add(item);
//                }
//            }
//        }

        return topList;
    }
}