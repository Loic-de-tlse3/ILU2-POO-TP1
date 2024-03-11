package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;

public class ScenarioCasDegrade {
    public static void main(String[] args){
        Etal etal = new Etal();
        Gaulois acheteur = new Gaulois(null, 0);
        try {
            etal.acheterProduit(-1, acheteur);
        } catch (IllegalArgumentException e){
            e.printStackTrace();
        } catch (IllegalStateException e){
            e.printStackTrace();
        }
        System.out.println("Fin du test");
    }
}
