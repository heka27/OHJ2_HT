package Treeniseuranta;

/**
 * @author Henrik Seppänen
 * @version 2.5.2019
 *
 */
public class SailoException extends Exception {
    private static final long serialVersionUID = 1L;

    
    /**
     * Poikkeuksen muodostaja jolle tuodaan poikkeuksessa käytettävä viesti
     * @param viesti poikkeuksen viesti
     */
    public SailoException(String viesti) {
        super(viesti);
    }
}
