package br.com.mayki.servico;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.Scanner;

public class AtualizaSvg implements Runnable {

	private String caminho = this.getClass().getResource("../../../../").getPath();
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
				// verifica existencia de arquivo svg_EDITADO_e_ANIMADO.svg e cria se nescessario
				if (!this.svgFinal.exists()) {
					FileWriter criaSvgFinal = new FileWriter(svgFinal, false);
					criaSvgFinal.close();
				}

				// recebe a requisição ja editada
				RequisitaStatus rStatus = new RequisitaStatus(this.urlRequisicao);
				File svgExterno = rStatus.executa();

				FileInputStream svgExternoScanner = new FileInputStream(svgExterno);
				FileOutputStream printWriter = new FileOutputStream(this.svgFinal);
				
				
				// carraga os dois blocos de arquivos.
				
				File bloco1 = new File(caminho + "svgFundo-bloco1.svg");
				File bloco2 = new File(caminho + "svgFundo-bloco2.svg");
				
				FileInputStream fs1 = new FileInputStream(bloco1);
				FileInputStream fs2 = new FileInputStream(bloco2);
				
				//grava tudo em svg_EDITADO_e_ANIMADO.svg
				fs1.transferTo(printWriter);
				svgExternoScanner.transferTo(printWriter);
				fs2.transferTo(printWriter);
				

				// fecha recursos
				fs1.close();
				fs2.close();
				svgExterno.delete();
				//fundoAnimadoScanner.close();
				svgExternoScanner.close();
				printWriter.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
