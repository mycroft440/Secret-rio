# Secretário

Assistente financeiro pessoal para Android, criado em Kotlin + Jetpack Compose.

## MVP atual

- Home financeira com período de 01-08 a 23-08
- Total recebido: R$ 3.000,00
- Gastos: R$ 2.333,00
- Saldo do período: R$ 667,00
- Lista resumida de entradas e gastos
- Gráfico em rosca por categoria
- Navegação inferior para Início, Movimentações, Análises e Ajustes
- Build automático de APK pelo GitHub Actions

### Categorias demonstrativas

- Farmácia: R$ 200,00
- Supermercado: R$ 500,00
- Lanches: R$ 140,00
- Ônibus: R$ 30,00
- Pix para terceiros: R$ 800,00
- Outros: R$ 663,00

`Outros` foi incluído automaticamente porque as categorias fornecidas originalmente somam R$ 1.670,00, enquanto o total de gastos informado é R$ 2.333,00.

## Próximas etapas

1. Persistência local com Room
2. Tela completa de movimentações e filtros
3. Categorias editáveis e aprendizado de classificação
4. Importação de CSV/OFX
5. Relatórios mensais
6. Integração Open Finance
7. Camada de assistente financeiro

## Build

O projeto usa GitHub Actions. Cada push na `main` executa `:app:assembleDebug` e publica o APK de debug como artefato do workflow.
