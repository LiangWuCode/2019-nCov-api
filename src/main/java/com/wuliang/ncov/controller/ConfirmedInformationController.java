package com.wuliang.ncov.controller;

import cn.hutool.json.JSONUtil;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.fastjson.JSON;
import com.wuliang.ncov.core.ResponseMode.Result;
import com.wuliang.ncov.core.ResponseMode.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 确诊信息
 * 伍亮
 */
@RequestMapping("/confirmedInformation")
@RestController
@Api(value = "/confirmedInformation", tags = "确诊信息")
public class ConfirmedInformationController {

    /**
     * 数据来源于夸克
     * 根据省市名称，获取当前市确诊人员信息
     *
     * @return
     */
    @GetMapping("/getGuideListByProvinceNameAndCityName")
    @ApiOperation("根据省市名称，获取当前市确诊人员信息")
    @SentinelResource("/getGuideListByProvinceNameAndCityName")
    public Result getGuideListByProvinceNameAndCityName(@RequestParam("provinceName") String provinceName, @RequestParam("cityName") String cityName, @RequestParam("page") Integer page) {
        Object res = "";
        try {
            Document doc = Jsoup.connect("https://m.sm.cn/api/rest?format=json&from=&method=Maskbuy.areaData&prov=" + provinceName + "&city=" + cityName + "&page=" + page)
                    .ignoreContentType(true)
                    .userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.15)")
                    .header("content-type", "application/json")
                    .timeout(5000).get();
            res = JSONUtil.parseObj(doc.body().html()).get("data");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultGenerator.genSuccessResult(JSON.parse(res.toString()));
    }

    /**
     * 数据来源于腾讯
     * 根据省名称，获取确诊人员信息
     *
     * @return
     */
    @GetMapping("/getGuideListByProvinceName")
    @ApiOperation("根据省名称，获取确诊人员信息")
    @SentinelResource("/getGuideListByProvinceName")
    public Result getGuideList(@RequestParam("provinceName") String provinceName, @RequestParam("num") Integer num, @RequestParam("page") Integer page) {
        Object res = "";
        try {
            Document doc = Jsoup.connect("https://pacaio.match.qq.com/virus/trackList?province=" + provinceName + "&num=" + num + "&page=" + page)
                    .ignoreContentType(true)
                    .userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.15)")
                    .header("content-type", "application/json")
                    .timeout(5000).get();
            res = JSONUtil.parseObj(doc.body().html()).get("data");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultGenerator.genSuccessResult(JSON.parse(res.toString()));
    }
}
