package modelo;

import java.util.ArrayList;
import java.util.Random;

public class Evento extends Casilla {
	private String[] eventos = { "pez", "bolas", "rapido", "lento", "pierdeTurno", "pierdeItem", "motos" };

	public Evento(int posicion, ArrayList<Jugador> jugadoresActuales, String tipoEvento) {
		super(posicion, jugadoresActuales);
	}

	@Override
	public void realizarAccion() {
		for (Jugador j : jugadoresActuales) {
			if (j instanceof Pinguino) {
				Random ran = new Random();
				int pos = ran.nextInt(eventos.length); // Selecciona un evento aleatorio

				if (eventos[pos].equals("pez")) {
					// Obtenir un peix
					System.out.println(j.getNombre() + " ha obtingut un peix!");
					((Pinguino) j).añadirItem(new Item("Pez", 1));

				} else if (eventos[pos].equals("bolas")) {
					// Obtenir 1-3 boles de neu
					int bolas = ran.nextInt(3) + 1; // Genera entre 1 y 3
					System.out.println(j.getNombre() + " ha obtingut " + bolas + " boles de neu!");
					((Pinguino) j).añadirItem(new Item("Bola de neu", bolas));

				} else if (eventos[pos].equals("rapido")) {
					// Obtenir un dau ràpid (avança 5-10 caselles, probabilitat baixa)
					int rapido = ran.nextInt(6) + 5; // Genera entre 5 y 10
					System.out.println(j.getNombre() + " ha obtingut un dau ràpid! Avança " + rapido + " caselles.");
					j.moverPosicion(rapido);

				} else if (eventos[pos].equals("lento")) {
					// Obtenir un dau lent (probabilitat alta, valors entre 1 i 3)
					int lento = ran.nextInt(3) + 1; // Genera entre 1 y 3
					System.out.println(j.getNombre() + " ha obtingut un dau lent! Avança " + lento + " caselles.");
					j.moverPosicion(lento);

				} else if (eventos[pos].equals("pierdeTurno")) {
					// Perder el turno
					System.out.println(j.getNombre() + " ha perdut el torn!");
					j.setPierdeTurno(true); // Marca que el jugador pierde el turno

				} else if (eventos[pos].equals("pierdeItem")) {
					// Perder un ítem
					Inventario inv = ((Pinguino) j).getInventario();
					if (!inv.getLista().isEmpty()) {
						Item itemPerdido = inv.getLista().remove(0); // Elimina el primer ítem
						System.out.println(j.getNombre() + " ha perdut un ítem: " + itemPerdido.getNombre());
					} else {
						System.out.println(j.getNombre() + " no té ítems per perdre.");
					}

				} else if (eventos[pos].equals("motos")) {
					// Motos de neu: permeten avançar fins el següent trineu
					System.out.println(j.getNombre() + " ha trobat una moto de neu!");
					int nuevaPosicion = buscarSiguienteTrineo(j.getPosicion());
					System.out.println(j.getNombre() + " avança fins al següent trineu a la casella " + nuevaPosicion);
					j.setPosicion(nuevaPosicion);
				}
			}
		}
	}

	// Método auxiliar para encontrar el siguiente trineo
	private int buscarSiguienteTrineo(int posicionActual) {
		for (Casilla casilla : this.jugadoresActuales.get(0).getTablero().getCasillas()) {
			if (casilla instanceof Trineo && casilla.getPosicion() > posicionActual) {
				return casilla.getPosicion();
			}
		}
		return posicionActual; // Si no hay más trineos, se queda en la misma posición
	}
}
