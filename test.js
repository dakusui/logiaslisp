[
    "$do",
    [
        ["$i", 0, ["$+", "$i", 1]]
    ],
    [
        ["$>", "$i", 5], "DONE!"
    ],
    ["$print", "hello, world"],
    ["$print", ["$*", "$i", "$i"]],
    ["$print", ["$/", 10, ["$-", "$i", "0.5"]]]
]
