package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtalsMaximum) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbEtalsMaximum);
	}
	
	private static class Marche {
		private Etal[] etals;

		private Marche(int nbEtal) {
			super();
			etals = new Etal[nbEtal];
		}
		
		public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		public int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if (! etals[i].isEtalOccupe()) {
					return i;
				}
			}
			System.out.println("Il n'y a pas d'étal libre");
			return -1;
		}
		
		Etal[] trouverEtals(String produit) {
			int occurenceProduit = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)){
					occurenceProduit++;
				}
			}
			Etal[] etalTrouvee = new Etal[occurenceProduit];
			int numeroEtalProduit = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)){
					etalTrouvee[numeroEtalProduit] = etals[i];
					numeroEtalProduit++;
				}
			}
			return etalTrouvee;
		}
		
		public Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].getVendeur().equals(gaulois)) {
					return etals[i];
				}
			}
			System.out.println("Ce vendeur n'est pas présent");
			return null;
		}
		
		public String afficherMarche() {
			StringBuilder chaineRenvoyee = new StringBuilder();
			int nbEtalVide = 0;
			for (int i = 0; i < etals.length; i++) {
				if(etals[i].isEtalOccupe()) {
					chaineRenvoyee.append(etals[i].afficherEtal());
				}
				else {
					nbEtalVide++;
				}
			}
			chaineRenvoyee.append("Il reste " + nbEtalVide + "étals non utilisés dans le marché.");
			return chaineRenvoyee.toString();
		}
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les lÃ©gendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaineRenvoyee = new StringBuilder();
		chaineRenvoyee.append(vendeur + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n");
		int premierEtalLibre = marche.trouverEtalLibre();
		marche.utiliserEtal(premierEtalLibre, vendeur, produit, nbProduit);
		chaineRenvoyee.append("Le vendeur " + vendeur + " vend des " + produit + " à l'étal n°" + premierEtalLibre + ".\n");
		return chaineRenvoyee.toString();
	}
}