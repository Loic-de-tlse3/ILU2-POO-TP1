package villagegaulois;

public class VillageSansChefException extends IllegalStateException {
    private static final long serialVersionUID = 1L;
    
    public VillageSansChefException(){
    }
    /**
     * @param message Le message détaillant l'exception
     */
    public VillageSansChefException(String message){
        super(message);
    }
    /**
     * @param cause L'exception à l'origine de cette exception
     */
    public VillageSansChefException(Throwable cause){
        super(cause);
    }

    public VillageSansChefException(String message, Throwable cause){
        super(message, cause);
    }
}
