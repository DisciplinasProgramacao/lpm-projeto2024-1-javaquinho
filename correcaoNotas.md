# JAVAQUINHO CODEMÃO

## Sprint 1 - Até 07/abril
  - Nota de grupo (8 pontos)
    - Modelo UML - restaurante, mesas, requisicoes, cliente (nota de grupo, 8 pontos)
	
  - Nota individual (12 pontos)
    - Implementações e testes + app
    - Documentação das classes.

### Commit 9d89f80
Diagrama sem relação requisição-mesa. Métodos da requisição.  (todos) - 7

Cliente sem documentação - Ian - 8 

Mesa sem documentação. Sem "podeAtender" - Eduardo - 7

Requisicao sem compilar. Erros de retorno. Sem documentação. Sem lógica de negócio. Sem teste - Saulo - 4

Restaurante sem documentação nem teste - Roberta - 8

Main sem código, só modelo - Hélio e João - 4

## Sprint 2 - Até 19/maio
  - Nota de grupo (6 pontos)
    - Modelo UML atualizado - cardápio e pedidos
	- Estrutura Spring
  
  - Nota individual (14 pontos)	
    - Implementações cardápio e pedidos
    - Controllers
    - Correções anteriores

### Commit c202ba1
Diagrama - pedido sem atributos ou métodos - 5

Classe/modelo Menu sem lógica de negócio. Controller Menu ok. (não era obrigatório)

Classe/modelo Produto sem lógica de negócio. Controller Produto ok. (não era obrigatório) - Roberta - 12

Classe Requisicao sem lógica de negócio. - Saulo - 0 

Sem regras no Controller da Requisicao (já que não tem no modelo) - Eduardo - 5

Classe Restaurante sem implementacao (meu código comentado), sem Controller - Helio - 0

Sem App - Joao - 0 

Cliente Controller - Ian 

Sem Controller nem classe Pedido - Ian - 8,5 (nota considerando implementação atrasada)

# Sprint 3 - Até 05/junho
  - Nota de grupo **nota como entrega em atraso**
    - Modelo atualizado - menu fechado
  
  - Nota individual (14 pontos)	
    - Implementações menu fechado e app
    - Correções anteriores

### Commit ae2a9d4 (10/06)
Corrigido: classe Pedido e Pedido controller - Ian (nota lançada na sprint anterior)

Diagrama sem menu fechado ou pedido fechado - 0 

~~A implementacao do master está praticamente igual ao fim da sprint 2. Vocês ficam sem nota aqui por enquanto para serem avaliados proporcionalmente ao que foi melhorado na sprint 4, mas considerando como entrega em atraso~~

_Atualização: notas replicadas da sprint 4, com 30% de penalização por atraso_


## Sprint 4 - Apresentação em 24/06
  - Nota de grupo 6/6 pontos
	- Modelo atualizado
	- Apresentação
	
  - Nota individual (14 pontos)
    - Ajustes do último quadro "Projeto GitHub"
    - Correções das sprints anteriores

### Commits SP4
Runner/Main (Joao): funcionalidades ok. Modularidade ruim (mesmo o código antigo de restaurante já tinha funcionalidades que não foram aproveitadas como modelo). 11,2
Documentacao (Ian): ok
Controller (Ian): uso de optional... 11
Controller (Roberta): Getclassname não é legal
PedidoFechado (Roberta): ok
Documentacao (Roberta): ok  12,6
Requisicao (Saulo): faltou encerrar o pedido na requisicao. não pode transformar pedido em nulo (perda de dados!!!). catch "exception" e lança "runtime".  8,4
Documentacao (Eduardo) 	
Controller(Eduardo): ok 12
Restaurante (Hélio): fazendo lógica de mesa. processar com processo pouco legível. uso de keyset. somente uma exceção e runtime.  9,8