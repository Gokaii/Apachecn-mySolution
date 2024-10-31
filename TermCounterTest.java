/**
 * 
 */
package com.allendowney.thinkdast;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;

/**
 * @author downey
 *
 */
public class TermCounterTest {

	private TermCounter counter;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		System.setProperty("http.proxyHost", "127.0.0.1");
        System.setProperty("http.proxyPort", "7890");
        System.setProperty("https.proxyHost", "127.0.0.1");
        System.setProperty("https.proxyPort", "7890");
		String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";
		
		WikiFetcher wf = new WikiFetcher();
		
		Elements paragraphs = wf.fetchWikipedia(url);
		counter = new TermCounter(url.toString());
		counter.processElements(paragraphs);
	}

	@Test
	public void testSize() {
		assertThat(counter.size(), is(3969));
	}
}
