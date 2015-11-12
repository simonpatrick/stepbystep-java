package com.hedwig.stepbystep.javatutorial.extractor;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.io.Files;
import com.hedwig.stepbystep.javatutorial.helpers.CollectionsHelper;
import com.hedwig.stepbystep.javatutorial.helpers.StringHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class Html2MarkDown {
    private static final Logger logger = LogManager.getLogger(Html2MarkDown.class.getName());

    public static void tableToMarkdown(String tableSection){
        //get th
        Elements trs = Jsoup.parse(tableSection).getElementsByTag("tr");
        //print markdown tables
        for (Element tr : trs) {
            Elements ths = tr.getElementsByTag("th");
            if(ths.size()>0){
                printMarkdownTableRow(ths);
                System.out.println("|"+ StringHelper.repeat("----|", ths.size()));
            }else{
                printMarkdownTableRow(tr.getElementsByTag("td"));
            }
        }
        //get tr
    }

    private static void printMarkdownTableRow(Elements ths){
        String output ="|"+ CollectionsHelper.join(ths, new Function<Element, String>() {
            @Override
            public String apply(Element element) {
                return element.text();
            }
        }, "|");
        System.out.println(output.replaceAll("<","&lt;").replaceAll(">", "&gt;"));

    }

    public static void main(String[] args) throws IOException {
        logger.info("this is test");
        String path = ClassLoader.getSystemClassLoader().getResource("table.txt").getPath();
        String input = Joiner.on("\n").join(Files.readLines(new File(path), Charset.defaultCharset()));
        tableToMarkdown(input);

    }
}
