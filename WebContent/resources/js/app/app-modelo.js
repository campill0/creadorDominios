var toggle = true, canvas, ctx, animateTv=true;//  se usa para el canvas del ruido blanco

function isHayReferenciasDisponibles(){
	var hayReferenciasDisponibles=$("#formHiddenData\\:referenciasDisponiblesHidden")[0].value;
	if(hayReferenciasDisponibles=="1"){return true;}
	else {return false;}
}
function eventosBotonesConcepto(){
	
$('div.deleteElement').unbind('click');
$('div.deleteElement').bind('click', function() {
	removeElement($(this).attr('data-idtmp'));
	borrarConceptoCommand([ {name : 'id',value : $(this).attr('data-idtmp')} ]);
});

$('div.editElement').unbind('click');
$('div.editElement').bind('click', function() {
	setConceptoSeleccionado([ {name : 'id',value : $(this).attr('data-idtmp')} ]);
	PF('widgetEditarConcepto').show();
});

}
function mostrarMainPanel(){
$( "#mainpanel" ).slideDown( "slow", function() {
	   console.log("mostrando mainpanel");
	  });
}

function nuevaRef() {
	var target = $(referenciaActual.target).attr("data-idtmp");
	var source = $(referenciaActual.source).attr("data-idtmp");
	var id = referenciaActual.id;
	var mReferenciaSeleccionada = $(PF('MReferenciaSeleccionadaWidget').jqId).val();
	referenciaActual.getOverlay("label").setLabel(mReferenciaSeleccionada);
	console.log("NUEVA REFERENCIA....1: source:" + source + ", target:" + target + ", id:" + id);
	nuevaReferencia([ {
					name : 'source',
					value : source
			}, {
				name : 'target',
				value : target
			} ]);
	$("._jsPlumb_endpoint").remove();
}
function preSeleccionarReferencia(){
	console.log('preseleccion:'+ $("#formHiddenData\\:referenciasDisponiblesHidden")[0].value);
}
function preNuevaRef(connection) {
	var source = $(connection.source).attr("data-idtmp");
	var target = $(connection.target).attr("data-idtmp");
	preNuevaReferencia([ {
		name : 'source',
		value : source
	}, {
		name : 'target',
		value : target
	} ]);
}


function setReferenciaSeleccionada(c) {
	setReferenciaSeleccionadaCommand([ {
		name : 'id',
		value : c.mid
	} ]);
	PF('widgetEditarReferencia').show();
}

function initialize2(){

	jsPlumb.unbind("click");
	jsPlumb.bind("click", function(c) {
		setReferenciaSeleccionada(c);
	}); 
	console.log("binding connection...");
	jsPlumb.unbind("connection").bind("connection", function(info) {
		if (!cargando) {

			info.connection.setPaintStyle({
				strokeStyle : defaultColour()
			});
			var sourcename = info.connection.target[0].children[0].innerText;
			var targetname = info.connection.source[0].children[0].innerText;
			preNuevaRef(info.connection);
			var metodo = function() {
				if(isHayReferenciasDisponibles()){
					PF('widgetNuevaReferencia').show();
				var connectionid = info.connection.id;
				info.connection.getOverlay("label").setLabel(targetname + " to " + sourcename);
				referenciaActual = info.connection;
				}
				else{
					console.log("No hay referencias disponibles");
				}
			}
			setTimeout(metodo,500);
			
			
			

		} 
	});
	
}


