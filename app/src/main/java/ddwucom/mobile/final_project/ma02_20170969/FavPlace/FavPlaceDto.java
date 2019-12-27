package ddwucom.mobile.final_project.ma02_20170969.FavPlace;

import java.io.Serializable;

public class FavPlaceDto implements Serializable {

	private long id;
	private String favName; // 날짜
	private String favPlace; // 시간

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFavName() {
		return favName;
	}

	public void setFavName(String favName) {
		this.favName = favName;
	}

	public String getFavPlace() {
		return favPlace;
	}

	public void setFavPlace(String favPlace) {
		this.favPlace = favPlace;
	}
}
