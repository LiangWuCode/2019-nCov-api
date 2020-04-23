package com.wuliang.ncov.controller;

import cn.hutool.core.text.UnicodeUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
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
 * 新闻数据
 * 伍亮
 */
@RequestMapping("/news")
@RestController
@Api(value = "/news", tags = "新闻数据")
public class NewsController {
    /**
     * 数据来源于夸克
     * 根据市的名称获取该市的新闻信息
     *
     * @return
     */
    @GetMapping("/getCityNewsList")
    @ApiOperation("根据市的名称获取该市的新闻信息")
    @SentinelResource("/getCityNewsList")
    public Result getCityNewsList(@RequestParam("cityName") String cityName) {
        String result2 = HttpUtil.get("https://m.sm.cn/api/rest?method=yiqing.getZixun&uc_param_str=gi&city=" + cityName, CharsetUtil.CHARSET_UTF_8);
        return ResultGenerator.genSuccessResult(JSONUtil.parse(UnicodeUtil.toString(result2)));
    }

    /**
     * 数据来源于腾讯
     * 关于疫情百科知识 （检查诊断）
     *
     * @return
     */
    @GetMapping("/getDiagnoseList")
    @ApiOperation("关于疫情百科知识 （检查诊断）")
    @SentinelResource("/getDiagnoseList")
    public Result getDiagnoseList(@RequestParam("count") Integer count) {
        String res = "";
        try {
            Document doc = Jsoup.connect("https://h5.baike.qq.com/api/jsonp/GetDocsByTag?callback=data&appid=2000000000000050&adtag=txxw.op.fybox&name=94700&count=" + count)
                    .ignoreContentType(true)
                    .userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.15)")
                    .header("content-type", "application/json")
                    .timeout(5000).get();
            String newsHeadlines = StrUtil.removePrefix(doc.body().html(), "data(");
            newsHeadlines = StrUtil.removeSuffix(newsHeadlines, ")");
            res = newsHeadlines;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultGenerator.genSuccessResult(JSON.parse(res));
    }


    /**
     * 数据来源于腾讯
     * 关于疫情百科知识 （预防指南）
     *
     * @return
     */
    @GetMapping("/getGuideList")
    @ApiOperation("关于疫情百科知识 （预防指南）")
    @SentinelResource("/getGuideList")
    public Result getGuideList(@RequestParam("count") Integer count) {
        String res = "";
        try {
            Document doc = Jsoup.connect("https://h5.baike.qq.com/api/jsonp/GetDocsByTag?callback=data&appid=2000000000000050&adtag=txxw.op.fybox&name=94699&count=" + count)
                    .ignoreContentType(true)
                    .userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.15)")
                    .header("content-type", "application/json")
                    .timeout(5000).get();
            String newsHeadlines = StrUtil.removePrefix(doc.body().html(), "data(");
            newsHeadlines = StrUtil.removeSuffix(newsHeadlines, ")");
            res = newsHeadlines;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultGenerator.genSuccessResult(JSON.parse(res));
    }
}
