package com.springboot30day.springboot30day;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.springboot30day.Springboot30dayApplication;
import com.springboot30day.entity.Book;
import com.springboot30day.repository.BookRepository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Springboot30dayApplication.class,
		// WebEnvironment:啟動class web環境
		// WebEnvironment.MOCK—提供一個Mock的Servlet環境，內置的Servlet容器並沒有真實的啟動，主要搭配使用@AutoConfigureMockMvc
		// WebEnvironment.RANDOM_PORT — 提供一個真實的Servlet環境，也就是說會啟動內置容器，然後使用的是隨機端口
		// WebEnvironment.DEFINED_PORT — 這個配置也是提供一個真實的Servlet環境，使用的默認的端口，如果沒有配置就是8080
		// WebEnvironment.NONE — 這是個神奇的配置，跟Mock一樣也不提供真實的Servlet環境。
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Springboot30dayApplicationTests {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testGetBook() {
		// insert
		Book book = new Book();
		book.setName("被討厭的勇氣：自我啟發之父「阿德勒」的教導");
		book.setAuthor("岸見一郎");
		book = bookRepository.save(book);

		//url
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/api/v1/book/{bookid}");
		//參數
		Map<String, Object> uriParams = new HashMap<String, Object>();
		uriParams.put("bookid", book.getBookid());

		//head
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		//實體
		HttpEntity<Object> entity = new HttpEntity<>(headers);
		
		//送出
		ResponseEntity<String> response = restTemplate.exchange(builder.buildAndExpand(uriParams).toUri().toString(),
				HttpMethod.GET, entity, String.class);

		assertTrue("testGetBook Fail:\n" + response.getBody(), response.getStatusCode().is2xxSuccessful());
	}

}
