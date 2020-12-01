


# 2019-nCov-api
# 新冠肺炎api

# 前言
本项目通过爬取腾讯、新浪、丁香园等疫情数据，获取新冠肺炎相关数据，并整合为api数据，做法简单粗暴，类似于端口转发。数据包含口罩预约、同乘车辆、疫情小区、数据分析、国内外详细数据、实时新闻动态、确诊人员信息流动轨迹、疫情谣言等。
当前接口部署到我自己的乞丐服务器上面的，可能速度有点慢，希望且用且珍惜。


接口文档浏览及测试：你的服务器ip+端口/doc.html#/home    例如：127.0.0.1:8080/doc.html


另外前期仓促之间也用flutter搞了个app，欢迎star https://github.com/LiangWuCode/2019-nCoV



# 总体图
<img src="https://raw.githubusercontent.com/LiangWuCode/2019-nCoV/master/resources/api.png" width = "300" height = "300" div align=center />

# api说明

**简介**:新冠肺炎api


**HOST**:wuliang.art


**联系人**:伍亮（qq：937743837（微信同））


api名称如果包含数字，则表示返回的数据内容类似（但数据格式不一样），只是数据来源不一样，使用时甄选使用。有任何问题call








# 口罩信息


## 口罩预约相关信息


**接口地址**:`/ncov/mask/gauzeMask`


**请求方式**:`GET`


**请求数据类型**:`*`


**响应数据类型**:`*/*`


**接口描述**:`获取相关城市的口罩预约信息`



# 同乘车辆


## 查询同程车辆数据1


**接口地址**:`/ncov/sameRide/getSameRide1`


**请求方式**:`GET`


**接口描述**:根据乘车日期或者是乘车车次返回相关车辆信息


**请求参数**:


| 参数名称 | 参数说明 | in    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|date|date|query|false|string||
|no|no|query|false|string||


## 查询同程车辆数据2


**接口地址**:`/ncov/sameRide/getSameRide2`


**请求方式**:`GET`


**接口描述**:可根据起始日期、结束日期、车次、车站、车辆类型等查询车辆的相关数据信息。


**请求参数**:


| 参数名称 | 参数说明 | in    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|endDate|endDate|query|false|string||
|no|no|query|false|string||
|page|page|query|false|integer(int32)||
|position|position|query|false|string||
|size|size|query|false|integer(int32)||
|startDate|startDate|query|false|string||
|type|type|query|false|integer(int32)||



## 获取所有同乘车辆数据


**接口地址**:`/ncov/sameRide/getSameRideAll`


**请求方式**:`GET`


**接口描述**:获取所有的目前渠道报道出来车次，该接口返回数量较大，可能会缓慢，不建议频繁调取。



# 小区数据


## 获取小区数据1


**接口地址**:`/ncov/village/getVillage1`


**请求方式**:`GET`



**接口描述**:根据城市的名称获取当前城市确诊小区的信息。例如：西安


**请求参数**:


| 参数名称 | 参数说明 | in    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|cityName|cityName|query|true|string||


## 获取小区数据2


**接口地址**:`/ncov/village/getVillage2`


**请求方式**:`GET`


**接口描述**:根据城市的名称获取当前城市确诊小区的信息。例如：西安


**请求参数**:


| 参数名称 | 参数说明 | in    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|cityName|cityName|query|true|string||



## 根据小区名称查询疫情小区


**接口地址**:`/ncov/village/getVillageByCommunityName`


**请求方式**:`GET`


**接口描述**:根据小区的名称进行模糊搜索，查询疫情小区


**请求参数**:


| 参数名称 | 参数说明 | in    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|communityName|communityName|query|true|string||



# 数据分析


## 每日数据分析


**接口地址**:`/ncov/dnalysis/everyday`


**请求方式**:`GET`


**接口描述**:对每日的疫情数据进行解读，以及以后趋势的分析，返回的为一个图片链接。



## 疫情地图数据


**接口地址**:`/ncov/dnalysis/ncovMaps`


**请求方式**:`GET`


**接口描述**:该接口，主要是对于疫情的一些图表信息的整理，方便于进行图表的渲染展示，例如柱状图、折线图等等。



# 数据统计


## 根据省市名称获取当前市区历史数据


**接口地址**:`/ncov/statistics/getCityHistoryList`


