package com.lyc.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.lyc.model.common.PageResult;
import com.lyc.model.common.Result;
import com.lyc.model.dto.CarouselReqVo;
import com.lyc.model.dto.CarouselStatusReq;
import com.lyc.model.vo.CarouselBackResp;
import com.lyc.model.vo.CarouselQuery;
import com.lyc.model.vo.CarouselResp;
import com.lyc.service.CarouselService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.lyc.constant.enums.StatusCodeEnum.SUCCESS;

/**
 * 轮播图控制器
 *
 * @author ican
 * @date 2024/02/03 12:02
 **/
@Api(tags = "轮播图模块")
@RestController
public class CarouselController {

    @Autowired
    private CarouselService carouselService;

    /**
     * 查看轮播图列表
     *
     * @return 轮播图列表
     */
    @ApiOperation(value = "查看轮播图列表")
    @GetMapping("/carousel/list")
    public Result<List<CarouselResp>> getCarouselList() {
        return Result.success(carouselService.getCarouselList());
    }

    /**
     * 查看轮播图列表
     *
     * @param carouselQuery 查询条件
     * @return {@link PageResult<CarouselBackResp>} 轮播图列表
     */
    @ApiOperation(value = "查看轮播图列表")
    @SaCheckPermission("web:carousel:list")
    @GetMapping("/admin/carousel/list")
    public Result<PageResult<CarouselBackResp>> getCarouselVOList(CarouselQuery carouselQuery) {
        return Result.success(carouselService.getCarouselVOList(carouselQuery));
    }

    /**
     * 上传轮播图片
     *
     * @param file 文件
     * @return {@link Result<String>} 图片地址
     */
    @ApiOperation(value = "上传轮播图片")
    @ApiImplicitParam(name = "file", value = "轮播图片", required = true, dataType = "MultipartFile")
    @SaCheckPermission("web:carousel:upload")
    @PostMapping("/admin/carousel/upload")
    public Result<String> uploadCarousel(@RequestParam("file") MultipartFile file) {
        return Result.success(carouselService.uploadCarousel(file));
    }

    /**
     * 添加轮播图
     *
     * @param carouselReqVo 轮播图信息
     * @return {@link Result<>}
     */
    @ApiOperation(value = "添加轮播图")
    @SaCheckPermission("web:carousel:add")
    @PostMapping("/admin/carousel/add")
    public Result<?> addCarousel(@Validated @RequestBody CarouselReqVo carouselReqVo) {
        carouselService.addCarousel(carouselReqVo);
        return Result.success(null,SUCCESS.getCode(),SUCCESS.getMsg());
    }

    /**
     * 修改轮播图
     *
     * @param carouselReqVo 轮播图信息
     * @return {@link Result<>}
     */
    @ApiOperation(value = "修改轮播图")
    @SaCheckPermission("web:carousel:update")
    @PostMapping("/admin/carousel/update")
    public Result<?> updateCarousel(@Validated @RequestBody CarouselReqVo carouselReqVo) {
        carouselService.updateCarousel(carouselReqVo);
        return Result.success(null,SUCCESS.getCode(),SUCCESS.getMsg());
    }

    /**
     * 删除轮播图
     *
     * @param carouselIdList 轮播图id集合
     * @return {@link Result<>}
     */
    @ApiOperation(value = "删除轮播图")
    @SaCheckPermission("web:carousel:delete")
    @DeleteMapping("/admin/carousel/delete")
    public Result<?> deleteCarousel(@RequestBody List<Integer> carouselIdList) {
        carouselService.removeByIds(carouselIdList);
        return Result.success(null,SUCCESS.getCode(),SUCCESS.getMsg());
    }

    /**
     * 修改轮播图状态
     *
     * @param carouselStatusReq 轮播图状态
     * @return {@link Result<>}
     */
    @ApiOperation(value = "修改轮播图状态")
    @SaCheckPermission("web:carousel:status")
    @PutMapping("/admin/carousel/status")
    public Result<?> updateCarouselStatus(@Validated @RequestBody CarouselStatusReq carouselStatusReq) {
        carouselService.updateCarouselStatus(carouselStatusReq);
        return Result.success(null,SUCCESS.getCode(),SUCCESS.getMsg());
    }

}