(defn scan-unique [n i s]
  (if (= n (count (into #{} (take n s))))
    (+ n i)
    (scan-unique n (inc i) (rest s))))

(map #(scan-unique % 0 (slurp "input_6")) [4 14])
