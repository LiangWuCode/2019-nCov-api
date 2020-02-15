package com.wuliang.ncov.controller;

import cn.hutool.json.JSONUtil;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
 * 口罩相关信息
 * 伍亮
 */
@RequestMapping("/mask")
@RestController
@Api(value = "/mask", tags = "口罩信息")
public class MaskController {


    /**
     * 口罩预约相关信息
     *
     * @return
     */
    @GetMapping("/gauzeMask")
    @SentinelResource("gauzeMask")
    @ApiOperation("口罩预约相关信息")
    public Result gauzeMask() {
        Object res = "";
        try {
            Document doc = Jsoup.connect("https://m.sm.cn/api/rest?format=json&uc_param_str=gicp&method=Maskbuy.mask")
                    .ignoreContentType(true)
                    .userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.15)")
                    .header("content-type", "application/json")
                    .timeout(5000).get();
            res = JSONArray.parse(JSONUtil.parseObj(JSONUtil.parseObj(doc.body().html()).get("data")).get("list").toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultGenerator.genSuccessResult(JSON.parse(res.toString()));
    }
}
