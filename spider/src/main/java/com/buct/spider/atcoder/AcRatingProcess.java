package com.buct.spider.atcoder;

import com.buct.spider.entity.Acrating;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

@Component
public class AcRatingProcess implements PageProcessor {

    private static final Site SITE = Site.me()
            // 设置字符编码集
            .setCharset("UTF-8")
            // 设置Http连接重试次数
            .setRetryTimes(30)
            // 设置线程休眠时间
            .setSleepTime(1000);

    @Override
    public void process(Page page) {
        Selectable table = page.getHtml().xpath("//*[@id=\"history\"]");
        List<String> tr = table.css("tbody > tr").all();

        for (int i = 1; i <= tr.size(); i++) {
            String date = table.css("tbody > tr:nth-child("+i+") > td:nth-child(1) > time","text").get();
            String contest = table.css("tbody > tr:nth-child("+i+") > td:nth-child(2) > a","text").get();
            String rank = table.css("tbody > tr:nth-child("+i+") > td:nth-child(3) > a","text").get();
            String performance = table.css("tbody > tr:nth-child("+i+") > td:nth-child(4)","text").get();
            String newRating = table.css("tbody > tr:nth-child("+i+") > td:nth-child(5) > span","text").get();
            String diff = table.css("tbody > tr:nth-child("+i+") > td:nth-child(6)","text").get();

            String oldRating;
            if (!diff.equals("-") && !newRating.equals("-") &&!diff.equals("±0")) {

                Integer news = Integer.parseInt(newRating);
                Integer diffs = Integer.parseInt(diff);
                Integer res = news - diffs;
                oldRating = res + "";
            } else {
                oldRating = "-";
            }
            if (newRating == null) newRating = "-";

            Selectable url = page.getUrl();
            String[] split = url.toString().split("/");

            Acrating acrating = new Acrating();
            acrating.setAcContestDate(date);
            acrating.setAcContestName(contest);
            acrating.setAcRank(rank);
            acrating.setAcPerformance(performance);
            acrating.setAcDiff(diff);
            acrating.setAcNewRating(newRating);
            acrating.setAcOldRating(oldRating);
            acrating.setAcUserId(split[split.length-2]);

            String key = "data" + i;
            page.putField(key, acrating);
        }
    }

    @Override
    public Site getSite() { return SITE; }

    public static void main(String[] args) {
        int a = 1000*60*60*24*7;
        System.out.println(a);
    }
}
