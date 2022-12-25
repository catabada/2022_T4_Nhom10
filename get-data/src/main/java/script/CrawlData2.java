package script;

import constant.StringConstant;
import constant.VNCharacterUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/**
 * (thu vien) jsoup is a Java library for working with real-world HTML
 * => dùng Jsoup.connect(url).get() để lấy document html của url đó (connect tới url rồi đợi 3 giây(.timeout(3000)) rồi mới lấy)
 */
public class CrawlData2 {
    public static String run(String url) throws IOException, ParseException {
        Document doc = Jsoup.connect(url).timeout(3000).get();
        //thời gian hiện tại
//        String timerPresent = doc.select("#timer").text();
//        String timePresent = timerPresent.substring(0, timerPresent.indexOf("|") - 1).trim();
//        String datePresent = timerPresent.substring(timerPresent.indexOf("|") + 1, timerPresent.length()).trim();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String time = dtf.format(now);
        String [] timerPresent = time.split(" ");
        String datePresent = timerPresent[0];
        String timePresent = timerPresent[1];



        //tại folder để lưu trữ file (đường link được lưu trong StringConstant)
        File folder = new File(StringConstant.FOLDER_PATH_LOCAL);
        if (!folder.exists()) {
            folder.mkdirs();
            System.out.println("Created new folder");
        }
        //tạo file .csv để lưu trữ dữ liệu
        String fileName = System.currentTimeMillis() + ".csv";
        System.out.println("Creating file name: " + fileName + "......");
        File csvFile = new File(StringConstant.FOLDER_PATH_LOCAL + "/" + fileName);
        PrintWriter pw = new PrintWriter(csvFile);
//        pw.append("natural_key,date,time,province,temp,cloud_description,min_temp,max_temp,humidity,vision,wind_spd,uv_index,atmosphere_quality");

        //lấy tất cả các tình thành trong danh mục tỉnh thành phố
        Elements linkProvinces = doc.select(".item-link.shadow-sm.my-2.p-1 a");
        for (Element e : linkProvinces) {
            //đường link vào từng trang chi tiết của từng tỉnh thành
            String link = url + e.attr("href");
            System.out.println(link);
            Document documentProvince = Jsoup.connect(link).get();
            //tên tỉnh (vì nó chỉ có "thời tiết hà giang" mà muốn chỉ lấy "hà giang" thì phải bỏ chữ "thời tiết")
            String province = documentProvince.select(".breadcrumb-item.active").text().replace("Thời tiết ", "").replaceAll("- ","");
            String naturalKeyPresent = createNaturalKey(province, datePresent, timePresent);
            //
            int temperaturePresent = Integer.parseInt(documentProvince.select(".current-temperature").text().replaceAll("°", "").trim());
            //
            String cloudDescriptionPresent = documentProvince.select(".overview-caption p").text();
            Element weatherDetailPresent = documentProvince.select(".weather-detail").get(0);
            Elements attrs = weatherDetailPresent.select("span.text-white.op-8.fw-bold");

            String maxMinTempPresent = attrs.get(0).select("span.text-white").text();
            int minTempPresent = Integer.parseInt(maxMinTempPresent.split("/")[0].trim().replaceAll("°", ""));
            int maxTempPresent = Integer.parseInt(maxMinTempPresent.split("/")[1].trim().replaceAll("°", ""));
            double humidityPresent = Double.parseDouble(Double.parseDouble(attrs.get(1).select("span.text-white").text().replaceAll("%", "")) / 100 + "");
            double visionPresent = Double.parseDouble(attrs.get(2).select("span.text-white").text().replaceAll("km", "").trim());
            //gio => h
            double windPresent = Double.parseDouble(attrs.get(3).select("span.text-white").text().replace("km/h", "").trim()); //them trim
            //
            double UVIndex = Double.parseDouble(attrs.get(5).select("span.text-white").text());

            String qualityAtmospherePresent = documentProvince.select(".air-active").text();
            pw.println(naturalKeyPresent + "," + datePresent + "," + timePresent + "," + province + "," + temperaturePresent + "," + cloudDescriptionPresent + "," + minTempPresent + "," + maxTempPresent + "," + humidityPresent + "," + visionPresent + "," + windPresent + "," + UVIndex + "," + qualityAtmospherePresent);
            System.out.println(naturalKeyPresent + "," + datePresent + "," + timePresent + "," + province + "," + temperaturePresent + "," + cloudDescriptionPresent + "," + minTempPresent + "," + maxTempPresent + "," + humidityPresent + "," + visionPresent + "," + windPresent + "," + UVIndex + "," + qualityAtmospherePresent);
            // VÀO TRANG THỜI TIẾT THEO GIỜ ĐỂ LẤY THỜI TIẾT TẤT CẢ CÁC GIỜ ĐƯỢC DỰ ĐOÁN TRONG 24 GIỜ TỚI
            Document docToday = Jsoup.connect(link + "/theo-gio").get();
            Elements timesToday = docToday.select("details.weather-day");
            // BO DAU =
            for (int i = 0; i < timesToday.size(); i++) {
                String hour = timesToday.get(i).select(".summary-day").text().trim();
                if(hour.contains("/")) break;
                if (getHour(hour) > getHour(timePresent)) {
                    String naturalKeyByHour = createNaturalKey(province, datePresent, hour).trim();
                    int temperatureByHour = -9999;
                    String maxMinTempByHour = timesToday.get(i).select(".summary-temperature").text().replaceAll("C", "").trim();
                    int minTempByHour = Integer.parseInt(maxMinTempByHour.split("/")[0].trim().replaceAll("°", "").trim());
                    int maxTempByHour = Integer.parseInt(maxMinTempByHour.split("/")[1].trim().replaceAll("°", "").trim());
                    String cloudDescriptionByHour = timesToday.get(i).select(".summary-description").text();
                    double humidityByHour = Double.parseDouble(Double.parseDouble(timesToday.get(i).select(".summary-humidity").text().replaceAll("%", "")) / 100 + "");
                    double windByHour = Double.parseDouble(timesToday.get(i).select(".summary-speed").text().replaceAll("Wind", "").trim().replace("km/h", "").replaceAll("km", "").trim());
                    Elements weatherContentByHour = timesToday.get(0).select(".weather-content-item-sun");

                    String uVIndexByHour = weatherContentByHour.get(0).select(".d-flex.ml-auto.align-items-center").text();
                    //
                    String visionByHour = weatherContentByHour.get(1).select(".weather-sun").text().replaceAll("km", "").trim();
                    //
                    pw.println(naturalKeyByHour + "," + datePresent + "," + hour + "," + province + "," + temperatureByHour + "," + cloudDescriptionByHour + "," + minTempByHour + "," + maxTempByHour + "," + humidityByHour + "," + visionByHour + "," + windByHour + "," + uVIndexByHour + "," + "Chưa rõ");
                    System.out.println(i+"  "+naturalKeyByHour + "," + datePresent + "," + hour + "," + province + "," + temperatureByHour + "," + cloudDescriptionByHour + "," + minTempByHour + "," + maxTempByHour + "," + humidityByHour + "," + visionByHour + "," + windByHour + "," + uVIndexByHour + "," + "Chưa rõ");
                }

            }
            // VÀO TRANG THỜI TIẾT NGÀY MAI ĐỂ LẤY THỜI TIẾT TẤT CẢ CÁC GIỜ ĐƯỢC DỰ ĐOÁN VÀO NGÀY MAI
            Document docTomorrow = Jsoup.connect(link + "/ngay-mai").get();
            Elements timesTomorrow = docTomorrow.select("details.weather-day");

            String dateTomorrow = "";  // Start date
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(datePresent));
            c.add(Calendar.DATE, 1);  // number of days to add
            dateTomorrow = sdf.format(c.getTime());
            //
//            String dateTomorrow = docTomorrow.select(".header-thoi-tiet-ngay span.d-block.text-right").text();
            //
            for (Element ele : timesTomorrow) {
                String hourTomorrow = ele.select(".summary-day").text();
                String naturalKeyByTomorrow = createNaturalKey(province, dateTomorrow, hourTomorrow);
                String maxMinTempByHourTomorrow = ele.select(".summary-temperature").text();
                int minTempByHourTomorrow = Integer.parseInt(maxMinTempByHourTomorrow.split("/")[0].trim().replaceAll("°", ""));
                int maxTempByHourTomorrow = Integer.parseInt(maxMinTempByHourTomorrow.split("/")[1].trim().replaceAll("°", ""));
                String cloudDescriptionByHourTomorrow = ele.select(".summary-description").text();
                double humidityByHourTomorrow = Double.parseDouble(Double.parseDouble(ele.select(".summary-humidity").text().replaceAll("%", "")) / 100 + "");
                double windByHourTomorrow = Double.parseDouble(ele.select(".summary-speed").text().replaceAll("Wind", "").trim().replace("km/h", "").replaceAll("km", "").trim());
                int tempByHourTomorrow = -9999;
                //
                Elements weatherContentByTomorrow = ele.select(".weather-content-item-sun");
                //
                double uVIndexByTomorrow = -9999;
                double visionByTomorrow = Double.parseDouble(weatherContentByTomorrow.get(1).select(".weather-sun").text().replaceAll("km", "").trim());

                pw.println(naturalKeyByTomorrow + "," + dateTomorrow + "," + hourTomorrow + "," + province + "," + tempByHourTomorrow + "," + cloudDescriptionByHourTomorrow + "," + minTempByHourTomorrow + "," + maxTempByHourTomorrow + "," + humidityByHourTomorrow + "," + visionByTomorrow + "," + windByHourTomorrow + "," + uVIndexByTomorrow + "," + "Chưa rõ");
                System.out.println(naturalKeyByTomorrow + "," + dateTomorrow + "," + hourTomorrow + "," + province + "," + tempByHourTomorrow + "," + cloudDescriptionByHourTomorrow + "," + minTempByHourTomorrow + "," + maxTempByHourTomorrow + "," + humidityByHourTomorrow + "," + visionByTomorrow + "," + windByHourTomorrow + "," + uVIndexByTomorrow + "," + "Chưa rõ");
            }
        }
        pw.close();
        return csvFile.getName();
    }

    //sua lai vi no dung pm va am
    public static int getHour(String timer) {
        if(timer.substring(timer.length() -2).equals("PM")){
            return Integer.parseInt(timer.substring(0, 2))+12;
        }
        return Integer.parseInt(timer.substring(0, 2));
    }

    public static String createNaturalKey(String province, String date, String time) {
        String dateModify = date.replaceAll("/", "");
        String provinceModify = VNCharacterUtils.removeAccent(province).toUpperCase().replaceAll(" ", "").replaceAll("-", "");
        String timeModify = "";
        if(time.substring(time.length() -2).equals("PM")){
            String timer = (Integer.parseInt(time.substring(0, 2))+12) +time.substring(2);
            timeModify = timer.replaceAll(":", "").replaceAll("PM", "").trim();
        }else{
            timeModify = time.replaceAll(":", "").replaceAll("AM", "").trim();
        }

        return provinceModify + dateModify + timeModify;
    }

    public static void main(String[] args) throws IOException, ParseException {
        CrawlData2 c1 = new CrawlData2();

//        c1.run("https://thoitiet.vn");
        c1.run("https://thoitiet.edu.vn");

//        System.out.println(getHour("04:00 PM"));

//        System.out.println(createNaturalKey("ha noi", "16/07/2001","04:00 PM"));
    }
}
