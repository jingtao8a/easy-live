package org.jingtao8a.component;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.xcontent.XContentType;
import org.jingtao8a.config.AppConfig;
import org.jingtao8a.exception.BusinessException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

@Component("esSearchComponent")
@Slf4j
public class EsSearchComponent {
    @Resource
    private AppConfig appConfig;

    @Resource
    private RestHighLevelClient restHighLevelClient;

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
}
