package com.wuliang.ncov.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 同乘车辆
 * 伍亮
 */
@RequestMapping("/sameRide")
@RestController
@Api(value = "/sameRide", tags = "同乘车辆")
public class SameRideController {
    /**
     * 数据来源于腾讯
     * 同乘车辆查询
     *
     * @return
     */
    @GetMapping("/getSameRideAll")
    @ApiOperation("获取所有同乘车辆数据")
    public Result getSameRideAll() {
        Object res = "";
        try {
            Document doc = Jsoup.connect("https://rl.inews.qq.com/taf/travelFront")
                    .ignoreContentType(true)
                    .maxBodySize(0)
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
     * 同乘车辆查询
     *
     * @return
     */
    @GetMapping("/getSameRide1")
    @ApiOperation("查询同程车辆数据1")
    public Result getSameRide(@RequestParam(value = "no", required = false) String no, @RequestParam(value = "date", required = false) String date) {
        Object res = "";
        JSONObject json = JSONUtil.createObj();
        try {
            Document doc = Jsoup.connect("https://rl.inews.qq.com/taf/travelFront")
                    .ignoreContentType(true)
                    .userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.15)")
                    .header("content-type", "application/json")
                    .timeout(5000).get();
            List<Map<String, String>> listObjectFir = (List<Map<String, String>>) JSONArray.parse(JSONUtil.parseObj(JSONUtil.parseObj(doc.body().html()).get("data")).get("list").toString());
            List<Map> realMaps = new ArrayList<>();
            for (int i = 0; i < listObjectFir.size(); i++) {
                if (!StrUtil.isEmpty(no) && !StrUtil.isEmpty(date)) {
                    if (StrUtil.contains(no, listObjectFir.get(i).get("no")) && StrUtil.contains(date, listObjectFir.get(i).get("date"))) {
                        realMaps.add(listObjectFir.get(i));
                    }
                } else if (!StrUtil.isEmpty(no)) {
                    if (StrUtil.contains(no, listObjectFir.get(i).get("no"))) {
                        realMaps.add(listObjectFir.get(i));
                    }
                } else {
                    if (StrUtil.contains(date, listObjectFir.get(i).get("date"))) {
                        realMaps.add(listObjectFir.get(i));
                    }
                }
            }
            json.put("list", realMaps);
            json.put("total", realMaps.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultGenerator.genSuccessResult(json);
    }

    /**
     * 数据来源于夸克
     * 同乘车辆查询
     *
     * @return
     */
    @GetMapping("/getSameRide2")
    @ApiOperation("查询同程车辆数据2")
    public Result getSameRide2(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                               @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                               @RequestParam(value = "position", required = false, defaultValue = "") String position,
                               @RequestParam(value = "type", required = false, defaultValue = "0") Integer type,
                               @RequestParam(value = "no", required = false, defaultValue = "") String no,
                               @RequestParam(value = "startDate", required = false, defaultValue = "2020-01-01") String startDate,
                               @RequestParam(value = "endDate", required = false, defaultValue = "2021-01-01") String endDate
    ) {
        Object res = "";
        try {
            String url = "https://m.sm.cn/api/rest?format=json&method=Huoshenshan.trailSearch&page=" + page + "&size=" + size + "&position=" + position + "&type=" + type + "&no=" + no + "&datefrom=" + startDate + "&dateto=" + endDate;
            Document doc = Jsoup.connect(url)
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
}
