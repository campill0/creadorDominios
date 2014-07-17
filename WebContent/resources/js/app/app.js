var cargando = true;
var debug = "";
var idCount = 0;
var referenciaActual;
var intervalVideo;
var video;
var videoUrlActual="";
var connexionActual;
var conceptosExistentes = {};
var curColourIndex = 1, maxColourIndex = 24;

function nextColour() { 
	var R, G, B;

	R = parseInt(128 + Math.sin((curColourIndex * 3 + 0) * 1.3) * 128);
	G = parseInt(128 + Math.sin((curColourIndex * 3 + 1) * 1.3) * 128);
	B = parseInt(128 + Math.sin((curColourIndex * 3 + 2) * 1.3) * 128);
	curColourIndex = curColourIndex + 1;
	if (curColourIndex > maxColourIndex)
		curColourIndex = 1;
	return "rgb(" + R + "," + G + "," + B + ")";
}
function defaultColour() {
	var R, G, B;

	R = parseInt(79);
	G = parseInt(134);
	B = parseInt(175);
	
	return "rgb(" + R + "," + G + "," + B + ")";
}
function sendFacesMessage(severity,message){
	rcSendFacesMessage([{name : 'severity' , value : severity},{name : 'message' , value : message}]);
}
function bindJsPlumb(){
	jsPlumb.bind("ready", function() {
		cargando = true;
		window.jsPlumbDemo = {

			init : function() {
				jsPlumb.importDefaults({Endpoint : [ "Blank", {	radius : 2	} ],
					HoverPaintStyle : {strokeStyle : "#7b7c2c",	lineWidth : 2},
					ConnectionOverlays : [ [ "Arrow", {	location : 1, id : "arrow",	length : 24, foldback : 0.8	} ],
					                       [ "Label", {	label : "",	id : "label" } ] ]
				});
				
				jsPlumb.draggable($(".w"), {containment : "parent"});
				$(".w").each(
						function(i, e) {
							jsPlumb.makeSource($(e), {
								filter : ".ep",
								anchor : "Continuous",
								connector : [ "StateMachine", {	curviness : 20 } ],
								connectorStyle : {strokeStyle : defaultColour(),lineWidth : 20},
								maxConnections : 500,
								onMaxConnections : function(info, e) {
									alert("Maximum connections ("+ info.maxConnections + ") reached");
								}
							});
						});

				jsPlumb.makeTarget($(".w"), {dropOptions : {hoverClass : "dragHover"},anchor : "Continuous"	});
				cargarListener();
				setTimeout(function() {	cargando = false;}, 1000);
			}
		};
		//initialize();
		jsPlumbDemo.init();

	});

	}

function removeElement(id) {

		var connections = jsPlumb.getConnections();
		for ( var index in connections) {
			var conn = connections[index];
			if ((conn.sourceId == id) || (conn.targetId == id)) {
				jsPlumb.detach(conn);

			}

		}
		$(' div[id=node' + id + '].w').remove(); 

	}
function corregirCajasFuera() {
		main = $("#main");
		caja = $($("div[data-draggable=box]")[0]);
		cajas = $("div[data-draggable=box]");
		max_x = main.width() - (caja.width() -50 );
	
		$.each(cajas, function(index, value) {
		
			var x = parseInt($(value).css("left").replace("px", ""));
			if (x > max_x) {
				$(value).css("left", (max_x - 100) + "px");
			}

		});
	}
function initialize() {
	// initialise draggable elements.
	jsPlumb.draggable($(".w"), {containment : "parent"});
	$(window).resize(function() {
		initialize();
		corregirCajasFuera();
		jsPlumb.repaintEverything();

	});
	$(".w").each(
			function(i, e) {
				jsPlumb.makeSource($(e), {
					filter : ".ep",
					anchor : "Continuous",
					connector : [ "StateMachine", {curviness : 2} ],
					connectorStyle : {strokeStyle : defaultColour(),lineWidth : 2},
					maxConnections : 50,
					onMaxConnections : function(info, e) {alert("Maximum connections (" + info.maxConnections	+ ") reached");
					}
				});
			});

	jsPlumb.bind("beforeDrop", function(connection) {
		var existingConnections = jsPlumb.getConnections({
			source : connection.sourceId,
			target : connection.targetId
		});
		console.log(existingConnections);
		return true;
});
	
	jsPlumb.makeTarget($(".w"), {
		dropOptions : {	hoverClass : "dragHover"},
		anchor : "Continuous"
	});

	initialize2(); 
}
function recuperarDraggable() {
	var met = function() {
		$('.draggable').draggable({	revert : true	});
	
	}

	setTimeout(met, 200);
	$( "#seccionsuperior" ).slideToggle( "slow");
}	

function mostrarOcultar(id){
	$( id ).slideToggle( "slow", function() {
		if($( "#pestanaOcultarVertical").css("margin-top")=="0px"){
			$( "#pestanaOcultarVertical").css("margin-top","-10px");
		}
		else{
			$( "#pestanaOcultarVertical").css("margin-top","0px");
		}
			
		  });
	jsPlumb.repaintEverything();
}


