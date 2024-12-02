# Simulador de Sistema de Arquivos com Journaling
Este projeto simula um sistema de arquivos básico em Java, com foco na implementação do conceito de journaling, uma técnica utilizada para garantir a integridade do sistema em caso de falhas inesperadas. O simulador inclui a gestão de arquivos e diretórios, além do registro de operações em um diário (journal) para recuperação após falhas.
## Metodologia
O simulador será desenvolvido utilizando a linguagem de programação Java. O programa implementará métodos que simulam as operações de um sistema de arquivos, tais como a criação de arquivos, a criação e manipulação de diretórios, e o registro dessas operações em um arquivo de diário.O objetivo do simulador é fornecer uma visão prática de como um sistema de arquivos pode ser estruturado e como o journaling contribui para a integridade e recuperação de dados em caso de falha.
 

## Parte 1: Introdução ao Sistema de Arquivos com Journaling

### Descrição do Sistema de Arquivos
Um sistema de arquivos é uma estrutura de dados usada para armazenar e organizar os arquivos em um dispositivo de armazenamento, como um disco rígido ou SSD. Ele define como os arquivos são nomeados, armazenados e acessados.A principal função do sistema de arquivos é garantir que os dados sejam armazenados de maneira eficiente, com segurança e acesso rápido.
### Journaling
Journaling é uma técnica utilizada em sistemas de arquivos para melhorar a confiabilidade e permitir a recuperação de dados após falhas inesperadas. O journaling funciona registrando informações sobre as mudanças realizadas no sistema de arquivos antes que elas sejam efetivamente aplicadas. Isso pode ser feito de diferentes maneiras, com os tipos mais comuns sendo:
  - **Write-Ahead Logging (WAL)**: As alterações são registradas no diário antes de serem aplicadas ao sistema de arquivos.
  - **Log-Structured**: As alterações são registradas de forma contínua e sequencial no diário, e depois aplicadas ao sistema de arquivos.O propósito do journaling é garantir que o sistema de arquivos possa ser recuperado para um estado consistente após falhas de energia, travamentos do sistema ou outros eventos inesperados.
 

## Parte 2: Arquitetura do Simulador

### Estrutura de Dados
O simulador utiliza as seguintes classes Java para representar os componentes do sistema de arquivos:
  1. **FileSystemEntry (Classe abstrata)**: Representa uma entrada no sistema de arquivos (pode ser um arquivo ou um diretório).
    - Atributos: nome, caminho, tempo de criação e último tempo de modificação.
  2. **Directory**: Representa um diretório no sistema de arquivos.
    - Atributos: um mapa de entradas, que pode conter arquivos e subdiretórios.
    - Métodos: adicionar/remover entradas e buscar diretórios por caminho.
  3. **File**: Representa um arquivo no sistema de arquivos.
    - Atributos: conteúdo do arquivo.
    - Métodos: manipular o conteúdo do arquivo.
  4. **Journal**: Representa o diário de operações do sistema de arquivos.
    - Atributos: uma lista de entradas de diário e o caminho do arquivo de diário.
    - Métodos: adicionar entradas ao diário, carregar e salvar o diário em um arquivo.
### Journaling
O journaling é implementado na classe `Journal`. Cada operação no sistema de arquivos (criação de arquivos, modificação de arquivos, criação de diretórios, etc.) será registrada no diário antes de ser aplicada. O diário será persistido em um arquivo de texto, permitindo que as operações sejam recuperadas após uma falha.O diário é composto por entradas (`JournalEntry`), que contêm informações sobre a operação realizada, como o tipo de operação (criação, modificação, remoção) e os parâmetros relevantes (nome, caminho, conteúdo, etc.).
 

## Parte 3: Implementação em Java

### Classe `FileSystemSimulator`
A classe `FileSystemSimulator` implementa o simulador do sistema de arquivos, gerenciando os métodos de operação e interagindo com o diário. Cada operação no sistema de arquivos (como a criação de arquivos e diretórios) gera uma entrada no diário.
### Classes `File` e `Directory`
As classes `File` e `Directory` representam os arquivos e diretórios no sistema de arquivos, respectivamente. Ambas herdam de `FileSystemEntry`, que fornece os atributos comuns, como nome, caminho, tempo de criação e última modificação.
### Classe `Journal`
A classe `Journal` gerencia o diário de operações, que é carregado ao iniciar o simulador e salvo a cada nova entrada. As entradas são salvas em um arquivo de texto para garantir que todas as operações sejam persistidas.
 
## Resultados
O simulador de sistema de arquivos apresentou um desempenho esperado nas operações básicas de manipulação de arquivos e diretórios. As ações realizadas, como criar diretórios, criar e remover arquivos, renomear, mover e copiar, foram bem-sucedidas, seguindo a lógica de funcionamento de um sistema de arquivos real. Abaixo estão os resultados das operações realizadas:
  1. **Criação de Diretório**: O comando `mkdir teste` criou o diretório com sucesso.
  2. **Criação de Arquivo**: O comando `touch teste.js` criou um arquivo dentro do diretório raiz. A remoção do arquivo com `rm teste.js` foi bem-sucedida.
  3. **Navegação entre Diretórios**: O comando `cd teste` entrou no diretório criado, e o comando `ls` listou corretamente o conteúdo do diretório.
  4. **Criação e Listagem de Arquivo no Novo Diretório**: Dentro do diretório `teste`, o comando `touch testearquivo.txt` criou o arquivo com sucesso, e o comando `ls` listou o arquivo corretamente.
  5. **Navegação para Diretório Anterior**: O comando `cd ..` retornou ao diretório raiz, permitindo nova navegação.
  6. **Cópia de Diretório**: O comando `cp teste teste2` copiou o diretório `teste` para um novo diretório `teste2`, e a listagem do diretório `teste2` mostrou o arquivo copiado com sucesso.
### Implementação de Journaling
Embora a simulação mostre um bom comportamento nas operações, o componente de **journaling** (registro de ações para recuperação em caso de falha) não foi explicitamente testado nos resultados fornecidos. A implementação de um diário, se aplicada corretamente, garantiria que cada operação (como criação, remoção, cópia, etc.) fosse registrada, o que possibilitaria a recuperação do estado anterior do sistema de arquivos após falhas, tornando o sistema mais robusto e confiável.
### Conclusão
Este simulador demonstrou com êxito as funcionalidades de um sistema de arquivos básico, incluindo criação, remoção e movimentação de arquivos e diretórios. A implementação de journaling contribuiria para aumentar a segurança e a confiabilidade do sistema, registrando as ações realizadas. O projeto fornece uma boa base para entender tanto a estrutura de um sistema de arquivos quanto o conceito de journaling, que pode ser estendido para sistemas reais para garantir a recuperação de dados em caso de falhas inesperadas.