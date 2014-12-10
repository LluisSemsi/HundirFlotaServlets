package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import partidas.Partida;

/**
 * Servlet implementation class HundirFlotaServlet
 */
public class HundirFlotaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HundirFlotaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter output = response.getWriter();
		
		Partida partida;
		HttpSession session = request.getSession(true);
						
		
		if (session.getAttribute("partida")==null){
			partida = new Partida(8, 8, 6);
		}else{
			partida = (Partida) session.getAttribute("partida");
			
			if(partida.getQuedan()>0){
				
				if(request.getParameter("casilla")!=null){
					String disparo[] = request.getParameter("casilla").split("#");
					int fila = Integer.valueOf(disparo[0]);
					int col = Integer.valueOf(disparo[1]);
					partida.pruebaCasilla(fila, col);
					
				}

			}	
		}
		
		output.println("<html><head><title>Hundir la Flota</title></head><body align = 'center'>");
		output.println("<h1>Hundir La Flota</h1>");
		if(partida.getDisparos()==0)
			output.println("NUEVA PARTIDA<br>");
		output.println("Barcos Navegando: " + partida.getQuedan() + "  <br>Barcos hundidos: " + (6 - partida.getQuedan()));
		output.println("<br>Numero de disparos efectuados: " + partida.getDisparos());
		output.println("<form action = 'HundirFlotaServlet' method = 'GET'>");
		output.println("<table align = 'center'><tr><td></td><td>A</td><td>B</td><td>C</td><td>D</td><td>E</td><td>F</td><td>G</td><td>H</td></tr>");
		output.println("<br>");
		String color;
		for(int f = 0; f<8; f++){
			output.println("<tr><td>" + (f+1) + "</td>");
			
			for(int c = 0; c < 8; c++){
				if(partida.casillaDisparada(f, c)){
					color = getColor(partida, f, c);
					output.println("<td bgcolor = '" + color +"' ><input type = 'radio' name = 'casilla' value = '"+f+"#"+c+"'></td>");
				}else
					output.println("<td bgcolor = 'white' ><input type = 'radio' name = 'casilla' value = '"+f+"#"+c+"'></td>");
			}
		}
		output.println("<tr><th colspan = '9'><input type = 'submit' value = 'Dispara!'></th></tr></table>");
		output.println("</form>");
		output.println("<br>");
		session.setAttribute("partida", partida);
		
		output.println("<a href='NuevaPartidaServlet'>Nueva Partida</a><br> ");
		output.println("<a href='SolucionServlet'>Mostrar Soluci&oacute;n</a><br> ");
		output.println("<a href='SalirServlet'>Salir</a> ");
		output.println("</body></html>");
		
		
		
	}
	
	public static String getColor(Partida partida, int f, int c){
		
		if(partida.getCasilla(f, c) == -2){
			return "orange";
		}else if(partida.getCasilla(f, c) == -1){
			return "blue";
		}else
			return "red";
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