**请求方式**:`GET`

**接口描述**:根据省的名称、市的名称获取当前市区的历史数据。


**请求参数**:


| 参数名称 | 参数说明 | in    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|cityName|cityName|query|true|string||
|provinceName|provinceName|query|true|string||


## 根据市级名称获取县区级数据


**接口地址**:`/ncov/statistics/getCountyList`


**请求方式**:`GET`


**接口描述**:根据市区的名称获取县级历史数据，该接口可能存在数据缺失的情况。例如：安康


**请求参数**:


| 参数名称 | 参数说明 | in    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|cityName|cityName|query|true|string||


## 根据省的名称获取该省的历史数据


**接口地址**:`/ncov/statistics/getProvinceHistoryList`


**请求方式**:`GET`


**接口描述**:


**请求参数**:根据省的名称获取该省的历史数据。例如：陕西


| 参数名称 | 参数说明 | in    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|provinceName|provinceName|query|true|string||


## 数据统计1


**接口地址**:`/ncov/statistics/totalDataOne`


**请求方式**:`GET`


**接口描述**:获取国内以及国外的相关疫情数据。


**请求参数**:


## 数据统计3


**接口地址**:`/ncov/statistics/totalDataThree`


**请求方式**:`GET`


**接口描述**:取国内以及国外的相关疫情数据。


## 数据统计2


**接口地址**:`/ncov/statistics/totalDataTwo`


**请求方式**:`GET`



**接口描述**:取国内以及国外的相关疫情数据。



# 新闻数据


## 根据市的名称获取该市的新闻信息


**接口地址**:`/ncov/news/getCityNewsList`


**请求方式**:`GET`


**接口描述**:获取当前市区的新闻，根据市的名称。例如：安康


**请求参数**:


| 参数名称 | 参数说明 | in    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|cityName|cityName|query|true|string||

## 关于疫情百科知识 （检查诊断）


**接口地址**:`/ncov/news/getDiagnoseList`


**请求方式**:`GET`



**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | in    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|count|count|query|true|integer(int32)||

## 关于疫情百科知识 （预防指南）


**接口地址**:`/ncov/news/getGuideList`


**请求方式**:`GET`



**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | in    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|count|count|query|true|integer(int32)||


# 确诊信息


## 根据省名称，获取确诊人员信息


**接口地址**:`/ncov/confirmedInformation/getGuideListByProvinceName`


**请求方式**:`GET`


**接口描述**:查询当前省，确证人员的信息，包括人员轨迹等。


**请求参数**:


| 参数名称 | 参数说明 | in    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|num|num|query|true|integer(int32)||
|page|page|query|true|integer(int32)||
|provinceName|provinceName|query|true|string||


## 根据省市名称，获取当前市确诊人员信息


**接口地址**:`/ncov/confirmedInformation/getGuideListByProvinceNameAndCityName`


**请求方式**:`GET`


**接口描述**:查询当前省，确证人员的信息，包括人员轨迹等。


**请求参数**:


| 参数名称 | 参数说明 | in    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|cityName|cityName|query|true|string||
|page|page|query|true|integer(int32)||
|provinceName|provinceName|query|true|string||


# 谣言


## 根据谣言id获取谣言详情


**接口地址**:`/ncov/rumor/getRumorDetail`


**请求方式**:`GET`

**接口描述**:根据谣言接口列表，返回的数据的id，查询当前谣言的具体信息。


**请求参数**:


| 参数名称 | 参数说明 | in    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|id|query|true|string||


## 谣言数据列表


**接口地址**:`/ncov/rumor/getRumorList`


**请求方式**:`GET`

**接口描述**:获取谣言列表数据。


**请求参数**:


| 参数名称 | 参数说明 | in    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|page|page|query|true|integer(int32)||


# 尾巴
在家这段时间是大学毕业以后最闲的了，等着复工，也是由于闲才来做一些自己喜欢的东西，写写代码，吹吹牛逼，
陪陪家人，准备准备面试，疫情结束以后肯定会重新换工作，希望一切顺利吧。
疫情定会结束，曙光就在前方。武汉加油！中国加油！


**另数据为自己爬取的，所以不保证数据的有效性，合法性，如涉及到侵权，请联系删除，定做妥善处理。**
