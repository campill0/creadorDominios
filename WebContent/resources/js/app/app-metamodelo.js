function preNuevaMRef(connection) {
	var source = $(connection.source).attr("data-idtmp");
	var target = $(connection.target).attr("data-idtmp");
	preNuevaMReferencia([ {
		name : 'source',
		value : source
	}, {
		name : 'target',
		value : target
	} ]);
}
/*
function nuevaMRef() {
	var target = $(referenciaActual.target).attr("data-idtmp");
	var source = $(referenciaActual.source).attr("data-idtmp");
	var id = referenciaActual.id;
	var nuevaEtiqueta = $(PF('nuevaEtiquetaMReferenciaWidget').jqId).val();
	
		if (nuevaEtiqueta != "") {
			referenciaActual.getOverlay("label").setLabel(nuevaEtiqueta);
			console.log("NUEVA REFERENCIA....1: source:" + source + ", target:" + target + ", id:" + id);
			nuevaMReferencia([ {
				name : 'source',
				value : source
			}, {
				name : 'target',
				value : target
			}, {
				name : 'etiqueta',
				value : nuevaEtiqueta
			} ]);
		} else {
			jsPlumb.detach(referenciaActual);
			PF('widgetNuevaMReferencia').hide();
		}

	
	$("._jsPlumb_endpoint").remove();
	// widgetselectref.value

	// nuevaReferencia([{name:'id', value: '32' }]);
}
*/

function nuevaMRef() {
	nuevaMReferencia();
	$("._jsPlumb_endpoint").remove();
}

function setMReferenciaSeleccionada(c) {
	setMReferenciaSeleccionadaCommand([ {
		name : 'id',
		value : c.mid
	} ]);
	PF('widgetEditarMReferencia').show();
}
function initialize2(){
	
	jsPlumb.unbind("click");
	jsPlumb.bind("click", function(c) {
		setMReferenciaSeleccionada(c);
	});

	jsPlumb.bind("connection", function(info) {
		if (!cargando) {

			info.connection.setPaintStyle({
				strokeStyle : defaultColour()
			});
			var sourcename = info.connection.target[0].children[0].innerText;
			var targetname = info.connection.source[0].children[0].innerText;

			$(PF('nuevaEtiquetaMReferenciaWidget').jqId).val("");
			preNuevaMRef(info.connection);
			PF('widgetNuevaMReferencia').show();
			
			
			var connectionid = info.connection.id;
			info.connection.getOverlay("label").setLabel(targetname + " to " + sourcename);
			referenciaActual = info.connection;

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
							var x = ui.position.left;
							var y = ui.position.top;
							var id = element.attr("data-idtmp");
							//var idTmp = element.attr("data-idTmp");
							actualizarPosicionMConcepto([{name : 'id',value : id}, {name : 'x',value : x}, {name : 'y',value : y} ]);
						}
					});

	$('div.deleteElement').unbind('click');
	$('div.deleteElement').bind('click', function() {
		removeElement($(this).attr('data-idtmp'));
		borrarMConceptoCommand([ {name : 'id',value : $(this).attr('data-idtmp')} ]);
	});
	
	$('div.editElement').unbind('click');
	$('div.editElement').bind('click', function() {
		setMConceptoSeleccionado([ {name : 'id',value : $(this).attr('data-idtmp')} ]);
		PF('widgetEditarMetaConcepto').show();
	});
	
}
