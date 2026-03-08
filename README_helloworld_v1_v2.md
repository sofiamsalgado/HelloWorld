# Assignment 1 — Hello World v1 e v2

**Curso:** Licenciatura em Engenharia Informatica e Multimedia
**Unidade Curricular:** Desenvolvimento de Aplicacoes Moveis (DAM)
**Aluno:** Sofia — dam_a51694
**Data:** Fevereiro/Marco 2026
**Repositorio:** https://github.com/sofiamsalgado/HelloWorld

---

## 1. Introducao

O objetivo foi criar a primeira aplicacao Android no Android Studio usando Kotlin e XML. A aplicacao passou por duas versoes: uma basica (v1) e uma melhorada (v2) com mais elementos visuais e funcionalidade.

---

## 2. Descricao do Sistema

Aplicacao Android com uma unica Activity que mostra texto, imagem e um calendario. A v2 acrescentou botoes com logica em Kotlin, contagem de cliques, alteracao de cor de fundo e modo escuro.

Funcionalidades principais:
- Titulo e subtitulo com estilos personalizados
- Imagem de um cachorro
- CalendarView
- Botao para alterar o texto aleatoriamente
- Botao contador de cliques
- Botao para alterar a cor de fundo aleatoriamente
- Botao de toggle Dark Mode
- Layout em landscape separado

---

## 3. Arquitetura e Design

Projeto criado no Android Studio como **Empty Views Activity**.

```
HelloWorld/
├── app/src/main/
│   ├── java/dam_a51694/helloworld/
│   │   └── MainActivity.kt
│   └── res/
│       ├── layout/activity_main.xml
│       ├── layout-land/activity_main.xml
│       ├── drawable/puppy.jpg
│       └── values/strings.xml
└── AndroidManifest.xml
```

- **Linguagem:** Kotlin
- **Layout:** ConstraintLayout com XML
- **Minimo SDK:** API 24 (Android 7.0)

---

## 4. Implementacao

### Passo a passo — Hello World v1

1. Abrir Android Studio > **File > New > New Project > Empty Views Activity**.
2. Configurar:
   - Nome: `Hello World`
   - Package: `dam_a51694.helloworld`
   - Linguagem: Kotlin
   - Minimum SDK: API 24
3. No `activity_main.xml`, alterar o texto do `TextView` para `"Hello Android World"` usando `@string/hello_string` em `strings.xml`.
4. Mudar o nome da app em `strings.xml` para `"Hello World V1"`.
5. Correr no emulador Pixel 9 Pro.

### Passo a passo — Hello World v2

1. Alterar nome da app para `"Hello World V2"` em `strings.xml`.
2. Mover o `TextView` para o topo e configurar: `textColor`, `textSize`, `textStyle`, `textAlignment`, `layout_width = 0dp`.
3. Adicionar segundo `TextView` com `"My First App"`, fundo amarelo, ligado abaixo do primeiro.
4. Fazer download de uma imagem de cachorro e colocar na pasta `drawable`. Adicionar `ImageView` ao layout com `contentDescription`.
5. Adicionar `CalendarView` ao layout.
6. Criar e editar o layout landscape:
   - No editor do `activity_main.xml`, clicar no **dropdown** no topo do editor (que mostra `activity_main.xml`) — aparece uma lista com `activity_main.xml`, `land/activity_main.xml` e opcoes para criar novos qualifiers. Selecionar `land/activity_main.xml`. Se ainda nao existir, o Android Studio cria automaticamente o ficheiro em `res/layout-land/`.
   - Para adicionar elementos ao layout, abrir o **Palette** no lado esquerdo do editor. O Palette esta organizado por categorias:
     - `Text` — contem `TextView`, `Plain Text`, `Multiline Text`, etc.
     - `Buttons` — contem `Button`, `ImageButton`, etc.
     - `Widgets` — contem `CalendarView`, `ImageView`, `Switch`, etc.
   - Para adicionar um elemento: arrastar do Palette para a area de design no centro. Por exemplo, para um `TextView`, ir a categoria `Text`, clicar em `TextView` e arrastar para a posicao pretendida. O mesmo processo para `Plain Text`, `Button` e `CalendarView`.
   - Apos colocar cada elemento, definir as constraints (ligar os circulos do elemento as bordas do ecra ou a outros elementos) e configurar os atributos no painel **Attributes** do lado direito.
   - Garantir que todos os IDs sao identicos aos do layout portrait (`btnChangeText`, `btnDarkMode`, `btnCount`, `btnColor`, `tvCount`, etc.) — se os IDs forem diferentes a app crasha ao rodar o ecra.
