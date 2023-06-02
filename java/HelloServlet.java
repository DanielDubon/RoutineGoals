

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;


import dataAccessLayer.EmbeddedNeo4j;
import dataAccessLayer.Ejercicio;

/**
 * Servlet implementation class HelloServlet
 */
@WebServlet("/HelloServlet")
public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HelloServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
			PrintWriter out = response.getWriter();
		 	response.setContentType("application/json");
		 	response.setCharacterEncoding("UTF-8");
		 	JSONObject myResponse = new JSONObject();
		 	
		 	JSONObject  ejercicio = new JSONObject ();
		 	
		 	JSONArray arrrest = new JSONArray();
		 	
		 	ArrayList<Ejercicio> arrRest = new ArrayList();
		 	
		 	
		 	try ( EmbeddedNeo4j greeter = new EmbeddedNeo4j("bolt://44.203.27.237:7687", "neo4j", "profiles-seamanship-ordnance" ) )
		        {
				 	LinkedList<Ejercicio> myEjercicios = greeter.getStrings();
				 	
				 	for (int i = 0; i < myEjercicios.size(); i++) {
				 		JSONObject caracteristicasString = new JSONObject();
				 		
				 		
				 		caracteristicasString.put("name", myEjercicios.get(i).getNombre());
				 		caracteristicasString.put("intensidad", myEjercicios.get(i).getIntensidad());
				 		caracteristicasString.put("duracion", myEjercicios.get(i).getDuracion());
				 		caracteristicasString.put("objetivo", myEjercicios.get(i).getObjetivo());
				 		caracteristicasString.put("estilo", myEjercicios.get(i).getEstilo());


				 		arrrest.add(caracteristicasString);
				 		
				 		
				 	}
				 	
		        	
		        } catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 	myResponse.put("Entrenamientos", arrrest);
		 	myResponse.put("conteo", arrrest.size()); 
		 	//Guardo la cantidad de actores
		 	out.println(myResponse);
		 	out.flush();   
		 	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}