package script;

import constant.StringConstant;
import constant.VNCharacterUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;

public class CrawlData {
    public static String run(String url) throws IOException {
        Document doc = Jsoup.connect(url).timeout(3000).get();
        String timerPresent = doc.select("#timer").text();
        String timePresent = timerPresent.substring(0, timerPresent.indexOf("|") - 1).trim().substring(0,2) + ":00";
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
//        pw.append("natural_key,date,time,province,temp,cloud_description,min_temp,max_temp,humidity,vision,wind_spd,uv_index,atmosphere_quality");

        Elements linkProvinces = doc.select(".mega-submenu li a[href]");
        for (Element e : linkProvinces) {
            String link = url + e.attr("href");
            Document documentProvince = Jsoup.connect(link).get();
            String province = documentProvince.select(".breadcrumb-item.active").text().replace("Thời tiết ", "").replaceAll("- ","");
            String naturalKeyPresent = createNaturalKey(province, datePresent, timePresent);
            int temperaturePresent = Integer.parseInt(documentProvince.select(".current-temperature").text().replaceAll("°", ""));
            String cloudDescriptionPresent = documentProvince.select(".overview-caption p").text();
            Element weatherDetailPresent = documentProvince.select(".weather-detail").get(0);
            Elements attrs = weatherDetailPresent.select("span.text-white.op-8.fw-bold");

            String maxMinTempPresent = attrs.get(0).select("span.text-white").text();
            int minTempPresent = Integer.parseInt(maxMinTempPresent.split("/")[0].trim().replaceAll("°", ""));
            int maxTempPresent = Integer.parseInt(maxMinTempPresent.split("/")[1].trim().replaceAll("°", ""));
            double humidityPresent = Double.parseDouble(Double.parseDouble(attrs.get(1).select("span.text-white").text().replaceAll("%", "")) / 100 + "");
            double visionPresent = Double.parseDouble(attrs.get(2).select("span.text-white").text().replaceAll("km", "").trim());
            double windPresent = Double.parseDouble(attrs.get(3).select("span.text-white").text().replace("km/giờ", "").replaceAll("km", "").trim());
            double UVIndex = Double.parseDouble(attrs.get(5).select("span.text-white").text());

            String qualityAtmospherePresent = documentProvince.select(".air-active").text();
            pw.println(naturalKeyPresent + "," + datePresent + "," + timePresent + "," + province + "," + temperaturePresent + "," + cloudDescriptionPresent + "," + minTempPresent + "," + maxTempPresent + "," + humidityPresent + "," + visionPresent + "," + windPresent + "," + UVIndex + "," + qualityAtmospherePresent);
            System.out.println(naturalKeyPresent + "," + datePresent + "," + timePresent + "," + province + "," + temperaturePresent + "," + cloudDescriptionPresent + "," + minTempPresent + "," + maxTempPresent + "," + humidityPresent + "," + visionPresent + "," + windPresent + "," + UVIndex + "," + qualityAtmospherePresent);
            Document docToday = Jsoup.connect(link + "/theo-gio").get();
            Elements timesToday = docToday.select("details.weather-day");
            for (int i = 0; i <= timesToday.size(); i++) {
                String hour = timesToday.get(i).select(".summary-day").text().trim();
                if(hour.contains("/")) break;
                if (getHour(hour) > getHour(timerPresent)) {
                    String naturalKeyByHour = createNaturalKey(province, datePresent, hour).trim();
                    int temperatureByHour = -9999;
                    String maxMinTempByHour = timesToday.get(i).select(".summary-temperature").text().replaceAll("C", "").trim();
                    int minTempByHour = Integer.parseInt(maxMinTempByHour.split("/")[0].trim().replaceAll("°", "").trim());
                    int maxTempByHour = Integer.parseInt(maxMinTempByHour.split("/")[1].trim().replaceAll("°", "").trim());
                    String cloudDescriptionByHour = timesToday.get(i).select(".summary-description").text();
                    double humidityByHour = Double.parseDouble(Double.parseDouble(timesToday.get(i).select(".summary-humidity").text().replaceAll("%", "")) / 100 + "");
                    double windByHour = Double.parseDouble(timesToday.get(i).select(".summary-speed").text().replaceAll("Wind", "").trim().replace("km/giờ", "").replaceAll("km", "").trim());
                    Elements weatherContentByHour = timesToday.get(i).select(".weather-content-item");

                    String uVIndexByHour = weatherContentByHour.get(0).select("span.op-8.fw-bold").text();
                    String visionByHour = weatherContentByHour.get(1).select("span.op-8.fw-bold").text().replaceAll("km", "").trim();

                    pw.println(naturalKeyByHour + "," + datePresent + "," + hour + "," + province + "," + temperatureByHour + "," + cloudDescriptionByHour + "," + minTempByHour + "," + maxTempByHour + "," + humidityByHour + "," + visionByHour + "," + windByHour + "," + uVIndexByHour + "," + "Chưa rõ");
                    System.out.println(naturalKeyByHour + "," + datePresent + "," + hour + "," + province + "," + temperatureByHour + "," + cloudDescriptionByHour + "," + minTempByHour + "," + maxTempByHour + "," + humidityByHour + "," + visionByHour + "," + windByHour + "," + uVIndexByHour + "," + "Chưa rõ");
                }

            }

            Document docTomorrow = Jsoup.connect(link + "/ngay-mai").get();
            Elements timesTomorrow = docTomorrow.select("details.weather-day");
            String dateTomorrow = docTomorrow.select(".header-thoi-tiet-ngay span.d-block.text-right").text();
            for (Element ele : timesTomorrow) {
                String hourTomorrow = ele.select(".summary-day").text();
                String naturalKeyByTomorrow = createNaturalKey(province, dateTomorrow, hourTomorrow);
                String maxMinTempByHourTomorrow = ele.select(".summary-temperature").text();
                int minTempByHourTomorrow = Integer.parseInt(maxMinTempByHourTomorrow.split("/")[0].trim().replaceAll("°C", ""));
                int maxTempByHourTomorrow = Integer.parseInt(maxMinTempByHourTomorrow.split("/")[1].trim().replaceAll("°C", ""));
                String cloudDescriptionByHourTomorrow = ele.select(".summary-description").text();
                double humidityByHourTomorrow = Double.parseDouble(Double.parseDouble(ele.select(".summary-humidity").text().replaceAll("%", "")) / 100 + "");
                double windByHourTomorrow = Double.parseDouble(ele.select(".summary-speed").text().replaceAll("Wind", "").trim().replace("km/giờ", "").replaceAll("km", "").trim());
                int tempByHourTomorrow = -9999;
                Elements weatherContentByTomorrow = ele.select(".weather-content-item");

                double uVIndexByTomorrow = -9999;
                double visionByTomorrow = Double.parseDouble(weatherContentByTomorrow.get(1).select("span.op-8.fw-bold").text().replaceAll("km", "").trim());

                pw.println(naturalKeyByTomorrow + "," + dateTomorrow + "," + hourTomorrow + "," + province + "," + tempByHourTomorrow + "," + cloudDescriptionByHourTomorrow + "," + minTempByHourTomorrow + "," + maxTempByHourTomorrow + "," + humidityByHourTomorrow + "," + visionByTomorrow + "," + windByHourTomorrow + "," + uVIndexByTomorrow + "," + "Chưa rõ");
                System.out.println(naturalKeyByTomorrow + "," + dateTomorrow + "," + hourTomorrow + "," + province + "," + tempByHourTomorrow + "," + cloudDescriptionByHourTomorrow + "," + minTempByHourTomorrow + "," + maxTempByHourTomorrow + "," + humidityByHourTomorrow + "," + visionByTomorrow + "," + windByHourTomorrow + "," + uVIndexByTomorrow + "," + "Chưa rõ");
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
        String provinceModify = VNCharacterUtils.removeAccent(province).toUpperCase().replaceAll(" ", "").replaceAll("-", "");
        String timeModify = time.replaceAll(":", "");
        return provinceModify + dateModify + timeModify;
    }
}
