package br.com.mayki.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import br.com.mayki.servico.AtualizaSvg;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/principal")
public class FaceGitServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static String urlRequisicao = "https://github-readme-stats.vercel.app/api?username=MaykiSantos&amp;count_private=true&amp;show_icons=true&amp;theme=vue&amp;bg_color=e8eff5&amp;hide_border=true&amp;locale=pt-br";
	private static File svgFundoAnimado = new File(
			"C:\\Users\\mayki\\eclipse-workspace-EE\\faceGit\\arquivos\\svg_ModeloFundo.svg");
	private static File svgFinal = new File(
			"C:\\Users\\mayki\\eclipse-workspace-EE\\faceGit\\arquivos\\svg_EDITADO_e_ANIMADO.svg");

	public FaceGitServlet() throws IOException {
		// criar threads para rodar sistema que atualiza svg
		AtualizaSvg atualiza = new AtualizaSvg(FaceGitServlet.urlRequisicao, FaceGitServlet.svgFundoAnimado,
				FaceGitServlet.svgFinal);

		System.out.println("Main executado");
		ScheduledExecutorService threadAtualizaSvgFinal = Executors.newSingleThreadScheduledExecutor();
		threadAtualizaSvgFinal.scheduleWithFixedDelay(atualiza, 1, 130, TimeUnit.MINUTES);

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Scanner sc = new Scanner(FaceGitServlet.svgFinal);
		PrintWriter pw = response.getWriter();

		response.setHeader("content-type", "image/svg+xml; charset=utf-8");

		System.out.println(FaceGitServlet.svgFinal.getAbsolutePath());
		
		System.out.println("Imagem entregue para o usuario");
		while (sc.hasNextLine()) {
			pw.println(sc.nextLine());
		}
		sc.close();
		pw.close();
	}

}
