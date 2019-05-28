import java.util.ArrayList;
import java.util.List;

/**
 * 这个类是wgs84 坐标系统下计算球面距离和球面面积，方法是openlayers 里面方法用java 重写，
 * 经测试没问题，如果有问题联系qq：120378731
 */
public class CalUtils {
    private static final double EARTH_RADIUS = 6378137.0D;

    /**
     * 计算两点距离
     * @param startLon 起点经度
     * @param startLat 起点纬度
     * @param endLon 终点经度
     * @param endLat 终点纬度
     * @return  单位米
     */
    public static double calDistance(double startLon, double startLat, double endLon, double endLat) {
        double lat1 = toRadians(startLat);
        double lat2 = toRadians(endLat);
        double deltaLatBy2 = (lat2 - lat1) / 2.0D;
        double deltaLonBy2 = toRadians(endLon - startLon) / 2.0D;
        double a = Math.sin(deltaLatBy2) * Math.sin(deltaLatBy2) + Math.sin(deltaLonBy2) * Math.sin(deltaLonBy2) * Math.cos(lat1) * Math.cos(lat2);
        return EARTH_RADIUS * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1.0D - a));
    }

    /**
     * 一个环的面积
     * @param coordinates  [[lon0,lat0],[lon1,lat1],[lon2,lat2],[lon3,lat3] ... [lonn,latn],[lon0,lat0]]
     * @return 单位平方米
     */
    public static double calArea(List<double[]> coordinates) {
        double area = 0.0D;
        int len = coordinates.size();
        if (len < 3) {
            return 0.0D;
        } else {
            double x1 = coordinates.get(len - 1)[0];
            double y1 = coordinates.get(len - 1)[1];

            for (int i = 0; i < len; ++i) {
                double x2 = coordinates.get(i)[0];
                double y2 = coordinates.get(i)[1];
                area += toRadians(x2 - x1) * (2.0D + Math.sin(toRadians(y1)) + Math.sin(toRadians(y2)));
                x1 = x2;
                y1 = y2;
            }

            return Math.abs(area * EARTH_RADIUS * EARTH_RADIUS / 2.0D);
        }
    }

    /**
     * 度转弧度
     * @param angleInDegrees
     * @return
     */
    static double toRadians(double angleInDegrees) {
        return angleInDegrees * 3.141592653589793D / 180.0D;
    }

    public static void main(String args[]) {
        System.out.println("Hello World!");
        double distance = calDistance(116.0, 39.0, 112, 35.0);
        System.out.println("距离是："+distance+"米");

        List<double[]> coordinates = new ArrayList<>();
        coordinates.add(new double[]{20,30});
        coordinates.add(new double[]{65,37});
        coordinates.add(new double[]{75,-23});
        coordinates.add(new double[]{35,-57});
        coordinates.add(new double[]{10,-30});
        coordinates.add(new double[]{20,30});
        double area = calArea(coordinates);
        System.out.println("面积是："+area+"米²");
    }
}
