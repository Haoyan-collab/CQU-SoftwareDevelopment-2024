package cn.edu.cqu.nsers.Service;

import cn.edu.cqu.nsers.Repository.KshRepository;
import cn.edu.cqu.nsers.pojo.Ksh;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KshService {
    @Autowired
    private KshRepository kshRepository;

    public List<Ksh> getAllInfo() {
        return kshRepository.findAll();
    }

    public Ksh saveOrUpdate(Ksh ksh) {
        return kshRepository.save(ksh);
    }
}
