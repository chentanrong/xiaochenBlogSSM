package com.ylt.dao;

import com.ylt.entity.Img;

import java.util.List;

public interface Imgdao {
    public Integer addImg(Img img);
    public List<Img> getImgByHost(String hostId);
    public Img getCurrentImgByHost(String hostId);
    public List<Img> getImgByUser(String userId);
}
