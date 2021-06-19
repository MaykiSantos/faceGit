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
				// verifica existencia de arquivo svg_EDITADO_e_ANIMADO.svg e cria se
				// nescessario
				if (!this.svgFinal.exists()) {
					FileWriter criaSvgFinal = new FileWriter(svgFinal, false);
					criaSvgFinal.close();
				}

				// recebe a requisição ja editada
				RequisitaStatus rStatus = new RequisitaStatus(this.urlRequisicao);
				File svgExterno = rStatus.requisita();

				// define um scanner para os varquivos que serão utilizado para criar o
				// svg_EDITADO_e_ANIMADO.svg
				Scanner fundoAnimadoScanner = new Scanner(this.svgFundoAnimado);
				Scanner svgExternoScanner = new Scanner(svgExterno);

				PrintWriter printWriter = new PrintWriter(this.svgFinal);

				// pecorre o svg_EDITADO_e_ANIMADO.svg e grava nele os svg_ModeloFundo.svg e
				// svgTemp.svg na ordem correta
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

				// fecha recursos
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
