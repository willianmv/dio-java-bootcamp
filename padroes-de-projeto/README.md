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