function cargarListener() {
	cargando = true;
	console.log("cargarListener()");
	$(".draggable").draggable({	revert : true});
	$("#main")
			.droppable(
					{
						drop : function(event, ui) {
							var element;
							var src=ui.draggable;
							if ($(src).attr("data-draggable") == "title") {
								element = $(src).parent();
							} else {
								element = $(src);
							}
							console.log("drop");
							var x = ui.position.left;
							var y = ui.position.top;

							var idTmp = element.attr("data-idtmp");

							actualizarPosicionConcepto([{name : 'id',value : idTmp}, {name : 'x',value : x}, {name : 'y',value : y} ]);								
							var isFromSideBar = $(element).hasClass("draggable");
							if (isFromSideBar) {
								// posición inicial de la caja que se ha arrastrado
								var inicialTopDraggable = parseInt(ui.draggable.attr("data-top"));
								// posición inicial de la primera de las cajas (La tomamos como referencia)
								var inicialTopPrimerDraggable=parseInt($("#sidebar div[data-draggable='box']").first().attr("data-top"));
								// diferencia entre la posicion inicial de la caja arrastrada y la posicion inicial de la primera caja de referencia
								var offsetTopDraggablePrimerDraggable= inicialTopDraggable  - inicialTopPrimerDraggable;
								x = ui.position.left - 257; 
								y = ui.position.top + offsetTopDraggablePrimerDraggable; 
								var idTmp = $(element).attr("data-idtmp");
								var metodo = function() {
									nuevoConcepto([{name : 'id',value : idTmp},{name : 'x',value : x},{name : 'y',value : y} ]);
								}
								setTimeout(metodo,1000);
								eventosBotonesConcepto();
								idCount++;
								initialize();
								
							}
						}
					});

	eventosBotonesConcepto();
	
}
function guardarPosicionDraggables(){
	$("#sidebar div[data-draggable='box']").each(
			function(index,element){
			$(element).attr("data-top",parseInt($(element).position().top));
			  
			});
	
}


function noise(ctx) {
    
    var w = ctx.canvas.width,
        h = ctx.canvas.height,
        idata = ctx.createImageData(w, h),
        buffer32 = new Uint32Array(idata.data.buffer),
        len = buffer32.length,
        i = 0;

    for(; i < len;i++)
        if (Math.random() < 0.5) buffer32[i] = 0xff000000;
    
    ctx.putImageData(idata, 0, 0);
}

// added toggle to get 30 FPS instead of 60 FPS
function loop() {
	if(animateTv){
    toggle = !toggle;
    if (toggle) {
        requestAnimationFrame(loop);
        return;
    }
    noise(ctx);
    requestAnimationFrame(loop);
	}
}


$(window).load(function(){
	canvas= document.getElementById('canvas');
	ctx = canvas.getContext('2d');
	videoPlayerWrapper=document.getElementById('videoPlayerWrapper');
	

	resizeCanvasVideoPlayer();
	loop();
 
}
		
);
function resizeCanvasVideoPlayer(){
	$("#canvas").attr("width",$("#videoPlayerWrapper").width()/1.5);
	$("#canvas").attr("height",$("#videoPlayerWrapper").height()/1.5);
	
	
}






function play(){
	
	if(video.paused()){
		
	console.log("IF:" + new Date().getTime());
	   fin=$('input[id$=\'endHidden\'][ type=\'hidden\']')[0].value;
	   inicio=end=$('input[id$=\'startHidden\'][ type=\'hidden\']')[0].value;
	   if(empezarDesdeInicio){
		   video.currentTime(inicio);
		   empezarDesdeInicio=false;
	   }
	   if (video.currentTime()>=fin){
		   video.currentTime(inicio);
	   }
	   video.play();
	   
	   
	   clearInterval(intervalVideo); 
	   intervalVideo=setInterval(function(){comprobarSiEstaEntreInicioYFin()},1000);
	   $($("#formVideoSlider\\:button-play-pause span")[0]).removeClass("ui-icon-play");
		$($("#formVideoSlider\\:button-play-pause span")[0]).addClass("ui-icon-pause");
		
	}
}
function togglePlayPause(){
	if(video.paused()){ 
		play(); 
		
	}
	else{
		
		 pause();
				
	}
}
function toggleMutedUnMuted(){
	if(video.muted()){ 
		video.unmute();
		  $($("#formVideoSlider\\:button-mute span")[0]).removeClass("ui-icon-volume-off");
			$($("#formVideoSlider\\:button-mute span")[0]).addClass("ui-icon-volume-on");
		
	}
	else{
		video.mute();
		$($("#formVideoSlider\\:button-mute span")[0]).removeClass("ui-icon-volume-on");
		$($("#formVideoSlider\\:button-mute span")[0]).addClass("ui-icon-volume-off");
	
				
	}
}
function pause(){
	
	
	
	$("#currentTime").removeClass( "cajaTiempoAlterno" );
	
	if(!video.paused()){
	 console.log("THEN:" + new Date().getTime());
	   video.pause();
	   clearInterval(intervalVideo);
	   $($("#formVideoSlider\\:button-play-pause span")[0]).removeClass("ui-icon-pause");
		$($("#formVideoSlider\\:button-play-pause span")[0]).addClass("ui-icon-play");
	
	} 
}


