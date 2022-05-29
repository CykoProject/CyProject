package com.example.CyProject.common;

import com.example.CyProject.home.model.photo.PhotoImgEntity;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class MyFileUtils {
    @Value("${spring.servlet.multipart.location}")
    private String uploadImagePath;

    //폴더 만들기
    public String makeFolders(String path) {
        File folder = new File(uploadImagePath, path);
        folder.mkdirs();
        return folder.getAbsolutePath();
    }

    //랜덤 파일명 만들기
    public String getRandomFileNm() {
        return UUID.randomUUID().toString();
    }

    //랜덤 파일명 만들기 (with 확장자)
    public String getRandomFileNm(String originFileNm) {
        return getRandomFileNm() + getExt(originFileNm);
    }

    //랜덤 파일명 만들기
    public String getRandomFileNm(MultipartFile file) {
        return getRandomFileNm(file.getOriginalFilename());
    }

    //확장자 얻기               "aaa.jpg"
    public String getExt(String fileNm) {
        return fileNm.substring(fileNm.lastIndexOf("."));
    }

    public String transferTo(MultipartFile mf, String target) {
        String fileNm = getRandomFileNm(mf); // "aslkdfjaslkf2130asdwds.jpg"
        String basePath = makeFolders(target); // (폴더가 없을 수 있기 때문에)폴더를 만들어준다.
        File saveFile = new File(basePath, fileNm);
        try {
            mf.transferTo(saveFile);
            return fileNm;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    // New File Upload -----------------------

    private final String getRandomString() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public List<PhotoImgEntity> uploadFiles(List<MultipartFile> files, int iphoto) {

        /* 업로드 파일 정보를 담을 비어있는 리스트 */
        List<PhotoImgEntity> attachList = new ArrayList<>();

        /* uploadPath에 해당하는 디렉터리가 존재하지 않으면, 부모 디렉터리를 포함한 모든 디렉터리를 생성 */
        File dir = new File(uploadImagePath + "/" + iphoto);
        if (dir.exists() == false) {
            dir.mkdirs();
        }

        /* 파일 개수만큼 forEach 실행 */
        for (MultipartFile file : files) {
            if(file.getSize() < 1) {
                continue;
            }
            try {
                /* 파일 확장자 */
                final String extension = FilenameUtils.getExtension(file.getOriginalFilename());
                /* 서버에 저장할 파일명 (랜덤 문자열 + 확장자) */
                final String saveName = getRandomString() + "." + extension;

                /* 업로드 경로에 saveName과 동일한 이름을 가진 파일 생성 */
                File target = new File(uploadImagePath + "/" + iphoto, saveName);
                file.transferTo(target);

                /* 파일 정보 저장 */
                PhotoImgEntity attach = new PhotoImgEntity();
                attach.setIphoto(iphoto);
                attach.setImg(saveName);

                /* 파일 정보 추가 */
                attachList.add(attach);

            } catch (IOException e) {
                throw new AttachFileException("[" + file.getOriginalFilename() + "] failed to save file...");

            } catch (Exception e) {
                throw new AttachFileException("[" + file.getOriginalFilename() + "] failed to save file...");
            }
        } // end of for

        return attachList;
    }
}
