package com.wuliang.ncov.controller;

import cn.hutool.core.text.UnicodeUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
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
 * 数据统计接口汇总
 * 伍亮
 */
@RequestMapping("/statistics")
@RestController
@Api(value = "/statistics", tags = "数据统计")
public class StatisticsController {
    /**
     * 数据来源于腾讯
     * 数据统计
     *
     * @return
     */
    @GetMapping("/totalDataOne")
    @ApiOperation("数据统计1")
    @SentinelResource("/totalDataOne")
    public Result totalDataOne() {
        Object res = "";
        try {
            Document doc = Jsoup.connect("https://view.inews.qq.com/g2/getOnsInfo?name=disease_h5")
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
     * 数据改变列表
     *
     * @return
     */
    @GetMapping("/dataChangeList")
    @ApiOperation("数据改变列表")
    @SentinelResource("/dataChangeList")
    public Result dataChangeList() {
        Object res = "";
        try {
            Document doc = Jsoup.connect("https://view.inews.qq.com/g2/getOnsInfo?name=disease_other")
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
     * 数据来源于夸克
     * 数据统计
     *
     * @return
     */
    @GetMapping("/totalDataTwo")
    @ApiOperation("数据统计2")
    @SentinelResource("/totalDataTwo")
    public Result totalDataTwo() {
        Object res = "";
        try {
            Document doc = Jsoup.connect("https://api.m.sm.cn/rest?format=json&method=Huoshenshan.healingCity&mapType=1")
                    .ignoreContentType(true)
                    .userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.15)")
                    .header("content-type", "application/json")
                    .timeout(5000).get();
            res = JSONUtil.parseObj(doc.body().html());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultGenerator.genSuccessResult(JSON.parse(res.toString()));
    }


    /**
     * 数据来源于夸克
     * 根据省的名称获取该省的历史数据
     *
     * @return
     */
    @GetMapping("/getCountyList")
    @ApiOperation("根据市级名称获取县区级数据")
    @SentinelResource("/getCountyList")
    public Result getCountyList(@RequestParam("cityName") String cityName) {
        String result2 = HttpUtil.get("https://m.sm.cn/api/rest?method=yiqing.getPlaceInfo&uc_param_str=gi&city=" + cityName, CharsetUtil.CHARSET_UTF_8);
        return ResultGenerator.genSuccessResult(JSONUtil.parse(UnicodeUtil.toString(result2)));
    }

    /**
     * 数据来源于腾讯
     * 根据省的名称获取该省的历史数据
     *
     * @return
     */
    @GetMapping("/getProvinceHistoryList")
    @ApiOperation("根据省的名称获取该省的历史数据")
    @SentinelResource("/getProvinceHistoryList")
    public Result getProvinceHistoryList(@RequestParam("provinceName") String provinceName) {
        Object res = "";
        try {
            Document doc = Jsoup.connect("https://api.inews.qq.com/newsqa/v1/query/pubished/daily/list?province=" + provinceName)
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
     * 根据省市名称获取当前市区历史数据
     *
     * @return
     */
    @GetMapping("/getCityHistoryList")
    @ApiOperation("根据省市名称获取当前市区历史数据")
    @SentinelResource("/getCityHistoryList")
    public Result getCityHistoryList(@RequestParam("provinceName") String provinceName, @RequestParam("cityName") String cityName) {
        Object res = "";
        try {
            Document doc = Jsoup.connect("https://api.inews.qq.com/newsqa/v1/query/pubished/daily/list?province=" + provinceName + "&city=" + cityName)
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
     * 数据统计（丁香园）
     *
     * @return
     */
    @GetMapping("/totalDataThree")
    @ApiOperation("数据统计3")
    @SentinelResource("/totalDataThree")
    public Result totalDataThree() {
        String res = "";
        JSONObject json = JSONUtil.createObj();
        try {
            Document doc = Jsoup.connect("https://ncov.dxy.cn/ncovh5/view/pneumonia").get();
            //国际数据统计
            String newsHeadlines = doc.select("#getListByCountryTypeService2true").html();
            newsHeadlines = StrUtil.removePrefix(newsHeadlines, "try { window.getListByCountryTypeService2true =");
            newsHeadlines = StrUtil.removeSuffix(newsHeadlines, "}catch(e){}");
            json.put("foreign", JSONUtil.parse(newsHeadlines));

            //国内数据统计
            String newsHeadlines1 = doc.select("#getAreaStat").html();
            newsHeadlines1 = StrUtil.removePrefix(newsHeadlines1, "try { window.getAreaStat =");
            newsHeadlines1 = StrUtil.removeSuffix(newsHeadlines1, "}catch(e){}");
            json.put("china", JSONUtil.parse(newsHeadlines1));

            //国内新增数据
            String newsHeadlines2 = doc.select("#getTimelineService2").html();
            newsHeadlines2 = StrUtil.removePrefix(newsHeadlines2, "try { window.getTimelineService2 =");
            newsHeadlines2 = StrUtil.removeSuffix(newsHeadlines2, "}catch(e){}");
            json.put("enForeign", JSONUtil.parse(newsHeadlines2));

            //疫情说明
            String newsHeadlines3 = doc.select("#getStatisticsService").html();
            newsHeadlines3 = StrUtil.removePrefix(newsHeadlines3, "try { window.getStatisticsService =");
            newsHeadlines3 = StrUtil.removeSuffix(newsHeadlines3, "}catch(e){}");
            json.put("desc", JSONUtil.parse(newsHeadlines3));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultGenerator.genSuccessResult(json);
    }


    /**
     * 数据统计（丁香园）
     *
     * @return
     */
    @GetMapping("/flutterAppForMyself")
    @ApiOperation("自己开发的flutterApp使用")
    @SentinelResource("/flutterAppForMyself")
    public Result flutterAppForMyself() {
        String res = "";
        JSONObject json = JSONUtil.createObj();
        try {
            Document doc = Jsoup.connect("https://ncov.dxy.cn/ncovh5/view/pneumonia").get();
            //疫情说明
            String newsHeadlines3 = doc.select("#getStatisticsService").html();
            newsHeadlines3 = StrUtil.removePrefix(newsHeadlines3, "try { window.getStatisticsService =");
            newsHeadlines3 = StrUtil.removeSuffix(newsHeadlines3, "}catch(e){}");
            json.put("desc", JSONUtil.parse(newsHeadlines3));

//            //国内新增数据
//            String newsHeadlines2 = doc.select("#getListByCountryTypeService1undefined").html();
//            newsHeadlines2 = StrUtil.removePrefix(newsHeadlines2, "try { window.getListByCountryTypeService1undefined =");
//            newsHeadlines2 = StrUtil.removeSuffix(newsHeadlines2, "}catch(e){}");
//            json.put("chinaAdd", JSONUtil.parse(newsHeadlines2));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultGenerator.genSuccessResult(json);
    }
}
