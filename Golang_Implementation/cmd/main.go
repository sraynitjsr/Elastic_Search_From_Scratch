package main

import (
	"fmt"
	"log"
	"strings"

	"github.com/elastic/go-elasticsearch/v7"
)

func main() {
	cfg := elasticsearch.Config{
		Addresses: []string{"http://localhost:9200"},
	}

	es, err := elasticsearch.NewClient(cfg)
	if err != nil {
		log.Fatalf("Error creating the Elasticsearch client: %s", err)
	}

	indexDocument(es)
	retrieveDocument(es, "1")

}

func indexDocument(es *elasticsearch.Client) {
	document := strings.NewReader(`{
		"field1": "value1",
		"field2": "value2"
	}`)

	indexResponse, err := es.Index("my_index_name", document)
	if err != nil {
		log.Fatalf("Error indexing document: %s", err)
	}
	defer indexResponse.Body.Close()

	fmt.Printf("Document indexed: %s\n", indexResponse)
}

func retrieveDocument(es *elasticsearch.Client, documentID string) {
	getResponse, err := es.Get("my_index_name", documentID)
	if err != nil {
		log.Fatalf("Error retrieving document: %s", err)
	}
	defer getResponse.Body.Close()

	fmt.Printf("Retrieved document: %s\n", getResponse.String())
}
