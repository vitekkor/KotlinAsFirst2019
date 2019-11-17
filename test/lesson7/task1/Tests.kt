package lesson7.task1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import java.io.File

class Tests {

    private fun assertFileContent(name: String, expectedContent: String) {
        val file = File(name)
        val content = file.readLines().joinToString("\n")
        assertEquals(expectedContent, content)
    }

    @Test
    @Tag("Example")
    fun alignFile() {
        alignFile("input/align_in1.txt", 50, "temp.txt")
        assertFileContent(
            "temp.txt",
            """Для написания разных видов программ сейчас
применяются разные языки программирования.
Например, в сфере мобильных программ сейчас правят
бал языки Swift (мобильные устройства под
управлением iOS) и Java (устройства под
управлением Android). Системные программы, как
правило, пишутся на языках C или {cpp}. Эти же
языки долгое время использовались и для создания
встраиваемых программ, но в последние годы в этой
области набирает популярность язык Java. Для
написания web-клиентов часто используется
JavaScript, а в простых случаях -- язык разметки
страниц HTML. Web-серверы используют опять-таки
Java (в сложных случаях), а также Python и PHP (в
более простых). Наконец, простые desktop-программы
сейчас могут быть написаны на самых разных языках,
и выбор во многом зависит от сложности программы,
области её использования, предполагаемой
операционной системы. В первую очередь следует
назвать языки Java, {cpp}, C#, Python, Visual
Basic, Ruby, Swift.

Самым универсальным и одновременно самым
распространённым языком программирования на данный
момент следует считать язык Java. Java в широком
смысле -- не только язык, но и платформа для
выполнения программ под самыми разными
операционными системами и на разной аппаратуре.
Такая универсальность обеспечивается наличием
виртуальной машины Java -- системной программы,
интерпретирующей Java байт-код в машинные коды
конкретного компьютера или системы. Java также
включает богатейший набор библиотек для
разработки."""
        )
        File("temp.txt").delete()
    }

    @Test
    @Tag("Normal")
    fun countSubstrings() {
        assertEquals(
            mapOf("РАЗНЫЕ" to 2, "ные" to 2, "Неряшливость" to 1, "е" to 49, "эволюция" to 0),
            countSubstrings("input/substrings_in1.txt", listOf("РАЗНЫЕ", "ные", "Неряшливость", "е", "эволюция"))
        )
        assertEquals(
            mapOf("Карминовый" to 2, "Некрасивый" to 2, "белоглазый" to 1),
            countSubstrings("input/substrings_in1.txt", listOf("Карминовый", "Некрасивый", "белоглазый"))
        )
        assertEquals(
            mapOf("--" to 4, "ее" to 2, "животное" to 2, "." to 2),
            countSubstrings("input/substrings_in2.txt", listOf("--", "ее", "животное", "."))
        )
    }

    @Test
    @Tag("Normal")
    fun sibilants() {
        sibilants("input/sibilants_in1.txt", "temp.txt")
        assertFileContent(
            "temp.txt",
            """/**
 * Простая
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жУри, броШУра, параШут) в рамках данного задания обрабатывать не нужно
 *
 * жИ шИ ЖИ Ши ЖА шА Жа ша жу шу жу щу ча шу щу ща жа жи жи жу чу ча
 */"""
        )
        File("temp.txt").delete()
    }

