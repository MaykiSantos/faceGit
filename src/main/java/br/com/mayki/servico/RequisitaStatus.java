package br.com.mayki.servico;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class RequisitaStatus {

	private URL url;
	private String caminho = getClass().getResource("../../../../").getPath();
	private File svgExternoTempFile = new File(caminho + "svgTemp.svg");

	public RequisitaStatus(String url) throws MalformedURLException {
		this.url = new URL(url);
	}

	public File requisita() throws IOException {
		// define parametros da conexão
		HttpURLConnection conexao = (HttpURLConnection)this.url.openConnection();
		//HttpURLConnection conexao = new HttpURLConnection(this.url);
		conexao.setRequestProperty("accept", "image/svg+xml, charset=utf-8");
		conexao.setRequestProperty("method", "GET");

		// recebe resultado
		InputStream resultado = conexao.getInputStream();

		// edita retornno svg para se encaixar com o fundo
		this.editaSvgExterno(resultado);

		return this.svgExternoTempFile;
	}

	private void editaSvgExterno(InputStream svg) throws IOException {
		// verifica existencia de arquivo temporario e cria se nescessario
		if (!this.svgExternoTempFile.exists()) {
			FileWriter criaArquivoTemp = new FileWriter(this.caminho + "svgTemp.svg", false);
			criaArquivoTemp.close();
		}

		// define scanner para iterar pela requisição svg
		Scanner sc = new Scanner(svg);
		// define PrintWriter para escrever no arquivo temporario
		PrintWriter svgExternoTempPW = new PrintWriter(this.svgExternoTempFile);

		// edita linhas de posicionamento e estilo do svg;
		int linha = 1;
		while (sc.hasNextLine()) {
			svgExternoTempPW.println(sc.nextLine());
			if (linha == 2) {
				sc.nextLine();
				linha++;
				sc.nextLine();
				linha++;
				svgExternoTempPW.println("width=\"19800\"");
				svgExternoTempPW.println("height=\"7800\"");
				svgExternoTempPW.println("x=\"26500\"");
				svgExternoTempPW.println("y=\"10100\"");
			} else if (linha == 92) {
				sc.nextLine();
				linha++;
				svgExternoTempPW.println("stroke=\"none\"");
			} else if (linha == 94) {
				sc.nextLine();
				linha++;
				svgExternoTempPW.println("fill=\"none\"");
			}

			linha++;
		}

		// fecha recursos
		sc.close();
		svgExternoTempPW.close();
	}

}
