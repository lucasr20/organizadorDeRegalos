package organizadorderegalos
import com.testapp.User

class Empresa {
	String razon_social
	String cuit

	static hasMany = [empleados: Empleado, admins: User]

    static constraints = {
    }
}