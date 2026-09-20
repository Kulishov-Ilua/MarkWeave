# MarkWeave
version: alpha-01
Это маленькая библиотека для преобразования текста в интерфейс

> Важно!
> Документацию формировала локальная ИИ моделька на 12B, когда-нибудь я проверю результат:)

1. Основные структуры данных (Models)
   MarkDownElement — Базовый кирпичик парсинга
   Это самая важная структура. Она определяет, как парсер должен реагировать на конкретный символ (например, #, >, ```). 
   •
   Параметры:
   ◦
   trigger: String — символ или последовательность символов (например, ###).
   ◦
   groupID: Int — идентификатор группы (используется для логической группировки элементов).
   ◦
   checkFun: ((String) -> Boolean)? — опциональная функция проверки. Если она возвращает true, парсер считает, что текст относится к этому элементу.
   ◦
   viewData: MarkDownElementViewData — метаданные для визуализации (имя, иконка, действие по клику).
   ◦
   transformer: (List<String>, (suspend (Long) -> CustomData?)?, ((ByteFile) -> Unit)?) -> @Composable () -> Unit — функция-трансформер. Она принимает накопленный текст, функцию загрузки файла и колбэк навигации, а на выходе возвращает готовый Composable-компонент.
   MarkDownElementViewData — Визуальные метаданные
   Используется для элементов, которые ведут себя как кнопки или ссылки.
   •
   Параметры:
   ◦
   name: String — текстовое название элемента.
   ◦
   icon: @Composable () -> Unit — компонент иконки.
   ◦
   onClick: () -> String — лямбда-выражение, возвращающая строку (например, путь или ID для навигации).
   Quote — Специализированная модель для цитат
   Используется для блоков с префиксом >.
   •
   Параметры:
   ◦
   name: String? — заголовок цитаты.
   ◦
   type: String — тип цитаты (warning, info, danger, success, todo, tip, question, abstract, example).
   ◦
   text: AnnotatedString — стилизованный текст самой цитаты.
   CustomData — Контейнер для произвольных данных
   Используется для передачи сложных объектов из логики парсинга в UI.
   •
   Параметры: Содержит списки Int, String, Boolean, ByteArray и LocalDateTime.
2. Ключевые функции и логика
   MDParse.mdParser — Главная точка входа
   Функция, которая превращает сырой текст в интерфейс.
   •
   Входные параметры:
   ◦
   input: String — исходный Markdown текст.
   ◦
   baseTextStyle: TextStyle — базовые стили текста (шрифт, размер).
   ◦
   primaryColor: Color — основной цвет (используется для выделений, иконок).
   ◦
   backgroundColor: Color — фоновый цвет.
   •
   Выходные данные: List<@Composable () -> Unit> — список готовых UI-компонентов, которые нужно отрисовать по порядку.
   MDParse.mdTextParses — Вспомогательный парсер текста
   Отвечает за обработку встроенных стилей (Bold, Italic и т.д.) внутри строки.
   •
   Входные параметры:
   ◦
   text: String — фрагмент текста.
   ◦
   textStyle: TextStyle.
   ◦
   primaryColor: Color.
   •
   Выходные данные: AnnotatedString — текст с примененными стилями (например, жирный шрифт в середине предложения).
   MarkDownDictionary.setBaseParams — Глобальная настройка стилей
   Функция для инициализации системы стилизации.
   •
   Входные параметры:
   ◦
   textStyle: Базовый стиль текста.
   ◦
   primary: Основной цвет.
   ◦
   background: Цвет фона.
   ◦
   stringFormatter, blockFormatter, lexFormatter: Списки кастомных элементов для расширения словаря (динамическое добавление новых триггеров).
   ◦
   getFileCustom: Функция для специфической загрузки данных из файлов.
   ◦
   onClickableCustom: Обработчик кликов.
   MarkDownDictionary.specialFormatter — Расширение словаря
   Позволяет "на лету" добавлять новые правила парсинга в приложение без изменения основного словаря.
   •
   Входные параметры: Списки MarkDownElement для трех типов: строковых модификаторов, блочных модификаторов и лексических модификаторов.
   Резюме архитектуры:
1.
Данные описываются через MarkDownElement.
2.
Настройки (цвета, шрифты) задаются через MarkDownDictionary.
3.
Процесс идет так: MDParse.mdParser берет текст → разбивает его → ищет совпадения в MarkDownDictionary → запускает transformer у найденного элемента → возвращает список Composable-функций.

### Running the apps

Use the run configurations provided by the run widget in your IDE's toolbar. You can also use these commands and options:

- Android app: `./gradlew :androidApp:assembleDebug`
- Desktop app:
  - Hot reload: `./gradlew :desktopApp:hotRun --auto`
  - Standard run: `./gradlew :desktopApp:run`
- Web app:
  - Wasm target (faster, modern browsers): `./gradlew :webApp:wasmJsBrowserDevelopmentRun`
  - JS target (slower, supports older browsers): `./gradlew :webApp:jsBrowserDevelopmentRun`
- iOS app: open the [/iosApp](./iosApp) directory in Xcode and run it from there.

### Running tests

Use the run button in your IDE's editor gutter, or run tests using Gradle tasks:

- Android tests: `./gradlew :shared:testAndroidHostTest`
- Desktop tests: `./gradlew :shared:jvmTest`
- Web tests:
  - Wasm target: `./gradlew :shared:wasmJsTest`
  - JS target: `./gradlew :shared:jsTest`
- iOS tests: `./gradlew :shared:iosSimulatorArm64Test`

---
