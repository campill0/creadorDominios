var lastClickTimestamp=0;
var clickTimestampTiempoDeSeguridad=100;
var cancelarNuevoVideo=false; // se utiliza al crear un nuevo v�deo.
var inicioCarga=0; // se utiliza para controlar el tiempo que ha pasado desde que se hizo un intento de carga de un v�deo al crear un nuevo v�deo.
var tiempoMaximoEspera=5000; // tiempo m�ximo de espera para la obtenci�n de informaci�n de un nuevo video. 10000 = 10 segundos.
var empezarDesdeInicio=false;  
var videoFake=null;
var duracionInicial=61.234;// en el constructor de FragmentoVideo se inicializa a este valor por que si es cero no funciona


function loadVideo(url){
	 if(videoUrlActual!=url){
		 
		 videoUrlActual=url; 
		 $("#videoPlayer").html("");
		 	video=null;
			video = Popcorn.smart(      '#videoPlayer',      url );
			console.log("Popcorn.smart(      '#videoPlayer',      "+url+" );");
			inicioCarga=new Date().getTime();
			video.controls(false);
		video.pause();
			//video.play();
			
			
		  empezarDesdeInicio=true;
		  PF('statusLoadVideoDialog').show();
			setTimeout(initializeSlider,2000);
	 }
	
   

}

function loadVideoSimple(url){
	$('#videoPlayer').html("");
	video=Popcorn.smart(      '#videoPlayer',      url);
	
}
function initializeSlider(){
	video.pause();
	
	if(isVideoCargado(video)){
		
		seekToA();
		$("#canvas").css("display","none");
		animateTv=false;
		$("#currentTime").html(secondsTohms(getInicio()));
		PF('statusLoadVideoDialog').hide();
		}
	else{
		if((new Date().getTime()-inicioCarga)<tiempoMaximoEspera){
			setTimeout(initializeSlider,1000); // sigue esperando.	
		}
		else{
			//sendFacesMessage('warn','No se ha podido cargar el vídeo.');
			PF('statusLoadVideoDialog').hide();
		console.log("error en carga de video");
		videoUrlActual="";
		//	$("#loadingVideoError").css("display","block");
			//$("#loadingVideoInfo").css("display","none");
			//$("#buttonLoadingVideoCancelar").css("display","block");
			//$("#buttonLoadingVideoReintentar").css("display","block");	
		}
	}
}
function isVideoCargado(v){
	if(isNaN(video.duration())){return false;}
	if(v.duration()==0){return false;}
	return true;
	
}
function setDuracionVideoInicial(){
	if(getDuracionHidden()==duracionInicial){
	if(isVideoCargado(video)){
		setDuracionVideo([ {name : 'duracion',value : video.duration()} ]);
	}
	else{
		setTimeout(setDuracionVideoInicial,3000);
	}
		
	}
	
}
// sirve para el p:slider que hay en modelo.faces
function getFin(){
	return parseFloat($('input[id$=\'endHidden\'][ type=\'hidden\']')[0].value);
}
function getInicio(){
	return parseFloat($('input[id$=\'startHidden\'][ type=\'hidden\']')[0].value);
}
function getDuracionHidden(){
	return parseFloat($('input[id$=\'durationHidden\'][ type=\'hidden\']')[0].value);
}
function getUrlHidden(){
	return $('input[id$=\'urlHidden\'][ type=\'hidden\']')[0].value;
}
function setDuracionVideoHidden(url,durationHidden){
	if(url==""){ 
		durationHidden.value=0;
		return ;}
	console.log("setduracionhidden");
	$("#loadingNuevoVideoError").css("display","none");
	$("#loadingNuevoVideoInfo").css("display","block");
	$("#buttonLoadingNuevoVideoCancelar").css("display","none");
	PF('statusNuevoVideoDialog').show();
	cancelarNuevoVideo=false;
	//var urlInput=$("#formNuevoVideo\\:input-url-nuevo-video")[0].value;
	videoFake=null;
	$("#videoHidden").html("");
	inicioCarga=new Date().getTime();
	videoFake = Popcorn.smart(      '#videoHidden',      url );
	
	
	setTimeout(setDuracionVideoHiddenComprobacionDuracion,1000);
}

