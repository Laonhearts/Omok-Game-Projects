import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;

public class ApiExplorer {
    public static void main(String[] args) throws IOException {
        StringBuilder urlBuilder = new StringBuilder(
                "http://openapi.epost.go.kr/postal/retrieveNewAdressAreaCdService/retrieveNewAdressAreaCdService/getNewAddressListAreaCd"); /*
         * URL
         */
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8")
                + "=eKZl6rkQywvMBSq%2BReH0s0sqLAXCqJ4iQDnaHwT9bHCQumCmQuYy8I%2B42juw9xXDCoH3NMB7P8DPaxT2IP%2B8Ag%3D%3D"); /*
         * Service
         * Key
         */
        urlBuilder.append("&" + URLEncoder.encode("searchSe", "UTF-8") + "="
                + URLEncoder.encode("post", "UTF-8")); /* dong : 동(읍/면)명road :도로명[default]post : 우편번호 */
        urlBuilder.append(
                "&" + URLEncoder.encode("srchwrd", "UTF-8") + "=" + URLEncoder.encode("07952", "UTF-8")); /* 검색어 */
        urlBuilder.append("&" + URLEncoder.encode("countPerPage", "UTF-8") + "="
                + URLEncoder.encode("10", "UTF-8")); /* 페이지당 출력될 개수를 지정 */
        urlBuilder.append("&" + URLEncoder.encode("currentPage", "UTF-8") + "="
                + URLEncoder.encode("1", "UTF-8")); /* 출력될 페이지 번호 */
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());
    }
}