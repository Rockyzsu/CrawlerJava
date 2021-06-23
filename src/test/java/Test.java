import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author 97646
 * @date 2020/12/11
 */
public class Test {

    public static void main(String[] args) {
//        byte[] ad = "00068702850".getBytes();
//        ad[10] = 0x00;
//        System.out.println(System.getProperty("user.dir"));
        try {
            BufferedReader in = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\input.txt"));
//            String str;
//            while ((str = in.readLine()) != null) {
//                System.out.println(str);
//            }
//            System.out.println(str);
            String strLine;
            while (StringUtils.isNotEmpty(strLine = in.readLine())) {
//                url.append("https://store.coupang.com/vp/vendors/");
//                url.append(strLine);
//                url.append("/product/lists?outboundShippingPlaceId=&attributeFilters=&brand=&componentId=&keyword=&maxPrice=&minPrice=&pageNum=1&rating=0&scpLanding=true");
////        url.append("&sortTypeValue=").append(sortTypeValue);
//                objects.add(url.toString());
                System.out.println(strLine);
            }
        } catch (IOException e) {
        }
    }


}
