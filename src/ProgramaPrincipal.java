import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.JOptionPane;

public class ProgramaPrincipal {

	public static void main(String[] args) throws IOException {

		// SE CREA EL ARREGLO DE PRONOSTICO
		String archivoPronostico = "archivos//pronostico.txt";
		List<String> listaPronostico = Files.readAllLines(Paths.get(archivoPronostico));

		// SE CREA EL ARREGLO DE RESULTADOS
		String archivoResultados = "archivos//resultados.txt";
		List<String> listaResultados = Files.readAllLines(Paths.get(archivoResultados));

		Partido unPartido;
		Equipo unEquipo1;
		Equipo unEquipo2;
		Pronostico pronosticoPartido;
		int puntos = 0;

		for (int i = 0; i < listaResultados.size(); i++) {
			String partido = listaResultados.get(i);
			String[] datosPartido = partido.split(" ");
			int totalGolesEquipo1 = Integer.parseInt(datosPartido[1]);
			int totalGolesEquipo2 = Integer.parseInt(datosPartido[2]);

			unEquipo1 = new Equipo();
			unEquipo1.nombre = datosPartido[0];

			unEquipo2 = new Equipo();
			unEquipo2.nombre = datosPartido[datosPartido.length - 1];

			unPartido = new Partido();
			unPartido.equipo1 = unEquipo1;
			unPartido.equipo2 = unEquipo2;
			unPartido.golesEquipo1 = totalGolesEquipo1;
			unPartido.golesEquipo2 = totalGolesEquipo2;
			resultadoPartido(unPartido);

			String pronostico = listaPronostico.get(i);
			String[] datosPronostico = pronostico.split(" ");

			pronosticoPartido = new Pronostico();
			pronosticoPartido.partido = unPartido;
			resultadoPronostico(pronosticoPartido, datosPronostico);
			
			System.out.println(pronosticoPartido.resultado.equipoGanador.nombre);
			System.out.println(unPartido.resultado.equipoGanador.nombre);
			System.out.println(pronosticoPartido.resultado.empate);
			System.out.println(unPartido.resultado.empate);
			
			if (unPartido.resultado.empate == pronosticoPartido.resultado.empate
					|| unPartido.resultado.equipoGanador.nombre
							.equals(pronosticoPartido.resultado.equipoGanador.nombre)) {
				puntos = puntos + 1;
			}
		}

		JOptionPane.showMessageDialog(null, "Puntaje: " + puntos);
		System.out.println("Puntaje: " + puntos);
	}

	public static void resultadoPartido(Partido unPartido) {
		ResultadoEnum ganador;
		ganador = new ResultadoEnum();

		if (unPartido.golesEquipo1 > unPartido.golesEquipo2) {
			ganador.equipoGanador = unPartido.equipo1;
			ganador.equipoPerdedor = unPartido.equipo2;
			unPartido.resultado = ganador;
		} else if (unPartido.golesEquipo1 == unPartido.golesEquipo2) {
			ganador.empate = true;
			unPartido.resultado = ganador;
		} else {
			ganador.equipoGanador = unPartido.equipo2;
			ganador.equipoPerdedor = unPartido.equipo1;
			unPartido.resultado = ganador;
		}

	}

	public static void resultadoPronostico(Pronostico pronosticoPartido, String pronosticos[]) {
		ResultadoEnum ganador;
		ganador = new ResultadoEnum();

		String[] valoresPronostico = new String[pronosticos.length - 2];
		for (int i = 1; i < pronosticos.length - 1; i++) {
			valoresPronostico[i - 1] = pronosticos[i];
		}

		if (valoresPronostico[0].equals("1")) {
			pronosticoPartido.equipo = pronosticoPartido.partido.equipo1;
			ganador.equipoGanador = pronosticoPartido.partido.equipo1;
			ganador.equipoPerdedor = pronosticoPartido.partido.equipo2;
			pronosticoPartido.resultado = ganador;
		} else if (valoresPronostico[1].equals("1")) {
			pronosticoPartido.empate = true;
			ganador.empate = true;
			pronosticoPartido.resultado = ganador;
		} else {
			pronosticoPartido.equipo = pronosticoPartido.partido.equipo2;
			ganador.equipoGanador = pronosticoPartido.partido.equipo2;
			ganador.equipoPerdedor = pronosticoPartido.partido.equipo1;
			pronosticoPartido.resultado = ganador;
		}

	}

}
