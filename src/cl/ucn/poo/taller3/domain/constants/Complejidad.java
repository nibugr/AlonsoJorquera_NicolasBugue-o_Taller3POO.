package cl.ucn.poo.taller3.domain.constants;

/**
 * Clase con constantes para niveles de complejidad
 */
public final class Complejidad {
    public static final String BAJA = "Baja";
    public static final String MEDIA = "Media";
    public static final String ALTA = "Alta";
    
    private Complejidad() {} // No instanciable
    
    /**
     * Valida si una complejidad es válida
     */
    public static boolean esComplejidadValida(String complejidad) {
        return complejidad != null && (
            complejidad.equals(BAJA) || 
            complejidad.equals(MEDIA) || 
            complejidad.equals(ALTA)
        );
    }
    
    /**
     * Obtiene complejidad por defecto
     */
    public static String getComplejidadPorDefecto() {
        return MEDIA;
    }
}


