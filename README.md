# Simulador de Escalonamento de Processos

Este projeto é um simulador de algoritmos de escalonamento de processos desenvolvido em Java.
Ele permite executar diferentes estratégias de escalonamento utilizando dados lidos de um arquivo de entrada.

## Algoritmos disponíveis

O sistema possui suporte para os seguintes algoritmos:

1. **FCFS (First Come First Served)**
2. **Prioridade**
3. **Round Robin**

---

# Como executar

## Pré-requisitos

* Java JDK 21+ instalado
* Terminal ou prompt de comando

Verifique a instalação com:

```bash
java -version
```

---

## Estrutura esperada

O projeto utiliza um arquivo chamado:

```txt
entrada.txt
```

Esse arquivo deve conter os dados dos processos e, quando necessário, o valor do quantum para o algoritmo Round Robin.

---

## Compilar o projeto

```bash
javac *.java
```

---

## Executar

Execute o programa com:

```bash
java Main
```

---

# Funcionamento

Ao iniciar, o sistema exibirá um menu:

```txt
===== SIMULADOR DE ESCALONAMENTO =====
1 - FCFS
2 - Prioridade
3 - Round Robin
Escolha o algoritmo:
```

Digite o número correspondente ao algoritmo desejado.

O programa irá:

1. Ler os dados do arquivo `entrada.txt`
2. Executar o algoritmo escolhido
3. Exibir o resultado no terminal

---

# Exemplo de execução

```txt
===== SIMULADOR DE ESCALONAMENTO =====
1 - FCFS
2 - Prioridade
3 - Round Robin
Escolha o algoritmo:
3

===== RESULTADO =====
...
```

---
