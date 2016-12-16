package theatre.service;

import javax.ejb.Remote;

@Remote
public interface StatelessLocal {
	public String showAllEvents();
	public String showEventById(int idevent);
	public String showBookedSeats(int idevent);
	public String showEventByName(String artistName);
	public String showBookingBySeat(String seat);
	public String showBookedEvent(String userName);
	public String showPricesByEvent(int idevent);
	public boolean addBooking(int idevent, String seat, String username , String cardNumber, String cardHolderName);
	public String showBookedSeatsByEventInSection(int idevent, String section);
	public float getPriceForSeat(int idevent, String section);
	public boolean checkReservation(int idevent, String seat) throws Exception;
	public boolean checkAvailability(int idevent) throws Exception;
	public boolean checkAvailabilityBySection(int idevent, String section) throws Exception;
}
