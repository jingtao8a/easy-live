package org.jingtao8a.component;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.xcontent.XContentType;
import org.jingtao8a.config.AppConfig;
import org.jingtao8a.dto.VideoInfoEsDto;
import org.jingtao8a.entity.po.UserInfo;
import org.jingtao8a.entity.po.VideoInfo;
import org.jingtao8a.entity.query.SimplePage;
import org.jingtao8a.entity.query.UserInfoQuery;
import org.jingtao8a.enums.PageSize;
import org.jingtao8a.enums.SearchOrderTypeEnum;
import org.jingtao8a.exception.BusinessException;
import org.jingtao8a.mapper.UserInfoMapper;
import org.jingtao8a.utils.CopyTools;
import org.jingtao8a.utils.JsonUtils;
import org.jingtao8a.utils.StringTools;
import org.jingtao8a.vo.PaginationResultVO;
import org.springframework.stereotype.Component;
import java.util.function.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.stream.Collectors;

@Component("esSearchComponent")
@Slf4j
public class EsSearchComponent {
    @Resource
    private AppConfig appConfig;

    @Resource
    private RestHighLevelClient restHighLevelClient;

    @Resource
    private UserInfoMapper userInfoMapper;

    private Boolean isIndexExist() throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest(appConfig.getEsIndexVideoName());
        return restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
    }

    public void createIndex() throws BusinessException {
        try {
            if (isIndexExist()) {
                log.info("es索引已经存在");
                return;
            }
            String analysisStr = "{\n" +
                    "  \"analysis\": {\n" +
                    "    \"analyzer\" : {\n" +
                    "      \"comma\": {\n" +
                    "        \"type\": \"pattern\",\n" +
                    "        \"pattern\": \",\"\n" +
                    "      }\n" +
                    "    }\n" +
                    "  }\n" +
                    "}";
            String propertiesStr = "{\n" +
                    "    \"properties\": {\n" +
                    "      \"videoId\": {\n" +
                    "        \"type\": \"text\",\n" +
                    "        \"index\": false\n" +
                    "      },\n" +
                    "      \"userId\": {\n" +
                    "        \"type\": \"text\",\n" +
                    "        \"index\": false\n" +
                    "      },\n" +
                    "      \"videoCover\": {\n" +
                    "        \"type\": \"text\",\n" +
                    "        \"index\": false\n" +
                    "      },\n" +
                    "      \"videoName\": {\n" +
                    "        \"type\": \"text\",\n" +
                    "        \"analyzer\": \"ik_max_word\"\n" +
                    "      },\n" +
                    "      \"tags\": {\n" +
                    "        \"type\": \"text\",\n" +
                    "        \"analyzer\": \"comma\"\n" +
                    "      },\n" +
                    "      \"playCount\": {\n" +
                    "        \"type\": \"integer\",\n" +
                    "        \"index\": false\n" +
                    "      },\n" +
                    "      \"danmuCount\": {\n" +
                    "        \"type\": \"integer\",\n" +
                    "        \"index\": false\n" +
                    "      },\n" +
                    "      \"collectCount\": {\n" +
                    "        \"type\": \"integer\",\n" +
                    "        \"index\": false\n" +
                    "      },\n" +
                    "      \"createTime\": {\n" +
                    "        \"type\": \"date\",\n" +
                    "        \"format\": \"yyyy-MM-dd HH:mm:ss\",\n" +
                    "        \"index\": false\n" +
                    "      }\n" +
                    "    }\n" +
                    "  }";
            CreateIndexRequest createIndexRequest = new CreateIndexRequest(appConfig.getEsIndexVideoName());
            createIndexRequest.settings(analysisStr, XContentType.JSON);
            createIndexRequest.mapping(propertiesStr, XContentType.JSON);
            CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
            Boolean acknowledged = createIndexResponse.isAcknowledged();
            if (!acknowledged) {
                throw new BusinessException("初始化es失败");
            }
            log.info("es索引创建成功");
        } catch (Exception e) {
            log.error("初始化es失败");
            throw new BusinessException("初始化es失败");//触发destroy
        }
    }

    public void saveDoc(VideoInfo videoInfo) throws BusinessException {
        try {
            if (docExist(videoInfo.getVideoId())) {//修改doc
                updateDoc(videoInfo);
            } else {//新增doc
                VideoInfoEsDto videoInfoEsDto = CopyTools.copy(videoInfo, VideoInfoEsDto.class);
                videoInfoEsDto.setCollectCount(0);
                videoInfoEsDto.setPlayCount(0);
                videoInfoEsDto.setDanmuCount(0);
                IndexRequest request = new IndexRequest((appConfig.getEsIndexVideoName()));
                String jsonStr = JsonUtils.convertObj2Json(videoInfoEsDto);
                request.id(videoInfoEsDto.getVideoId()).source(jsonStr, XContentType.JSON);
                restHighLevelClient.index(request, RequestOptions.DEFAULT);
            }
        } catch (Exception e) {
            log.error("保存到es失败", e);
            throw new BusinessException("保存到es失败");
        }
    }

    private boolean docExist(String id) throws IOException {
        GetRequest getRequest = new GetRequest(appConfig.getEsIndexVideoName(), id);
        GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        return getResponse.isExists();
    }

    private void updateDoc(VideoInfo videoInfo) throws BusinessException {
        try {
            videoInfo.setLastUpdateTime(null);
            videoInfo.setCreateTime(null);
            Map<String, Object> dataMap = new HashMap<>();
            Field[] fields = VideoInfo.class.getFields();
            for (Field field : fields) {
                String methodName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                Method method = VideoInfo.class.getMethod(methodName);
                Object object = method.invoke(videoInfo);
                if (object != null) {
                    if (object instanceof String && StringTools.isEmpty((String) object) || !(object instanceof String)) {
                        dataMap.put(field.getName(), object);
                    }
                }
            }
            if (dataMap.isEmpty()) {
                return;
            }
            UpdateRequest updateRequest = new UpdateRequest(appConfig.getEsIndexVideoName(), videoInfo.getVideoId());
            updateRequest.doc(dataMap);
            restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("es更新视频失败", e);
            throw new BusinessException("保存视频失败");
        }
    }

    public void updateDocCount(String videoId, String fieldName, Integer count) throws BusinessException {
        try {
            UpdateRequest updateRequest = new UpdateRequest(appConfig.getEsIndexVideoName(), videoId);
            Script script = new Script(ScriptType.INLINE, "painless", "ctx._source." + fieldName + " += params.count", Collections.singletonMap("count", count));
            updateRequest.script(script);
            restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("es更新视频失败", e);
            throw new BusinessException("保存视频失败");
        }
    }

    public void deleteDoc(String videoId) throws BusinessException {
        DeleteRequest deleteRequest = new DeleteRequest(appConfig.getEsIndexVideoName(), videoId);
        try {
            restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("从es删除视频失败", e);
            throw new BusinessException("从es删除视频失败");
        }
    }

    public PaginationResultVO<VideoInfo> search(Boolean highlight, String keyWord, Integer orderType, Integer pageNo, Integer pageSize) throws BusinessException {
        try {
            SearchOrderTypeEnum searchOrderTypeEnum = SearchOrderTypeEnum.getEnum(orderType);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.multiMatchQuery(keyWord, "videoName", "tags"));
            //高亮
            if (highlight) {
                HighlightBuilder highlightBuilder = new HighlightBuilder();
                highlightBuilder.field("videoName");
                highlightBuilder.preTags("<span class='highlight'>");
                highlightBuilder.postTags("</span>");
                searchSourceBuilder.highlighter(highlightBuilder);
            }
            //排序
            searchSourceBuilder.sort("_score", SortOrder.ASC);
            if (searchOrderTypeEnum != null) {
                searchSourceBuilder.sort(searchOrderTypeEnum.getField(), SortOrder.DESC);
            }

            pageNo = pageNo == null ? 1 : pageNo;
            pageSize = pageSize == null ? PageSize.SIZE20.getSize() : pageSize;
            searchSourceBuilder.from((pageNo - 1) * pageSize);
            SearchRequest searchRequest = new SearchRequest(appConfig.getEsIndexVideoName());
            searchRequest.source(searchSourceBuilder);

            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

            SearchHits searchHits = searchResponse.getHits();
            Integer totalCount = (int)searchHits.getTotalHits().value;//总数量

            List<VideoInfo> videoInfoList = new ArrayList<>();
            List<String> userIdList = new ArrayList<>();
            for (SearchHit searchHit : searchHits.getHits()) {
                VideoInfo videoInfo = JsonUtils.convertJson2Obj(searchHit.getSourceAsString(), VideoInfo.class);
                if (searchHit.getHighlightFields().get("videoName") != null) {
                    videoInfo.setVideoName(searchHit.getHighlightFields().get("videoName").getFragments()[0].toString());
                }
                videoInfoList.add(videoInfo);
                userIdList.add(videoInfo.getUserId());
            }
            UserInfoQuery userInfoQuery = new UserInfoQuery();
            userInfoQuery.setUserIdList(userIdList);
            List<UserInfo> userInfoList = userInfoMapper.selectList(userInfoQuery);
            Map<String, UserInfo> userInfoMap = userInfoList.stream().collect(Collectors.toMap(item->item.getUserId(), Function.identity(), (item1, item2)->item1));
            videoInfoList.forEach( item-> {
                item.setNickName(userInfoMap.get(item.getUserId()).getNickName());
                item.setAvatar(userInfoMap.get(item.getUserId()).getAvatar());
            });
            SimplePage page = new SimplePage((long)pageNo, (long)totalCount, (long)pageSize);
            PaginationResultVO<VideoInfo> resultVO = new PaginationResultVO<>((long)totalCount, page.getPageSize(), page.getPageNo(), page.getPageTotal(), videoInfoList);
            return resultVO;
        } catch (Exception e) {
            log.error("从es查询视频失败", e);
            throw new BusinessException("从es查询视频失败");
        }
    }
}
