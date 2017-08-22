package com.pzybrick.iote2e.tests.bdbb;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreateCourse {
	private static final Logger logger = LogManager.getLogger(CreateCourse.class);

	
	public static CourseResult run( CourseRequest courseRequest ) throws Exception {
		double wayPtDeltaLat = (courseRequest.getEndLat() - courseRequest.getStartLat()) / (courseRequest.getNumWayPts()-1);  // From airport is a waypoint
		double wayPtDeltaLng = (courseRequest.getEndLng() - courseRequest.getStartLng()) / (courseRequest.getNumWayPts()-1);
		long timeMillis = courseRequest.getStartMsecs();
		
		List<CourseWayPoint> courseWayPoints = new ArrayList<CourseWayPoint>();
		for( int i=0 ; i< courseRequest.getNumWayPts() ; i++ ) {
			double lat = courseRequest.getStartLat() + (i * wayPtDeltaLat);
			double lng = courseRequest.getStartLng() + (i * wayPtDeltaLng);
			float alt = courseRequest.getStartAltFt() + (i*1000);
			if( alt > courseRequest.getCruiseAltFt() ) alt = courseRequest.getCruiseAltFt();
			CourseWayPoint wayPoint = new CourseWayPoint( lat, lng, alt, timeMillis);
			courseWayPoints.add( wayPoint );
			timeMillis += courseRequest.getFreqMSecs();
		}
		// this is a hack to set the descent altitudes
		float nextEndAltFt = courseRequest.getEndAltFt();
		for( int i=courseWayPoints.size()-1 ; i>0 ; i-- ) {
			CourseWayPoint wayPoint = courseWayPoints.get(i);
			wayPoint.setAlt(nextEndAltFt);
			if( nextEndAltFt == courseRequest.getCruiseAltFt()  ) break;
			nextEndAltFt += 1000;
			if( nextEndAltFt > courseRequest.getCruiseAltFt()  ) nextEndAltFt = courseRequest.getCruiseAltFt() ;
		}
		
		return new CourseResult().setCourseRequest(courseRequest).setCourseWayPoints(courseWayPoints);
	}
}