    @Test
    @Tag("Normal")
    fun centerFile() {
        centerFile("input/center_in1.txt", "temp.txt")
        assertFileContent(
            "temp.txt",
            """              Съешь же ещё этих мягких французских булок, да выпей чаю.
Широкая электрификация южных губерний даст мощный толчок подъёму сельского хозяйства.
                                        Тест
                                          """ +  // Avoiding trailing whitespaces problem
                    """
                                     Hello World
           Во входном файле с именем inputName содержится некоторый текст.
        Вывести его в выходной файл с именем outputName, выровняв по центру."""
        )
        File("temp.txt").delete()
        centerFile("input/center_in2.txt", "temp1.txt")
        assertFileContent(
            "temp1.txt",
            """                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                Я в своем познании настолько преисполнился, что я как будто бы уже
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      сто триллионов миллиардов лет проживаю на триллионах и
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 триллионах таких же планет, как эта Земля, мне этот мир абсолютно
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   понятен, и я здесь ищу только одного - покоя, умиротворения и
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 вот этой гармонии, от слияния с бесконечно вечным, от созерцания
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              великого фрактального подобия и от вот этого замечательного всеединства
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             существа, бесконечно вечного, куда ни посмотри, хоть вглубь - бесконечно
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               малое, хоть ввысь - бесконечное большое, понимаешь? А ты мне опять со
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  своим вот этим, иди суетись дальше, это твоё распределение, это
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  твой путь и твой горизонт познания и ощущения твоей природы, он
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               несоизмеримо мелок по сравнению с моим, понимаешь? Я как будто бы уже
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               давно глубокий старец, бессмертный, ну или там уже почти бессмертный,
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             который на этой планете от её самого зарождения, ещё когда только Солнце
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              только-только сформировалось как звезда, и вот это газопылевое облако,
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                вот, после взрыва, Солнца, когда оно вспыхнуло, как звезда, начало
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              формировать вот эти коацерваты, планеты, понимаешь, я на этой Земле уже
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                как будто почти пять миллиардов лет живу и знаю её вдоль и поперёк
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              этот весь мир, а ты мне какие-то... мне не важно на твои тачки, на твои
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     яхты, на твои квартиры, там, на твоё благо. Я был на этой
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             планете бесконечным множеством, и круче Цезаря, и круче Гитлера, и круче
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               всех великих, понимаешь, был, а где-то был конченым говном, ещё хуже,
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                чем здесь. Я множество этих состояний чувствую. Где-то я был больше
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              подобен растению, где-то я больше был подобен птице, там, червю, где-то
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              был просто сгусток камня, это всё есть душа, понимаешь? Она имеет грани
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              подобия совершенно многообразные, бесконечное множество. Но тебе этого
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  не понять, поэтому ты езжай себе , мы в этом мире как бы живем
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             разными ощущениями и разными стремлениями, соответственно, разное наше и
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              место, разное и наше распределение. Тебе я желаю все самые крутые тачки
чтоб были у тебя, и все самые лучше самки, если мало идей, обращайся ко мне, я тебе на каждую твою идею предложу сотню триллионов, как всё делать. Ну а я всё, я иду как глубокий старец,узревший вечное, прикоснувшийся к Божественному, сам стал богоподобен и устремлен в это бесконечное, и который в умиротворении, покое, гармонии, благодати, в этом сокровенном блаженстве пребывает, вовлеченный во всё и во вся, понимаешь, вот и всё, в этом наша разница. Так что я иду любоваться мирозданием, а ты идёшь преисполняться в ГРАНЯХ каких-то, вот и вся разница, понимаешь, ты не зришь это вечное бесконечное, оно тебе не нужно. Ну зато ты, так сказать, более активен, как вот этот дятел долбящий, или муравей, который очень активен в своей стезе, поэтому давай, наши пути здесь, конечно, имеют грани подобия, потому что всё едино, но я-то тебя прекрасно понимаю, а вот ты меня - вряд ли, потому что я как бы тебя в себе содержу, всю твою природу, она составляет одну маленькую там песчиночку, от того что есть во мне, вот и всё, поэтому давай, ступай, езжай, а я пошел наслаждаться прекрасным осенним закатом на берегу теплой южной реки. Всё, ступай, и я пойду."""
        )
        File("temp1.txt").delete()
    }

    @Test
    @Tag("Hard")
    fun alignFileByWidth() {
        alignFileByWidth("input/width_in1.txt", "temp.txt")
        assertFileContent(
            "temp.txt",
            """Простая

Во       входном       файле       с       именем       inputName       содержится       некоторый      текст.
Вывести   его  в  выходной  файл  с  именем  outputName,  выровняв  по  левому  и  правому  краю  относительно
самой                                              длинной                                             строки.
Выравнивание   производить,   вставляя  дополнительные  пробелы  между  словами:  равномерно  по  всей  строке

Слова     внутри     строки     отделяются     друг     от     друга     одним     или     более     пробелом.

Следующие                   правила                   должны                  быть                  выполнены:
1)     Каждая     строка     входного    и    выходного    файла    не    должна    заканчиваться    пробелом.
2) Пустые строки или строки из пробелов во входном файле должны превратиться в пустые строки в выходном файле.
3)   Число   строк   в   выходном  файле  должно  быть  равно  числу  строк  во  входном  (в  т.  ч.  пустых).

Равномерность              определяется              следующими             формальными             правилами:
1)  Число  пробелов  между  каждыми  двумя  парами  соседних  слов  не  должно  отличаться  более,  чем  на 1.
2)  Число  пробелов  между  более  левой  парой  соседних  слов  должно  быть  больше или равно числу пробелов
между                более               правой               парой               соседних               слов."""
        )
        File("temp.txt").delete()
    }

    @Test
    @Tag("Normal")
    fun top20Words() {
        assertEquals(mapOf(
            "привет" to 4,
            "все" to 3,
            "и" to 3,
            "прямо" to 3,
            "всё" to 2,
            "let" to 2,
            "us" to 2,
            "write" to 2,
            "some" to 2,
            "digits" to 2
        ), top20Words("input/top20.txt").filter { it.value > 1 })
        assertEquals(
            mapOf(
                "и" to 1106,
                "в" to 674,
                "не" to 411,
                "он" to 306,
                "на" to 290,
                "я" to 261,
                "с" to 260,
                "как" to 211,
                "но" to 210,
                "что" to 187,
                "все" to 131,
                "к" to 130,
                "она" to 126,
                "его" to 109,
                "за" to 105,
                "то" to 104,
                "а" to 98,
                "ее" to 95,
                "мне" to 95,
                "уж" to 95
            ), top20Words("input/onegin.txt")
        )
    }

