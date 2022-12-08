(require '[clojure.string :as str])

(defn parse-int [s]
  (Integer/parseInt s))

(def input (str/split-lines (slurp "input_5")))

(def crates
  (->> (take 8 input)
       (map #(->> (re-seq #"[\[ ](.)[\] ]" %)
                  (map (comp first rest))))
       (apply map vector)
       (map (partial filter (partial not= " ")))))

(def moves
  (->> (drop 10 input)
       (map #(->> (re-find #"move (\d+) from (\d+) to (\d+)" %)
                  rest
                  (map (comp dec parse-int))))))

(defn replacement [coll n row]
  (let [[head tail] (split-at n coll)]
    (concat head [row] (rest tail))))

(defn top-crates [crates]
  (->> crates
       (map (comp #(if (nil? %) " " %) first))
       str/join))

(defn permutate-move [crates [n from to]]
  (let [[crane-load nfrom] (split-at (inc n) (nth crates from))
        nto (concat (reverse crane-load) (nth crates to))
        crates'  (replacement crates to nto)
        crates'' (replacement crates' from nfrom)]
    crates''))

(top-crates (reduce permutate-move crates moves))
