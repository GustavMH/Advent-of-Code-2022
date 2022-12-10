(require '[clojure.string :as str])

(defn parse-int [s]
  (Integer/parseInt s))

(def steps
  (->> ;(slurp "input_9")
       "R 4
U 4
L 3
D 1
R 4
D 1
L 5
R 2"
       str/split-lines
       (map (fn [[d _ n]] [d (parse-int (str n))]))))

(def moves {\L [-1 0] \R [1 0] \U [0 1] \D [0 -1]})

(defn step-head [pos dir]
  (map + pos (get moves dir)))

(defn step-tail [[x y] [hx hy]]
  ;(println "Move" (- x hx) (- y hy))
  (if (not (or (< 1 (abs (- x hx)))
               (< 1 (abs (- y hy))))) [x y]
      (if (not= 0 (* (- x hx) (- y hy)))
          (map - [hx hy] (get moves dir))
          (map + [x  y ] (get moves dir)))))

(->> (do-steps steps [0 0] [0 0] #{}) count)

(defn step [[h-pos t-pos ts] dir]
  (let [new-h-pos (step-head h-pos dir)
        new-t-pos (step-tail t-pos new-h-pos dir)]
    ;(println h-pos "->" new-h-pos "," t-pos "->" new-t-pos "in" dir)
    [new-h-pos new-t-pos (conj ts new-t-pos)]))

(defn do-steps [[[dir n] & steps] h-pos t-pos ts]
  (if (not steps) ts
      (let [[new-h-pos new-t-pos new-ts]
            (reduce step [h-pos t-pos ts] (repeat n dir))]
        (do-steps steps new-h-pos new-t-pos new-ts))))
