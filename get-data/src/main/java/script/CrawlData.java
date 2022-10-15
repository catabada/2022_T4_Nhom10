package script;

import constant.StringConstant;
import constant.VNCharacterUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;

public class CrawlData {
    public static void main(String[] args) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter("E:/test.txt");
        pw.println("natural_key,date,time,province,temp,cloud_description,min_temp,max_temp,humidity,vision,wind_spd,uv_index,atmosphere_quality");
        pw.close();
    }
    public static String run(String url) throws IOException {
        Document doc = Jsoup.connect(url).timeout(3000).get();
        String timerPresent = doc.select("#timer").text();
        String timePresent = timerPresent.substring(0, timerPresent.indexOf("|") - 1).trim();
        String datePresent = timerPresent.substring(timerPresent.indexOf("|") + 1, timerPresent.length()).trim();

        File folder = new File(StringConstant.FOLDER_PATH_LOCAL);
        if (!folder.exists()) {
            folder.mkdirs();
            System.out.println("Created new folder");
        }
        String fileName = System.currentTimeMillis() + ".csv";
        System.out.println("Creating file name: " + fileName + "......");
        File csvFile = new File(StringConstant.FOLDER_PATH_LOCAL + "/" + fileName);
        PrintWriter pw = new PrintWriter(csvFile);
        pw.append("natural_key,date,time,province,temp,cloud_description,min_temp,max_temp,humidity,vision,wind_spd,uv_index,atmosphere_quality");

        Elements linkProvinces = doc.select(".mega-submenu li a[href]");
        for (Element e : linkProvinces) {
            String link = url + e.attr("href");
            Document documentProvince = Jsoup.connect(link).get();
            String province = documentProvince.select(".breadcrumb-item.active").text().replace("Thời tiết ", "");
            String naturalKeyPresent = createNaturalKey(province, datePresent, timePresent);
            String temperaturePresent = documentProvince.select(".current-temperature").text().replaceAll("°", "");
            String cloudDescriptionPresent = documentProvince.select(".overview-caption p").text();
            Element weatherDetailPresent = documentProvince.select(".weather-detail").get(0);
            Elements attrs = weatherDetailPresent.select("span.text-white.op-8.fw-bold");

            String maxMinTempPresent = attrs.get(0).select("span.text-white").text();
            String minTempPresent = maxMinTempPresent.split("/")[0].trim().replaceAll("°", "");
            String maxTempPresent = maxMinTempPresent.split("/")[1].trim().replaceAll("°", "");
            String humidityPresent = Double.parseDouble(attrs.get(1).select("span.text-white").text().replaceAll("%", "")) / 100 + "";
            String visionPresent = attrs.get(2).select("span.text-white").text().replaceAll("km", "").trim();
            String windPresent = attrs.get(3).select("span.text-white").text().replace("km/giờ", "").replaceAll("km", "").trim();
            String UVIndex = attrs.get(5).select("span.text-white").text();

            String qualityAtmospherePresent = documentProvince.select(".air-active").text();
            pw.println(naturalKeyPresent + "," + datePresent + "," + timePresent + "," + province + "," + temperaturePresent + "," + cloudDescriptionPresent + "," + minTempPresent + "," + maxTempPresent + "," + humidityPresent + "," + visionPresent + "," + windPresent + "," + UVIndex + "," + qualityAtmospherePresent);
            System.out.println(naturalKeyPresent + "," + datePresent + "," + timePresent + "," + province + "," + temperaturePresent + "," + cloudDescriptionPresent + "," + minTempPresent + "," + maxTempPresent + "," + humidityPresent + "," + visionPresent + "," + windPresent + "," + UVIndex + "," + qualityAtmospherePresent);
            Document docToday = Jsoup.connect(link + "/theo-gio").get();
            Elements timesToday = docToday.select("details.weather-day");
            for (int i = 1; i <= 23 - getHour(timerPresent); i++) {
                String hour = timesToday.get(i).select(".summary-day").text().trim();
                String naturalKeyByHour = createNaturalKey(province, datePresent, hour).trim();
                String temperatureByHour = "";
                String maxMinTempByHour = timesToday.get(i).select(".summary-temperature").text().replaceAll("C", "").trim();
                String minTempByHour = maxMinTempByHour.split("/")[0].trim().replaceAll("°", "").trim();
                String maxTempByHour = maxMinTempByHour.split("/")[1].trim().replaceAll("°", "").trim();
                String cloudDescriptionByHour = timesToday.get(i).select(".summary-description").text();
                String humidityByHour = Double.parseDouble(timesToday.get(i).select(".summary-humidity").text().replaceAll("%", "")) / 100 + "";
                String windByHour = timesToday.get(i).select(".summary-speed").text().replaceAll("Wind", "").trim().replace("km/giờ", "").replaceAll("km", "").trim();
                Elements weatherContentByHour = timesToday.get(i).select(".weather-content-item");

                String uVIndexByHour = weatherContentByHour.get(0).select("span.op-8.fw-bold").text();
                String visionByHour = weatherContentByHour.get(1).select("span.op-8.fw-bold").text().replaceAll("km", "").trim();

                pw.println(naturalKeyByHour + "," + datePresent + "," + hour + "," + province + "," + null + "," + cloudDescriptionByHour + "," + minTempByHour + "," + maxTempByHour + "," + humidityByHour + "," + visionByHour + "," + windByHour + "," + uVIndexByHour + "," + null);
                System.out.println(naturalKeyByHour + "," + datePresent + "," + hour + "," + province + "," + null + "," + cloudDescriptionByHour + "," + minTempByHour + "," + maxTempByHour + "," + humidityByHour + "," + visionByHour + "," + windByHour + "," + uVIndexByHour);
            }

            Document docTomorrow = Jsoup.connect(link + "/ngay-mai").get();
            Elements timesTomorrow = docTomorrow.select("details.weather-day");
            String dateTomorrow = docTomorrow.select(".header-thoi-tiet-ngay span.d-block.text-right").text();
            for (Element ele : timesTomorrow) {
                String hourTomorrow = ele.select(".summary-day").text();
                String naturalKeyByTomorrow = createNaturalKey(province, dateTomorrow, hourTomorrow);
                String maxMinTempByHourTomorrow = ele.select(".summary-temperature").text();
                String minTempByHourTomorrow = maxMinTempPresent.split("/")[0].trim().replaceAll("°", "");
                String maxTempByHourTomorrow = maxMinTempPresent.split("/")[1].trim().replaceAll("°", "");
                String cloudDescriptionByHourTomorrow = ele.select(".summary-description").text();
                String humidityByHourTomorrow = Double.parseDouble(ele.select(".summary-humidity").text().replaceAll("%", "")) / 100 + "";
                String windByHourTomorrow = ele.select(".summary-speed").text().replaceAll("Wind", "").trim().replace("km/giờ", "").replaceAll("km", "").trim();

                Elements weatherContentByTomorrow = ele.select(".weather-content-item");

                String uVIndexByTomorrow = null;
                String visionByTomorrow = weatherContentByTomorrow.get(1).select("span.op-8.fw-bold").text().replaceAll("km", "").trim();

                pw.println(naturalKeyByTomorrow + "," + dateTomorrow + "," + hourTomorrow + "," + province + "," + null + "," + cloudDescriptionByHourTomorrow + "," + minTempByHourTomorrow + "," + maxTempByHourTomorrow + "," + humidityByHourTomorrow + "," + visionByTomorrow + "," + windByHourTomorrow + "," + uVIndexByTomorrow + "," + null);
                System.out.println(naturalKeyByTomorrow + "," + dateTomorrow + "," + hourTomorrow + "," + province + "," + null + "," + cloudDescriptionByHourTomorrow + "," + minTempByHourTomorrow + "," + maxTempByHourTomorrow + "," + humidityByHourTomorrow + "," + visionByTomorrow + "," + windByHourTomorrow + "," + uVIndexByTomorrow);
            }
        }
        pw.close();
        return csvFile.getName();
    }

    public static int getHour(String timer) {
        return Integer.parseInt(timer.substring(0, 2));
    }

    public static String createNaturalKey(String province, String date, String time) {
        String dateModify = date.replaceAll("/", "");
        String provinceModify = VNCharacterUtils.removeAccent(province).toUpperCase().replaceAll(" ", "");
        String timeModify = time.replaceAll(":", "");
        return provinceModify + dateModify + timeModify;
    }
}
