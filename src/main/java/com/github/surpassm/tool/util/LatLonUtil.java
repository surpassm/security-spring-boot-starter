package com.github.surpassm.tool.util;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author minchao
 * @version V1.0
 * Title:LatLonUtil
 * Description:经纬度周边范围计算通用类
 * Company: www.cqprosper.com
 * Package com.cqprosper.ods.collectionUtil
 * date 2017年8月31日下午3:10:42
 */
public class LatLonUtil {
	/**
	 * 圆周率
	 */
    private static final double PI = 3.14159265;
    /**
     * 地球半径
     */
    private static final double EARTH_RADIUS = 6378137;  
    /**
     * 计算半径
     */
    private static final double RAD = Math.PI / 180.0;
    /**
     * 
     * @Description:根据经纬度和半径计算经纬度范围
     * @param lat
     * @param lon
     * @param raidus 单位米(半径)
     * @return double[minLat, minLng, maxLat, maxLng] 
     * @throws
     */
    public static Map<String,Double> getAround(double lat, double lon, int raidus) {
    	
	    Double latitude = lat;
	    Double longitude = lon;

	    Double degree = (24901 * 1609) / 360.0;
	    double raidusMile = raidus;

	    Double dpmLat = 1 / degree;
	    Double radiusLat = dpmLat * raidusMile;
	    Double minLat = latitude - radiusLat;
	    Double maxLat = latitude + radiusLat;

	    Double mpdLng = degree * Math.cos(latitude * (PI / 180));
	    Double dpmLng = 1 / mpdLng;
	    Double radiusLng = dpmLng * raidusMile;
	    Double minLng = longitude - radiusLng;
	    Double maxLng = longitude + radiusLng;
	    Map<String,Double> result = new HashMap<>();
	    result.put("minLat", minLat);
	    result.put("minLng", minLng);
	    result.put("maxLat", maxLat);
	    result.put("maxLng", maxLng);
	    
	    return result;
	}
    /**
     * 
     * @Description:计算任意两点(经纬度)距离
     * @param long1 第一个经度
     * @param lat1 第一个纬度
     * @param long2 第二经度
     * @param lat2 第二纬度
     * @return double 
     * @throws
     */
    public static double distanceByLongNLat(double long1, double lat1, double long2, double lat2) {
        double a, b;
        lat1 = lat1 * Math.PI / 180.0;
        lat2 = lat2 * Math.PI / 180.0;
        a = lat1 - lat2;
        b = (long1 - long2) * Math.PI / 180.0;
        double d;
        double sa2, sb2;
        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2 * EARTH_RADIUS * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1) * Math.cos(lat2) * sb2 * sb2));
        return d;
    }

	/**
	 * 距离单位为：米
	 */
	private static double EARTH_RADIUS1 = 6371.393;
	private static double rad(double d){
		return d * Math.PI / 180.0;
	}
	/**
	 * 计算两个经纬度之间的距离
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return
	 */
	public static double GetDistance(double lat1, double lng1, double lat2, double lng2){

		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
				Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
		s = s * EARTH_RADIUS1;
		s = Math.round(s * 1000);
		return s;
	}

	/**
	 * 计算是否在圆上（单位/千米）
	 *
	 * @Title: GetDistance
	 * @Description: TODO()
	 * @param radius 半径
	 * @param lat1  纬度
	 * @param lng1  经度
	 * @return
	 * @return double
	 * @throws
	 */
	public static boolean isInCircle(double radius,double lat1, double lng1, double lat2, double lng2)
	{
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
				Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s < radius;
	}

	/**
	 * 是否在矩形区域内
	 * @Title: isInArea
	 * @Description: TODO()
	 * @param lat 测试点经度
	 * @param lng 测试点纬度
	 * @param minLat 纬度范围限制1
	 * @param maxLat 纬度范围限制2
	 * @param minLng 经度限制范围1
	 * @param maxLng 经度范围限制2
	 * @return
	 * @return boolean
	 * @throws
	 */
	public static boolean isInRectangleArea(double lat,double lng,double minLat,
											double maxLat,double minLng,double maxLng){
		//如果在纬度的范围内
		if(isInRange(lat, minLat, maxLat)){
			if(minLng*maxLng>0){
				if(isInRange(lng, minLng, maxLng)){
					return true;
				}else {
					return false;
				}
			}else {
				if(Math.abs(minLng)+Math.abs(maxLng)<180){
					if(isInRange(lng, minLng, maxLng)){
						return true;
					}else {
						return false;
					}
				}else{
					double left = Math.max(minLng, maxLng);
					double right = Math.min(minLng, maxLng);
					if(isInRange(lng, left, 180)||isInRange(lng, right,-180)){
						return true;
					}else {
						return false;
					}
				}
			}
		}else{
			return false;
		}
	}

	/**
	 * 判断是否在经纬度范围内
	 * @Title: isInRange
	 * @Description: TODO()
	 * @param point
	 * @param left
	 * @param right
	 * @return
	 * @return boolean
	 * @throws
	 */
	public static boolean isInRange(double point, double left,double right){
		if(point>=Math.min(left, right)&&point<=Math.max(left, right)){
			return true;
		}else {
			return false;
		}
	}


	/**
	 * 判断点是否在多边形内
	 * @Title: IsPointInPoly
	 * @Description: TODO()
	 * @param point 测试点
	 * @param pts 多边形的点
	 * @return
	 * @return boolean
	 * @throws
	 */
	public static boolean isInPolygon(Point2D.Double point, List<Point2D.Double> pts){

		int n = pts.size();
		boolean boundOrVertex = true;
		//交叉点数量
		int intersectCount = 0;
		//浮点类型计算时候与0比较时候的容差
		double precision = 2e-10;
		//临近顶点
		Point2D.Double p1, p2;
		//当前点
		Point2D.Double p = point;

		p1 = pts.get(0);
		for(int i = 1; i <= n; ++i){
			if(p.equals(p1)){
				return true;
			}

			p2 = pts.get(i % n);
			if(p.x < Math.min(p1.x, p2.x) || p.x > Math.max(p1.x, p2.x)){
				p1 = p2;
				continue;
			}

			//射线穿过算法
			if(p.x > Math.min(p1.x, p2.x) && p.x < Math.max(p1.x, p2.x)){
				if(p.y <= Math.max(p1.y, p2.y)){
					if(p1.x == p2.x && p.y >= Math.min(p1.y, p2.y)){
						return true;
					}

					if(p1.y == p2.y){
						if(p1.y == p.y){
							return true;
						}else{
							++intersectCount;
						}
					}else{
						double xinters = (p.x - p1.x) * (p2.y - p1.y) / (p2.x - p1.x) + p1.y;
						if(Math.abs(p.y - xinters) < precision){
							return true;
						}

						if(p.y < xinters){
							++intersectCount;
						}
					}
				}
			}else{
				if(p.x == p2.x && p.y <= p2.y){
					Point2D.Double p3 = pts.get((i+1) % n);
					if(p.x >= Math.min(p1.x, p3.x) && p.x <= Math.max(p1.x, p3.x)){
						++intersectCount;
					}else{
						intersectCount += 2;
					}
				}
			}
			p1 = p2;
		}
		//偶数在多边形外
		if(intersectCount % 2 != 0){
			return true;
			//奇数在多边形内
		} else {
			return false;
		}
	}




}
