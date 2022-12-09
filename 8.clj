(require '[clojure.string :as str])

(defn parse-int [s]
  (Integer/parseInt s))

(defn str->array [s]
  (->> (str/split-lines s)
       (map (partial map identity))))

(defn rotate [rekt]
  (->> rekt
       (apply map list)
       (map reverse)))

(defn trees-visible [trees]
  (->> trees
       (map #(->> (map (comp inc parse-int str) %)
                  (reductions max)))
       (map #(cons (first %) (map - (take (dec (count %)) %) (rest %))))
       (map (partial map (partial not= 0)))))

(->> (str->array (slurp "input_8"))
     (iterate rotate)
     (take 4)
     (map trees-visible)
     (map #(flatten (nth (iterate rotate %2) %1)) [0 3 2 1])
     (apply map list)
     (map (partial reduce #(or %1 %2)))
     (filter identity)
     count) ; => 1794

(defn part-visible [[head & tail]]
  (->> (take-while (partial > head) tail)
       count
       (#(if (= % (count tail)) % (inc %)))))

(defn line-visible [line]
  (->> line
       (#(take (count %) (iterate rest %)))
       (map part-visible)))

(->> (str->array (slurp "input_8"))
     (map #(->> (map (comp inc parse-int str) %)))
     (iterate rotate)
     (take 4)
     (map (partial map line-visible))
     (map #(flatten (nth (iterate rotate %2) %1)) [0 3 2 1])
     (apply map list)
     (map (partial reduce *))
     (apply max)) ; => 199272
