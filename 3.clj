(require '[clojure.string :as str]
         '[clojure.set :as set])

(def data (-> (slurp "input3")
              str/split-lines))

(defn halve [str]
  (let [l (count (into [] str))]
    (split-at (/ l 2) str)))

(defn score-char [ch]
  (let [c (int ch)]
    (if (>= c (int \a))
        (->> c (#(- % 96)))
        (->> c (#(- % 38))))))

(defn score-bag [bag]
    (->> bag
        halve
        (map (partial into #{}))
        (apply set/intersection)
        first
        score-char))

(->> (map score-bag data)
     (reduce +))

(->> data
     (map (partial into #{}))
     (partition 3)
     (map (partial apply set/intersection))
     (map first)
     (map score-char)
     (reduce +))