    @Test
    @Tag("Normal")
    fun transliterate() {
        transliterate(
            "input/trans_in1.txt",
            mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!"),
            "temp.txt"
        )
        assertFileContent("temp.txt", "Zzdrавствуy,\nmyyr!!!")
        File("temp.txt").delete()

        transliterate(
            "input/trans_in1.txt",
            mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!"),
            "temp.txt"
        )
        assertFileContent("temp.txt", "Zzdrавствуy,\nmyyr!!!")
        File("temp.txt").delete()
    }

    @Test
    @Tag("Normal")
    fun chooseLongestChaoticWord() {
        chooseLongestChaoticWord("input/chaotic_in1.txt", "temp.txt")
        assertFileContent("temp.txt", "Карминовый, Некрасивый")
        File("temp.txt").delete()
    }


    private fun checkHtmlSimpleExample() {
        val result = File("temp.html").readText().replace(Regex("[\\s\\n\\t]"), "")
        val expected =
            """
                    <html>
                        <body>
                            <p>
                                Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
                                Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
                            </p>
                            <p>
                                Suspendisse <s>et elit in enim tempus iaculis</s>.
                            </p>
                        </body>
                    </html>
                    """.trimIndent().replace(Regex("[\\s\\n\\t]"), "")
        assertEquals(expected, result)

        File("temp.html").delete()
    }

    @Test
    @Tag("Hard")
    fun markdownToHtmlSimple() {
        markdownToHtmlSimple("input/markdown_simple.md", "temp.html")
        checkHtmlSimpleExample()
    }

    private fun checkHtmlListsExample() {
        val result = File("temp.html").readText().replace(Regex("[\\s\\n\\t]"), "")
        val expected =
            """
                    <html>
                      <body>
                        <ul>
                          <li>
                            Утка по-пекински
                            <ul>
                              <li>Утка</li>
                              <li>Соус</li>
                            </ul>
                          </li>
                          <li>
                            Салат Оливье
                            <ol>
                              <li>Мясо
                                <ul>
                                  <li>
                                      Или колбаса
                                  </li>
                                </ul>
                              </li>
                              <li>Майонез</li>
                              <li>Картофель</li>
                              <li>Что-то там ещё</li>
                            </ol>
                          </li>
                          <li>Помидоры</li>
                          <li>
                            Фрукты
                            <ol>
                              <li>Бананы</li>
                              <li>
                                Яблоки
                                <ol>
                                  <li>Красные</li>
                                  <li>Зелёные</li>
                                </ol>
                              </li>
                            </ol>
                          </li>
                        </ul>
                      </body>
                    </html>
                    """.trimIndent().replace(Regex("[\\s\\n\\t]"), "")
        assertEquals(expected, result)

        File("temp.html").delete()
    }

    @Test
    @Tag("Hard")
    fun markdownToHtmlLists() {
        markdownToHtmlLists("input/markdown_lists.md", "temp.html")
        checkHtmlListsExample()
    }

    @Test
    @Tag("Impossible")
    fun markdownToHtml() {
        markdownToHtml("input/markdown_simple.md", "temp.html")
        checkHtmlSimpleExample()

        markdownToHtml("input/markdown_lists.md", "temp.html")
        checkHtmlListsExample()
    }

    @Test
    @Tag("Normal")
    fun printMultiplicationProcess() {
        fun test(lhv: Int, rhv: Int, res: String) {
            printMultiplicationProcess(lhv, rhv, "temp.txt")
            assertFileContent("temp.txt", res.trimIndent())
            File("temp.txt").delete()
        }

        test(
            19935,
            111,
            """
                19935
             *    111
             --------
                19935
             + 19935
             +19935
             --------
              2212785
             """
        )

        test(
            12345,
            76,
            """
               12345
             *    76
             -------
               74070
             +86415
             -------
              938220
             """
        )

        test(
            12345,
            6,
            """
              12345
             *    6
             ------
              74070
             ------
              74070
             """
        )

    }

    @Test
    @Tag("Hard")
    fun printDivisionProcess() {

        fun test(lhv: Int, rhv: Int, res: String) {
            printDivisionProcess(lhv, rhv, "temp.txt")
            assertFileContent("temp.txt", res.trimIndent())
            File("temp.txt").delete()
        }

        test(
            19935,
            22,
            """
              19935 | 22
             -198     906
             ----
                13
                -0
                --
                135
               -132
               ----
                  3
             """
        )

        test(
            2,
            20,
            """
              2 | 20
             -0   0
             --
              2
             """
        )

        test(
            99999,
            1,
            """
              99999 | 1
             -9       99999
             --
              09
              -9
              --
               09
               -9
               --
                09
                -9
                --
                 09
                 -9
                 --
                  0
             """
        )

        File("temp.txt").delete()
    }
}
