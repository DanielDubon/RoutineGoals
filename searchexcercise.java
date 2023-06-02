

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
import dataAccessLayer.EmbeddedNeo4j; 
import org.json.simple.JSONArray;
import dataAccessLayer.Ejercicio;

/**
 * Servlet implementation class SearchEjercicios
 */
@WebServlet("/searchexcercise")
public class searchexcercise extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public searchexcercise() {
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
	 	
	 	String intensidad = request.getParameter("intensidad");
	 	String duracion = request.getParameter("duracion");
	 	String objetivo = request.getParameter("objetivo");
	 	String estilo = request.getParameter("estilo");
	 	
	 	try ( EmbeddedNeo4j greeter = new EmbeddedNeo4j( "bolt://44.203.27.237:7687", "neo4j", "profiles-seamanship-ordnance" ) )
	        {
			 	LinkedList<Ejercicio> myEjercicios = greeter.getSearch(intensidad,duracion,objetivo,estilo);
	 	
			 	for (int i = 0; i < myEjercicios.size(); i++) {
			 		JSONObject caracteristicasStrings = new JSONObject();
			 		
			 		caracteristicasStrings.put("name", myEjercicios.get(i).getNombre());
			 		caracteristicasStrings.put("intensidad", myEjercicios.get(i).getIntensidad());
			 		caracteristicasStrings.put("duracion", myEjercicios.get(i).getDuracion());
			 		caracteristicasStrings.put("objetivo", myEjercicios.get(i).getObjetivo());
			 		caracteristicasStrings.put("estilo", myEjercicios.get(i).getEstilo());
			 	


			 		arrrest.add(caracteristicasStrings);
			 		
			 		
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