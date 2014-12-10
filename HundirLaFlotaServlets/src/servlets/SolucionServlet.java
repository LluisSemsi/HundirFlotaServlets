package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import partidas.Partida;

/**
 * Servlet implementation class SolucionServlet
 */
public class SolucionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SolucionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter output = response.getWriter();
		Partida partida = (Partida) request.getSession().getAttribute("partida");
	
		output.println("<html><head><title>Hundir la Flota Solucion</title></head><body align = 'center'>");
		output.println("<h1>Hundir La Flota</h1>");
		output.println("Barcos Navegando: 0");
		output.println("<br>Barcos hundidos: " + partida.getNumBarcos());
		output.println("Numero de disparos efectuados: " + partida.getDisparos());
		output.println("<br>");
		output.println("<table align = 'center' text-align = 'center'><tr><td></td><td>A</td><td>B</td><td>C</td><td>D</td><td>E</td><td>F</td><td>G</td><td>H</td></tr>");
		for(int f = 0; f<8; f++){
			output.println("<tr><td>" + (f+1) + "</td>");
			
			for(int c = 0; c < 8; c++){
				if(partida.getCasilla(f, c) != -1){
					output.println("<td bgcolor = ' red ' ><input type = 'radio' name = 'casilla' value = '"+f+"#"+c+"'></td>");
				}else
					output.println("<td bgcolor = 'blue' ><input type = 'radio' name = 'casilla' value = '"+f+"#"+c+"'></td>");
			}
		}
		output.println("</table><br>");
				
		output.println("<a href='NuevaPartidaServlet'>Nueva Partida</a><br> ");
		output.println("<a href='SalirServlet'>Salir</a> ");
		output.println("</body></html>");
		
		
		
		
	}


}
