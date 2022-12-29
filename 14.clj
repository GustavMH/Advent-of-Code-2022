(require '[clojure.string :as str])

(defn parse-int [s]
  (Integer/parseInt s))

; Draw lines into a set
(def walls
  (->> (slurp "input_14")
       str/split-lines
       (map #(->> %
                  (re-seq #"(\d+?),(\d+)")
                  (map (comp (partial map parse-int) rest))))
       (map segment-path)
       (mapcat (partial mapcat (partial apply line)))
       (into #{})))

(def boundry
  (second (apply max-key second walls)))

(def bottom-path
  (let [dist (+ 2 boundry)]
    [[(- 500 (* 2 dist)) dist][(+ 500 (* 2 dist)) dist]]))

(def new-walls
  (->> (apply line bottom-path)
       (apply conj walls)))

(defn segment-path [[head next & rest]]
  (if rest
    (cons [head next] (segment-path (cons next rest)))
    [[head next]]))

(defn line [[ax ay] [bx by]]
  (let [dx (- bx ax)
        dy (- by ay)
        mag (max (abs dx) (abs dy))
        dir [(if (zero? dx) 0 (/ dx (abs dx)))
             (if (zero? dy) 0 (/ dy (abs dy)))]]
    (reductions (partial mapv +) [ax ay] (repeat mag dir))))

; Simulate sand particles, until rest
(defn sand-move [boundry walls grain]
  (let [[down left right]
        (map (partial mapv - grain) [[0 -1] [1 -1] [-1 -1]])]
    ;(println boundry (second grain))
    (cond
      ; Stop if the grains are below all walls
      (<= boundry (second grain)) (reduced nil)
      ; Move if there is space
      (not (contains? walls down))  down
      (not (contains? walls left))  left
      (not (contains? walls right)) right
      ; Or rest
      :else (reduced grain))))

; add to set recursively
(defn add-grain [boundry walls grain]
  (->> (reduce (fn [grain _] (sand-move boundry walls grain)) grain (repeat nil))
       (#(if (or (nil? %) (= grain %))
           (reduced walls)
           (conj walls %)))))

(- (count (reduce (partial add-grain boundry) walls (repeat [500 0])))
   (count walls))

(- (count (reduce (partial add-grain (+ boundry 5)) new-walls (repeat [500 0])))
   (count new-walls)
   -1) ;account for missing drain

