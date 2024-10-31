package com.allendowney.thinkdast;

import static org.hamcrest.CoreMatchers.nullValue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Node;

public class WikiPhilosophy {

    final static List<String> visited = new ArrayList<String>();
    final static WikiFetcher wf = new WikiFetcher();
    private static int counter = 0;

    /**
     * Tests a conjecture about Wikipedia and Philosophy.
     *
     * https://en.wikipedia.org/wiki/Wikipedia:Getting_to_Philosophy
     *
     * 1. Clicking on the first non-parenthesized, non-italicized link
     * 2. Ignoring external links, links to the current page, or red links
     * 3. Stopping when reaching "Philosophy", a page with no links or a page
     *    that does not exist, or when a loop occurs
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        String destination = "https://en.wikipedia.org/wiki/Philosophy";
        String source = "https://en.wikipedia.org/wiki/Java_(programming_language)";

        testConjecture(destination, source, 20);
    }

    /**
     * Starts from given URL and follows first link until it finds the destination or exceeds the limit.
     *
     * @param destination
     * @param source
     * @throws IOException
     */
    public static void testConjecture(String destination, String source, int limit) throws IOException {
        // TODO: FILL THIS IN!
        System.setProperty("http.proxyHost", "127.0.0.1");
        System.setProperty("http.proxyPort", "7890");
        System.setProperty("https.proxyHost", "127.0.0.1");
        System.setProperty("https.proxyPort", "7890");
        digUrl(source,destination,limit);
        
    }

    private static void digUrl(String url, String destination, int limit) throws IOException{
        Elements paras = wf.fetchWikipedia(url);
        Element firstPara = findFirstNonEmptyPara(paras);
        Iterable<Node> iter = new WikiNodeIterable(firstPara);

        if(counter>limit){
            return;
        }

        for(Node node : iter){
            if(node instanceof Element){
                String href = node.attr("href");
                if(href.equals("")){
                    continue;
                }
                else{
                    if(href.startsWith("/wiki/") && !href.contains(":")){
                        String nextUrl = node.attr("abs:href");
                        System.out.println(nextUrl);
                        System.out.println(counter);
                        counter++;
                        if(nextUrl.equals(destination)){
                            System.out.println(String.format("Reached in %d trys.", counter));
                            return;
                        }
                        digUrl(nextUrl, destination, limit);
                        return;
                    }
                }                
            }
        }
        return;

    }

    private static Element findFirstNonEmptyPara(Elements paras) {
        for (Element para : paras) {
            if (!para.hasClass("mw-empty-elt") && !para.text().trim().isEmpty()) {
                return para;
            }
        }
        return null;
    }
}
