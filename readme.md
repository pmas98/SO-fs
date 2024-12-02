# Simulador de Sistema de Arquivos com Journaling
Este projeto simula um sistema de arquivos básico em Java, com foco na implementação do conceito de journaling, uma técnica utilizada para garantir a integridade do sistema em caso de falhas inesperadas. O simulador inclui a gestão de arquivos e diretórios, além do registro de operações em um diário (journal) para recuperação após falhas.

## Para rodar
  
  1. **Entrar no diretório**: Rode `cd so_fs`
  2. **Compilar o projeto**: Rode `javac -d bin -source 8 -target 8 -sourcepath src src/filesystem/*.java` em `so_fs`
  3. **Rodar o projeto**: Rode `java -cp bin filesystem.FileSystemSimulator` em `so_fs`


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
  5. **JournalEntry**: Representa uma entrada no diário de operações.
    - Atributos: Operação a ser realizada, timestamp, diretório atual e diretório pai
    - Métodos: Criar uma entrada no diário
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
O simulador de sistema de arquivos apresentou um desempenho esperado nas operações básicas de manipulação de arquivos e diretórios. As ações realizadas, como criar diretórios, criar e remover arquivos, renomear, mover e copiar, foram bem-sucedidas, seguindo a lógica de funcionamento de um sistema de arquivos real. Abaixo estão as operações realizadas:
  1. **Criação de Diretório**: O comando `mkdir [nome]` é capaz de criar diretórios.
  2. **Criação de Arquivo**: O comando `touch [nome]` criou um arquivo dentro do diretório atual. 
  3. **Deleção de Arquivos e Diretórios**:Para a remoção de arquivos e diretórios, basta usar `rm [nome]`.
  4. **Navegação entre Diretórios**: O comando `cd [nome]` é capaz de entrar em diretórios criados
  5. **Listar arquivos de diretório**: O comando `ls` lista corretamente o conteúdo do diretório.
  6. **Cópia de Diretório e Arquivos**: O comando `cp [nome] [nome2]` copia tanto diretórios como arquivos, usando uma abordagem recursiva.
  7. **Renomear Diretórios e Arquivos**: Para renomear diretórios foi criado o comando `mv [nome] [nome2]`
### Implementação de Journaling
O componente de journaling funciona através de um arquivo, que é lido no começo ou criado, caso não exista. As operações são registradas da seguinte forma `[timestamp] FUNÇÃO: DIRETÓRIO PAI -> DIRETÓRIO ATUAL`
### Conclusão
Este simulador demonstrou com êxito as funcionalidades de um sistema de arquivos básico, incluindo criação, remoção e movimentação de arquivos e diretórios. A implementação de journaling contribui para aumentar a segurança e do sistema, registrando as ações realizadas.