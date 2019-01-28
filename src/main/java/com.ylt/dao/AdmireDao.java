package com.ylt.dao;

import com.ylt.common.Page;
import com.ylt.entity.Admire;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdmireDao {
    public Integer addAdmire(Admire img);
    public Integer deleteAdmire(@Param("admiredId") String admiredId,@Param("userId") String userId );
    public String getAdmireNumberByAdmiredId(@Param("admiredId") String admiredId,@Param("userId") String userId);
    public String getAdmireNumber(@Param("admiredId") String admiredId,@Param("userId") String userId,@Param("type") String type);
    public String getAdmireNumberByAdmire(@Param("admiredId") String admiredId);

}