function seek(time){
	 $('#videoplayer')[0].currentTime=time;
}
function step(time){
	 $('#videoplayer')[0].currentTime=  $('#videoplayer')[0].currentTime + time;
}

function onSlideEnd223(event, ui){
	$('input[id$=\'startHidden\'][ type=\'hidden\']')[0].value=ui.values[0];
	if(ui.values[1]>ui.values[0]){
	$('input[id$=\'endHidden\'][ type=\'hidden\']')[0].value=ui.values[1];
	}
	else{
		$('input[id$=\'endHidden\'][ type=\'hidden\']')[0].value=ui.values[0]+1;
	}
	$('#videoplayer')[0].currentTime=value=ui.value;
	
	$('#videoplayer').unbind('timeupdate'); 
	 $('#videoplayer').on( 'timeupdate',
	 function (){ 		
		 var end=$('input[id$=\'endHidden\'][ type=\'hidden\']')[0].value; 
		 
		 if($('#videoplayer')[0].currentTime >= end){ 	
			 $('#videoplayer')[0].pause();  
			$('#videoplayer').unbind('timeupdate'); 	
		
		 } 
	 }); 
	 
	 
	 $('button[id$=\'wikipediaButton\']')[0].click();
}

function playAToB(){
	$('#videoplayer')[0].currentTime=$('input[id$=\'startHidden\'][ type=\'hidden\']')[0].value; 
	$('#videoplayer')[0].play();
	$('#videoplayer').unbind('timeupdate'); 
	 $('#videoplayer').on( 'timeupdate',
	 function (){ 		
		 var end=$('input[id$=\'endHidden\'][ type=\'hidden\']')[0].value; 
		 
		 if($('#videoplayer')[0].currentTime >= end){ 	
			 $('#videoplayer')[0].pause();  
			$('#videoplayer').unbind('timeupdate'); 	
		
		 } 
	 }); 
	 }

	function continueToB(){
		 $('#videoplayer').on( 'timeupdate',
		 function (){ 		
			 var end=$('input[id$=\'endHidden\'][ type=\'hidden\']')[0].value; 
			 
			 if($('#videoplayer')[0].currentTime >= end){ 	
				 $('#videoplayer')[0].pause();  
				$('#videoplayer').unbind('timeupdate'); 	
			
			 } 
		 }); 
			$('#videoplayer')[0].play();

		 }
	function setA(){
		
		$('input[id$=\'startHidden\'][ type=\'hidden\']')[0].value=video.currentTime();
	}
	function setDuracion(){
		
		$('input[id$=\'durationHidden\'][ type=\'hidden\']')[0].value=video.duration();
	}
	function seekToA(){
		
		clearInterval(intervalVideo);
		video.currentTime(getInicio());
		//widgetFakeSliderButton.jq.click();
	}
	function seekToB(){
		
		video.currentTime(getFin());
	}
	function setB(){
		$('input[id$=\'endHidden\'][ type=\'hidden\']')[0].value=$('#videoplayer')[0].currentTime;
	}