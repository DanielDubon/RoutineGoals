package dataAccessLayer;

public class Ejercicio {
	private String name;
    private String intensidad;
    private String duracion;
    private String objetivo;
    private String estilo;
   

    public Ejercicio(String name, String intensidad, String duracion, String objetivo, String estilo) {
        this.name = name;
        this.intensidad = intensidad;
        this.duracion = duracion;
        this.objetivo = objetivo;
        this.estilo = estilo;
    
    }

    public String getNombre() {
        return name;
    }

    public void setNombre(String nombre) {
        this.name = nombre;
    }

    public String getIntensidad() {
        return intensidad;
    }

    public void setIntensidad(String intensidad) {
        this.intensidad = intensidad;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    
    public String toString() {
		return name + ";" + intensidad + ";" + duracion + ";" + objetivo + ";" + estilo + ";" ;
    	
    }
}