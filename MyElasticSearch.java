import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.Map;

public class MyElasticSearch {

    public static void main(String[] args) {
        
        try (RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")))) {

            // Indexing a document
            IndexRequest indexRequest = new IndexRequest("my_index_name");
            indexRequest.id("1");
            indexRequest.source(Map.of("field1", "value1", "field2", "value2"));
            IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
            System.out.println("Document indexed: " + indexResponse.getId());

            // Retrieving a document
            GetRequest getRequest = new GetRequest("my_index_name", "1");
            GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
            System.out.println("Retrieved document: " + getResponse.getSourceAsString());

            // Searching for documents
            SearchRequest searchRequest = new SearchRequest("my_index_name");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchAllQuery());
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            System.out.println("Search results:");
            for (SearchHit hit : searchResponse.getHits().getHits()) {
                System.out.println(hit.getSourceAsString());
            }

            // Deleting a document
            DeleteRequest deleteRequest = new DeleteRequest("my_index_name", "1");
            DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
            System.out.println("Document deleted: " + deleteResponse.getId());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
