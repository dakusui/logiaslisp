["$let", 
    [ ["$addr", ["$+", 255, 512]], ["$b", ["$hex", "11", "22", "4a"]] ],
    ["$defvar", 
     "$jv1010dataset", 
     ["$lambda",["$address", "$data"], 
      ["$let", [["$sum", 0]],
       ["$dolist", ["$cur", "$data"],
        ["$print", "$cur"],
        ["$setq", "$sum", ["$add", "$sum", "$cur"]]
       ],
       ["$setq", "$ret",
        ["$cons", ["$hex", "70"],
         ["$cons", ["$hex", "41"],
          ["$cons", ["$hex", "10"],
           ["$cons", ["$hex", "6a"],
            ["$cons", ["$hex", "12"],
             ["$cons", ["$eval", ["$setq", "$adr1", ["$shift", -24, ["$&", ["$hex", "ff000000"], "$address"]]]],
              ["$cons", ["$eval", ["$setq", "$adr2", ["$shift", -16, ["$&", ["$hex", "00ff0000"], "$address"]]]],
               ["$cons", ["$eval", ["$setq", "$adr3", ["$shift",  -8, ["$&", ["$hex", "0000ff00"], "$address"]]]],
                ["$cons", ["$eval", ["$setq", "$adr4", ["$shift",   0, ["$&", ["$hex", "000000ff"], "$address"]]]],
                 "$nil"]
               ]
              ]
             ]
            ]
           ]
          ]
         ]
        ]
       ],
       ["$setq", "$ret", ["$append", "$ret", "$data"]],
       ["$setq", "$ret", ["$append", "$ret", ["$cons", ["$%", ["$-", 128, ["$%", ["$+", "$adr1", "$adr2","$adr3", "$adr4", "$sum"], 128]], 128], "$nil"]]],
       "$ret",
     ],
     ],
     ],
     [$print, ["$jv1010dataset", "$addr", "$b"]],
     [$print, "bye"]
]
