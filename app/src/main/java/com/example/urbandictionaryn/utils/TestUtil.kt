package com.example.urbandictionaryn.utils

import com.example.urbandictionaryn.models.WordDefinitions

fun sampleDefinitionList(word: String) =
    listOf(
        WordDefinitions(
            "definition",
            "permalink",
            1,
            listOf("Url1", "Url2"),
            "author",
            word,
            0,
            "currentVote",
            "writtenOn",
            "example",
            1
        )
    )

fun sampleDefinitionListForUnitTest(word: String) =
    listOf(
        WordDefinitions(
            "definition",
            "permalink",
            1,
            listOf("Url1", "Url2"),
            "author",
            word,
            0,
            "currentVote",
            "writtenOn",
            "example",
            1
        ),
        WordDefinitions(
            "definition",
            "permalink",
            5,
            listOf("Url1", "Url2"),
            "author",
            word,
            0,
            "currentVote",
            "writtenOn",
            "example",
            8
        ),
        WordDefinitions(
            "definition",
            "permalink",
            9,
            listOf("Url1", "Url2"),
            "author",
            word,
            0,
            "currentVote",
            "writtenOn",
            "example",
            5
        )
    )