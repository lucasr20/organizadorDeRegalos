package organizadorderegalos

class RegalosController {

    def index() { 
		[regalos:Regalo.list()]
	}
	
	def crearRegalo(){
		[empleados:Empleado.list()]
	}
	
	def guardarRegalo(){
		Regalo nuevo=new Regalo(titulo : params.titulo, url : params.url,
			thumbnail : params.thumbnail, anio : params.anio, empleado_id: params.empleadoId, 
			idMLA : params.idMLA, costo : params.costo);
		Empleado.get(params.empleadoId).addToRegalos(nuevo);
		nuevo.save();
		println nuevo;
		println params.costo
	}
	
	def guardarRegaloEditado(){
		println "se guardo bien"
		def regalo=Regalo.get(params.idRegalo)
		if(params.url!="" && params.url!=null){
			regalo.url=params.url
			regalo.titulo=params.titulo
			regalo.thumbnail=params.thumbnail
			regalo.idMLA=params.idMLA
			regalo.costo=Float.parseFloat(params.costo)
		}
		if(params.anio!="" && params.anio!=null){
			regalo.anio=Integer.parseInt(params.anio)
		}
		if(params.empleadoId!="" && params.empleadoId!=null){
			regalo.empleado.removeFromRegalos(regalo)
			Empleado.get(params.empleadoId).addToRegalos(regalo)
		}
		println "se guardo bien"
		if(!regalo.save(flush: true, failOnError: true))println("se rompio loco, no pudo guardar el regalo editado")
	}
	
	def editarRegalo(){
		[regalo:Regalo.get(params.idRegalo), empleados:Empleado.list()]
	}
	
	def elegirRegalo(){
		println "elegi bien"
		[regalos:Regalo.list()]
	}
	
	def accionRegalo(){
		switch(params.accion){
			case "editar":
				redirect(controller:"regalos",action:"editarRegalo",params:params);
				break;
			case "eliminar":
				redirect(controller:"regalos",action:"eliminarRegalo",params:params);
				break;
		}
	}
	
	def eliminarRegalo(){
		def regalo=Regalo.get(params.idRegalo)
		regalo.empleado.removeFromRegalos(regalo)
		regalo.delete(flush:true)
		redirect(controller:"index",action:"index")
	}
}