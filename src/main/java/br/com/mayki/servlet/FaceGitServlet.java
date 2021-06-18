package br.com.mayki.servlet;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import br.com.mayki.servico.AtualizaSvg;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/principal")
public class FaceGitServlet extends HttpServlet {

	private final long serialVersionUID = 1L;
	private String caminho = getClass().getResource("../../../../").getPath();

	private String urlRequisicao = "https://github-readme-stats.vercel.app/api?username=MaykiSantos&amp;count_private=true&amp;show_icons=true&amp;theme=vue&amp;bg_color=e8eff5&amp;hide_border=true&amp;locale=pt-br";
	private File svgFundoAnimado = new File(caminho + "svg_ModeloFundo.svg");
	private File svgFinal = new File(caminho + "svg_EDITADO_e_ANIMADO.svg");

	public FaceGitServlet() throws IOException, URISyntaxException {

		// criar threads para rodar sistema que atualiza svg
		AtualizaSvg atualiza = new AtualizaSvg(this.urlRequisicao, this.svgFundoAnimado, this.svgFinal);

		ScheduledExecutorService threadAtualizaSvgFinal = Executors.newSingleThreadScheduledExecutor();
		threadAtualizaSvgFinal.scheduleWithFixedDelay(atualiza, 1, 130, TimeUnit.MINUTES);

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Scanner sc = new Scanner(this.svgFinal);
		ServletOutputStream pw = response.getOutputStream();

		response.setHeader("content-type", "image/svg+xml; charset=utf-8");

		System.out.println("Imagem entregue para o usuario");

		while (sc.hasNextLine()) {
			pw.println(sc.nextLine());
		}
		sc.close();
		pw.close();
	}

}
