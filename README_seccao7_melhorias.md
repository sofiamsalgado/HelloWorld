# Assignment 1 — Seccao 7: Melhorias Android (10%)

**Curso:** Licenciatura em Engenharia Informatica e Multimedia
**Unidade Curricular:** Desenvolvimento de Aplicacoes Moveis (DAM)
**Aluno:** Sofia — dam_a51694
**Data:** Fevereiro/Marco 2026
**Repositorio:** https://github.com/sofiamsalgado/HelloWorld

---

## 1. Introducao

Esta seccao corresponde aos 10% extras da nota, onde foram acrescentadas melhorias ao projeto Hello World. As tarefas foram realizadas por esta ordem: exploracao do ADB em modo consola, implementacao do layout landscape, e adicao de novas funcionalidades (dark mode, contador de cliques, cor aleatoria de fundo e alteracao de titulo).

---

## 2. Descricao do Sistema

Melhorias feitas sobre a aplicacao Hello World v2, por ordem de realizacao:

- Exploracao do ADB para instalar e depurar a app via linha de comandos.
- Layout landscape completamente redesenhado para melhor aproveitamento do ecra horizontal.
- Novos botoes com logica adicional (toggle dark mode, alteracao de titulo aleatoriamente, contador de cliques, cor aleatoria de fundo).

---

## 3. Arquitetura e Design

As alteracoes foram feitas sobre o projeto `HelloWorld` existente, acrescentando:

```
res/
├── layout/activity_main.xml          (portrait - ja existia)
└── layout-land/activity_main.xml     (landscape - criado nesta seccao)
```

O layout landscape foi criado manualmente em `res > New > Layout Resource File` com o qualifier `Orientation = Landscape`. O Android carrega automaticamente o ficheiro correto consoante a orientacao do dispositivo.

---

## 4. Implementacao

### 1. ADB em modo consola

O Android Debug Bridge (ADB) e uma ferramenta de linha de comandos que permite comunicar diretamente com um dispositivo Android (fisico ou emulador). Esta disponivel na pasta `platform-tools` dentro da instalacao do Android Studio.

