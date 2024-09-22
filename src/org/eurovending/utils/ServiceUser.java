package org.eurovending.utils;

import java.sql.SQLException;

import org.eurovending.dao.LocationDAO;
import org.eurovending.dao.UserLocationListDAO;
import org.eurovending.pojo.Location;
import org.eurovending.pojo.UsersLocationList;

public class ServiceUser {

	public void insertUserLocation(UsersLocationList userLocList) throws SQLException {
		UserLocationListDAO usrLocListDao = new UserLocationListDAO();
		Location location = new Location();
		LocationDAO ldao = new LocationDAO();
		if(userLocList.getLocationName()!=null) {
			int[] idUserCont = new int[userLocList.getLocationName().size()];
			int[] idLocationCont = new int[userLocList.getLocationName().size()];
			for(int i =0;i<userLocList.getLocationName().size();i++) {
				location = ldao.getLocationByName(userLocList.getLocationName().get(i));
				idLocationCont[i]= location.getId();
				idUserCont[i]= userLocList.getIdUser();
				userLocList.setIdUserList(idUserCont);
			}
			userLocList.setIdLocationList(idLocationCont);
			usrLocListDao.createTableLocationUserLocationList(userLocList.getIdUser());
			int count = 0;								
			count = usrLocListDao.getUserLocationListIfExist(userLocList.getIdUser());
			if(count == 0) {								
			usrLocListDao.insertUserLocationList(userLocList);
			}
			else {
				usrLocListDao.dropUserLocationList(userLocList.getIdUser());
				usrLocListDao.createTableLocationUserLocationList(userLocList.getIdUser());
				usrLocListDao.insertUserLocationList(userLocList);
			}
			}	
		
	}

}
