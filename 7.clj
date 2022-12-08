(require '[clojure.string :as str])

(defn parse-int [s]
  (Integer/parseInt s))

(def commands (-> "$ cd /
$ ls
dir a
14848514 b.txt
8504156 c.dat
dir d
$ cd a
$ ls
dir e
29116 f
2557 g
62596 h.lst
$ cd e
$ ls
584 i
$ cd ..
$ cd ..
$ cd d
$ ls
4060174 j
8033020 d.log
5626152 d.ext
7214296 k"
                 (str/split #"\$ ")
                 rest))

(defn process-command-str [command-str]
     (let [cmd (second (re-find #"^(\w+)" command-str))]
       cmd
       (case cmd
         "cd" ["cd" (second (re-find #"cd (.+)" command-str))]
         "ls" ["ls" (->> (str/split-lines command-str)
                         rest
                         (filter (comp not (partial re-find #"^dir")))
                         (map (partial re-find #"^\d+"))
                         (map parse-int))])))

(map process-command-str commands)

(defn make-tree
  ([] nil)
  ([[[cmd args] rst-commands]]
   (case cmd
     "cd" (if (not= args "..")
            (make-tree rst-commands))
     "ls" (into [] args))))

(make-tree commands)