7. Adicionar icone personalizado na pasta `mipmap`.

### Logica em Kotlin (MainActivity.kt)

**Botao alterar texto:**
```kotlin
val frases = listOf("Hello Android World!", "Kotlin!", "Mobile Dev!", "Keep coding!", "Hello DAM!")
val tvHello = findViewById<TextView>(R.id.textHello)
val btnChangeText = findViewById<Button>(R.id.btnChangeText)
btnChangeText.setOnClickListener {
    tvHello.text = frases.random()
}
```

**Botao contador:**
```kotlin
var count = 0
val tvCount = findViewById<TextView>(R.id.tvCount)
val btnCount = findViewById<Button>(R.id.btnCount)
btnCount.setOnClickListener {
    count++
    tvCount.text = "Clicks: $count"
}
```

**Botao cor aleatoria:**
```kotlin
val layout = findViewById<ConstraintLayout>(R.id.main)
val btnColor = findViewById<Button>(R.id.btnColor)
btnColor.setOnClickListener {
    val cor = Color.rgb(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
    layout.setBackgroundColor(cor)
}
```

**Botao Dark Mode:**
```kotlin
val btnDarkMode = findViewById<Button>(R.id.btnDarkMode)
btnDarkMode.setOnClickListener {
    val currentMode = AppCompatDelegate.getDefaultNightMode()
    if (currentMode == AppCompatDelegate.MODE_NIGHT_YES) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    } else {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }
}
```

### Bug corrigido

O layout landscape tinha o botao `btnChangeText` com ID `btnChangeText2` em vez de `btnChangeText`, o que causava um `NullPointerException` ao rodar o ecra. Corrigido alterando o ID no ficheiro `res/layout-land/activity_main.xml`.

---

## 5. Testes e Validacao

- Testado em emulador Pixel 9 Pro (portrait e landscape).
- Testado no dispositivo fisico via USB.
- Verificado que todos os botoes funcionam em ambas as orientacoes.
- O crash em landscape foi identificado via Logcat e corrigido.

---

## 6. Instrucoes de Uso

1. Abrir o projeto `HelloWorld` no Android Studio.
2. Iniciar o emulador Pixel 9 Pro pelo Device Manager.
3. Clicar em **Run > Run 'app'** ou `Shift + F10`.
4. Para testar no telemovel fisico: ativar USB Debugging nas opcoes de programador e ligar via USB.

---

## 12. Controlo de Versao

O mesmo repositorio contem o Hello World v1/v2 (secao 4), o Logcat/Debug (secao 5.1 e 5.2) e as melhorias da secao 7. Os commits relativos ao Hello World v1 e v2 sao:

- `Initial commit`
- `4.2.2. Change the TextView`
- `4.2.3. Added a second TextView`
- `4.2.4. Added an image and description`
- `4.2.5. Added a CalendarView`
- `4.2.6, 4.2.7 Added a Landscape layout variant and app icon`
- `5.1 e 5.2`

---

## 13. Dificuldades e Licoes Aprendidas

- O crash em landscape foi o problema mais dificil — levou varios relancamentos ate perceber que o ID do botao no layout landscape estava errado (`btnChangeText2` vs `btnChangeText`). O Logcat foi essencial para identificar o problema.
- Perceber como funcionam os constraints no ConstraintLayout exigiu alguma pratica no editor visual.
- Aprender a diferenca entre `layout/` e `layout-land/` para suporte a landscape.

---

## 14. Melhorias Futuras

- Guardar o estado do contador ao rodar o ecra (usando `onSaveInstanceState`).
- Adicionar animacao ao alterar a cor de fundo.
- Suporte a mais idiomas usando ficheiros `strings.xml` localizados.

---

## 15. Declaracao de Uso de IA (Obrigatorio)

Esta tarefa foi realizada **sem uso de IA** (AC YES, AI NO). O auto-complete padrao do Android Studio foi utilizado.

A IA (Claude) foi consultada pontualmente apenas para:
- Identificar a causa do crash em landscape apos analise do Logcat (bug do ID `btnChangeText2`)
- Consultar exemplos de como usar `setOnClickListener`, `AppCompatDelegate` para dark mode e `Color.rgb` para cores aleatorias

Todo o restante codigo foi escrito manualmente com base na documentacao oficial do Android (https://developer.android.com/guide) e do Kotlin (https://kotlinlang.org/docs/home.html).
