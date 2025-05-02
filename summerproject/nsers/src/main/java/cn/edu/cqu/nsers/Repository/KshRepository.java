package cn.edu.cqu.nsers.Repository;

import cn.edu.cqu.nsers.pojo.Ksh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KshRepository extends JpaRepository<Ksh, Long> {
}
