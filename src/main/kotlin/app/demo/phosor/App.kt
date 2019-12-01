package app.demo.phosor

import app.demo.phosor.core.ArgsParser
import app.demo.phosor.core.Phosor

fun main(args: Array<String>) = ArgsParser(
        thenDo = { simpleDateFormat, directory, verbose ->
            Phosor(verbose).process(directory, simpleDateFormat)
        })
        .main(args)