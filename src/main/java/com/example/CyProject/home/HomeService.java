package com.example.CyProject.home;

import com.example.CyProject.home.model.visit.VisitEntity;
import com.example.CyProject.home.model.visit.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class HomeService {

    @Autowired private VisitRepository visitRepository;
    public Page<VisitEntity> visitPaging(int ihost, int page, int rowCnt) {
        return visitRepository.findAllByIhost(ihost, PageRequest.of(page-1, rowCnt, Sort.by(Sort.Direction.DESC, "rdt")));
    }

    public int visitMaxPage(int ihost, int rowCnt) {
        int maxPage = (int) Math.ceil((double) visitRepository.countAllByIhost(ihost) / rowCnt);
        return maxPage;
    }
}
