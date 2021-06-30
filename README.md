# faceGit
Programa que disponibiliza a capa do gitHub (Atualizável)

![Anurag's GitHub stats modificado](http://app.meus-projetos.com:8080/faceGit/)

## Descrição
O faceGit foi um projeto feito para aplicar alguns conhecimentos sobre as bibliotecas:
* Java.io;
* Java.util;
* Jakarta.servlet;

O projeto consiste em montar um arquivo .svg a partir de outros dois arquivos e disponibilizar este novo .svg modificado para que o github possa acessá-lo. De forma que as informações do svg possam ser atualizadas.


### Estrutura Projeto:
![image](https://user-images.githubusercontent.com/58126683/123556554-3459cf80-d762-11eb-93cc-7599faa24db7.png)

O "externo.svg" é gerado pelo serviço do [github-readme-stats](https://github.com/anuraghazra/github-readme-stats), ele é responsável por gerar o bloco com as métricas e a nota.

O "fundo.svg" é um arquivo local que foi gerado e animado por mim,foi utilizando o corelDraw para montar o vetor, e as animações foram feitas utilizando css.

O programa está hospedado em uma VPS e atende as requisições pelo link https://app.meus-projetos.com:8443/faceGit , o svg entregue pelo link é atualizado em intervalos de 2 horas.

Link Vídeo rápido:
https://www.youtube.com/watch?v=KnjNoFR_NsM&t=34s&ab_channel=1BestCsharpblog




#### Porque utilizar um programa para gerar um svg e não um svg com um link externo?
Ao fazer o upload de alguns arquivos svg com links externos para o github, percebi que os blocos com os links externos não eram carregados. Como a imagem a baixo.

> ![Anurag's GitHub stats modificado](https://raw.githubusercontent.com/MaykiSantos/MaykiSantos/df90a46ff95cb2c537963b5feadee252b4b4d4d5/img/arquivo-com-links-externos.svg)

*Embora não seja possível visualizar a imagem, trata-se de um svg com links externos para um gif e para o [github-readme-stats](https://github.com/anuraghazra/github-readme-stats) para que fose gerado um efeito similar a capa atual. Se a imagem for baixada e aberta pelo navegador ela irá funcionar*

Ao pesquisar sobre o assunto cheguei em https://stackoverflow.com/questions/13808020/include-an-svg-hosted-on-github-in-markdown que explica que esse comportamento ocorre por conta de um tratamento de falha de segurança. Para contornar esse problema foi criado o faceGit que atualiza um svg sem links externos e o disponibiliza para utilização.