function setDuracionVideoHiddenComprobacionDuracion() {
	if(isNaN(videoFake.duration()) || (videoFake.duration()==0)){
		if(!cancelarNuevoVideo){
			if((new Date().getTime()-inicioCarga)<tiempoMaximoEspera){
				setTimeout(setDuracionVideoHiddenComprobacionDuracion,1000); // sigue esperando.	
			} 
			else{
				sendFacesMessage("error","Se ha superado el tiempo máximo de espera, revise la url.");
				console.log("tiempo de espera superado");
				PF('statusNuevoVideoDialog').hide();
				videoFake=null;
				durationHidden.value=0;
				//$("#formNuevoVideo\\:durationHidden")[0].value=0;
				//$("input#formEditarVideoActual\\:durationHidden")[0].value=0;
				//mostramos un error.
			}
			
			
		}
			
	}
	else{
		$("#formNuevoVideo\\:durationHidden")[0].value=parseInt(videoFake.duration());
		$("input#formEditarVideoActual\\:durationHidden")[0].value=parseInt(videoFake.duration());
		PF('statusNuevoVideoDialog').hide();
		  loadVideoSimple(url); 
		  
		videoFake=null;
	}
}
function comprobarSiEstaEntreInicioYFin()
{
	
	$("#currentTime").html(secondsTohms(video.currentTime()));
	$("#currentTime").toggleClass("cajaTiempoAlterno");
	
	
console.log("fin:"+getFin());
	if(video.currentTime()>getFin()){
		console.log("hey");
		clearInterval(intervalVideo);
		
		pause();
		video.currentTime(getInicio());
		 
	}

}
function secondsTohms(time){
	var hours = Math.floor(time / 3600);
	time -= hours * 3600;

	var minutes = Math.floor(time / 60);
	time -= minutes * 60;

	var seconds = parseInt(time % 60, 10);
	return (hours < 10 ? '0' + hours : hours) + ':' + (minutes < 10 ? '0' + minutes : minutes) + ':' + (seconds < 10 ? '0' + seconds : seconds);
}
function ocultar(elemento){
	elemento.css("display","none");
}
function mostrar(elemento)
{
	
	elemento.css("display","block");
	}
function toggleVideoTable(){
	if($("#videoPlayerWrapper").css("display")=="none"){
		mostrarTodo();
	}
	else{
		mostrarSoloVideoTable();
	}
}

function toggleVideoPlayer(){
	if($("#videotablesearch").css("display")=="none"){
		
		mostrarTodo();
	}
	else{
		mostrarSoloVideoPlayer();
	}
	resizeCanvasVideoPlayer();
}
function mostrarSoloVideoTable(){
	$("#videoPlayerWrapper").css("display","none");
	$("#videotablesearch").css("display","block");
	$("#videotablesearch").css("width","100%");
	$("#fullScreenVideoTable").removeClass("fullScreenIconOn");
	$("#fullScreenVideoTable").addClass("fullScreenIconOff");
	
}
function mostrarSoloVideoPlayer(){
	$("#videoPlayerWrapper").css("display","block");
	$("#videotablesearch").css("display","none");
	$("#videoPlayerWrapper").css("width","100%");
	$("#fullScreenVideoPlayer").removeClass("fullScreenIconOn");
	$("#fullScreenVideoPlayer").addClass("fullScreenIconOff");
	
}
function mostrarTodo(){
	processTable();
	$("#videoPlayerWrapper").css("display","block");
	$("#videotablesearch").css("display","block");
	$("#videotablesearch").css("width","40%");
	$("#videoPlayerWrapper").css("width","calc(60% - 20px)");
	$("#fullScreenVideoTable").removeClass("fullScreenIconOff");
	$("#fullScreenVideoPlayer").removeClass("fullScreenIconOff");
	$("#fullScreenVideoPlayer").addClass("fullScreenIconOn");
	$("#fullScreenVideoTable").addClass("fullScreenIconOn");
}
function toggleTablaVideoPlayer(){
	$("#videotablesearch").css("display","none");
	$("#videoPlayerWrapper").css("display","none");
	$("#videoPlayerWrapper").css("padding-left","0px");
	$("#videoPlayerWrapper").css("padding-left","20px");
}


function onSlide(event, ui){
	console.log("onSlide...");
	$("#sliderTime").css("display","block");
	$("#sliderTime").css("top",($(ui.handle).offset().top-45)+"px");
	$("#sliderTime").css("left",($(ui.handle).offset().left-42)+"px");
	var segundos=secondsTohms(ui.value);
	$($("#sliderTime span")[0]).html(segundos); 
	if(ui.values[0]==ui.value){//slider inicial
		$("#spinnerInicioHMS").html(segundos);
	}
	else{////slider final
		$("#spinnerFinHMS").html(segundos);
	}
} 
function onSlideEnd(event, ui){
	$("#sliderTime").css("display","none");
	var inicio=parseFloat(ui.values[0]);
	var fin=parseFloat(ui.values[1]);
	var isInicio= (ui.value==inicio);
	clearInterval(intervalVideo);
	if(isInicio){
		
		video.currentTime(inicio);
	}
	else if (video.currentTime()>fin){
		video.currentTime(fin);
	}
	pause();
	
	//setDuracion();
	// intervalVideo=setInterval(function(){comprobarSiEstaEntreInicioYFin()},1000); 
/*	video.off('timeupdate'); 
	 video.on( 'timeupdate',
	 function (){ 		
		 var end=$('input[id$=\'fin\'][ type=\'hidden\']')[0].value; 
		 
		 if(video.currentTime >= end){ 	
			 video.pause();  
			video.off('timeupdate'); 	
		
		 } 
	 }); 
	*/  
	PF('widgetFakeSliderButton').jq.click();
	
	
}


















