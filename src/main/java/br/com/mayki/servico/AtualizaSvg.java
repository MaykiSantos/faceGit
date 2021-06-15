package br.com.mayki.servico;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;


public class AtualizaSvg implements Runnable {

	private String urlRequisicao;
	private File svgFundoAnimado;
	private File svgFinal;

	public AtualizaSvg(String urlRequisicao, File svgFundoAnimado, File svgFinal) {
		super();
		this.urlRequisicao = urlRequisicao;
		this.svgFundoAnimado = svgFundoAnimado;
		this.svgFinal = svgFinal;
	}

	@Override
	public void run() {
		// faz requisição para pegar svg com os status.
		synchronized (this) {

			System.out.println("Atualizando SVG");
			try {
				if (!this.svgFinal.exists()) {
					FileWriter criaSvgFinal = new FileWriter(svgFinal, false);
					criaSvgFinal.close();
				}

				RequisitaStatus rStatus = new RequisitaStatus(this.urlRequisicao);
				File svgExterno = rStatus.requisita();

				Scanner fundoAnimadoScanner = new Scanner(this.svgFundoAnimado);
				Scanner svgExternoScanner = new Scanner(svgExterno);

				PrintWriter printWriter = new PrintWriter(this.svgFinal);

				int linha = 1;
				while (fundoAnimadoScanner.hasNextLine()) {
					printWriter.println(fundoAnimadoScanner.nextLine());
					if (linha == 215) {
						while (svgExternoScanner.hasNextLine()) {
							printWriter.println(svgExternoScanner.nextLine());
						}
					}

					linha++;
				}
				;

				svgExterno.delete();
				fundoAnimadoScanner.close();
				svgExternoScanner.close();
				printWriter.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
