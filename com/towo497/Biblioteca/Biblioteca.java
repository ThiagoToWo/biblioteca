package com.towo497.Biblioteca;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class Biblioteca {
	
	JFrame janela;
	Font letraVisivelRotulo = new Font("dialog", Font.BOLD, 16);
	Font letraVisivelTexto = new Font("dialog", Font.PLAIN, 16);
	JTextField isbn = new JTextField(10);
	JTextField titulo = new JTextField(20);
	JTextField autor = new JTextField(15);	
	JTextField editora = new JTextField(10);
	JTextField ano = new JTextField(3);
	JTextField ediçao = new JTextField(2);
	JTextField paginas = new JTextField(3);
	JTextField preço = new JTextField(4);	
	JTextArea infoDaBiblioteca = new JTextArea(17, 40);	
	JTextArea infoDaPesquisa = new JTextArea(8, 40);
	JTextField textoPesquisa = new JTextField(20);
	Pattern pat;
	Matcher mat;
	JLabel rotuloNumDeLivros = new JLabel("Existem _____ livros. Num total de R$ ________ , no arquivo ______________.");
	NumberFormat fmt = NumberFormat.getCurrencyInstance();

	public void janela() {
		JFrame janela = new JFrame("Minha Biblioteca");	
		JMenuBar barraDeMenu = new JMenuBar();
		JMenu menuSobre = new JMenu("Sobre");
		JMenuItem autoria = new JMenuItem("Autor");
		autoria.addActionListener(new AutorListener());
		JMenuItem versao = new JMenuItem("Versão");
		versao.addActionListener(new VersaoListener());
		menuSobre.add(autoria);
		menuSobre.add(versao);
		barraDeMenu.add(menuSobre);		
		janela.setJMenuBar(barraDeMenu);
		
		JPanel painelLivro = new JPanel();	
		painelLivro.setBorder(BorderFactory.createRaisedBevelBorder());
		painelLivro.setLayout(new BoxLayout(painelLivro,BoxLayout.X_AXIS));		
		JLabel rotuloISBN = new JLabel("  ISBN: ");	
		rotuloISBN.setFont(letraVisivelRotulo);
		JLabel rotuloAutor = new JLabel("  Autor: ");
		rotuloAutor.setFont(letraVisivelRotulo);
		JLabel rotuloTitulo = new JLabel("  Título: ");	
		rotuloTitulo.setFont(letraVisivelRotulo);
		JLabel rotuloEditora = new JLabel("  Editora: ");	
		rotuloEditora.setFont(letraVisivelRotulo);
		JLabel rotuloEdiçao = new JLabel("  Edição: ");
		rotuloEdiçao.setFont(letraVisivelRotulo);
		JLabel rotuloAno = new JLabel("  Ano: ");	
		rotuloAno.setFont(letraVisivelRotulo);
		JLabel rotuloPaginas = new JLabel("  Páginas: ");
		rotuloPaginas.setFont(letraVisivelRotulo);
		JLabel rotuloPreço = new JLabel("  Preço médio: ");	
		rotuloPreço.setFont(letraVisivelRotulo);
		Box infoLivro = new Box(BoxLayout.X_AXIS);		
		infoLivro.add(rotuloISBN);
		infoLivro.add(isbn);
		isbn.setFont(letraVisivelTexto);		
		infoLivro.add(rotuloTitulo);
		infoLivro.add(titulo);
		titulo.setFont(letraVisivelTexto);
		infoLivro.add(rotuloAutor);
		infoLivro.add(autor);
		autor.setFont(letraVisivelTexto);
		infoLivro.add(rotuloEditora);
		infoLivro.add(editora);
		editora.setFont(letraVisivelTexto);
		infoLivro.add(rotuloEdiçao);
		infoLivro.add(ediçao);
		ediçao.setFont(letraVisivelTexto);
		infoLivro.add(rotuloAno);
		infoLivro.add(ano);
		ano.setFont(letraVisivelTexto);
		infoLivro.add(rotuloPaginas);
		infoLivro.add(paginas);
		paginas.setFont(letraVisivelTexto);
		infoLivro.add(rotuloPreço);
		infoLivro.add(preço);
		preço.setFont(letraVisivelTexto);
		preço.setToolTipText("Formato \"0000.00\"");
		painelLivro.add(infoLivro);
		JButton botaoSalvarComo = new JButton("Salvar Como");
		botaoSalvarComo.setFont(letraVisivelRotulo);
		botaoSalvarComo.addActionListener(new SalvarComoListener());
		JButton botaoAdicionar = new JButton("Adicionar");
		botaoAdicionar.setFont(letraVisivelRotulo);
		botaoAdicionar.addActionListener(new AdicionarListener());		
		painelLivro.add(botaoAdicionar);		
		painelLivro.add(botaoSalvarComo);
		janela.getContentPane().add(BorderLayout.NORTH, painelLivro);
		
		JPanel painelBiblioteca = new JPanel();
		painelBiblioteca.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));		
		painelBiblioteca.setLayout(new BoxLayout(painelBiblioteca, BoxLayout.Y_AXIS));
		Font fonteAreaDeTexto = new Font("sanserif", Font.PLAIN, 24);
		JLabel rotuloInfoDaBiblioteca = new JLabel("BIBLIOTECA.");
		rotuloInfoDaBiblioteca.setFont(letraVisivelRotulo);
		painelBiblioteca.add(rotuloInfoDaBiblioteca);
		infoDaBiblioteca.setFont(fonteAreaDeTexto);
		infoDaBiblioteca.setBackground(Color.black);
		infoDaBiblioteca.setForeground(Color.orange);
		infoDaBiblioteca.setLineWrap(false);
		infoDaBiblioteca.setEditable(true);
		JScrollPane barraDeRolagem = new JScrollPane(infoDaBiblioteca);
		barraDeRolagem.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		barraDeRolagem.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);		
		painelBiblioteca.add(barraDeRolagem);
		JLabel rotuloInfoDAPesquisa = new JLabel("RESULTADOS DA PESQUISA.");
		rotuloInfoDAPesquisa.setFont(letraVisivelRotulo);
		painelBiblioteca.add(rotuloInfoDAPesquisa);
		infoDaPesquisa.setFont(fonteAreaDeTexto);
		infoDaPesquisa.setBackground(Color.black);
		infoDaPesquisa.setForeground(Color.orange);
		infoDaPesquisa.setLineWrap(false);
		infoDaPesquisa.setEditable(true);
		JScrollPane barraDeRolagemPesquisa = new JScrollPane(infoDaPesquisa);
		barraDeRolagemPesquisa.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		barraDeRolagemPesquisa.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);		
		painelBiblioteca.add(barraDeRolagemPesquisa);
		janela.getContentPane().add(BorderLayout.CENTER, painelBiblioteca);
		
		JPanel painelSul = new JPanel();
		painelSul.setBorder(BorderFactory.createRaisedBevelBorder());
		JButton botaoCarregar = new JButton("Carregar Livros");
		botaoCarregar.setFont(letraVisivelRotulo);
		botaoCarregar.addActionListener(new CarregarListener());
		JLabel rotuloPesquisa = new JLabel("  Pesquisa: ");
		rotuloPesquisa.setFont(letraVisivelRotulo);
		JButton botaoPesquisar = new JButton("Pesquisar");
		botaoPesquisar.setFont(letraVisivelRotulo);
		botaoPesquisar.addActionListener(new PesquisarListener());
		JButton botaoLimparBiblioteca = new JButton("Limpar biblioteca");
		botaoLimparBiblioteca.setFont(letraVisivelRotulo);
		botaoLimparBiblioteca.addActionListener(new LimparBibListener());	
		JButton botaoLimparPesquisa = new JButton("Limpar pesquisa");
		botaoLimparPesquisa.setFont(letraVisivelRotulo);
		botaoLimparPesquisa.addActionListener(new LimparPesListener());
		painelSul.add(botaoCarregar);
		painelSul.add(botaoLimparBiblioteca);
		painelSul.add(rotuloPesquisa);
		textoPesquisa.setFont(letraVisivelTexto);
		painelSul.add(textoPesquisa);
		painelSul.add(botaoPesquisar);
		painelSul.add(botaoLimparPesquisa);
		painelSul.add(rotuloNumDeLivros);
		rotuloNumDeLivros.setFont(letraVisivelRotulo);
		janela.getContentPane().add(BorderLayout.SOUTH, painelSul);
		
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.pack();
		janela.setLocation(99, 0);
		janela.setVisible(true);		
	}	
	
	public class LimparBibListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			infoDaBiblioteca.setText("");

		}

	}
	
	public class LimparPesListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			infoDaPesquisa.setText("");

		}

	}
	
	public class PesquisarListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser carregarArquivo = new JFileChooser();
			carregarArquivo.showOpenDialog(janela);
			int contador = 0;
			try {
				BufferedReader br = new BufferedReader(new FileReader(carregarArquivo.getSelectedFile()));				
				infoDaPesquisa.setText(infoDaPesquisa.getText() + "Nova pesquisa:\n");				
				String linha = null;
				pat = Pattern.compile(textoPesquisa.getText(), Pattern.CASE_INSENSITIVE);	
				Pattern pat1 = Pattern.compile("\\d+[.]\\d{2}");
				Matcher mat1;
				double valor = 0;
				while ((linha = br.readLine())!= null) {					
					mat = pat.matcher(linha);					
					if (mat.find()) {
						infoDaPesquisa.append(linha + "\n\n");						
						contador++;	
						mat1 = pat1.matcher(linha);
						if (mat1.find()) {
							valor += Double.parseDouble(mat1.group());
						}
					}
					infoDaPesquisa.moveCaretPosition(0);
				}
				rotuloNumDeLivros.setText(contador <= 1 ? "Existe " + contador + " livro. Num total de " +
                        fmt.format(valor) + ", no arquivo " + carregarArquivo.getSelectedFile().getName(): 
                        "Existem " + contador + " livros. Num total de " + fmt.format(valor) +
                        " no arquivo " + "\"" + carregarArquivo.getSelectedFile().getName() + "\"");				
				br.close();
			} catch (IOException e2) {
				e2.printStackTrace();
			}

		}

	}
	
	public class CarregarListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser carregarArquivo = new JFileChooser();
			carregarArquivo.showOpenDialog(janela);
			int contador = 0;			
			try {
				BufferedReader br = new BufferedReader(new FileReader(carregarArquivo.getSelectedFile()));				
				String linha = null;
				pat = Pattern.compile("\\d+[.]\\d{2}");				
				double valor = 0;
				while ((linha = br.readLine())!= null) {					
					infoDaBiblioteca.append(linha + "\n\n");
					contador++;	
					mat = pat.matcher(linha);
					if (mat.find()) {
						valor += Double.parseDouble(mat.group());
					}
				}
				infoDaBiblioteca.moveCaretPosition(0);
				rotuloNumDeLivros.setText(contador <= 1 ? "Existe " + contador + " livro. Num total de " +
				                          fmt.format(valor) + ", no arquivo " + carregarArquivo.getSelectedFile().getName(): 
                                          "Existem " + contador + " livros. Num total de " + fmt.format(valor) +
                                          " no arquivo " + "\"" + carregarArquivo.getSelectedFile().getName() + "\"");
				br.close();
			} catch (IOException e2) {
				e2.printStackTrace();
			}

		}

	}

	public class AdicionarListener implements ActionListener {	
		
		@Override
		public void actionPerformed(ActionEvent e) {
			pat = Pattern.compile("\\d+[.]\\d{2}");
			mat = pat.matcher(preço.getText());
			if (!mat.find()) {
				JOptionPane.showMessageDialog(null,
						"Soluções: (1) Separe os centavos usando '.' (ponto) ao invés de ',' (vírgula).\n"
								+ "(2) É obrigatório colocar, no mínimo, dois dígitos decimais.\n" +
								"Exemplo: 1234.56",	"Erro na entrada do preço médio", JOptionPane.ERROR_MESSAGE);
			} else {
				String livro = infoDaBiblioteca.getText() + "ISBN: " + isbn.getText() + "     TÍTULO: "
						+ titulo.getText() + " @@@ AUTOR: " + autor.getText() + "     Editora " + editora.getText()
						+ ", " + ano.getText() + ", " + ediçao.getText() + "a edição, " + paginas.getText()
						+ " pág.     PREÇO: " + "R$ " + preço.getText() + "\n\n";
				infoDaBiblioteca.setText(livro);
				isbn.setText("");
				titulo.setText("");
				autor.setText("");
				editora.setText("");
				ano.setText("");
				ediçao.setText("");
				paginas.setText("");
				preço.setText("");				
				isbn.requestFocus();
			}

		}

	}	

	public class SalvarComoListener implements ActionListener {	
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser salvarNovoArquivo = new JFileChooser();
			salvarNovoArquivo.showSaveDialog(janela);
			TreeSet<String> listaDeLivros = new TreeSet<String>();
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(salvarNovoArquivo.getSelectedFile()));				
				String infoDoAluno = infoDaBiblioteca.getText();
				String[] livros = infoDoAluno.split("\n");				
				for (int i = 0; i < livros.length; i++) {
					listaDeLivros.add(livros[i]);
				}
				String[] livrosOrganizados = new String[listaDeLivros.size()];
				livrosOrganizados = listaDeLivros.toArray(livrosOrganizados);
				for (int i = 1; i < livrosOrganizados.length; i++) {
					bw.write(livrosOrganizados[i] + "\n");
				}				
				bw.close();
			} catch (IOException e2) {
				e2.printStackTrace();
			}

		}

	}
	
	public class AutorListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {			
			JOptionPane.showMessageDialog(null, "Autor: Thiago de Oliveira Alves\ntowo497@gmail.com", "Sobre mim", JOptionPane.INFORMATION_MESSAGE);

		}

	}
	
	public class VersaoListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null, "Versão: 1.0 \n 03-04-2020", "Sobre este", JOptionPane.INFORMATION_MESSAGE);

		}

	}	
	
}

