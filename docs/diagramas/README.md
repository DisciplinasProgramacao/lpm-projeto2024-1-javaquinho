# Diagramas de classe
Coloque aqui todas as versões do seu diagrama de classes. Cada versão deve começar com um número de dois dígitos, na ordem crescente.

01 - ![DiagramaClasses V01 - Trabalho LPM](https://github.com/DisciplinasProgramacao/lpm-projeto2024-1-joaoo/assets/143658544/fd3e041a-6138-4223-bc88-16f1499f1baf)
No diagrama é representado o sistema de atendimento de um restaurante. O qual possui diversas mesas com capacidades diversas, mas todas objetos da mesma classe. Estas, dentro do sistema, são armazenados em um vetor de objetos que é compartilhado com a classe Restaurante. 
Simultaneamente, para realizar um atendimento, o Restaurante necessita de uma Requisição contendo as informações de quantas pessoas vão se sentar à mesa e qual é o Cliente que fez a requisição assim como seu contato. Individuando aqui duas outras classes, indicadas pela inicial maiúscula. 
A classe Cliente é o cadrastro do cliente do restaurante e contém seu nome e telefone para contato; cada cliente pode efetuar nenhuma ou várias Requisições. Estas que armazenam os dados de quantas pessoas são esperadas, o horário e data que foi efetuada a requisição, posteriormente, o horário de saída e as informações do Cliente responsável.
O Restaurante recebe tanto o vetor com as Mesas quanto um vetor FilaAtendimento contendo as Requisições na ordem que foram feitas. Com estas informações o Restaurante analisa as Requisições e compara a quantidade de pessoas esperada com a capacidade das mesas disponíveis, assim podendo alocar as Requisições nas suas respectivas Mesas na ordem que foram efetuadas e seguindo a capacidade máxima das Mesas disponíveis.
Ao final do atendimento, o Restaurante fecha a conta encerrando assim o atendimento, com isso a própria classe Restaurante armazena a Requisição que foi concluída em um vetor de historicoRequisicao e chama uma função que desocupa a Mesa, podendo assim esta receber outra Requisição.

02 -![DiagramaClasses V01 - Trabalho LPM](https://github.com/DisciplinasProgramacao/lpm-projeto2024-1-javaquinho/assets/136115980/58bc95ce-deca-48d1-835b-d7676909a580)

03 -![DiagramaClasses V03 - Trabalho LPM](https://github.com/DisciplinasProgramacao/lpm-projeto2024-1-javaquinho/assets/136115980/83b8c5ae-baa7-4b3f-9769-3995dd32eb10)

04 -![DiagramaClasses V03 - Trabalho LPM](https://github.com/DisciplinasProgramacao/lpm-projeto2024-1-javaquinho/assets/136115980/99bfdbca-720c-4697-95ea-fd86dd1384ab)

05 -![DiagramaClasses V03 - Trabalho LPM (1)](https://github.com/DisciplinasProgramacao/lpm-projeto2024-1-javaquinho/assets/136115980/9e1787d5-00ec-481e-864f-49a25f4b39ea)

06 -![Diagrama em branco](https://github.com/DisciplinasProgramacao/lpm-projeto2024-1-javaquinho/assets/136115980/b7bbea9d-e729-4cd0-993f-e450e0a7bc63)


