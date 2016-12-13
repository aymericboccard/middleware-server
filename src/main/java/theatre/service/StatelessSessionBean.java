/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2013 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package theatre.service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jws.WebParam;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

import model.Event;
import model.MyBank;
import model.BankClient;
import model.Booking;

@Stateless(name = "BK")
@TransactionManagement(TransactionManagementType.BEAN)
public class StatelessSessionBean implements StatelessLocal {

	@Resource
	private EJBContext context;
	@PersistenceContext(type = PersistenceContextType.TRANSACTION)
	private EntityManager em;

	// pour tester le fonctionnement de la table EVENTS
	@SuppressWarnings("unchecked")
	@Override
	public String showAllEvents() {
		Query query = em.createNamedQuery("Event.getAllEvents");

		List<Event> events = (List<Event>) query.getResultList();

		return events.toString();
	}

	// pour tester le fonctionnement de la table EVENTS
	@Override
	public String showEventById(int idevent) {
		Query query = em.createNamedQuery("Event.getEventById");
		query.setParameter("idevent", idevent);
		Event event = (Event) query.getSingleResult();
		return event.toString();
	}
	
	@Override
	public String showBookedSeats(int idevent) {
		Query query = em.createNamedQuery("Booking.getBookingSeats");
		query.setParameter("idevent", idevent);
		List<String> seats = (List<String>) query.getResultList();
		return seats.toString();
	}

	@Override
	public String showEventByName(String artistName) {
		Query query = em.createNamedQuery("Event.getEventByName");
		query.setParameter("artistName", artistName);
		List<Event> events = (List<Event>) query.getResultList();
		return events.toString();
	}
	
	@Override
	public String showBookedEvent(String userName) {
		Query query1 = em.createQuery("SELECT e.artistName FROM Booking b, Event e WHERE b.userName = :userName AND b.idEvent = e.idevent");
		query1.setParameter("userName", userName);
		Query query2 = em.createQuery("SELECT  e.date FROM Booking b, Event e WHERE b.userName = :userName AND b.idEvent = e.idevent");
		query2.setParameter("userName", userName);
		Query query3 = em.createQuery("SELECT b.seat FROM Booking b, Event e WHERE b.userName = :userName AND b.idEvent = e.idevent");
		query3.setParameter("userName", userName);
		List<String> names = (List<String>) query1.getResultList();
		List<String> dates = (List<String>) query2.getResultList();
		List<String> seats = (List<String>) query3.getResultList();
		
		String result = "";
		
		for(int i=0; i<names.size(); i++){
			result += names.get(i)+", "+dates.get(i)+", "+seats.get(i)+"\n";
		}
	
		
		return result.isEmpty() ? "You have no reservation with this name" : result;
	}
	
	@Override
	public String showBookedSeatsByEventInSection(int idevent, String section) {
		
		if(section.equals("A") || section.equals("B") || section.equals("C") || section.equals("D")){
			
			Query query = em.createNamedQuery("Booking.getBookedSeatsByEventIn".concat(section));
			query.setParameter("idEvent", idevent);
			List<String> result = (List<String>) query.getResultList();
			
			return result.isEmpty() ? "Aucun siège réservé pour cette section" : result.toString();
			
		}else{
			return "La section "+section+ " n'existe pas";
		}
	}
	
	@Override
	public String showPricesByEvent(int idevent){
		try{
			Query query = em.createNamedQuery("Event.getCategory");
			query.setParameter("idevent", idevent);
			String category = (String)query.getSingleResult();
			
			String result = "";
			switch(category){
			case "C1":
				result = "Sector A: 15,00€ \nSector B: 12,50€ \nSector C: 10,00€ \n Sector D: 5,00€";
				break;
			case "C2":
				result = "Sector A: 30,00€ \nSector B: 25,00€ \nSector C: 20,00€ \n Sector D: 10,00€";
				break;
			case "C3":
				result = "Sector A: 60,00€ \nSector B: 50,00€ \nSector C: 40,00€ \n Sector D: 20,00€";
				break;
			case "C4":
				result = "Sector A: 150,00€ \nSector B: 125,00€ \nSector C: 100,00€ \n Sector D: 50,00€";
				break;
			}
			
			return result;
		}catch(Exception e){
			return e.getMessage();
		}
	}

