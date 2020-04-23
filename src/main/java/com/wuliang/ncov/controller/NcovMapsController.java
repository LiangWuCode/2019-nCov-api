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
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 数据分析
 * 伍亮
 */
@RequestMapping("/dnalysis")
@RestController
@Api(value = "/dnalysis", tags = "数据分析")
public class NcovMapsController {
    /**
     * 数据来源于腾讯
     * 每日数据分析
     *
     * @return
     */
    @GetMapping("/everyday")
    @ApiOperation("每日数据分析")
    @SentinelResource("/everyday")
    public Result everyday() {
        Object res = "";
        try {
            Document doc = Jsoup.connect("https://eyesight.news.qq.com/ncov/alldata")
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
     * 数据来源于第三方
     * 疫情地图数据
     *
     * @return
     */
    @GetMapping("/ncovMaps")
    @ApiOperation("疫情地图数据")
    @SentinelResource("/ncovMaps")
    public Result ncovMaps() {
        Object res = "";
        try {
            Document doc = Jsoup.connect("http://app.21jingji.com/html/2020yiqing/json/trend.json?19")
                    .ignoreContentType(true)
                    .userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.15)")
                    .header("content-type", "application/json")
                    .timeout(5000).get();
            res = JSONUtil.parseObj(doc.body().html());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultGenerator.genSuccessResult(res);
    }
}
