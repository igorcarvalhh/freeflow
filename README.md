# 🌀 Freeflow PM

**Freeflow PM** é um fork open source do [ProjectLibre](https://www.projectlibre.com/), com o objetivo de modernizar e refatorar uma das ferramentas de gestão de projetos mais conhecidas do mundo corporativo.

Nosso foco inicial está em **limpar o código**, **resolver warnings**, **melhorar a legibilidade** e tornar o projeto mais acessível para desenvolvedores que desejam aprender, contribuir ou criar suas próprias versões. A longo prazo, buscamos evoluir a interface, melhorar a experiência do usuário e trazer novas funcionalidades.

## 🚀 Principais objetivos

- ✅ Eliminar a maior parte dos warnings da base original
- ♻️ Refatorar trechos de código desatualizados
- 🪟 Modernizar gradualmente a interface gráfica (Swing → JavaFX ou Web?)
- 📦 Facilitar a compilação e execução do código localmente
- 🤝 Tornar a colaboração open source fácil e acessível

## 📂 Estrutura do projeto

O projeto está organizado em múltiplos módulos, conforme o padrão original do ProjectLibre:

```
projectlibre-code/
├── projectlibre_core/ # Lógica de negócio
├── projectlibre_ui/ # Interface do usuário (Swing)
├── projectlibre_reports/ # Geração de relatórios
├── projectlibre_exchange/ # Importação/exportação de dados
├── projectlibre_build/ # Scripts e configs de build
├── projectlibre_contrib/ # Contribuições externas / extras
```

## 💻 Como rodar localmente

### 🔧 Requisitos

- [Java JDK 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
- [IntelliJ IDEA (Community ou Ultimate)](https://www.jetbrains.com/idea/download/)

### 📝 Passo a passo

1. Clone o repositório:
   
```
git clone https://github.com/seu-usuario/freeflow-pm.git
```

2. Abra a pasta projectlibre-code no IntelliJ.

3. Vá em File > Project Structure... e selecione o JDK 21 como SDK do projeto.

4. Abra o arquivo projectlibre_ui/src/.../Main.java e clique em ▶️ para rodar.

5. (Opcional) Configure uma Run Configuration fixa para facilitar futuras execuções.

## 🧼 Iniciativa: "Limpeza de Warnings"
Uma das nossas primeiras missões é limpar os warnings apontados pelo IntelliJ, sem alterar o comportamento da aplicação.

🎯 Veja a lista de issues: [Acesse as issues abertas ›](https://github.com/igorcarvalhh/freeflow/issues)

🛠️ Você pode contribuir resolvendo uma delas ou sugerindo novas!

## 🤝 Como contribuir
Quer ajudar? Todo tipo de contribuição é bem-vindo! Você pode:

- Abrir issues com melhorias, bugs ou dúvidas
- Corrigir avisos de compilação (warnings)
- Refatorar partes específicas do código
- Atualizar esta documentação
- Compartilhar o projeto com outros devs

**Passos para contribuir:**

1. Faça um fork do projeto
2. Crie uma branch com sua feature/fix (git checkout -b fix/warning-xyz)
3. Commit suas alterações
4. Envie um pull request para a branch main

## 📄 Licença
Este projeto é baseado no ProjectLibre, licenciado sob a Common Public Attribution License (CPAL) 1.0. O Freeflow PM segue a mesma licença.

## 📬 Contato
Tem dúvidas, sugestões ou quer bater um papo sobre o projeto?

Entre em contato ou abra uma discussão no GitHub.
