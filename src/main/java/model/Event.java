package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the EVENTS database table.
 * 
 */
@Entity
@Table(name = "EVENTS")

@NamedQueries({ @NamedQuery(name = "Event.getEventById", query = "SELECT e FROM Event e WHERE e.idevent = :idevent"),
	@NamedQuery(name = "Event.getEventByName", query = "SELECT e FROM Event e WHERE e.artistName = :artistName"),
	@NamedQuery(name = "Event.getAllEvents", query = "SELECT e FROM Event e"),
	@NamedQuery(name = "Event.getIdEvent", query = "SELECT e.idevent FROM Event e"),
	@NamedQuery(name = "Event.getCategory", query = "SELECT e.category FROM Event e WHERE e.idevent = :idevent")
})

public class Event implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "IDEVENT")
	private int idevent;

	@Column(name = "ARTISTNAME")
	private String artistName;

	@Column(name = "DATEEVENT")
	private String date;

	@Column(name = "CATEGORY")
	private String category;

	public Event() {
	}

	public int getIdEvent() {
		return this.idevent;
	}

	public void setIdEvent(int idevent) {
		this.idevent = idevent;
	}

	public String getArtistName() {
		return this.artistName;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "\nEvent [ArtistName=" + artistName + ", date=" + date + ", category=" + category + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (artistName == null) {
			if (other.artistName != null)
				return false;
		} else {
			if (date == null) {
				if (other.date != null)
					return false;
			} else {
				if (category == null) {
					if (other.category != null)
						return false;
				} else {
					if (!artistName.equals(other.artistName) || !category.equals(other.category)
							|| !date.equals(other.date)) {
						return false;
					}
				}
			}
		}
		return true;
	}

}