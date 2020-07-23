package com.restful;

import java.util.List;
import javax.ws.rs.*;
import javax.persistence.*;
import com.google.gson.Gson;
import com.entity.Survey;

@Path("survey")
public class SurveyResource{
	@PersistenceContext(unitName = "SurveyUnit")
	private EntityManager em = Persistence.createEntityManagerFactory("SurveyUnit").createEntityManager();
	
	public SurveyResource() {}
	
	
	@SuppressWarnings("unchecked")
	@GET
	@Produces({ "application/json"})
	@Consumes({"*/*"})
	public String findAll(){
		List<Survey> surveyList = null;
		surveyList = em.createQuery("SELECT s FROM Survey s").getResultList();
		em.close();
		return new Gson().toJson(surveyList);
	}
	
	
	@POST
	@Path("create")
	@Consumes({"*/*"})
	public Survey createSurvey(@FormParam("fName")String fName,
								@FormParam("lName")String lName,
								@FormParam("address")String address,
								@FormParam("city")String city,
								@FormParam("state")String state,
								@FormParam("zip")String zip,
								@FormParam("phone")String phone,
								@FormParam("email")String email,
								@FormParam("date")String date,
								@FormParam("campus")String campus,
								@FormParam("reason")String reason,
								@FormParam("likelihood")String likelihood
								) {
		Survey newSurvey = new Survey();
		newSurvey.setFirstName(fName);
		newSurvey.setLastName(lName);
		newSurvey.setAddress(address);
		newSurvey.setCity(city);
		newSurvey.setState(state);
		newSurvey.setZip(zip);
		newSurvey.setPhone(phone);
		newSurvey.setEmail(email);
		newSurvey.setDate(date);
		newSurvey.setCampus(campus);
		newSurvey.setReason(reason);
		newSurvey.setLikelihood(likelihood);
		
		em.getTransaction().begin();
		em.persist(newSurvey);
		em.getTransaction().commit();
		em.close();
//		return new Gson().toJson(newSurvey);
		return newSurvey;
	}
	

	
	
}


