["$let", 
    [["$y", 200]],
    ["$defvar", "$testfunc", ["$lambda",["$x"], ["$print", ["$+", "$x", "$y"]]]],
    ["$testfunc", 100]
]
