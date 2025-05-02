package cn.edu.cqu.nsers.pojo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "ksh")
@ApiModel(value = "可视化大屏")
public class Ksh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer count = 1;
    private String dorm_name;
    private String sgender;
    private String smajor;
    private String sprovince;
    private String sname;
    private Integer sid;
}