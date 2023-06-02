$(document).ready(function(){
    
    alert("Bienvenido a Routine Goals ")
    fillDataSelect();
    loadAllExcercises();


    
});

var productList = [];


function fillDataSelect(){
    var intensidades = [    
        { value: "baja", text: "baja" },
        { value: "media", text: "media" },
        { value: "alta", text: "alta" },
        { value: "", text: "ninguno" }
        ];
    
    var selectIntensidad = $('#intensidad-select');
    
    $.each(intensidades, function(key, intensidad) {
        var optionIntensidad = $('<option></option>').attr('value', intensidad.value).text(intensidad.text);
        selectIntensidad.append(optionIntensidad);
    });

    var duraciones =[
        {value:"larga", text:"larga"},
        {value:"media", text:"media"},
        {value:"corta", text:"corta"},
        { value: "", text: "ninguno" }
    ];

    var selectDuracion = $("#duracion-select");

    $.each(duraciones, function(key, duracion){
        var optionDuracion = $('<option></option>').attr("value", duracion.value).text(duracion.text);
        selectDuracion.append(optionDuracion);
    });

    var Estilos = [
        {value: "Solitario", text:"Solitario"},
        {value: "En pareja", text:"En pareja"},
        {value: "En grupo", text:"En grupo"},
        { value: "", text: "ninguno" }
       
    ];

    var selectEstilos = $('#estilo-select');

    $.each(Estilos, function(key, estilo){
        var optionEstilo = $('<option></option>').attr("value", estilo.value).text(estilo.text);
        selectEstilos.append(optionEstilo);
    });

    var Objetivos= [
        {value: "Fuerza", text:"Fuerza"},
        {value: "Resistencia", text:"Resistencia"},
        {value: "Fuerza y Resistencia", text:"Fuerza y Resistencia"},
        { value: "", text: "ninguno" }

    ];

    var selectObjetivo = $('#objetivo-select');

    $.each(Objetivos, function(key, objetivo){
        var optionObjetivo = $('<option></option>').attr("value",objetivo.value).text(objetivo.text);
        selectObjetivo.append(optionObjetivo);
    });

    
}


function createObjects(nombre,intensidad,duracion,estilo,objetivo){

    productList.push({
        nombre: nombre,
        intensidad: intensidad,
        duracion: duracion,
        estilo: estilo,
        objetivo: objetivo,
    
    });
}

    function renderCards(arrEjercicios){
   
    $(".card-container").empty
        if(arrEjercicios.length > 0){
            for(ejercicio of arrEjercicios){
            	console.log(ejercicio);
            	
            	
            	
            	
                var ejercicioCard = $("<div>").addClass("card");
    			
                var cardBody = $("<div>").addClass("card-body");
    
                var cardTitle = $("<h2>").addClass("card-title").text(ejercicio.nombre);
            
                var textoIntensidad = $("<h4>").text("Intensidad: "+ejercicio.intensidad);
                var textoduracion = $("<h4>").text("Duracion: "+ejercicio.duracion);
                var textoestilo = $("<h4>").text("Estilo: "+ejercicio.estilo);
                var textobjetivo = $("<h4>").text("Objetivo: "+ejercicio.objetivo);
              
                cardBody.append(cardTitle,textoIntensidad ,textoduracion,textoestilo,textobjetivo);
    
                ejercicioCard.append(cardBody);
                $(".card-container").append(ejercicioCard);
            }
        }else{
        $(".card-container").empty();
        }
        
    }

    

function loadAllExcercises(){
 		
    $.ajax( {
               
        type: "GET",
        url: '/RoutineGoalsV2/HelloServlet',
        dataType: "json",
       
        success: function(data) {
      
            
            var conteo = data.conteo
            var ejercicios = data.Entrenamientos;
           
        		
            $.each(ejercicios, function(index, ejercicio) {
           
              
            
                var nombre = ejercicio.name;
                 console.log("Nombres "+nombre)
                 var intensidad = ejercicio.intensidad.replace(/"/g, "");
                 console.log("Intensidad "+intensidad)
                 var duracion = ejercicio.duracion.replace(/"/g, "");
                 console.log("Duracion "+duracion)
                 var estilo = ejercicio.estilo.replace(/"/g, "");
                  console.log("Estilo "+estilo)
                var objetivo = ejercicio.objetivo.replace(/"/g, "");
                 console.log("Objetivo "+objetivo)
                
                createObjects(nombre,intensidad,duracion,estilo,objetivo)
            });
            renderCards(productList);
        },
        error: function(xhr, status, error) {
        console.log("ERROR OBTENIDO SUCCESS DE LOADALL:", error);
        alert("Error sucess")
       
        }
        
    } );
    
    
}

$("button").click(function() {
     
     var intensidad = $("#intensidad-select").val() || "";
    var duracion = $("#duracion-select").val() || "";
    var estilo = $("#estilo-select").val() || "";
    var objetivo = $("#objetivo-select").val() || "";
    productList = [];
    renderCards(productList);
    $.ajax({
    	
        type: "GET",
        url: '/RoutineGoalsV2/searchexcercise',
        dataType: "json",
        data: {
        intensidad: intensidad,
        duracion: duracion,
        estilo: estilo,
        objetivo: objetivo,
        
        },
        success: function(data) {
        
        
       		var conteo = data.conteo
            var ejercicios = data.Entrenamientos;
			
            $.each(ejercicios, function(index, ejercicio) {
                var nombre = ejercicio.name.replace(/"/g, "");
                 var intensidad = ejercicio.intensidad.replace(/"/g, "");
                 var duracion = ejercicio.duracion.replace(/"/g, "");
                  var estilo = ejercicio.estilo.replace(/"/g, "");
                   var objetivo = ejercicio.objetivo.replace(/"/g, "");
                createObjects(nombre,intensidad,duracion,estilo,objetivo)
            });
            renderCards(productList);
        },
        error: function(xhr, status, error) {
        console.log("ERROR OBTENIDO:", error);
       
        }
    });
    
});
