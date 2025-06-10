# ⚙️ Padrões de Projeto

> 💡A seguir vamos abordar como funcionam alguns padrões de projeto

---

## ☝️ Padrão de Projeto Singleton

### 📘 O que é o Singleton?

O **Singleton** é um padrão de projeto da categoria **Criacional**, que tem como objetivo garantir que **uma classe tenha apenas UMA instância** e que forneça um **ponto de acesso global** a essa instância.

Imagine uma **central de configuração**, um **sistema de logging** ou um **gerenciador de conexão com banco de dados**. Você **não quer criar múltiplas instâncias** desses componentes — quer **uma só**, que possa ser reutilizada.

---

### 🧠 Quando Usar?

Use o Singleton quando você precisar de:

✅ **Uma única instância de uma classe** em todo o sistema  
✅ **Acesso global** e controlado a essa instância  
✅ **Gerenciar recursos compartilhados**, como:

- 📁 Arquivos de log
- 🗃️ Conexões com banco de dados
- ⚙️ Configurações do sistema
- 📊 Gerenciadores de cache

---

### 💡 Exemplo no Mundo Real

Imagine que você tenha um sistema que precisa registrar logs em um arquivo. Se cada parte do sistema criar sua própria instância do `Log`, o arquivo pode ficar **corrompido ou duplicado**. Com o Singleton, você garante que **todos os logs passem por uma única instância**, mantendo o controle e a consistência.

---

### 🎯 Benefícios

✨ Garante que só exista **uma única instância**  
🌍 Fornece **acesso global e controlado**  
🧩 Pode ser usado como **ponto central** para coordenar ações no sistema

---

### ⚠️ Cuidados ao Usar

⚠️ Pode introduzir **estado global indesejado**  
⚠️ Dificulta **testes unitários** se não for bem planejado  
⚠️ Pode **quebrar o princípio da responsabilidade única (SRP)**  
⚠️ Mau uso pode levar a **forte acoplamento**

---

### 🛠️ Boas Práticas

- Use Singleton com moderação, apenas quando **realmente necessário**
- Prefira versões **thread-safe**
- Em projetos maiores, considere **injeção de dependência** para facilitar testes
- Para casos mais avançados, use o Singleton com **enum** (thread-safe e seguro por padrão)

---

### 📌 Resumo Rápido

| Situação                          | Singleton é indicado? ✅❌ |
|----------------------------------|---------------------------|
| Central de logs                  | ✅                        |
| Gerenciador de configurações     | ✅                        |
| Serviço de autenticação global   | ✅                        |
| Objeto com múltiplas variações   | ❌                        |
| Modelos de domínio               | ❌                        |

---

### 🧪 Dica Extra

Se estiver em dúvida se deve usar Singleton, pergunte a si mesmo:

> *"Eu realmente preciso de **apenas uma instância** disso no sistema inteiro?"*  
> Se sim, Singleton pode ser uma boa escolha 😉

---

## 🔁 Padrão de Projeto Strategy

### 📘 O que é o Strategy?

O **Strategy** é um padrão de projeto do tipo **Comportamental**, que permite **definir uma família de algoritmos**, encapsulá-los e torná-los **intercambiáveis** dentro do contexto em que são usados.

Em vez de ter uma lógica fixa e rígida, você pode **trocar o comportamento de um objeto em tempo de execução** — sem precisar alterar o código da classe principal.

---

### 🧠 Quando Usar?

Use o Strategy quando você precisar de:

✅ Vários comportamentos diferentes para uma mesma operação  
✅ Eliminar estruturas com muitos `if-else` ou `switch`  
✅ Tornar seu código mais **flexível**, **extensível** e **orientado a interfaces**

Exemplos práticos:

- 💳 Cálculo de frete com diferentes estratégias (Sedex, PAC, Transportadora)
- 📈 Diferentes formas de ordenar ou buscar dados
- 🎮 Lógica de comportamento para inimigos em jogos

---

### 💡 Exemplo no Mundo Real

Imagine um aplicativo de pagamentos. Você pode pagar com **cartão de crédito**, **PIX**, **boleto** ou **PayPal**. Cada uma dessas formas de pagamento tem uma lógica diferente, mas o cliente só precisa chamar um método como `pagar()`.

Com o Strategy, cada método de pagamento será uma estratégia separada, podendo ser facilmente trocada e reutilizada.

---

### 🎯 Benefícios

