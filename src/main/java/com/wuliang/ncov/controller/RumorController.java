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
 * 谣言
 * 伍亮
 */
@RequestMapping("/rumor")
@RestController
@Api(value = "/rumor", tags = "谣言")
public class RumorController {
    /**
     * 数据来源于腾讯
     * 谣言数据列表
     *
     * @return
     */
    @GetMapping("/getRumorList")
    @ApiOperation("谣言数据列表")
    @SentinelResource("/getRumorList")
    public Result getRumorList(@RequestParam("page") Integer page) {
        Object res="";
        try {
            Document doc = Jsoup.connect("https://vp.fact.qq.com/loadmore?page="+page)
                    .ignoreContentType(true)
                    .userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.15)")
                    .header("content-type", "application/json")
                    .timeout(5000).get();
            res = JSONUtil.parseObj(doc.body().html()).get("content");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultGenerator.genSuccessResult(JSON.parse(res.toString()));
    }

    /**
     * 数据来源于腾讯
     * 谣言数据列表
     *
     * @return
     */
    @GetMapping("/getRumorDetail")
    @ApiOperation("根据谣言id获取谣言详情")
    @SentinelResource("/getRumorDetail")
    public Result getRumorDetail(@RequestParam("id") String id) {
        Object res="";
        try {
            Document doc = Jsoup.connect("https://vp.fact.qq.com/miniArtData?id="+id)
                    .ignoreContentType(true)
                    .userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.15)")
                    .header("content-type", "application/json")
                    .timeout(5000).get();
            res = JSONUtil.parseObj(doc.body().html()).get("content");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultGenerator.genSuccessResult(JSON.parse(res.toString()));
    }
}
