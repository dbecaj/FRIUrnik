package me.dbecaj.friurnik.data.models.schedule;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import timber.log.Timber;

/**
 * Created by HP on 10/18/2017.
 */

public class ScheduleModel {

    private SubjectModel schedule[][] = new SubjectModel[5][13];

    public void parseHtml(String html){
        Document document = Jsoup.parse(html);
        Element table = document.body().getElementsByTag("table").first();
        for(Element ele : table.getAllElements()) {
            Timber.d(ele.tagName());
        }
    }

    public SubjectModel[][] getSchedule() {
        return schedule;
    }
}
