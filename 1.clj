(require '[clojure.string :as str])

(def sums
  (->> (slurp "input")
       (#(str/split % #"\n\n"))
       (map str/split-lines)
       (map (partial map #(Integer/parseInt %)))
       (map (partial reduce +))))

(apply max sums)
(->> sums sort reverse (take 3) (reduce +))

