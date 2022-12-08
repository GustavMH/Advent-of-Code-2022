(require '[clojure.string :as str])

(defn parse-int [s]
  (Integer/parseInt s))

(def commands (-> (slurp "input_7")
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
                         (map parse-int)
                         (reduce +))])))

(defn path [path cmd]
  (cond
    (= cmd ["cd" ".."]) (rest path)
    (= (first cmd) "cd") (cons (second cmd) path)
    :else path))


(def processed-cmds (map process-command-str commands))

(def paths
  (->>
    processed-cmds
    (reductions path '())
    (map vector processed-cmds)
    (filter #(not= "cd" (first (first %))))
    (map #(vector ((comp second first) %) (second %)))))

(def dir-sizes
  (->> paths
       (map (fn [[size p]] (->> (filter (fn [[_ path]] (= (reverse p) (take (count p) (reverse path)))) paths)
                                (map first)
                                (reduce +))))))
(->> dir-sizes ; Q1
     (filter (partial > 100000))
     (reduce +))

(def space-needed
  (->> paths
       (map first)
       (reduce +)
       (- 70000000)
       (- 30000000)))

(->> dir-sizes ; Q2
     (filter (partial < space-needed))
     (apply min))
