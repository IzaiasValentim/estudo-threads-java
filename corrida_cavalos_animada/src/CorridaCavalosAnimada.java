import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CorridaCavalosAnimada extends JFrame {

    private final JButton botaoAcao;
    private final List<JProgressBar> barras = new ArrayList<>();
    private final List<JLabel> labelsCavalos = new ArrayList<>();

    private ImageIcon iconeCavalo;

    public CorridaCavalosAnimada() {
        setTitle("Corrida de Cavalos em Threads");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        try {
            ImageIcon imagemOriginal = new ImageIcon("corrida_cavalos_animada/src/media/cavalo_corrida.png");
            if (imagemOriginal.getIconWidth() == -1) {
                System.err.println("Erro: Imagem não encontrada no caminho especificado!");
                iconeCavalo = null;
            } else {
                Image imgRedimensionada = imagemOriginal.getImage()
                        .getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                iconeCavalo = new ImageIcon(imgRedimensionada);
            }

        } catch (Exception e) {
            System.out.println("Erro ao carregar imagem: " + e.getMessage());
            iconeCavalo = null;
        }

        JPanel painelTopo = new JPanel();
        painelTopo.setLayout(new BorderLayout());
        painelTopo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Cabeçalho ---
        JLabel titulo = new JLabel("Corrida de Threads", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(titulo, BorderLayout.NORTH);

        // --- Botão para sair ---
        JButton botaoSair = new JButton("Sair");
        botaoSair.setBackground(Color.RED);
        botaoSair.setForeground(Color.WHITE);
        botaoSair.setFont(new Font("Arial", Font.BOLD, 18));
        botaoSair.addActionListener(e -> System.exit(0));

        botaoSair.setFocusPainted(false);

        JPanel painelBotaoCanto = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotaoCanto.add(botaoSair);

        painelTopo.add(titulo, BorderLayout.CENTER);
        painelTopo.add(painelBotaoCanto, BorderLayout.EAST);

        add(painelTopo, BorderLayout.NORTH);

        JPanel painelPista = new JPanel();
        painelPista.setLayout(new GridLayout(5, 1, 10, 10));

        for (int i = 0; i < 5; i++) {
            painelPista.add(criarPistaCavalo(i + 1));
        }
        add(painelPista, BorderLayout.CENTER);

        botaoAcao = new JButton("Começar");
        botaoAcao.setBackground(Color.GREEN);
        botaoAcao.setForeground(Color.WHITE); // Texto branco para contraste
        botaoAcao.setFont(new Font("Arial", Font.BOLD, 18));
        botaoAcao.addActionListener(e -> iniciarCorrida());
        add(botaoAcao, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CorridaCavalosAnimada().setVisible(true));
    }

    private JPanel criarPistaCavalo(int numero) {
        JPanel painel = new JPanel();
        painel.setLayout(null);
        painel.setBorder(BorderFactory.createTitledBorder("Cavalo " + numero));

        JProgressBar barra = new JProgressBar(0, 100);
        barra.setStringPainted(true);
        barra.setBounds(10, 50, 760, 30);
        barra.setForeground(Color.GRAY);
        barras.add(barra);

        JLabel lblCavalo = new JLabel();
        lblCavalo.setOpaque(false);
        if (iconeCavalo != null) {
            lblCavalo.setIcon(iconeCavalo);
        } else {
            lblCavalo.setText("🐎");
            lblCavalo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 30));
        }
        lblCavalo.setBounds(60, 0, 50, 50);
        labelsCavalos.add(lblCavalo);

        painel.add(lblCavalo);
        painel.add(barra);

        return painel;
    }

    private void iniciarCorrida() {
        botaoAcao.setEnabled(false);
        botaoAcao.setText("Corrida em andamento...");
        botaoAcao.setBackground(Color.GRAY);

        Cavalo.jaExisteVencedor = false;

        for (JProgressBar b : barras) b.setValue(0);
        for (JLabel l : labelsCavalos) l.setBounds(10, 0, 50, 50);

        for (int i = 0; i < 5; i++) {
            JProgressBar barra = barras.get(i);
            JLabel label = labelsCavalos.get(i);

            Runnable acaoVitoria = () -> {
                botaoAcao.setText("Recomeçar");
                botaoAcao.setBackground(Color.BLUE);
                botaoAcao.setEnabled(true);
            };

            Cavalo c = new Cavalo("Cavalo " + (i + 1), barra, label, acaoVitoria);

            new Thread(c).start();
        }
    }
}