package com.lyc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyc.model.po.Carousel;
import com.lyc.model.vo.CarouselBackResp;
import com.lyc.model.vo.CarouselQuery;
import com.lyc.model.vo.CarouselResp;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 轮播图Mapper
 *
 * @author ican
 */
@Repository
public interface CarouselMapper extends BaseMapper<Carousel> {

    /**
     * 查询后台轮播图列表
     *
     * @param carouselQuery 轮播图查询条件
     * @return 后台轮播图列表
     */
    List<CarouselBackResp> selectBackCarouselList(@Param("param") CarouselQuery carouselQuery);

    /**
     * 查看轮播图列表
     *
     * @return 轮播图列表
     */
    List<CarouselResp> selectCarouselList();

}




