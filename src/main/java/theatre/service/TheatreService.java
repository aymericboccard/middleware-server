package theatre.service;


import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@Stateless
@WebService
public class TheatreService {
	@EJB(beanName = "BK")
	private StatelessLocal metier;

	//test de la table EVENTS
	@WebMethod
	public String showAllEvents(){
		try {
			return metier.showAllEvents();
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	//test de la table EVENTS
	@WebMethod
	public String showEventById(@WebParam(name = "idevent") int idevent){
		try {
			return metier.showEventById(idevent);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return "Event Not Found!";
		}
	}
	
	//test de la table EVENTS
	@WebMethod
	public String showEventByName(@WebParam(name = "artistName") String artistName){
		try {
			return metier.showEventByName(artistName);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return "Event Not Found!";
		}
	}
	
	@WebMethod
	public String showBookedEvent(@WebParam(name = "userName") String userName){
		try {
			return metier.showBookedEvent(userName);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return "Event Not Found or Name Incorrect";
		}
	}

	@WebMethod
	public String addBooking(@WebParam(name = "idevent") int idevent,@WebParam(name = "seat") String seat,@WebParam(name = "username") String username, @WebParam(name = "cardnumber") String cardNumber,@WebParam(name = "cardholdername") String cardHolderName){
		try {
			return metier.addBooking(idevent,seat,username,cardNumber,cardHolderName);
		} catch (Exception e) {
			return e.getMessage();
		}
	}


	//test de la table BOOKING
	@WebMethod
	public String showBookingBySeat(@WebParam(name = "seat") String seat){
		try {
			return metier.showBookingBySeat(seat);
		} catch (Exception e) {
			return e.getMessage();
		}
		
	}
	
	//test de la table BOOKING
	@WebMethod
	public String showBookedSeats(@WebParam(name = "idevent") int idevent){
		try {
			return metier.showBookedSeats(idevent);
		} catch (Exception e) {
			return e.getMessage();
		}
		
	}
	
	@WebMethod
	public String showPricesByEvent(@WebParam(name = "idevent") int idevent){
		try {
			return metier.showPricesByEvent(idevent);
		} catch (Exception e) {
			return e.getMessage();
		}
	}
}
