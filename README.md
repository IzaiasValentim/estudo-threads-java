<div align="center">
<h1>🐎 Simulador de Corrida com Java Threads</h1>
</div>

Neste projeto apresento uma aplicação gráfica desenvolvida em Java (Swing) para demonstrar visualmente o funcionamento de processamento concorrente e multithreading. O objetivo é simular uma corrida de cavalos onde cada competidor processa sua tarefa de forma independente e não-sequencial.

🚀 Como Executar
> Certifique-se de ter o JDK 8 ou superior instalado (Recomendado JDK 21 pois foi a versão do meu desenvolvimento).

:arrow_right: Em ...src/

1. Compile:
```bash
javac -encoding UTF-8 *.java
```
2. Execute:
```bash
java CorridaCavalosAnimada
```

:framed_picture: Demonstração
1. Estado inicial, o usuário pode clicar em "Começar" para iniciar a competição.
  - <img width="803" height="600" alt="Captura de tela de 2025-12-31 14-55-20" src="https://github.com/user-attachments/assets/43c63b8c-eb2e-477d-931f-d1ba70f1dc06" />
2. Corrida em andamento:
  - <img width="803" height="600" alt="Captura de tela de 2025-12-31 14-56-28" src="https://github.com/user-attachments/assets/7c7e040b-91a0-4287-ad97-ce50ca4f423f" />
3. Corrida finalizada. Após clicar em "Ok" o usuário pode iniciar outra corrida em "Recomeçar" ou sair da aplicação.
  - <img width="803" height="600" alt="Captura de tela de 2025-12-31 15-00-00" src="https://github.com/user-attachments/assets/1883d3f1-9bb5-4695-a82a-ed65313dfa65" />

🎯 Objetivos Didáticos:
- Criação e execução de Threads.
- Manipulação de interface gráfica (GUI) por threads secundárias.
- Condições de corrida (Race Conditions) e Sincronização.

🛠️ Tecnologias e Conceitos Aplicados
1. Multithreading (Runnable e Thread)
Cada cavalo é uma instância da classe Cavalo, que implementa a interface Runnable. Isso permite que cada cavalo tenha seu próprio ciclo de vida de execução (run()), avançando na pista e pausando (Thread.sleep) de forma independente dos outros.

2. Interface Gráfica (Java Swing)
   - A interface visual foi construída utilizando JFrame, JPanel e JProgressBar.
   - Barras de Progresso: Representam a distância percorrida (0 a 100m).
   - Imagens: Ícones dinâmicos que se movem sobre a barra conforme o progresso.
   - Interatividade: Botões de controle para iniciar e encerrar a aplicação.

3. Thread Safety na UI (SwingUtilities.invokeLater)
Um dos maiores desafios em aplicações Desktop é que a Interface Gráfica não é Thread-Safe.
    - Problema: Se uma thread de um cavalo tentar atualizar a barra de progresso diretamente, a aplicação pode travar ou apresentar falhas visuais.

    - Solução: Utilizamos SwingUtilities.invokeLater(() -> { ... }). Isso coloca a atualização visual na fila da Event Dispatch Thread (EDT), a única thread autorizada a desenhar na tela, garantindo uma execução fluida e segura.

4. Controle de Concorrência (O Vencedor)
Para determinar quem venceu, foi utilizada uma variável estática compartilhada (static boolean jaExisteVencedor).

    - Todos os cavalos verificam essa variável ao cruzar a linha de chegada.

    - O uso de blocos synchronized garante que apenas um cavalo consiga "levantar a bandeira" de vencedor, evitando que dois cavalos sejam declarados vencedores simultaneamente em caso de empate técnico (milissegundos).

📂 Estrutura do Projeto
- src/Cavalo.java:

  - Contém a lógica de "correr" (loop de incremento de distância).

  - Define a velocidade aleatória usando ThreadLocalRandom.

  - Comunica o progresso para a interface.

- CorridaInterface.java:

  - Gerencia a janela principal, layout e carregamento de imagens.

  - Instancia as Threads e reinicia a corrida criando novos objetos (já que threads mortas não podem ser reiniciadas).

  - Implementa botões com design "Flat" e lógica de encerramento (System.exit(0)).



   