**Como abrir o ADB em modo consola (Windows):**
1. Abrir o Explorador de Ficheiros e navegar ate `C:\Users\<utilizador>\AppData\Local\Android\Sdk\platform-tools\`
2. Clicar com o botao direito na pasta > **Abrir no Terminal**
3. Ou adicionar o caminho ao PATH do sistema para usar `adb` a partir de qualquer diretoria

---

**Comandos utilizados e para que servem:**

```bash
adb devices
```
Lista todos os dispositivos e emuladores ligados ao computador. Mostra o numero de serie do dispositivo e o estado (`device` = pronto, `offline` = nao responde). Foi o primeiro comando a correr para confirmar que o telemovel estava a ser reconhecido via USB.

---

```bash
adb install app-debug.apk
```
Instala um ficheiro APK diretamente no dispositivo sem precisar do Android Studio. O APK de debug encontra-se em `app/build/outputs/apk/debug/app-debug.apk`. Util para instalar a app num telemovel sem ter o projeto aberto.

---

```bash
adb logcat
```
Mostra todos os logs do dispositivo em tempo real na consola. E o equivalente ao Logcat do Android Studio mas na linha de comandos. Util para ver crashes e mensagens `println` da app.

---

```bash
adb logcat | findstr dam_a51694
```
Filtra o logcat para mostrar apenas as linhas relacionadas com a nossa app (no Windows usa-se `findstr` em vez de `grep`). Muito util para nao se perder no volume de mensagens do sistema.

---

```bash
adb logcat | findstr "NullPointerException"
```
Filtra especificamente por erros de NullPointerException — foi usado para identificar o crash do landscape mais rapidamente sem ter de procurar manualmente no Logcat.

---

```bash
adb logcat -c
```
Limpa todos os logs existentes no dispositivo. Util para comecar uma sessao de debug limpa, sem logs antigos a interferir.

---

```bash
adb kill-server
adb start-server
```
Para e reinicia o servidor ADB. Foi necessario usar estes comandos quando o dispositivo ficou `offline` e nao respondia.

---

```bash
adb shell
```
Abre uma shell interativa diretamente no dispositivo Android (semelhante a um terminal Linux). A partir daqui e possivel navegar pelo sistema de ficheiros, executar comandos e inspecionar o estado interno do dispositivo.

---

```bash
adb shell ps | findstr dam_a51694
```
Lista os processos em execucao no dispositivo e filtra pelo nome da nossa app. Permite confirmar se a app esta a correr e ver o seu PID (Process ID) — o mesmo numero que aparece nos logs do Logcat.

---

```bash
adb shell am force-stop dam_a51694.helloworld
```
Forca o fecho da app diretamente pela linha de comandos, sem precisar de a fechar manualmente no dispositivo. `am` e o Activity Manager do Android.

---

```bash
adb shell dumpsys package dam_a51694.helloworld
```
Mostra informacao detalhada sobre o pacote instalado: versao, permissoes, activities declaradas, data de instalacao.

---

**Onde encontrar o ADB no Android Studio:**

O caminho completo para o `adb.exe` no Windows e normalmente:
```
C:\Users\<utilizador>\AppData\Local\Android\Sdk\platform-tools\adb.exe
```

Para adicionar ao PATH do sistema:
1. Pesquisar "variaveis de ambiente" no Windows
2. Editar a variavel `Path` do utilizador
3. Adicionar o caminho `C:\Users\<utilizador>\AppData\Local\Android\Sdk\platform-tools`
4. Reiniciar o terminal — depois o comando `adb` funciona em qualquer diretoria

---

### 2. Layout Landscape

Passo a passo:
1. No editor do `activity_main.xml`, clicar no **dropdown** no topo do editor (que mostra `activity_main.xml`) — aparece uma lista com `activity_main.xml`, `land/activity_main.xml` e opcoes para criar novos qualifiers. Selecionar `land/activity_main.xml`. Se ainda nao existir, o Android Studio cria automaticamente o ficheiro em `res/layout-land/`.
2. Redesenhar o layout para modo horizontal usando o **Palette** no lado esquerdo do editor:
   - Para adicionar elementos: arrastar do Palette para a area de design. Por exemplo, `TextView` esta na categoria `Text`, `Button` esta em `Buttons`, `CalendarView` e `ImageView` estao em `Widgets`.
   - Definir as constraints de cada elemento (ligar os circulos as bordas do ecra ou a outros elementos) e configurar os atributos no painel **Attributes** do lado direito.
3. Reorganizar os elementos para modo horizontal: imagem a esquerda, calendario a direita, botoes na linha inferior.
4. Garantir que todos os IDs sao identicos aos do layout portrait (`btnChangeText`, `btnDarkMode`, `btnCount`, `btnColor`, `tvCount`).

**Atencao:** Os IDs nos dois layouts devem ser identicos. Se um ID existir num layout mas nao no outro, o `findViewById` devolve `null` e a app crasha ao rodar o ecra.

---

### 3. Novas Funcionalidades

Foram adicionados quatro botoes novos ao layout, cada um com a sua logica em `MainActivity.kt`, implementados por esta ordem:

**Toggle Dark Mode:**
```kotlin
val btnDarkMode = findViewById<Button>(R.id.btnDarkMode)
btnDarkMode.setOnClickListener {
    val currentMode = AppCompatDelegate.getDefaultNightMode()
    if (currentMode == AppCompatDelegate.MODE_NIGHT_YES) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        btnDarkMode.text = "Toggle Dark Mode"
    } else {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        btnDarkMode.text = "Toggle Light Mode"
    }
}
```

**Alterar titulo aleatoriamente:**
```kotlin
val frases = listOf("Hello Android World!", "Kotlin!", "Mobile Dev!", "Keep coding!", "Hello DAM!")
val tvHello = findViewById<TextView>(R.id.textHello)
val btnChangeText = findViewById<Button>(R.id.btnChangeText)
btnChangeText.setOnClickListener {
    tvHello.text = frases.random()
}
```

**Contador de cliques:**
```kotlin
var count = 0
val tvCount = findViewById<TextView>(R.id.tvCount)
val btnCount = findViewById<Button>(R.id.btnCount)
btnCount.setOnClickListener {
    count++
    tvCount.text = "Clicks: $count"
}
```

**Cor aleatoria de fundo:**
```kotlin
val layout = findViewById<ConstraintLayout>(R.id.main)
val btnColor = findViewById<Button>(R.id.btnColor)
btnColor.setOnClickListener {
    val cor = Color.rgb(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
    layout.setBackgroundColor(cor)
}
```

---

## 5. Testes e Validacao

- Testado que ao rodar o telemovel para landscape, o layout correto e carregado sem crash.
- Verificado que todos os botoes funcionam em ambas as orientacoes.
- Testado o `adb devices` com o telemovel ligado via USB — dispositivo reconhecido corretamente.
- Testado `adb logcat` para observar os logs da app em tempo real.

---

## 6. Instrucoes de Uso

1. Abrir o projeto `HelloWorld` no Android Studio.
2. Correr no emulador ou telemovel fisico.
3. Rodar o dispositivo para landscape — o layout adapta-se automaticamente.
4. Para usar o ADB: abrir uma linha de comandos e navegar ate `platform-tools` dentro da pasta de instalacao do Android Studio (ou adicionar ao PATH do sistema).

---

## 12. Controlo de Versao

As melhorias da secao 7 estao no mesmo repositorio do Hello World. Commits realizados:

- `7. added new features, toggle dark mode, landscape button`
- `7. added new features, alterar titulo aleatoriamente`
- `7. added new features, counter`
- `7. added new features, change background color aleatoriamente`

---

## 13. Dificuldades e Licoes Aprendidas

- O crash ao rodar o ecra foi o maior desafio desta seccao. O Logcat mostrou um `NullPointerException` na linha do `btnChangeText`. A causa foi o ID errado (`btnChangeText2`) no layout landscape. Esta experiencia ensinou a importancia de manter os IDs consistentes entre layouts.
- O ADB e uma ferramenta muito mais poderosa do que parece — e possivel fazer praticamente tudo o que o Android Studio faz, mas pela linha de comandos.

---

## 14. Melhorias Futuras

- Criar um layout para tablet usando o qualifier `sw600dp`.
- Explorar mais comandos ADB como `adb shell dumpsys` para inspecionar o estado do sistema.

---

## 15. Declaracao de Uso de IA (Obrigatorio)

Esta tarefa foi realizada **sem uso de IA** (AC YES, AI NO). O auto-complete padrao do Android Studio foi utilizado.

A IA (Claude) foi consultada pontualmente apenas para:
- Identificar a causa do crash em landscape apos analise do Logcat (bug do ID `btnChangeText2`)
- Consultar exemplos de comandos ADB e para que servem

Todo o restante codigo e configuracao foram feitos manualmente com base na documentacao oficial do Android (https://developer.android.com/studio/command-line/adb) e do Kotlin (https://kotlinlang.org/docs/home.html).
