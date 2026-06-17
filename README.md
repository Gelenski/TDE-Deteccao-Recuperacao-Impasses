# TDE — Detecção e recuperação de impasses

Grupo: TDE - 12 | Alunos: Adrian Lazier, Carlos Lopes, Luan Saviski & Lucas Gelenski

Linguagem utilizada: Java

## Parte 1 - Jantar dos Filósofos

O problema do Jantar dos filósofos contém as quatro condições de Coffman para
ocorrência de um DeadLock que são:

- Espera Circular;
- Manter e esperar;
- Não preempção;
- Exclusão mútua.

Ou seja, todos podem tentar pegar simultaneamente um garfo e aguardam para sempre o
segundo, assim ocorrendo o deadlock.

### Solução

A solução do problema com o Semaphore justo, configurado com N - 1 permissões, funciona
como um árbitro que permite que somente 4 filósofos possam competir por recursos de maneira
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

## Parte 3 - Deadlock

O deadlock ocorre quando duas threads ficam bloqueadas esperando recursos que estão
sendo segurados uma pela outra, sem que nenhuma consiga avançar. As quatro condições
de Coffman presentes no cenário são:

- Espera Circular;
- Manter e esperar;
- Não preempção;
- Exclusão mútua.

Ou seja, Thread 1 segura o Recurso A e aguarda o B, enquanto Thread 2 segura o
Recurso B e aguarda o A, formando um ciclo onde nenhuma avança.

### Solução

A correção impõe hierarquia de recursos: ambas as threads adquirem sempre o Recurso A
antes do Recurso B, eliminando a espera circular pois não é possível formar um ciclo
quando todas seguem a mesma ordem de aquisição.

### Pseudocódigo com Deadlock

```text
Thread 1:
    adquirir(RecursoA)
    dormir(50ms)
    adquirir(RecursoB)
    concluir()

Thread 2:
    adquirir(RecursoB)
    dormir(50ms)
    adquirir(RecursoA)
    concluir()
```

### Pseudocódigo com Tratamento

```text
Thread 1 e Thread 2:
    adquirir(RecursoA)
    dormir(50ms)
    adquirir(RecursoB)
    concluir()
```

### Log - Versão com Deadlock

```
Iniciando simulação do DeadLock...
Thread 1 pegou o Recurso A
Thread 2 pegou o Recurso B
Thread 2 aguardando o Recurso A
Thread 1 aguardando o Recurso B
```

### Log - Versão Corrigida

```
Iniciando versão corrigida por hierarquia de recursos...
Thread 1 pegou o Recurso A.
Thread 1 pegou o Recurso B.
Thread 1 concluiu.
Thread 2 pegou o Recurso A.
Thread 2 pegou o Recurso B.
Thread 2 concluiu.
Execução finalizada sem deadlock.
```