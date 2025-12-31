import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class Cavalo implements Runnable {
    public static boolean jaExisteVencedor = false;
    private final String nome;
    private final JProgressBar barraProgresso;
    private final JLabel labelImagem; // Por padrão está sendo usada a mesma imagem para todos.
    private final Runnable aoFinalizar;

    public Cavalo(String nome, JProgressBar barraProgresso, JLabel labelImagem, Runnable aoFinalizar) {
        this.nome = nome;
        this.barraProgresso = barraProgresso;
        this.labelImagem = labelImagem;
        this.aoFinalizar = aoFinalizar;
    }

    @Override
    public void run() {
        int distanciaPercorrida = 0;
        int total = 100;
        barraProgresso.setForeground(Color.GRAY);

        // Enquanto não terminar a corrida (100 metros)
        while (distanciaPercorrida < total && !jaExisteVencedor) {

            // Simula o passo do cavalo
            int salto = ThreadLocalRandom.current().nextInt(2, 5);
            distanciaPercorrida += salto;

            if (distanciaPercorrida > total) {
                distanciaPercorrida = total;
            }

            // Atualiza a Interface Gráfica
            final int progressoAtual = distanciaPercorrida;

            SwingUtilities.invokeLater(() -> {
                barraProgresso.setValue(progressoAtual);
                moverImagem(progressoAtual); // Chama a função que move a representação do cavalo
            });

            // Pausa simulada entre os passos do cavalo.
            try {
                Thread.sleep(ThreadLocalRandom.current().nextLong(40, 180));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        // Verifica se cruzou a linha de chegada e se é o primeiro
        if (distanciaPercorrida == total) {
            synchronized (Cavalo.class) {
                if (!jaExisteVencedor) {
                    jaExisteVencedor = true;
                    System.out.println(nome + " VENCEU!");
                    barraProgresso.setForeground(Color.YELLOW);
                    // Executa a ação de "Fim de jogo" na interface (mudar botão)
                    SwingUtilities.invokeLater(aoFinalizar);

                    JOptionPane.showMessageDialog(null, "O vencedor foi o " + nome + "!");
                }
            }
        }
    }

    // Lógica visual para empurrar a imagem para a direita
    private void moverImagem(int porcentagem) {
        int larguraBarra = barraProgresso.getWidth();
        int novoX = (larguraBarra * porcentagem) / 100;

        if (novoX > larguraBarra - 50) novoX = larguraBarra - 50;

        labelImagem.setBounds(novoX, 0, 50, 50); // x, y, largura, altura
    }
}