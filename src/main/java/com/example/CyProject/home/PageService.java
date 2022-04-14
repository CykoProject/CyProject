package com.example.CyProject.home;

import com.example.CyProject.home.model.diary.DiaryEntity;
import com.example.CyProject.home.model.diary.DiaryRepository;
import com.example.CyProject.home.model.visit.VisitEntity;
import com.example.CyProject.home.model.visit.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PageService {

    @Autowired private DiaryRepository diaryRepository;
    @Autowired private VisitRepository visitRepository;

    // 다이어리 -- start --------------------------------------------------------------------------------------------------------------------------------------
    public Page<DiaryEntity> diaryPaging(int ihost, int page, int rowCnt, String rdt) {
        if("".equals(rdt) || rdt == null) {
            return diaryPaging(ihost, page, rowCnt);
        }

        PageEntity dateData = new PageEntity.DateBuilder()
                .startDate(rdt)
                .tomorrow(rdt)
                .build();
        return diaryRepository.findAllByIhostAndRdt(ihost, dateData.getStartDate(), dateData.getTomorrow(), PageRequest.of(page-1, rowCnt, Sort.by(Sort.Direction.DESC, "rdt")));
    }
    public Page<DiaryEntity> diaryPaging(int ihost, int page, int rowCnt) {
        return diaryRepository.findAllByIhost(ihost, PageRequest.of(page-1, rowCnt, Sort.by(Sort.Direction.DESC, "rdt")));
    }

    public int diaryMaxPage(int ihost, int rowCnt, String rdt) {
        if("".equals(rdt) || rdt == null) {
            return diaryMaxPage(ihost, rowCnt);
        }

        PageEntity dateData = new PageEntity.DateBuilder()
                .startDate(rdt)
                .tomorrow(rdt)
                .build();

        int maxPage = (int) Math.ceil((double) diaryRepository.countAllByIhostAndRdt(ihost, dateData.getStartDate(), dateData.getTomorrow()) / rowCnt);

        return maxPage;
    }
    public int diaryMaxPage(int ihost, int rowCnt) {
        int maxPage =(int) Math.ceil((double) diaryRepository.countAllByIhost(ihost) / rowCnt);
        return maxPage;
    }
    // 다이어리 -- finish --------------------------------------------------------------------------------------------------------------------------------------

    public Page<VisitEntity> visitPaging(int ihost, int page, int rowCnt) {
        return visitRepository.findAllByIhost(ihost, PageRequest.of(page-1, rowCnt, Sort.by(Sort.Direction.DESC, "rdt")));
    }

    public int visitMaxPage(int ihost, int rowCnt) {
        int maxPage = (int) Math.ceil((double) visitRepository.countAllByIhost(ihost) / rowCnt);
        return maxPage;
    }
}
