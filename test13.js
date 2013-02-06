["$let", [],
 ["$defvar", "$exp_patchnum", 
  ["$lambda", ["$patchnum"],
   ["$cons", ["$shift", -4, ["$&", ["$hex", "f0"], "$patchnum"] ],
    ["$cons", ["$&", ["$hex", "0f"], "$patchnum"],
     "$nil"
     ]
   ]
  ]
 ],
 ["$defvar", "$patchdata",
  ["$lambda", ["$patchgroup", "$patchnum"],
   ["$cond",
    [ ["$eq", "$patchgroup", "USER"], 
      ["$append", ["$quote", [0, 1] ], ["$exp_patchnum", "$patchnum"] ] 
    ],
    [ ["$eq", "$patchgroup", "CARD"], 
      ["$append", ["$quote", [0, 2] ], ["$exp_patchnum", "$patchnum"] ] 
    ],
    [ ["$eq", "$patchgroup", "PR-A"], 
      ["$append", ["$quote", [0, 3] ], ["$exp_patchnum", "$patchnum"] ] 
    ],
    [ ["$eq", "$patchgroup", "PR-B"], 
      ["$append", ["$quote", [0, 4] ], ["$exp_patchnum", "$patchnum"] ] 
    ],
    [ ["$eq", "$patchgroup", "PR-C"], 
      ["$append", ["$quote", [0, 5] ], ["$exp_patchnum", "$patchnum"] ] 
    ],
    [ ["$eq", "$patchgroup", "PR-D"], 
      ["$append", ["$quote", [0, 6] ], ["$exp_patchnum", "$patchnum"] ] 
    ],
    [ ["$eq", "$patchgroup", "PCM" ], 
      ["$append", ["$quote", [1, 7] ], ["$exp_patchnum", "$patchnum"] ] 
    ],
    [ ["$eq", "$patchgroup", "XP-A"], 
      ["$append", ["$quote", [2, 1] ], ["$exp_patchnum", "$patchnum"] ] 
    ],
    [ ["$eq", "$patchgroup", "XP-B"], 
      ["$append", ["$quote", [2, 2] ], ["$exp_patchnum", "$patchnum"] ] 
    ],
    [ ["$eq", "$patchgroup", "XP-C"], 
      ["$append", ["$quote", [2, 3] ], ["$exp_patchnum", "$patchnum"] ] 
    ],
    [ ["$eq", "$patchgroup", "XP-D"], 
      ["$append", ["$quote", [2, 4] ], ["$exp_patchnum", "$patchnum"] ] 
    ],
    [ ["$eq", "$patchgroup", "XP-E"], 
      ["$append", ["$quote", [2, 5] ], ["$exp_patchnum", "$patchnum"] ] 
    ],
    [ ["$eq", "$patchgroup", "XP-F"], 
      ["$append", ["$quote", [2, 6] ], ["$exp_patchnum", "$patchnum"] ] 
    ],
    [ ["$eq", "$patchgroup", "XP-G"], 
      ["$append", ["$quote", [2, 7] ], ["$exp_patchnum", "$patchnum"] ] 
    ],
    [ ["$eq", "$patchgroup", "XP-H"], 
      ["$append", ["$quote", [2, 8] ], ["$exp_patchnum", "$patchnum"] ] 
    ],
    [ ["$eq", "$patchgroup", "XP-I"], 
      ["$append", ["$quote", [2, 9] ], ["$exp_patchnum", "$patchnum"] ] 
    ]
   ]
  ]
 ],
 ["$patchdata", "USER", 254]
]