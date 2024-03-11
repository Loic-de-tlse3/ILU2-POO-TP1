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
			for (int i = 0; i < etals.length; i++) {
				etals[i] = new Etal();
			}
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
			System.out.println("Il n'y a pas d'�tal libre");
			return -1;
		}
		
		Etal[] trouverEtals(String produit) {
			int occurenceProduit = 0;
			int nbEtal = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					nbEtal++;
					if (etals[i].contientProduit(produit)){
						occurenceProduit++;
					}
				}
			}
			Etal[] etalTrouvee = new Etal[occurenceProduit];
			int numeroEtalProduit = 0;
			for (int i = 0; i < nbEtal; i++) {
				if (etals[i].contientProduit(produit)){
					etalTrouvee[numeroEtalProduit] = etals[i];
					numeroEtalProduit++;
				}
			}
			return etalTrouvee;
		}
		
		public Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					if (etals[i].getVendeur().equals(gaulois)) {
						return etals[i];
					}
				}
			}
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
			chaineRenvoyee.append("Il reste " + nbEtalVide + "�tals non utilis�s dans le march�.");
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

	public String afficherVillageois() throws VillageSansChefException{
		StringBuilder chaine = new StringBuilder();
		if (chef == null){
			throw new VillageSansChefException("Le village doit avoir un chef");
		}
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaineRenvoyee = new StringBuilder();
		chaineRenvoyee.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n");
		int premierEtalLibre = marche.trouverEtalLibre();
		if (premierEtalLibre == -1) {
			chaineRenvoyee.append(vendeur.getNom() + " n'a pas trouv� de place pour s'installer \n");
		}
		else {
			marche.utiliserEtal(premierEtalLibre, vendeur, produit, nbProduit);
			chaineRenvoyee.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " � l'�tal n�" + premierEtalLibre + ".\n");
		}
		return chaineRenvoyee.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder ChaineRenvoyee = new StringBuilder();

		Etal[] etalsProduit = marche.trouverEtals(produit);
		if (etalsProduit.length == 0) {
			ChaineRenvoyee.append("Il n'y a pas de vendeur qui propose des " + produit + " au march�.\n");
		}
		else if (etalsProduit.length == 1) {
			ChaineRenvoyee.append("Seul le vendeur " + etalsProduit[0].getVendeur().getNom() + " propose des " + produit + " au march�\n");
		}
		else {
			ChaineRenvoyee.append("les vendeurs qui proposent des fleurs sont :\n");
			for (int i = 0; i < etalsProduit.length; i++) {
				ChaineRenvoyee.append("- " + etalsProduit[i].getVendeur().getNom() + "\n");
			}
		}
		return ChaineRenvoyee.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		Etal etalVendeur = marche.trouverVendeur(vendeur);
			return etalVendeur;
	}
	
	public String partirVendeur(Gaulois vendeur) {
		StringBuilder ChaineRenvoyee = new StringBuilder();
		ChaineRenvoyee.append("Le vendeur " + vendeur.getNom() + " quitte son �tal\n");
		return ChaineRenvoyee.toString();
	}
	
	public String afficherMarche() {
		StringBuilder ChaineRenvoyee = new StringBuilder();
		ChaineRenvoyee.append("Le march� du village \"le village des irr�ductibles\" poss�de plusieurs �tals :\n");
		ChaineRenvoyee.append(marche.afficherMarche());
		return ChaineRenvoyee.toString();
	}
}
















