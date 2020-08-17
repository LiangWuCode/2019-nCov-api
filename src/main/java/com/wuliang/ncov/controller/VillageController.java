package com.wuliang.ncov.controller;

import cn.hutool.core.collection.CollUtil;
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
 * 小区数据
 * 伍亮
 */
@RequestMapping("/village")
@RestController
@Api(value = "/village", tags = "小区数据")
public class VillageController {
    /**
     * 数据来源于第三方
     * 通过省市区等信息获取小区信息
     *
     * @return
     */
    @GetMapping("/getVillage1")
    @ApiOperation("获取小区数据1")
    public Result getVillage1(@RequestParam("cityName") String cityName) {
        Object res = "";

        try {
            Document doc = Jsoup.connect("https://lspengine.map.sogou.com/coronavirus/epidemic/search/entity?city=" + cityName + "&keyword=疫情小区")
                    .ignoreContentType(true)
                    .userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.15)")
                    .header("content-type", "application/json")
                    .timeout(5000).get();
            if (!StrUtil.contains("{}", doc.body().html())) {
                res = JSONUtil.parseObj(doc.body().html()).get("poiResults");
            } else {
                JSONObject json = JSONUtil.createObj();
                List list = new ArrayList();
                json.put("datas", list);
                json.put("keyword", "疫情小区");
                res = json;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultGenerator.genSuccessResult(res);
    }

    /**
     * 数据来源于夸克
     * 通过省市区等信息获取小区信息
     *
     * @return
     */
    @GetMapping("/getVillage2")
    @ApiOperation("获取小区数据2")
    public Result getVillage2(@RequestParam("cityName") String cityName) {
        Object res = "";

        try {
            //查询目前所有已经确诊的小区数据
            Document doc = Jsoup.connect("https://iflow-api.uc.cn/feiyan/area?uc_param_str=utpclocp&city=1&range=city")
                    .ignoreContentType(true)
                    .userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.15)")
                    .header("content-type", "application/json")
                    .timeout(5000).get();
            List<Map<String, String>> listObjectFir = (List<Map<String, String>>) JSONArray.parse(JSONUtil.parseObj(JSONUtil.parseObj(doc.body().html()).get("data")).get("cities").toString());
            Integer j = 0;
            String code = "";
            //判断当前城市是否存在确诊的数据
            if (!CollUtil.isEmpty(listObjectFir)) {
                for (int i = 0; i < listObjectFir.size(); i++) {
                    if (StrUtil.contains(cityName, listObjectFir.get(i).get("two_level_area"))) {
                        j++;
                        code = listObjectFir.get(i).get("citycode");
                    }
                }
            } else {
                List list = new ArrayList();
                res = list;
            }
            //如果存在根据获取的code查询对应的小区疫情数据
            if (j > 0) {
                Document doc1 = Jsoup.connect("https://iflow-api.uc.cn/feiyan/area?uc_param_str=utpclocp&code=" + code)
                        .ignoreContentType(true)
                        .userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.15)")
                        .header("content-type", "application/json")
                        .timeout(5000).get();
                res = JSONArray.parse(JSONUtil.parseObj(JSONUtil.parseObj(doc1.body().html()).get("data")).get("areaes").toString());
            } else {
                List list = new ArrayList();
                res = list;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultGenerator.genSuccessResult(res);
    }

    /**
     * 数据来源于腾讯
     * 根据省市名称获取当前市区历史数据
     *
     * @return
     */
    @GetMapping("/getVillageByCommunityName")
    @ApiOperation("根据小区名称查询疫情小区")
    public Result getVillageByCommunityName(@RequestParam("communityName") String communityName) {
        Object res = "";
        try {
            Document doc = Jsoup.connect("https://iflow-api.uc.cn/feiyan/area?uc_param_str=utpclocp&q=" + communityName)
                    .ignoreContentType(true)
                    .userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.15)")
                    .header("content-type", "application/json")
                    .timeout(5000).get();
            res = JSONArray.parse(JSONUtil.parseObj(JSONUtil.parseObj(doc.body().html()).get("data")).get("areaes").toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultGenerator.genSuccessResult(JSON.parse(res.toString()));
    }
}
