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
## Parte 2 - Threads e semáforos
O semáforo garante controle quando threads querem acessar o mesmo recurso do sistema ao mesmo tempo,
garantindo que ocorra justiça e não haja nenhuma perda.

### Execução sem semáforo


| Execução | Valor Esperado | Valor Obtido | Tempo de Execução (ms) |
| :---: | :---: | :---: | :---: |
| **1ª** | 1.600.000 | 353.736 | 26,294 ms |
| **2ª** | 1.600.000 | 389.996 | 32,826 ms |
| **3ª** | 1.600.000 | 386.811 | 30,132 ms |

Com Semáforo Binário (FIFO)

| Execução | Valor Esperado | Valor Obtido | Tempo de Execução (ms) |
| :---: | :---: | :---: | :---: |
| **1ª** | 1.600.000 | 1.600.000 | 4.661,981 ms |
| **2ª** | 1.600.000 | 1.600.000 | 4.596,703 ms |
| **3ª** | 1.600.000 | 1.600.000 | 4.538,603 ms |

### Considerações Finais


* **Consequências da Condição de Corrida:** O contador++ executa 3 ações: leitura do valor atual, incremento e
por fim gravação, por conta da falta de sincronização os threads podem incrementar ao mesmo tempo, fazendo com que
se perca incrementos no caminho, como observado no primeiro teste;
* **Por que a versão com semáforo é correta ?:** Utilizando da exclusão mútua o programa realiza apenas 1 incremento por vez, como numa fila para
ir ao banheiro, se há apenas uma vaga e está ocupado a próxima pessoa na fila deve esperar a vaga liberar para assim entrar;
* **Trade-off e throughput:** Na versão sem sincronização o tempo de execução é relativamente menor, porém a quantidade de incrementos
perdidos faz com que esse tempo seja inútil.
Já na versão com semáforo (justo) utilizando FIFO, o tempo de execução acabou sendo maior, porém nenhum
incremento se perdeu, sendo bem mais efetivo;
* **Happens-before:** A regra de Happens-before em Java faz com que os problemas de visibilidade
e ordenação sejam corrigidos, se um thread A acontece antes de B, o Java garante que o resultado de A será
visível para B antes de executar, e quando B adquiri o semáforo, será forçado a ler o valor atualizado direto na memória global.


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

A correção faz com que as duas threads adquiram sempre o Recurso A antes do Recurso B.
Com isso, a espera circular deixa de existir, pois se ambas seguem a mesma ordem,
nunca haverá um ciclo de espera entre elas.

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
← programa trava aqui, sem mais saídas
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