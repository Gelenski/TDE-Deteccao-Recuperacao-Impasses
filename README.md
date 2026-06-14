# TDE — Detecção e recuperação de impasses

Grupo: TDE - 12 | Alunos: Adrian Lazier, Carlos Lopes, Luan Saviski & Lucas Gelenski

Linguagem utilizada: Java

## Parte 1 - Jantar dos Filósofos

O problema do Jantar dos filósofos contém as quatro condições de Coffman para
ocorrência de um DeadLock que são:

- Espera Circular;
- Manter e esperar;
- Não preempção;
- Exclusão mutúa.

Ou seja, todos podem tentar pegar simultaneamente um garfo e aguardam para sempre o
segundo, assim ocorrendo o deadlock.

### Solução

A solução do problema com o Semaphore justo, configurado com N - 1 permissões, funciona
como um arbítro que permite que somente 4 filósofos possam competir por recursos de maneira
simultânea, impedindo que todos os cinco entrem em um ciclo de espera circular, o Semaphore
também garante ordem de execução com FIFO, assim reduzindo a possibilidade de inanição.

### Pseudocódigo com Deadlock

```text
enquanto (verdadeiro):
    pensar()
    pegarGarfoEsquerdo
    pegarGarfoDireito
    comer()
    largarGarfoDireito
    largarGarfoEsquerdo
```

### Pseudocódigo com Tratamento

```text
enquanto (verdadeiro):
    pensar()
    solicitarPermissaoGarcom
    pegarGarfoEsquerdo
    pegarGarfoDireito
    comer()
    largarGarfoDireito
    largarGarfoEsquerdo
    liberarGarcom
```