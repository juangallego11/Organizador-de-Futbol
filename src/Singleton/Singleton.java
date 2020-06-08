
package Singleton;


public class Singleton {
    
    private String usuario;
    private String clave;
    
    private static Singleton admin;

    public Singleton(String usuario, String clave) {
        this.usuario = usuario;
        this.clave = clave;
    }
    
    public static Singleton getSingletonInstance(String usuario, String clave){
        boolean v = false;
        if(admin == null){
            admin = new Singleton(usuario, clave);
        }else{
            System.out.println("No se puede crear otro administrador con el usuario" + usuario);
        }
        return admin;
    }
    
    
    
            
}