🔄 Permite **alterar o comportamento em tempo de execução**  
🚫 Elimina cadeias de `if-else`  
🧱 Segue o **princípio aberto/fechado (OCP)**  
🧪 Facilita testes unitários (testa cada estratégia isoladamente)

---

### ⚠️ Cuidados ao Usar

⚠️ Pode aumentar o número de classes no projeto  
⚠️ Pode gerar **complexidade desnecessária** se houver poucas variações  
⚠️ Exige maior conhecimento de interfaces e polimorfismo

---

### 🛠️ Boas Práticas

- Crie uma **interface comum** para todas as estratégias
- Mantenha as estratégias **isoladas e reutilizáveis**
- Use injeção de dependência para facilitar a troca de estratégias
- Evite usar Strategy se só houver uma implementação (nesse caso, o padrão não agrega valor)

---

### 📌 Resumo Rápido

| Situação                                  | Strategy é indicado? ✅❌ |
|------------------------------------------|--------------------------|
| Múltiplos algoritmos intercambiáveis     | ✅                       |
| Eliminar blocos grandes de `if` ou `switch` | ✅                     |
| Uma única variação de comportamento      | ❌                       |
| Lógica fixa que nunca muda               | ❌                       |

---

### 🧪 Dica Extra

Se você perceber que tem muitos `if/else` com base em tipos, pense:

> *"Será que cada bloco desses não poderia ser uma estratégia?"*

Se a resposta for sim, **Strategy pode ajudar muito** a deixar seu código mais limpo e desacoplado! 🚀

---

## 🧱 Padrão de Projeto Facade

### 📘 O que é o Facade?

O **Facade** é um padrão de projeto do tipo **Estrutural** que tem como objetivo **simplificar o acesso** a sistemas complexos, fornecendo **uma interface unificada** para um conjunto de interfaces mais detalhadas.

Em outras palavras, ele age como uma **fachada** (daí o nome!) que esconde a complexidade dos bastidores e expõe apenas o necessário para o cliente.

---

### 🧠 Quando Usar?

Use o Facade quando você precisar de:

✅ Uma **interface simplificada** para um subsistema complexo  
✅ **Isolar** as partes internas do sistema do código cliente  
✅ **Reduzir o acoplamento** entre os módulos de alto e baixo nível

Exemplos práticos:

- 🎬 Sistema de home theater: uma fachada para ligar TV, luz, som e projetor com um único comando
- 🏦 Operações bancárias: fachada para realizar transferências, verificar saldo, autenticar usuário
- 📦 APIs complexas: esconder múltiplos serviços por trás de um ponto único de acesso

---

### 💡 Exemplo no Mundo Real

Imagine que você tenha um sistema de home theater. Para assistir a um filme, você precisa:

- Ligar o projetor
- Ligar o sistema de som
- Apagar as luzes
- Selecionar a entrada correta

Com o padrão Facade, você pode encapsular tudo isso em uma única chamada: `homeTheater.iniciarFilme()` — o sistema cuida do resto nos bastidores.

---

### 🎯 Benefícios

✅ Reduz a **complexidade para o cliente**  
✅ **Organiza melhor** a estrutura do sistema  
✅ Promove **baixo acoplamento** entre subsistemas  
✅ Facilita **migrações e manutenções**

---

### ⚠️ Cuidados ao Usar

⚠️ Pode ocultar **funcionalidades importantes** do sistema  
⚠️ Não substitui o entendimento da lógica interna, apenas **abstrai**  
⚠️ Pode se tornar **um "Deus" objeto** se for mal projetado

---

### 🛠️ Boas Práticas

- Use o Facade para **esconder complexidade**, mas sem impedir extensões
- Mantenha o subsistema **modular e testável separadamente**
- Não coloque **muita lógica na fachada** — ela deve apenas orquestrar chamadas
- Combine com outros padrões (como Strategy, Factory) para sistemas mais robustos

---

### 📌 Resumo Rápido

| Situação                                   | Facade é indicado? ✅❌ |
|-------------------------------------------|------------------------|
| Sistema com muitos subsistemas            | ✅                     |
| Código cliente chamando muitos métodos    | ✅                     |
| Sistema simples, com poucos componentes   | ❌                     |
| Quando a fachada começa a conter lógica demais | ❌                 |

---

### 🧪 Dica Extra

Se você perceber que seu código cliente está chamando **muitos métodos em sequência** para fazer algo simples, pergunte:

> *"Será que posso criar uma **interface única** que faça tudo isso por mim?"*

Se a resposta for sim, o padrão **Facade** pode deixar seu sistema **muito mais limpo e elegante** ✨

---