	@Override
	public boolean addBooking(int idevent, String seat, String username, String cardNumber, String cardHolderName) {
		try {
	
			MyBank bnp = new MyBank();

			if (bnp.checkCardValidity(new BankClient(new BigInteger(cardNumber), cardHolderName))) {
				UserTransaction utx = context.getUserTransaction();

				utx.begin();

				Booking booking = new Booking();
				booking.setIdEvent(idevent);
				booking.setSeat(seat);
				booking.setUserName(username);

				em.persist(booking);
				utx.commit();
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public float getPriceForSeat(int idevent, String section) {
		
		if(section.equals("A") || section.equals("B") || section.equals("C") || section.equals("D")){
			
			HashMap<String, Float> prices = null;
			prices.put("C1", (float) 5);
			prices.put("C2", (float) 10);
			prices.put("C3", (float) 20);
			prices.put("C4", (float) 50);
			
			HashMap<String, Float> coef = null;
			coef.put("A", (float) 3);
			coef.put("B", (float) 2.5);
			coef.put("C", (float) 2);
			coef.put("D", (float) 1);
			
			Query query = em.createNamedQuery("Event.getEventById");
			query.setParameter("idevent", idevent);
			Event result = (Event) query.getSingleResult();
			
			if(result == null){
				return -1;
			}else{
				return prices.get(result.getCategory())*coef.get(section);
			}
			
		}else{
			return -1;
		}
	}

	// pour tester le fonctionnement de la table BOOKING
	@Override
	public String showBookingBySeat(String seat) {

		Query query = em.createNamedQuery("Booking.getBookingBySeat");
		query.setParameter("seat", seat);

		List<Booking> Bookings = (List<Booking>) query.getResultList();

		if (Bookings.isEmpty()) {
			return "seat empty!";
		} else {
			return Bookings.toString();
		}
	};

	public boolean checkReservation(int idevent, String seat) throws Exception {

		try {
			Query query = em.createNamedQuery("Booking.getBookingByIdEventandSeat");
			query.setParameter("idEvent", idevent);
			query.setParameter("seat", seat);
			int number = query.getResultList().size();
			if (number > 0) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	public boolean checkAvailability(int idevent) throws Exception {
		try {

			Query testQuery = em.createNamedQuery("Event.getIdEvent");
			List<Integer> eventsIds = (List<Integer>) testQuery.getResultList();
			if (!eventsIds.contains(idevent)) {
				return false;
			}

			Query query = em.createNamedQuery("Booking.getSeatsoccupiednumber");
			query.setParameter("idEvent", idevent);
			long number = (long) query.getSingleResult();
			// return number;

			if (number < 670) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}

	}

	public boolean checkAvailabilityBySection(int idevent, String section) throws Exception {
		try {

			Query testQuery = em.createNamedQuery("Event.getIdEvent");
			List<Integer> eventsIds = (List<Integer>) testQuery.getResultList();
			if (!eventsIds.contains(idevent)) {
				return false;
			}

			char firstletter = 0;
			if (!section.isEmpty()) {
				firstletter = section.charAt(0);
			} else {
				return false;
			}

			switch (firstletter) {
			case 'A':
				Query query1 = em.createNamedQuery("Booking.getBookingByEventandsectionA");
				query1.setParameter("idEvent", idevent);
				// query1.setParameter("seat",seat);
				if ((long) query1.getSingleResult() < 25) {
					return true;
				} else {
					return false;
				}
			case 'B':
				Query query2 = em.createNamedQuery("Booking.getBookingByEventandsectionB");
				query2.setParameter("idEvent", idevent);
				// query2.setParameter("seat",seat);
				if ((long) query2.getSingleResult() < 45) {
					return true;
				} else {
					return false;
				}

			case 'C':
				Query query3 = em.createNamedQuery("Booking.getBookingByEventandsectionC");
				query3.setParameter("idEvent", idevent);
				// query3.setParameter("seat",seat);
				if ((long) query3.getSingleResult() < 100) {
					return true;
				} else {
					return false;
				}
			case 'D':
				Query query4 = em.createNamedQuery("Booking.getBookingByEventandsectionD");
				query4.setParameter("idEvent", idevent);
				// query4.setParameter("seat",seat);
				if ((long) query4.getSingleResult() < 500) {
					return true;
				} else {
					return false;
				}
			default:
				return false;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}

	}
}
