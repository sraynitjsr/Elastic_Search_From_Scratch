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
