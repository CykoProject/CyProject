package com.example.CyProject.home.model.visitor;

import com.example.CyProject.user.model.UserEntity;
import com.example.CyProject.user.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
public class VisitorService {
    @Autowired private VisitorRepository visitorRepository;
    @Autowired private UserRepository userRepository;

    public int saveVisitor(int loginUser, int ihomePk) {
        int result = 0;
        VisitorPk visitorPk = new VisitorPk();
        UserEntity user = new UserEntity();
        user.setIuser(loginUser);
        visitorPk.setIhome(ihomePk);
        visitorPk.setIuser(user);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        String rdt = simpleDateFormat.format(calendar.getTime());
        visitorPk.setRdt(rdt);
        VisitorEntity visitorEntity = new VisitorEntity();
        visitorEntity.setVisitorPk(visitorPk);
        VisitorEntity entity = visitorRepository.save(visitorEntity);
        if (entity != null) {
            result++;
        }

        return result;
    }
}
