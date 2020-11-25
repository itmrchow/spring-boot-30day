package com.springboot30day.other.algolia;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

import com.algolia.search.DefaultSearchClient;
import com.algolia.search.SearchClient;
import com.algolia.search.SearchIndex;
import com.algolia.search.models.indexing.Query;
import com.algolia.search.models.indexing.SearchForFacetRequest;
import com.algolia.search.models.indexing.SearchForFacetResponse;
import com.algolia.search.models.settings.IndexSettings;
import com.springboot30day.entity.Book;

public class Indexing {
    public static void main(String[] args) {

        // 放值到index
        // insert();
        search();

    }

    static SearchIndex<Book> getIndex() {
        SearchClient client = DefaultSearchClient.create("G8TRARI1S8", "ac870eed8dd03412412c238f1c1ea9b5");
        SearchIndex<Book> index = client.initIndex("index_BOOK", Book.class);

        index.setSettings(new IndexSettings()
                // Select the attributes you want to search in
                .setSearchableAttributes(Arrays.asList("author", "name"))
                // Define business metrics for ranking and sorting
                .setCustomRanking(Collections.singletonList("desc(bookid)"))
                // Set up some attributes to filter results on
                .setAttributesForFaceting(Arrays.asList("searchable(author)", "name"))).waitTask();

        return index;
    }

    static void insert() {
        SearchIndex<Book> index = getIndex();
        Book book = new Book();
        book.setBookid(78);
        book.setAuthor("藤子不二雄");
        book.setName("哆啦A夢");
        index.saveObject(book, true).waitTask();
    }

    static void search() {
        SearchIndex<Book> index = getIndex();
        // SearchResult<Book> search1 = index.search(new Query("天"));

        // Sync version
        SearchForFacetResponse result = index
                .searchForFacetValues(new SearchForFacetRequest().setFacetName("author").setFacetQuery("斯"));

        // Async version
        CompletableFuture<SearchForFacetResponse> result2 = index
                .searchForFacetValuesAsync(new SearchForFacetRequest().setFacetName("author").setFacetQuery("斯"));

        Query query = new Query().setFilters("name:咖啡的科學");

        CompletableFuture<SearchForFacetResponse> result3 = index
                .searchForFacetValuesAsync(new SearchForFacetRequest()
                    .setFacetName("author")
                    .setFacetQuery("斯")
                    .setSearchParameters(query));
        

        System.out.println("end");
    }
}
